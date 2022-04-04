package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.cli.parameters.MappingParameters;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.controlpositions.ControlfieldPositionList;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.tags.control.*;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control006Positions;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control007Positions;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control008Positions;
import de.gwdg.metadataqa.marc.definition.controlpositions.LeaderPositions;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;
import de.gwdg.metadataqa.marc.utils.keygenerator.PositionalControlFieldKeyGenerator;
import net.minidev.json.JSONValue;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MappingToJson {

  private static final Logger logger = Logger.getLogger(MappingToJson.class.getCanonicalName());

  private boolean exportSubfieldCodes = false;
  private boolean exportSelfDescriptiveCodes = false;
  private Map<String, Object> mapping;
  private final Options options;
  private MappingParameters parameters;

  public MappingToJson(String[] args) throws ParseException {
    parameters = new MappingParameters(args);
    options = parameters.getOptions();

    exportSubfieldCodes = parameters.doExportSubfieldCodes();
    exportSelfDescriptiveCodes = parameters.doExportSelfDescriptiveCodes();

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
    Map<String, Object> fields = new LinkedHashMap<>();

    fields.put("LDR", buildLeader());
    fields.putAll(buildSimpleControlFields());

    fields.put("006", buildControlField(Control006Definition.getInstance(), Control006Positions.getInstance()));
    fields.put("007", buildControlField(Control007Definition.getInstance(), Control007Positions.getInstance()));
    fields.put("008", buildControlField(Control008Definition.getInstance(), Control008Positions.getInstance()));

    for (Class<? extends DataFieldDefinition> tagClass : MarcTagLister.listTags()) {
      try {
        Method getInstance = tagClass.getMethod("getInstance");
        DataFieldDefinition fieldTag = (DataFieldDefinition) getInstance.invoke(tagClass);
        if (!parameters.isWithLocallyDefinedFields() && !fieldTag.getMarcVersion().equals(MarcVersion.MARC21))
          continue;
        dataFieldToJson(fields, fieldTag);
      } catch (NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException e) {
        logger.log(Level.SEVERE, "build", e);
      }
    }
    mapping.put("fields", fields);
  }

  private Map<String, Object> buildControlField(ControlFieldDefinition field, ControlfieldPositionList positionDefinition) {
    Map<String, Object> positions;
    Map<String, Object> tag = new LinkedHashMap<>();
    tag.put("tag", field.getTag());
    tag.put("label", field.getLabel());
    tag.put("repeatable", resolveCardinality(field.getCardinality()));
    Map<String, Object> types = new LinkedHashMap<>();
    PositionalControlFieldKeyGenerator generator = getPositionalControlFieldKeyGenerator(field);
    for (String type : positionDefinition.getPositions().keySet()) {
      Map<String, Object> typeMap = new LinkedHashMap<>();
      positions = new LinkedHashMap<>();
      for (ControlfieldPositionDefinition subfield : positionDefinition.getPositions().get(type)) {
        Map<String, Object> position = controlPositionToJson(subfield, generator);
        String key = (String) position.remove("position");
        positions.put(key, position);
      }
      typeMap.put("positions", positions);
      types.put(type, typeMap);
    }
    tag.put("types", types);
    return tag;
  }

  private Map<String, Object> buildSimpleControlFields() {
    Map fields = new HashMap();
    List<DataFieldDefinition> simpleControlFields = Arrays.asList(
      Control001Definition.getInstance(),
      Control003Definition.getInstance(),
      Control005Definition.getInstance()
    );
    for (DataFieldDefinition field : simpleControlFields)
      fields.put(field.getTag(), buildSImpleControlField(field));

    return fields;
  }

  private Map<String, Object> buildSImpleControlField(DataFieldDefinition field) {
    Map<String, Object> tag;
    PositionalControlFieldKeyGenerator keyGenerator = new PositionalControlFieldKeyGenerator(field.getTag(), field.getMqTag(), parameters.getSolrFieldType());
    tag = new LinkedHashMap<>();
    tag.put("tag", field.getTag());
    tag.put("label", field.getLabel());
    tag.put("repeatable", resolveCardinality(field.getCardinality()));
    if (exportSelfDescriptiveCodes)
      tag.put("solr", keyGenerator.forTag());
    return tag;
  }

  private Map<String, Object> buildLeader() {
    Map<String, Object> tag = new LinkedHashMap<>();
    tag.put("repeatable", false);
    Map<String, Object> positions = new LinkedHashMap<>();

    ControlFieldDefinition field = LeaderDefinition.getInstance();
    PositionalControlFieldKeyGenerator generator = getPositionalControlFieldKeyGenerator(field);

    LeaderPositions leaderSubfields = LeaderPositions.getInstance();
    for (ControlfieldPositionDefinition subfield : leaderSubfields.getPositionList()) {
      Map<String, Object> position = controlPositionToJson(subfield, generator);
      String key = (String) position.remove("position");
      positions.put(key, position);
    }
    tag.put("positions", positions);
    return tag;
  }

  private PositionalControlFieldKeyGenerator getPositionalControlFieldKeyGenerator(ControlFieldDefinition field) {
    PositionalControlFieldKeyGenerator generator = exportSelfDescriptiveCodes
      ? new PositionalControlFieldKeyGenerator(field.getTag(), field.getMqTag(), parameters.getSolrFieldType())
      : null;
    return generator;
  }

  private static Map<String, Object> controlPositionToJson(ControlfieldPositionDefinition subfield, PositionalControlFieldKeyGenerator generator) {
    Map<String, Object> values = new LinkedHashMap<>();
    values.put("position", subfield.formatPositon());
    values.put("label", subfield.getLabel());
    values.put("url", subfield.getDescriptionUrl());
    values.put("start", subfield.getPositionStart());
    values.put("end", subfield.getPositionEnd());
    values.put("repeatableContent", subfield.isRepeatableContent());
    if (subfield.isRepeatableContent()) {
      values.put("unitLength", subfield.getUnitLength());
    }

    if (generator != null)
      values.put("solr", generator.forSubfield(subfield));


    if (subfield.getCodes() != null) {
      LinkedHashMap<String, Object> codes = new LinkedHashMap<>();
      for (EncodedValue code : subfield.getCodes()) {
        Map<String, Object> codeMap = new LinkedHashMap<>();
        // codeMap.put("code", code.getCode());
        codeMap.put("label", code.getLabel());
        codes.put(code.getCode(), codeMap);
      }
      values.put("codes", codes);
    }
    if (subfield.getHistoricalCodes() != null) {
      LinkedHashMap<String, Object> codes = new LinkedHashMap<>();
      for (EncodedValue code : subfield.getHistoricalCodes()) {
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
    DataFieldKeyGenerator keyGenerator = new DataFieldKeyGenerator(tag, parameters.getSolrFieldType());

    Map<String, Object> tagMap = new LinkedHashMap<>();
    tagMap.put("tag", tag.getTag());
    tagMap.put("label", tag.getLabel());
    tagMap.put("url", tag.getDescriptionUrl());
    tagMap.put("repeatable", resolveCardinality(tag.getCardinality()));
    if (exportSelfDescriptiveCodes)
      tagMap.put("solr", keyGenerator.getIndexTag());

    if (parameters.doExportFrbrFunctions())
      extractFunctions(tagMap, tag.getFrbrFunctions());

    if (parameters.doExportCompilanceLevel())
      extractCompilanceLevel(tagMap, tag.getNationalCompilanceLevel(), tag.getMinimalCompilanceLevel());

    if (parameters.isWithLocallyDefinedFields())
      tagMap.put("version", tag.getMarcVersion().getCode());

    tagMap.put("indicator1", indicatorToJson(tag.getInd1()));
    tagMap.put("indicator2", indicatorToJson(tag.getInd2()));

    Map<String, Object> subfields = new LinkedHashMap<>();
    if (tag.getSubfields() != null) {
      for (SubfieldDefinition subfield : tag.getSubfields()) {
        subfields.put(subfield.getCode(), subfieldToJson(subfield, keyGenerator));
      }
    } else {
      logger.info(tag + " does not have subfields");
    }
    tagMap.put("subfields", subfields);

    if (parameters.isWithLocallyDefinedFields()) {
      Map<MarcVersion, List<SubfieldDefinition>> versionSpecificSubfields = tag.getVersionSpecificSubfields();
      if (versionSpecificSubfields != null && !versionSpecificSubfields.isEmpty()) {
        Map<String, Map<String, Object>> versionSpecificSubfieldsMap = new LinkedHashMap<>();
        for (Map.Entry<MarcVersion, List<SubfieldDefinition>> entry : versionSpecificSubfields.entrySet()) {
          String version = entry.getKey().getCode();
          subfields = new LinkedHashMap<>();
          for (SubfieldDefinition subfield : entry.getValue()) {
            subfields.put(subfield.getCode(), subfieldToJson(subfield, keyGenerator));
          }
          versionSpecificSubfieldsMap.put(version, subfields);
        }
        if (!versionSpecificSubfieldsMap.isEmpty())
          tagMap.put("versionSpecificSubfields", versionSpecificSubfieldsMap);
      }
    }

    if (tag.getHistoricalSubfields() != null) {
      subfields = new LinkedHashMap<>();
      for (EncodedValue code : tag.getHistoricalSubfields()) {
        Map<String, Object> labelMap = new LinkedHashMap<>();
        labelMap.put("label", code.getLabel());
        subfields.put(code.getCode(), labelMap);
      }
      tagMap.put("historical-subfields", subfields);
    }

    if (fields.containsKey(tag.getTag())) {
      Object existing = fields.get(tag.getTag());
      List<Map> list = null;
      if (existing instanceof Map) {
        list = new ArrayList<>();
        list.add((Map) existing);
      } else if (existing instanceof List) {
        list = (List) existing;
      } else {
        System.err.println("a strange object: " + existing.getClass().getCanonicalName());
        list = new ArrayList<>();
      }
      list.add(tagMap);
      fields.put(tag.getTag(), list);
    } else {
      fields.put(tag.getTag(), tagMap);
    }
  }

  private void extractFunctions(Map<String, Object> tagMap, List<FRBRFunction> functions) {
    if (functions != null && !functions.isEmpty()) {
      List<String> paths = new ArrayList<>();
      for (FRBRFunction function : functions) {
        paths.add(function.getPath());
      }
      tagMap.put("frbr-functions", paths);
    }
  }

  private Map<String, Object> subfieldToJson(SubfieldDefinition subfield, DataFieldKeyGenerator keyGenerator) {
    Map<String, Object> codeMap = new LinkedHashMap<>();
    codeMap.put("label", subfield.getLabel());
    codeMap.put("repeatable", resolveCardinality(subfield.getCardinality()));

    if (exportSelfDescriptiveCodes)
      codeMap.put("solr", keyGenerator.forSubfield(subfield));

    if (subfield.getCodeList() != null
        && !subfield.getCodeList().getCodes().isEmpty()) {
      CodeList codeList = subfield.getCodeList();
      Map<String, Object> meta = new LinkedHashMap<>();
      meta.put("name", codeList.getName());
      meta.put("url", codeList.getUrl());

      if (exportSubfieldCodes && !codeList.getName().equals("MARC Organization Codes")) {
        if (subfield.getCodeList() != null) {
          Map<String, Object> codes = new LinkedHashMap<>();
          for (EncodedValue code : subfield.getCodeList().getCodes()) {
            Map<String, Object> codeListMap = new LinkedHashMap<>();
            codeListMap.put("label", code.getLabel());
            codes.put(code.getCode(), codeListMap);
          }
          meta.put("codes", codes);
        }
      }
      codeMap.put("codelist", meta);
    }

    if (parameters.doExportFrbrFunctions())
      extractFunctions(codeMap, subfield.getFrbrFunctions());

    if (parameters.doExportCompilanceLevel())
      extractCompilanceLevel(codeMap, subfield.getNationalCompilanceLevel(), subfield.getMinimalCompilanceLevel());

    return codeMap;
  }

  private void extractCompilanceLevel(Map<String, Object> codeMap,
                                      CompilanceLevel nationalCompilanceLevel,
                                      CompilanceLevel minimalCompilanceLevel) {
    Map<String, String> levels = new LinkedHashMap<>();
    if (nationalCompilanceLevel != null)
      levels.put("national", nationalCompilanceLevel.getLabel());

    if (minimalCompilanceLevel != null)
      levels.put("minimal", minimalCompilanceLevel.getLabel());

    if (!levels.isEmpty())
      codeMap.put("compilance-level", levels);
  }

  private static Map<String, Object> indicatorToJson(Indicator indicator) {
    if (!indicator.exists()) {
      return null;
    }
    Map<String, Object> value = new LinkedHashMap<>();
    value.put("label", indicator.getLabel());
    Map<String, Object> codes = new LinkedHashMap<>();
    for (EncodedValue code : indicator.getCodes()) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("label", code.getLabel());
      codes.put(code.getCode(), map);
    }
    value.put("codes", codes);
    if (indicator.getHistoricalCodes() != null) {
      codes = new LinkedHashMap<>();
      for (EncodedValue code : indicator.getHistoricalCodes()) {
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

  public static void main(String[] args) throws ParseException {
    MappingToJson mapping = new MappingToJson(args);
    mapping.build();
    System.out.println(mapping.toJson());
  }
}
