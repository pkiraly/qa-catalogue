package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.cli.parameters.MappingParameters;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.CompilanceLevel;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.controlpositions.ControlfieldPositionList;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control006Positions;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control007Positions;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control008Positions;
import de.gwdg.metadataqa.marc.definition.controlpositions.LeaderPositions;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;
import de.gwdg.metadataqa.marc.definition.tags.control.Control001Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control003Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control005Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control006Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control007Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control008Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.LeaderDefinition;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;
import de.gwdg.metadataqa.marc.utils.keygenerator.PositionalControlFieldKeyGenerator;
import net.minidev.json.JSONValue;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MappingToJson {

  private static final Logger logger = Logger.getLogger(MappingToJson.class.getCanonicalName());

  private boolean exportSubfieldCodes = false;
  private boolean exportSelfDescriptiveCodes = false;
  private Map<String, Object> mapping;
  private final Options options;
  private MappingParameters parameters;
  private Map<String, List<EncodedValue>> referencesCodeLists;

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

    referencesCodeLists = new LinkedHashMap<>();

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

    Map<String, Object> codelists = new LinkedHashMap<>();
    for (Map.Entry<String, List<EncodedValue>> entry : referencesCodeLists.entrySet()) {
      String url = entry.getKey();
      Map<String, Map<String, Object>> codes = new LinkedHashMap<>();
      for (EncodedValue code : entry.getValue()) {
        codes.put(code.getCode(), Map.of("label", code.getLabel()));
        if (code.getRange() != null)
          codes.get(code.getCode()).put("range", code.getRange());
      }
      codelists.put(url, codes);
    }
    mapping.put("codelists", codelists);

    // System.err.println(referencesCodeLists.keySet());
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
    return exportSelfDescriptiveCodes
      ? new PositionalControlFieldKeyGenerator(field.getTag(), field.getMqTag(), parameters.getSolrFieldType())
      : null;
  }

  private static Map<String, Object> controlPositionToJson(ControlfieldPositionDefinition subfield, PositionalControlFieldKeyGenerator generator) {
    Map<String, Object> values = new LinkedHashMap<>();
    values.put("position", subfield.formatPositon());
    values.put("label", subfield.getLabel());
    values.put("url", subfield.getDescriptionUrl());
    values.put("start", subfield.getPositionStart());
    values.put("end", subfield.getPositionEnd() - 1);
    values.put("repeatableContent", subfield.isRepeatableContent());
    if (subfield.isRepeatableContent()) {
      values.put("unitLength", subfield.getUnitLength());
    }

    if (generator != null)
      values.put("solr", generator.forSubfield(subfield));

    LinkedHashMap<String, Object> codes = new LinkedHashMap<>();
    if (subfield.getCodes() != null) {
      for (EncodedValue code : subfield.getCodes()) {
        Map<String, Object> codeMap = new LinkedHashMap<>();
        codeMap.put("code", code.getCode());
        codeMap.put("label", code.getLabel());
        codes.put(code.getCode(), codeMap);
      }
    }
    if (subfield.getHistoricalCodes() != null) {
      for (EncodedValue code : subfield.getHistoricalCodes()) {
        Map<String, Object> codeMap = new LinkedHashMap<>();
        codeMap.put("code", code.getCode());
        codeMap.put("label", code.getLabel());
        codeMap.put("deprecated", true);
        codes.put(code.getCode(), codeMap);
      }
    }
    if (codes.size() > 0) {
      values.put("codes", codes);
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

    if (tag.getHistoricalSubfields() != null) {
      for (EncodedValue code : tag.getHistoricalSubfields()) {
        Map<String, Object> subfieldDefinition = new LinkedHashMap<>();
        subfieldDefinition.put("label", code.getLabel());
        subfieldDefinition.put("deprecated", true);
        subfields.put(code.getCode(), subfieldDefinition);
      }
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

    if (subfield.getContentParser() != null && subfield.getContentParser() instanceof LinkageParser)
      codeMap.put("pattern", ((LinkageParser)subfield.getContentParser()).getPattern());

    if (subfield.getContentParser() != null && subfield.getContentParser() instanceof RecordControlNumberParser)
      codeMap.put("pattern", ((RecordControlNumberParser)subfield.getContentParser()).getPattern());

    if (subfield.getValidator() != null && subfield.getValidator() instanceof RegexValidator)
      codeMap.put("pattern", ((RegexValidator)subfield.getValidator()).getPattern());

    if (subfield.getCodeList() != null
        && !subfield.getCodeList().getCodes().isEmpty()) {
      CodeList codeList = subfield.getCodeList();
      referencesCodeLists.put(codeList.getUrl(), codeList.getCodes());
      codeMap.put("codes", codeList.getUrl());

      /*
      Map<String, Object> meta = new LinkedHashMap<>();
      meta.put("name", codeList.getName());
      meta.put("url", codeList.getUrl());

      if (exportSubfieldCodes
          && !codeList.getName().equals("MARC Organization Codes")
          && subfield.getCodeList() != null) {
        Map<String, Object> codes = new LinkedHashMap<>();
        for (EncodedValue code : subfield.getCodeList().getCodes()) {
          Map<String, Object> codeListMap = new LinkedHashMap<>();
          codeListMap.put("label", code.getLabel());
          codes.put(code.getCode(), codeListMap);
        }
        meta.put("codes", codes);
      }
      codeMap.put("codelist", meta);
       */
    }

    if (subfield.hasPositions())
      codeMap.put("positions", getSubfieldPositions(subfield));

    if (parameters.doExportFrbrFunctions())
      extractFunctions(codeMap, subfield.getFrbrFunctions());

    if (parameters.doExportCompilanceLevel())
      extractCompilanceLevel(codeMap, subfield.getNationalCompilanceLevel(), subfield.getMinimalCompilanceLevel());

    return codeMap;
  }

  private Map<String, Object> getSubfieldPositions(SubfieldDefinition subfield) {
    Map<String, Object> positionListMap = new LinkedHashMap<>();
    for (ControlfieldPositionDefinition position : subfield.getPositions()) {
      Map<String, Object> positionMap = new LinkedHashMap<>();
      positionMap.put("label", position.getLabel());
      positionMap.put("start", position.getPositionStart());
      positionMap.put("end", position.getPositionEnd() - 1);
      positionMap.put("repeatableContent", position.isRepeatableContent());
      if (position.isRepeatableContent())
        positionMap.put("unitLength", position.getUnitLength());

      if (position.hasCodelist()) {
        if (position.getCodes() != null && !position.getCodes().isEmpty()) {
          positionMap.put("codes", extractCodes(position.getCodes()));
        } else if (position.getCodeList() != null) {
          referencesCodeLists.put(position.getCodeList().getUrl(), position.getCodeList().getCodes());
          positionMap.put("codes", position.getCodeList().getUrl());
          // positionMap.put("codes", extractCodes(position.getCodeList().getCodes()));
        } else if (position.getCodeListReference() != null) {
          String url = String.format("%s#%s", position.getCodeListReference().getDescriptionUrl(), position.getCodeListReference().getPositionStart());
          referencesCodeLists.put(url, position.getCodeListReference().getCodes());
          positionMap.put("codes", url);
          // positionMap.put("codes", extractCodes(position.getCodeListReference().getCodes()));
        } else {
          logger.log(Level.WARNING, "{0}${1}/{2}: missing code list!", new Object[]{
            subfield.getParent().getTag(), subfield.getCode(), position.getPositionStart()});
        }
      } else if (position.getValidator() != null) {
        if (position.getValidator() instanceof RegexValidator)
          positionMap.put("pattern", ((RegexValidator)position.getValidator()).getPattern());
      } else {
        logger.log(Level.WARNING, "{0}${1}/{2}: missing code list and validation!", new Object[]{
          subfield.getParent().getTag(), subfield.getCode(), position.getPositionStart()});
      }
      positionListMap.put(position.formatPositon(), positionMap);
    }
    return positionListMap;
  }

  private static Map<String, Map<String, Object>> extractCodes(List<EncodedValue> codeList) {
    Map<String, Map<String, Object>> codes = new HashMap<>();
    for (EncodedValue code : codeList) {
      Map<String, Object> codeInfo = new LinkedHashMap<>();
      codeInfo.put("label", code.getLabel());
      if (code.getRange() != null)
        codeInfo.put("range", code.getRange());
      codes.put(code.getCode(), codeInfo);
    }
    return codes;
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
    if (indicator.getCodes() != null) {
      for (EncodedValue code : indicator.getCodes()) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", code.getCode());
        map.put("label", code.getLabel());
        codes.put(code.getCode(), map);
      }
    }
    if (indicator.getHistoricalCodes() != null) {
      for (EncodedValue code : indicator.getHistoricalCodes()) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", code.getCode());
        map.put("label", code.getLabel());
        map.put("deprecated", true);
        codes.put(code.getCode(), map);
      }
    }
    if (codes.size() > 0) { 
      value.put("codes", codes);
    }

    return value;
  }

  private boolean resolveCardinality(Cardinality cardinality) {
    return cardinality.getCode().equals("R");
  }

  public static void main(String[] args) {
    try {
      MappingToJson mapping = new MappingToJson(args);
      mapping.build();
      System.out.println(mapping.toJson());
    } catch (ParseException e) {
      System.err.println("ERROR. " + e.getLocalizedMessage());
      System.exit(1);
    }
  }
}
