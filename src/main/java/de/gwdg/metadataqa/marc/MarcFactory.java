package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.json.JsonBranch;
import de.gwdg.metadataqa.api.model.pathcache.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.marc.dao.Control001;
import de.gwdg.metadataqa.marc.dao.Control003;
import de.gwdg.metadataqa.marc.dao.Control005;
import de.gwdg.metadataqa.marc.dao.Control006;
import de.gwdg.metadataqa.marc.dao.Control007;
import de.gwdg.metadataqa.marc.dao.Control008;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;

import de.gwdg.metadataqa.marc.utils.alephseq.AlephseqLine;
import de.gwdg.metadataqa.marc.utils.MapToDatafield;

import de.gwdg.metadataqa.marc.utils.alephseq.MarclineLine;
import de.gwdg.metadataqa.marc.utils.pica.PicaFieldDefinition;
import de.gwdg.metadataqa.marc.utils.pica.PicaLine;
import de.gwdg.metadataqa.marc.utils.pica.PicaSubfield;
import de.gwdg.metadataqa.marc.utils.pica.PicaTagDefinition;
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

  private MarcFactory() {
    throw new IllegalStateException("This is a utility class, can not be instantiated");
  }

  public static MarcRecord create(JsonPathCache cache) {
    return create(cache, MarcVersion.MARC21);
  }

  public static MarcRecord create(JsonPathCache cache, MarcVersion version) {
    var marcRecord = new MarcRecord();
    for (JsonBranch branch : schema.getPaths()) {
      if (branch.getParent() != null)
        continue;
      switch (branch.getLabel()) {
        case "leader":
          marcRecord.setLeader(new Leader(extractFirst(cache, branch)));
          break;
        case "001":
          marcRecord.setControl001(new Control001(extractFirst(cache, branch)));
          break;
        case "003":
          marcRecord.setControl003(new Control003(extractFirst(cache, branch)));
          break;
        case "005":
          marcRecord.setControl005(new Control005(extractFirst(cache, branch), marcRecord));
          break;
        case "006":
          marcRecord.setControl006(
            new Control006(extractFirst(cache, branch), marcRecord));
          break;
        case "007":
          marcRecord.setControl007(
            new Control007(extractFirst(cache, branch), marcRecord));
          break;
        case "008":
          marcRecord.setControl008(
            new Control008(extractFirst(cache, branch), marcRecord));
          break;
        default:
          JSONArray fieldInstances = (JSONArray) cache.getFragment(branch.getJsonPath());
          for (var fieldInsanceNr = 0; fieldInsanceNr < fieldInstances.size(); fieldInsanceNr++) {
            var fieldInstance = (Map) fieldInstances.get(fieldInsanceNr);
            var field = MapToDatafield.parse(fieldInstance, version);
            if (field != null) {
              marcRecord.addDataField(field);
              field.setMarcRecord(marcRecord);
            } else {
              marcRecord.addUnhandledTags(branch.getLabel());
            }
          }
          break;
      }
    }
    return marcRecord;
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
    var marcRecord = new MarcRecord();

    if (marc4jRecord.getLeader() != null) {
      marcRecord.setLeader(new Leader(marc4jRecord.getLeader().marshal(), defaultType));

      if (marcRecord.getType() == null) {
        throw new InvalidParameterException(
          String.format(
            "Error in '%s': no type has been detected. Leader: '%s'.",
            marc4jRecord.getControlNumberField(), marcRecord.getLeader().getLeaderString()
          )
        );
      }
    }

    importMarc4jControlFields(marc4jRecord, marcRecord, fixAlephseq);

    importMarc4jDataFields(marc4jRecord, marcRecord, marcVersion);

    return marcRecord;
  }

  public static MarcRecord createPicaFromMarc4j(Record marc4jRecord, Map<String, PicaFieldDefinition> schemaDirectory) {
    var marcRecord = new MarcRecord();
    marcRecord.setSchemaType(SchemaType.PICA);

    importMarc4jControlFields(marc4jRecord, marcRecord, false);

    importMarc4jDataFields(marc4jRecord, marcRecord, schemaDirectory);

    return marcRecord;
  }

  private static void importMarc4jControlFields(Record marc4jRecord,
                                                MarcRecord marcRecord,
                                                boolean fixAlephseq) {
    for (ControlField controlField : marc4jRecord.getControlFields()) {
      String data = controlField.getData();
      if (fixAlephseq && isFixable(controlField.getTag()))
        data = data.replace("^", " ");
      switch (controlField.getTag()) {
        case "001":
          marcRecord.setControl001(new Control001(data)); break;
        case "003":
          marcRecord.setControl003(new Control003(data)); break;
        case "005":
          marcRecord.setControl005(new Control005(data, marcRecord)); break;
        case "006":
          marcRecord.setControl006(new Control006(data, marcRecord)); break;
        case "007":
          marcRecord.setControl007(new Control007(data, marcRecord)); break;
        case "008":
          marcRecord.setControl008(new Control008(data, marcRecord)); break;
        default:
          break;
      }
    }
  }

  private static boolean isFixable(String tag) {
    return fixableControlFields.contains(tag);
  }

  private static void importMarc4jDataFields(Record marc4jRecord,
                                             MarcRecord marcRecord,
                                             MarcVersion marcVersion) {
    for (org.marc4j.marc.DataField dataField : marc4jRecord.getDataFields()) {
      var definition = getDataFieldDefinition(dataField, marcVersion);
      if (definition == null) {
        marcRecord.addUnhandledTags(dataField.getTag());
      }
      var field = extractDataField(dataField, definition, marcVersion);
      marcRecord.addDataField(field);
    }
  }

  private static void importMarc4jDataFields(Record marc4jRecord,
                                             MarcRecord marcRecord,
                                             Map<String, PicaFieldDefinition> schemaDirectory) {
    for (org.marc4j.marc.DataField dataField : marc4jRecord.getDataFields()) {
      var definition = (schemaDirectory.containsKey(dataField.getTag())) ? schemaDirectory.get(dataField.getTag()) : null;
      if (definition == null) {
        marcRecord.addUnhandledTags(dataField.getTag());
      }
      var field = extractPicaDataField(dataField, definition, MarcVersion.MARC21);
      marcRecord.addDataField(field);
    }
  }

  public static DataFieldDefinition getDataFieldDefinition(org.marc4j.marc.DataField dataField,
                                                           MarcVersion marcVersion) {
    return getDataFieldDefinition(dataField.getTag(), marcVersion);
  }

  public static DataFieldDefinition getDataFieldDefinition(String tag, MarcVersion marcVersion) {
    return TagDefinitionLoader.load(tag, marcVersion);
  }

  private static DataField extractDataField(org.marc4j.marc.DataField dataField,
                                            DataFieldDefinition definition,
                                            MarcVersion marcVersion) {
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
      var code = Character.toString(subfield.getCode());
      SubfieldDefinition subfieldDefinition = definition == null ? null : definition.getSubfield(code);
      MarcSubfield marcSubfield = null;
      if (subfieldDefinition == null) {
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

  private static DataField extractPicaDataField(org.marc4j.marc.DataField dataField,
                                                PicaFieldDefinition definition,
                                                MarcVersion marcVersion) {
    DataField field = null;
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
      var code = Character.toString(subfield.getCode());
      SubfieldDefinition subfieldDefinition = definition == null ? null : definition.getSubfield(code);
      MarcSubfield marcSubfield = null;
      if (subfieldDefinition == null) {
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
    List<XmlFieldInstance> instances = cache.get(branch.getJsonPath());
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

    var marcRecord = new MarcRecord();
    for (String line : lines) {
      if (line.startsWith("LEADER ")) {
        marcRecord.setLeader(line.replace("LEADER ", ""), marcVersion);
      } else {
        var tag = line.substring(0, 3);
        var content = line.substring(4);
        marcRecord.setField(tag, content, marcVersion);
      }
    }
    return marcRecord;
  }

  public static MarcRecord createFromAlephseq(List<AlephseqLine> lines,
                                              MarcVersion marcVersion) {
    if (marcVersion == null)
      marcVersion = MarcVersion.MARC21;

    var marcRecord = new MarcRecord();
    for (AlephseqLine line : lines) {
      if (line.isLeader()) {
        marcRecord.setLeader(line.getContent());
      } else if (line.isNumericTag()) {
        marcRecord.setField(line.getTag(), line.getInd1(), line.getInd2(), line.getContent(), marcVersion);
      }
    }
    return marcRecord;
  }

  public static Record createRecordFromAlephseq(List<AlephseqLine> lines) {
    Record marc4jRecord = new RecordImpl();
    for (AlephseqLine line : lines) {
      if (line.isLeader()) {
        marc4jRecord.setLeader(new LeaderImpl(line.getContent()));
      } else if (line.isNumericTag()) {
        if (line.isControlField()) {
          marc4jRecord.addVariableField(new ControlFieldImpl(line.getTag(), line.getContent()));
        } else {
          var df = new DataFieldImpl(
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
          marc4jRecord.addVariableField(df);
        }
      }
    }
    return marc4jRecord;
  }

  public static Record createRecordFromMarcline(List<MarclineLine> lines) {
    Record marc4jRecord = new RecordImpl();
    for (MarclineLine line : lines) {
      if (line.isLeader()) {
        marc4jRecord.setLeader(new LeaderImpl(line.getContent()));
      } else if (line.isNumericTag()) {
        if (line.isControlField()) {
          marc4jRecord.addVariableField(new ControlFieldImpl(line.getTag(), line.getContent()));
        } else {
          var df = new DataFieldImpl(line.getTag(), line.getInd1().charAt(0), line.getInd2().charAt(0));
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
          marc4jRecord.addVariableField(df);
        }
      }
    }
    return marc4jRecord;
  }

  public static Record createRecordFromPica(List<PicaLine> lines) {
    Record marc4jRecord = new RecordImpl();
    String id = null;
    for (PicaLine line : lines) {
      DataFieldImpl df = new DataFieldImpl(line.getTag(), ' ', ' ');
      for (PicaSubfield picaSubfield : line.getSubfields()) {
        df.addSubfield(new SubfieldImpl(picaSubfield.getCode().charAt(0), picaSubfield.getValue()));
        if (line.getTag().equals("003@") && picaSubfield.getCode().equals("0"))
          id = picaSubfield.getValue();
      }
      marc4jRecord.addVariableField(df);
    }
    if (id != null)
      marc4jRecord.addVariableField(new ControlFieldImpl("001", id));
    return marc4jRecord;
  }
}
