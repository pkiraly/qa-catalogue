package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.json.JsonBranch;
import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import de.gwdg.metadataqa.marc.utils.MapToDatafield;
import net.minidev.json.JSONArray;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Factory class to create MarcRecord from JsonPathCache
 */
public class MarcFactory {

	private static final Logger logger = Logger.getLogger(MarcFactory.class.getCanonicalName());

	private static Schema schema = new MarcJsonSchema();

	public static MarcRecord create(JsonPathCache cache) {
		MarcRecord record = new MarcRecord();
		for (JsonBranch branch : schema.getPaths()) {
			if (branch.getParent() != null)
				continue;
			switch (branch.getLabel()) {
				case "leader":
					record.setLeader(new Leader(extractFirst(cache, branch)));
					break;
				case "001":
					record.setControl001(new Control001(extractFirst(cache, branch)));
					break;
				case "003":
					record.setControl003(new Control003(extractFirst(cache, branch)));
					break;
				case "005":
					record.setControl005(new Control005(extractFirst(cache, branch)));
					break;
				case "006":
					record.setControl006(
						new Control006(extractFirst(cache, branch), record.getType()));
					break;
				case "007":
					record.setControl007(new Control007(extractFirst(cache, branch)));
					break;
				case "008":
					record.setControl008(
						new Control008(extractFirst(cache, branch), record.getType()));
					break;
				default:
					JSONArray fieldInstances = (JSONArray) cache.getFragment(branch.getJsonPath());
					for (int fieldInsanceNr = 0; fieldInsanceNr < fieldInstances.size();
						  fieldInsanceNr++) {
						Map fieldInstance = (Map) fieldInstances.get(fieldInsanceNr);
						DataField field = MapToDatafield.parse(fieldInstance);
						if (field != null) {
							record.addDataField(field);
							field.setRecord(record);
						} else {
							record.addUnhandledTags(branch.getLabel());
						}
					}
					break;
			}
		}
		return record;
	}

	public static MarcRecord createFromMarc4j(Record marc4jRecord) {
		return createFromMarc4j(marc4jRecord, null, null);
	}

	public static MarcRecord createFromMarc4j(Record marc4jRecord, Leader.Type defaultType) {
		return createFromMarc4j(marc4jRecord, defaultType, null);
	}

	public static MarcRecord createFromMarc4j(Record marc4jRecord, MarcVersion marcVersion) {
		return createFromMarc4j(marc4jRecord, null, marcVersion);
	}

	public static MarcRecord createFromMarc4j(Record marc4jRecord, Leader.Type defaultType, MarcVersion marcVersion) {
		MarcRecord record = new MarcRecord();

		if (defaultType == null)
			record.setLeader(new Leader(marc4jRecord.getLeader().marshal()));
		else
			record.setLeader(new Leader(marc4jRecord.getLeader().marshal(), defaultType));

		if (record.getType() == null) {
			throw new InvalidParameterException(
				String.format(
				"Error in '%s': no type has been detected. Leader: '%s'",
					marc4jRecord.getControlNumberField(), record.getLeader().getLeaderString()));
		}

		importMarc4jControlFields(marc4jRecord, record);

		importMarc4jDataFields(marc4jRecord, record, marcVersion);

		return record;
	}

	private static void importMarc4jControlFields(Record marc4jRecord, MarcRecord record) {
		for (ControlField controlField : marc4jRecord.getControlFields()) {
			String data = controlField.getData();
			switch (controlField.getTag()) {
				case "001":
					record.setControl001(new Control001(data)); break;
				case "003":
					record.setControl003(new Control003(data)); break;
				case "005":
					record.setControl005(new Control005(data)); break;
				case "006":
					record.setControl006(new Control006(data, record.getType())); break;
				case "007":
					record.setControl007(new Control007(data)); break;
				case "008":
					record.setControl008(new Control008(data, record.getType())); break;
			}
		}
	}

	private static void importMarc4jDataFields(Record marc4jRecord, MarcRecord record, MarcVersion marcVersion) {
		for (org.marc4j.marc.DataField dataField : marc4jRecord.getDataFields()) {
			DataFieldDefinition definition = getDataFieldDefinition(dataField, marcVersion);
			if (definition == null) {
				record.addUnhandledTags(dataField.getTag());
			} else {
				DataField field = extractDataField(dataField, definition, record.getControl001().getContent());
				record.addDataField(field);
			}
		}
	}

	private static DataFieldDefinition getDataFieldDefinition(org.marc4j.marc.DataField dataField, MarcVersion marcVersion) {
		DataFieldDefinition definition = null;
		if (marcVersion == null)
			definition = TagDefinitionLoader.load(dataField.getTag());
		else
			definition = TagDefinitionLoader.load(dataField.getTag(), marcVersion);
		return definition;
	}

	private static DataField extractDataField(org.marc4j.marc.DataField dataField,
															DataFieldDefinition definition,
															String identifier) {
		DataField field = new DataField(definition, Character.toString(dataField.getIndicator1()), Character.toString(dataField.getIndicator2()));
		for (Subfield subfield : dataField.getSubfields()) {
			String code = Character.toString(subfield.getCode());
			SubfieldDefinition subfieldDefinition = definition.getSubfield(code);
			if (subfieldDefinition == null) {
				// if (!(definition.getTag().equals("886") && code.equals("k")))
					// field.addUnhandledSubfields(code);
					/*
					logger.warning(String.format(
						"Problem in record '%s': %s$%s is not a valid subfield (value: '%s')",
						identifier, definition.getTag(), code, subfield.getData()));
					*/
				MarcSubfield marcSubfield = new MarcSubfield(null, code, subfield.getData());
				marcSubfield.setField(field);
				field.getSubfields().add(marcSubfield);
			} else {
				field.getSubfields().add(new MarcSubfield(subfieldDefinition, code, subfield.getData()));
			}
		}
		field.indexSubfields();
		return field;
	}

	private static List<String> extractList(JsonPathCache cache, JsonBranch branch) {
		List<XmlFieldInstance> instances = (List<XmlFieldInstance>) cache.get(branch.getJsonPath());
		List<String> values = new ArrayList<>();
		if (instances != null)
			for (XmlFieldInstance instance : instances)
				values.add(instance.getValue());
		return values;
	}

	private static String extractFirst(JsonPathCache cache, JsonBranch branch) {
		List<String> list = extractList(cache, branch);
		if (!list.isEmpty())
			return list.get(0);
		return null;
	}
}
