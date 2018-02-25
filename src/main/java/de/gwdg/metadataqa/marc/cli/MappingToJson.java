package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.*;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;
import net.minidev.json.JSONValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MappingToJson {

	private boolean exportSubfieldCodes = false;
	private static List<String> nonMarc21TagLibraries = Arrays.asList(
		"oclctags", "fennicatags", "dnbtags", "sztetags", "genttags",
		"holdings"
	);
	private Map<String, Object> mapping;

	public MappingToJson() {
		mapping = new LinkedHashMap<>();
		mapping.put("$schema", "https://format.gbv.de/schema/avram/schema.json");
		mapping.put("title", "MARC 21 Format for Bibliographic Data.");
		mapping.put("description", "MARC 21 Format for Bibliographic Data.");
		mapping.put("url", "https://www.loc.gov/marc/bibliographic/");
	}

	public void setExportSubfieldCodes(boolean exportSubfieldCodes) {
		this.exportSubfieldCodes = exportSubfieldCodes;
	}

	public String toJson() {
		return JSONValue.toJSONString(mapping);
	}

	public void build() {
		Map fields = new LinkedHashMap<>();

		Map<String, Object> tag = new LinkedHashMap<>();
		tag.put("repeatable", false);
		Map<String, Object> positions = new LinkedHashMap<>();
		for (ControlSubfield subfield : LeaderSubfields.getSubfields()) {
			Map<String, Object> position = controlSubfieldToJson(subfield);
			String key = (String) position.remove("position");
			positions.put(key, position);
		}
		tag.put("positions", positions);
		fields.put("LDR", tag);

		tag = new LinkedHashMap<>();
		tag.put("tag", Control001.getTag());
		tag.put("label", Control001.getLabel());
		tag.put("repeatable", resolveCardinality(Control001.getCardinality()));
		fields.put("001", tag);

		tag = new LinkedHashMap<>();
		tag.put("tag", Control003.getTag());
		tag.put("label", Control003.getLabel());
		tag.put("repeatable", resolveCardinality(Control003.getCardinality()));
		fields.put("003", tag);

		tag = new LinkedHashMap<>();
		tag.put("tag", Control005.getTag());
		tag.put("label", Control005.getLabel());
		tag.put("repeatable", resolveCardinality(Control005.getCardinality()));
		fields.put("005", tag);

		tag = new LinkedHashMap<>();
		tag.put("tag", Control006.getTag());
		tag.put("label", Control006.getLabel());
		tag.put("repeatable", resolveCardinality(Control006.getCardinality()));
		Map<String, Object> types = new LinkedHashMap<>();
		for (Control008Type type : Control006Subfields.getSubfields().keySet()) {
			Map<String, Object> typeMap = new LinkedHashMap<>();
			positions = new LinkedHashMap<>();
			for (ControlSubfield subfield : Control006Subfields.getSubfields().get(type)) {
				Map<String, Object> position = controlSubfieldToJson(subfield);
				String key = (String) position.remove("position");
				positions.put(key, position);
			}
			typeMap.put("positions", positions);
			types.put(type.getValue(), typeMap);
		}
		tag.put("types", types);
		fields.put("006", tag);

		tag = new LinkedHashMap<>();
		tag.put("tag", Control007.getTag());
		tag.put("label", Control007.getLabel());
		tag.put("repeatable", resolveCardinality(Control007.getCardinality()));
		types = new LinkedHashMap<>();
		for (Control007Category category : Control007Subfields.getSubfields().keySet()) {
			Map<String, Object> typeMap = new LinkedHashMap<>();
			positions = new LinkedHashMap<>();
			for (ControlSubfield subfield : Control007Subfields.getSubfields().get(category)) {
				Map<String, Object> position = controlSubfieldToJson(subfield);
				String key = (String) position.remove("position");
				positions.put(key, position);
			}
			typeMap.put("positions", positions);
			types.put(category.getLabel(), typeMap);
		}
		tag.put("types", types);
		fields.put("007", tag);

		tag = new LinkedHashMap<>();
		tag.put("tag", Control008.getTag());
		tag.put("label", Control008.getLabel());
		tag.put("repeatable", resolveCardinality(Control008.getCardinality()));
		types = new LinkedHashMap<>();
		for (Control008Type type : Control008Subfields.getSubfields().keySet()) {
			Map<String, Object> typeMap = new LinkedHashMap<>();
			positions = new LinkedHashMap<>();
			for (ControlSubfield subfield : Control008Subfields.getSubfields().get(type)) {
				Map<String, Object> position = controlSubfieldToJson(subfield);
				String key = (String) position.remove("position");
				positions.put(key, position);
			}
			typeMap.put("positions", positions);
			types.put(type.getValue(), typeMap);
		}
		tag.put("types", types);
		fields.put("008", tag);

		for (Class<? extends DataFieldDefinition> tagClass : MarcTagLister.listTags()) {
			if (isNonMarc21Tag(tagClass))
				continue;

			Method getInstance;
			DataFieldDefinition fieldTag;
			try {
				getInstance = tagClass.getMethod("getInstance");
				fieldTag = (DataFieldDefinition) getInstance.invoke(tagClass);
				dataFieldToJson(fields, fieldTag);
			} catch (NoSuchMethodException
				| IllegalAccessException
				| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		mapping.put("fields", fields);
	}

	private static boolean isNonMarc21Tag(Class<? extends DataFieldDefinition> tagClass) {
		boolean isNonMarc21Tag = false;
		for (String nonCore : nonMarc21TagLibraries) {
			if (tagClass.getCanonicalName().contains(nonCore)) {
				isNonMarc21Tag = true;
				break;
			}
		}
		return isNonMarc21Tag;
	}

	private static Map<String, Object> controlSubfieldToJson(ControlSubfield subfield) {
		Map<String, Object> values = new LinkedHashMap<>();
		values.put("position", subfield.formatPositon());
		values.put("label", subfield.getLabel());
		values.put("url", subfield.getDescriptionUrl());

		if (subfield.getCodes() != null) {
			LinkedHashMap<String, Object> codes = new LinkedHashMap<>();
			for (Code code : subfield.getCodes()) {
				Map<String, Object> codeMap = new LinkedHashMap<>();
				// codeMap.put("code", code.getCode());
				codeMap.put("label", code.getLabel());
				codes.put(code.getCode(), codeMap);
			}
			values.put("codes", codes);
		}
		if (subfield.getHistoricalCodes() != null) {
			LinkedHashMap<String, Object> codes = new LinkedHashMap<>();
			for (Code code : subfield.getHistoricalCodes()) {
				Map<String, Object> codeMap = new LinkedHashMap<>();
				// codeMap.put("code", code.getCode());
				codeMap.put("label", code.getLabel());
				codes.put(code.getCode(), codeMap);
			}
			values.put("historical-codes", codes);
		}
		return values;
	}

	private void dataFieldToJson(Map fields, DataFieldDefinition tag) {
		Map<String, Object> tagMap = new LinkedHashMap<>();
		tagMap.put("tag", tag.getTag());
		tagMap.put("label", tag.getLabel());
		tagMap.put("url", tag.getDescriptionUrl());
		tagMap.put("repeatable", resolveCardinality(tag.getCardinality()));
		tagMap.put("indicator1", indicatorToJson(tag.getInd1()));
		tagMap.put("indicator2", indicatorToJson(tag.getInd2()));

		Map<String, Object> subfields = new LinkedHashMap<>();
		for (SubfieldDefinition subfield : tag.getSubfields()) {
			subfields.put(subfield.getCode(), subfieldToJson(subfield));
		}
		tagMap.put("subfields", subfields);

		if (tag.getHistoricalSubfields() != null) {
			subfields = new LinkedHashMap<>();
			for (Code code : tag.getHistoricalSubfields()) {
				Map<String, Object> labelMap = new LinkedHashMap<>();
				labelMap.put("label", code.getLabel());
				subfields.put(code.getCode(), labelMap);
			}
			tagMap.put("historical-subfields", subfields);
		}

		fields.put(tag.getTag(), tagMap);
	}

	private Map<String, Object> subfieldToJson(SubfieldDefinition subfield) {
		Map<String, Object> codeMap = new LinkedHashMap<>();
		codeMap.put("label", subfield.getLabel());
		codeMap.put("repeatable", resolveCardinality(subfield.getCardinality()));

		if (subfield.getCodeList() != null
		    && !subfield.getCodeList().getCodes().isEmpty()) {
			CodeList codeList = subfield.getCodeList();
			Map<String, Object> meta = new LinkedHashMap<>();
			meta.put("name", codeList.getName());
			meta.put("url", codeList.getUrl());
			if (exportSubfieldCodes
			    && !codeList.getName().equals("MARC Organization Codes")) {
				Map<String, Object> codes = new LinkedHashMap<>();
				for (Code code : subfield.getCodeList().getCodes()) {
					Map<String, Object> codeListMap = new LinkedHashMap<>();
					codeListMap.put("label", code.getLabel());
					codes.put(code.getCode(), codeListMap);
				}
				meta.put("codes", codes);
			}
			codeMap.put("codelist", meta);
		}
		return codeMap;
	}

	private static Map<String, Object> indicatorToJson(Indicator indicator) {
		if (!indicator.exists()) {
			return null;
		}
		Map<String, Object> value = new LinkedHashMap<>();
		value.put("label", indicator.getLabel());
		Map<String, Object> codes = new LinkedHashMap<>();
		for (Code code : indicator.getCodes()) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("label", code.getLabel());
			codes.put(code.getCode(), map);
		}
		value.put("codes", codes);
		if (indicator.getHistoricalCodes() != null) {
			codes = new LinkedHashMap<>();
			for (Code code : indicator.getHistoricalCodes()) {
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("label", code.getLabel());
				codes.put(code.getCode(), map);
			}
			value.put("historical-codes", codes);
		}
		return value;
	}

	private boolean resolveCardinality(Cardinality cardinality) {
		return cardinality.getCode().equals("R");
	}

	public static void main(String[] args) {
		boolean exportSubfieldCodes = (args.length > 0 && args[0].equals("--with-subfield-codelists"));

		MappingToJson mapping = new MappingToJson();
		mapping.setExportSubfieldCodes(exportSubfieldCodes);
		mapping.build();
		System.out.println(mapping.toJson());
	}
}
