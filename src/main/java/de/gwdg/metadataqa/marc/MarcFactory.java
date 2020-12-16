package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.json.JsonBranch;
import de.gwdg.metadataqa.api.model.pathcache.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import de.gwdg.metadataqa.marc.definition.tags.control.Control001Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control003Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control005Definition;

import de.gwdg.metadataqa.marc.utils.alephseq.AlephseqLine;
import de.gwdg.metadataqa.marc.utils.MapToDatafield;

import de.gwdg.metadataqa.marc.utils.pica.PicaLine;
import net.minidev.json.JSONArray;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.impl.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Factory class to create MarcRecord from JsonPathCache
 */
public class MarcFactory {

  private static final Logger logger = Logger.getLogger(MarcFactory.class.getCanonicalName());
  private static final List<String> fixableControlFields = Arrays.asList("006", "007", "008");

  private static Schema schema = new MarcJsonSchema();

  public static MarcRecord create(JsonPathCache cache) {
    return create(cache, MarcVersion.MARC21);
  }

  public static MarcRecord create(JsonPathCache cache, MarcVersion version) {
    MarcRecord record = new MarcRecord();
    for (JsonBranch branch : schema.getPaths()) {
      if (branch.getParent() != null)
        continue;
      switch (branch.getLabel()) {
        case "leader":
          record.setLeader(new Leader(extractFirst(cache, branch)));
          break;
        case "001":
          record.setControl001(new MarcControlField(Control001Definition.getInstance(), extractFirst(cache, branch)));
          break;
        case "003":
          record.setControl003(new MarcControlField(Control003Definition.getInstance(), extractFirst(cache, branch)));
          break;
        case "005":
          record.setControl005(new MarcControlField(Control005Definition.getInstance(), extractFirst(cache, branch)));
          break;
        case "006":
          record.setControl006(
            new Control006(extractFirst(cache, branch), record.getType()));
          break;
        case "007":
          record.setControl007(
            new Control007(extractFirst(cache, branch)));
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
            DataField field = MapToDatafield.parse(fieldInstance, version);
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
    return createFromMarc4j(marc4jRecord, null, MarcVersion.MARC21);
  }

  public static MarcRecord createFromMarc4j(Record marc4jRecord,
                              Leader.Type defaultType) {
    return createFromMarc4j(marc4jRecord, defaultType, MarcVersion.MARC21);
  }

  public static MarcRecord createFromMarc4j(Record marc4jRecord,
                              MarcVersion marcVersion) {
    return createFromMarc4j(marc4jRecord, null, marcVersion);
  }

  public static MarcRecord createFromMarc4j(Record marc4jRecord,
                              Leader.Type defaultType,
                              MarcVersion marcVersion) {
    return createFromMarc4j(marc4jRecord, defaultType, marcVersion, false);
  }

  /**
   * Create a MarcRecord object from Marc4j object
   * @param marc4jRecord The Marc4j record
   * @param defaultType The defauld document type
   * @param marcVersion The MARC version
   * @param fixAlephseq Replace ^ character to space in control fields
   * @return
   */
  public static MarcRecord createFromMarc4j(Record marc4jRecord,
                              Leader.Type defaultType,
                              MarcVersion marcVersion,
                              boolean fixAlephseq) {
    MarcRecord record = new MarcRecord();

    record.setLeader(new Leader(marc4jRecord.getLeader().marshal(), defaultType));

    if (record.getType() == null) {
      throw new InvalidParameterException(
        String.format(
          "Error in '%s': no type has been detected. Leader: '%s'.",
          marc4jRecord.getControlNumberField(), record.getLeader().getLeaderString()
        )
      );
    }

    importMarc4jControlFields(marc4jRecord, record, fixAlephseq);

    importMarc4jDataFields(marc4jRecord, record, marcVersion);

    return record;
  }

  private static void importMarc4jControlFields(Record marc4jRecord,
                                 MarcRecord record,
                                 boolean fixAlephseq) {
    for (ControlField controlField : marc4jRecord.getControlFields()) {
      String data = controlField.getData();
      if (fixAlephseq && isFixable(controlField.getTag()))
        data = data.replace("^", " ");
      switch (controlField.getTag()) {
        case "001":
          record.setControl001(new MarcControlField(
            Control001Definition.getInstance(), data)); break;
        case "003":
          record.setControl003(new MarcControlField(
            Control003Definition.getInstance(), data)); break;
        case "005":
          record.setControl005(new MarcControlField(
            Control005Definition.getInstance(), data)); break;
        case "006":
          record.setControl006(new Control006(data, record.getType())); break;
        case "007":
          record.setControl007(new Control007(record, data)); break;
        case "008":
          record.setControl008(new Control008(data, record.getType())); break;
        default:
          break;
      }
    }
  }

  private static boolean isFixable(String tag) {
    return fixableControlFields.contains(tag);
  }

  private static void importMarc4jDataFields(Record marc4jRecord, MarcRecord record, MarcVersion marcVersion) {
    for (org.marc4j.marc.DataField dataField : marc4jRecord.getDataFields()) {
      DataFieldDefinition definition = getDataFieldDefinition(dataField, marcVersion);
      if (definition == null) {
        record.addUnhandledTags(dataField.getTag());
      }
      DataField field = extractDataField(dataField, definition, marcVersion, record.getControl001().getContent());
      record.addDataField(field);
    }
  }

  public static DataFieldDefinition getDataFieldDefinition(org.marc4j.marc.DataField dataField, MarcVersion marcVersion) {
    return getDataFieldDefinition(dataField.getTag(), marcVersion);
  }

  public static DataFieldDefinition getDataFieldDefinition(String tag, MarcVersion marcVersion) {
    return TagDefinitionLoader.load(tag, marcVersion);
  }

  private static DataField extractDataField(org.marc4j.marc.DataField dataField,
                                            DataFieldDefinition definition,
                                            MarcVersion marcVersion,
                                            String identifier) {
    DataField field;
    if (definition == null) {
      field = new DataField(dataField.getTag(),
              Character.toString(dataField.getIndicator1()),
              Character.toString(dataField.getIndicator2()),
              marcVersion
      );
    } else {
      field = new DataField(
              definition,
              Character.toString(dataField.getIndicator1()),
              Character.toString(dataField.getIndicator2())
      );
    }
    for (Subfield subfield : dataField.getSubfields()) {
      String code = Character.toString(subfield.getCode());
      SubfieldDefinition subfieldDefinition = definition == null ? null : definition.getSubfield(code);
      MarcSubfield marcSubfield = null;
      if (subfieldDefinition == null) {
        // if (!(definition.getTag().equals("886") && code.equals("k")))
        // field.addUnhandledSubfields(code);
        /*
        logger.warning(String.format(
          "Problem in record '%s': %s$%s is not a valid subfield (value: '%s')",
          identifier, definition.getTag(), code, subfield.getData()));
        */
        marcSubfield = new MarcSubfield(null, code, subfield.getData());
      } else {
        marcSubfield = new MarcSubfield(subfieldDefinition, code, subfield.getData());
      }
      marcSubfield.setField(field);
      field.getSubfields().add(marcSubfield);
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

  public static MarcRecord createFromFormattedText(String marcRecordAsText) {
    return createFromFormattedText(Arrays.asList(marcRecordAsText.split("\n")));
  }

  public static MarcRecord createFromFormattedText(String marcRecordAsText, MarcVersion marcVersion) {
    return createFromFormattedText(Arrays.asList(marcRecordAsText.split("\n")), marcVersion);
  }

  public static MarcRecord createFromFormattedText(List<String> lines) {
    return createFromFormattedText(lines, MarcVersion.MARC21);
  }

  public static MarcRecord createFromFormattedText(List<String> lines, MarcVersion marcVersion) {
    if (marcVersion == null)
      marcVersion = MarcVersion.MARC21;

    MarcRecord record = new MarcRecord();
    for (String line : lines) {
      if (line.startsWith("LEADER ")) {
        record.setLeader(line.replace("LEADER ", ""), marcVersion);
      } else {
        String tag = line.substring(0, 3);
        String content = line.substring(4);
        record.setField(tag, content, marcVersion);
      }
    }
    return record;
  }

  public static MarcRecord createFromAlephseq(List<AlephseqLine> lines, MarcVersion marcVersion) {
    if (marcVersion == null)
      marcVersion = MarcVersion.MARC21;

    MarcRecord record = new MarcRecord();
    for (AlephseqLine line : lines) {
      if (line.isLeader()) {
        record.setLeader(line.getContent());
      } else if (line.isNumericTag()) {
        record.setField(line.getTag(), line.getInd1(), line.getInd2(), line.getContent(), marcVersion);
      }
    }
    return record;
  }

  public static Record createRecordFromAlephseq(List<AlephseqLine> lines) {
    Record record = new RecordImpl();
    boolean deleted = false;
    for (AlephseqLine line : lines) {
      if (line.isLeader()) {
        record.setLeader(new LeaderImpl(line.getContent()));
      } else if (line.isNumericTag()) {
        if (line.isControlField()) {
          record.addVariableField(new ControlFieldImpl(line.getTag(), line.getContent()));
        } else {
          DataFieldImpl df = new DataFieldImpl(
            line.getTag(), line.getInd1().charAt(0), line.getInd2().charAt(0)
          );
          for (String[] pair : line.parseSubfields()) {
            if (pair.length == 2 && pair[0] != null && pair[1] != null) {
              df.addSubfield(new SubfieldImpl(pair[0].charAt(0), pair[1]));
            } else {
              logger.warning(String.format(
                "parse error in record #%s) tag %s: '%s'",
                line.getRecordID(), line.getTag(), line.getRawContent()
              ));
            }
          }
          record.addVariableField(df);
        }
      }
    }
    return record;
  }

  public static Record createRecordFromPica(List<PicaLine> lines) {
    Record record = new RecordImpl();
    for (PicaLine line : lines) {
      if (line.isLeader()) {
        record.setLeader(new LeaderImpl(line.getContent()));
      } else if (line.isNumericTag()) {
        if (line.isControlField()) {
          record.addVariableField(new ControlFieldImpl(line.getTag(), line.getContent()));
        } else {
          /*
          DataFieldImpl df = new DataFieldImpl(line.getTag(), line.getInd1().charAt(0), line.getInd2().charAt(0));
          for (String[] pair : line.parseSubfields()) {
            if (pair.length == 2 && pair[0] != null && pair[1] != null) {
              df.addSubfield(new SubfieldImpl(pair[0].charAt(0), pair[1]));
            } else {
              logger.warning(String.format(
                "parse error in record #%s) tag %s: '%s'",
                line.getRecordID(), line.getTag(), line.getRawContent()
              ));
            }
          }
          record.addVariableField(df);
           */
        }
      }
    }
    return record;
  }
}
