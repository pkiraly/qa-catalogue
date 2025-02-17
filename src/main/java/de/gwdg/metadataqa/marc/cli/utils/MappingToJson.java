package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.cli.parameters.MappingParameters;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.CompilanceLevel;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control006Positions;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control007Positions;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control008Positions;
import de.gwdg.metadataqa.marc.definition.controlpositions.ControlfieldPositionList;
import de.gwdg.metadataqa.marc.definition.controlpositions.LeaderPositions;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control001Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control003Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control005Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control006Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control007Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control008Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.LeaderDefinition;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGeneratorFactory;
import de.gwdg.metadataqa.marc.utils.keygenerator.PositionalControlFieldKeyGenerator;
import net.minidev.json.JSONValue;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        addCodeOrRange(codes, code, false);
      }
      Map<String, Object> codelist = new LinkedHashMap<>();
      codelist.put("codes", codes);
      codelist.put("url", url);
      codelists.put(url, codelist);
    }
    mapping.put("codelists", codelists);

    // System.err.println(referencesCodeLists.keySet());
  }

  static void addCodeOrRange(Map<String, Map<String, Object>> codes, EncodedValue code, boolean deprecated) {

    if (code.getRange() != null) {
      String[] range = code.getCode().split("-");
      try {
        int from = Integer.parseInt(range[0]);
        int to = Integer.parseInt(range[1]);
        for (int i=from; i<=to; i++) {
          String codeNumber = Integer.toString(i);
          Map<String, Object> map = new LinkedHashMap<>();
          map.put("code", codeNumber);
          map.put("label", code.getLabel());
          if (deprecated) {
            map.put("deprecated", true);
          }
          codes.put(codeNumber, map);
        }
      } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        System.err.println("Invalid range in codelist!");
      }
    } else {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("code", code.getCode());
      map.put("label", code.getLabel());
      if (deprecated) {
        map.put("deprecated", true);
      }
      codes.put(code.getCode(), map);
    }
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
    if (!codes.isEmpty()) {
      String codesOrFlags = subfield.isRepeatableContent() ? "flags" : "codes";
      values.put("codes", codes);
    }
    return values;
  }

  private void dataFieldToJson(Map fields, DataFieldDefinition fieldDefinition) {
    DataFieldKeyGenerator keyGenerator = DataFieldKeyGeneratorFactory.create(parameters.getSolrFieldType(), fieldDefinition);

    Map<String, Object> tagMap = new LinkedHashMap<>();
    tagMap.put("tag", fieldDefinition.getTag());
    tagMap.put("label", fieldDefinition.getLabel());
    tagMap.put("url", fieldDefinition.getDescriptionUrl());
    tagMap.put("repeatable", resolveCardinality(fieldDefinition.getCardinality()));
    if (exportSelfDescriptiveCodes)
      tagMap.put("solr", keyGenerator.getIndexTag());

    if (parameters.doExportFrbrFunctions())
      extractFunctions(tagMap, fieldDefinition.getFrbrFunctions());

    if (parameters.doExportCompilanceLevel())
      extractCompilanceLevel(tagMap, fieldDefinition.getNationalCompilanceLevel(), fieldDefinition.getMinimalCompilanceLevel());

    if (parameters.isWithLocallyDefinedFields())
      tagMap.put("version", fieldDefinition.getMarcVersion().getCode());

    tagMap.put("indicator1", indicatorToJson(fieldDefinition.getInd1()));
    tagMap.put("indicator2", indicatorToJson(fieldDefinition.getInd2()));

    Map<String, Object> subfields = new LinkedHashMap<>();
    if (fieldDefinition.getSubfields() != null) {
      for (SubfieldDefinition subfield : fieldDefinition.getSubfields()) {
        subfields.put(subfield.getCode(), subfieldToJson(subfield, keyGenerator));
      }
    } else {
      logger.info(fieldDefinition + " does not have subfields");
    }

    if (fieldDefinition.getHistoricalSubfields() != null) {
      for (EncodedValue code : fieldDefinition.getHistoricalSubfields()) {
        Map<String, Object> subfieldDefinition = new LinkedHashMap<>();
        subfieldDefinition.put("label", code.getLabel());
        subfieldDefinition.put("deprecated", true);
        subfields.put(code.getCode(), subfieldDefinition);
      }
    }

    tagMap.put("subfields", subfields);

    if (parameters.isWithLocallyDefinedFields()) {
      Map<MarcVersion, List<SubfieldDefinition>> versionSpecificSubfields = fieldDefinition.getVersionSpecificSubfields();
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

    if (fields.containsKey(fieldDefinition.getTag())) {
      Object existing = fields.get(fieldDefinition.getTag());
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
      fields.put(fieldDefinition.getTag(), list);
    } else {
      fields.put(fieldDefinition.getTag(), tagMap);
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

  private Map<String, Object> subfieldToJson(SubfieldDefinition subfieldDefinition, DataFieldKeyGenerator keyGenerator) {
    Map<String, Object> codeMap = new LinkedHashMap<>();
    codeMap.put("label", subfieldDefinition.getLabel());
    codeMap.put("repeatable", resolveCardinality(subfieldDefinition.getCardinality()));

    if (exportSelfDescriptiveCodes)
      codeMap.put("solr", keyGenerator.forSubfieldDefinition(subfieldDefinition));

    if (subfieldDefinition.getContentParser() instanceof LinkageParser)
      codeMap.put("pattern", ((LinkageParser)subfieldDefinition.getContentParser()).getPattern());

    if (subfieldDefinition.getContentParser() instanceof RecordControlNumberParser)
      codeMap.put("pattern", ((RecordControlNumberParser)subfieldDefinition.getContentParser()).getPattern());

    if (subfieldDefinition.getValidator() instanceof RegexValidator)
      codeMap.put("pattern", ((RegexValidator)subfieldDefinition.getValidator()).getPattern());

    if (subfieldDefinition.getCodeList() != null
        && !subfieldDefinition.getCodeList().getCodes().isEmpty()) {
      CodeList codeList = subfieldDefinition.getCodeList();
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

    if (subfieldDefinition.hasPositions())
      codeMap.put("positions", getSubfieldPositions(subfieldDefinition));

    if (parameters.doExportFrbrFunctions())
      extractFunctions(codeMap, subfieldDefinition.getFrbrFunctions());

    if (parameters.doExportCompilanceLevel())
      extractCompilanceLevel(codeMap, subfieldDefinition.getNationalCompilanceLevel(), subfieldDefinition.getMinimalCompilanceLevel());

    return codeMap;
  }

  private Map<String, Object> getSubfieldPositions(SubfieldDefinition subfield) {
    Map<String, Object> positionListMap = new LinkedHashMap<>();
    for (ControlfieldPositionDefinition position : subfield.getPositions()) {
      Map<String, Object> positionMap = new LinkedHashMap<>();
      positionMap.put("label", position.getLabel());
      positionMap.put("start", position.getPositionStart());
      positionMap.put("end", position.getPositionEnd() - 1);

      if (position.hasCodelist()) {
        String codesOrFlags = position.isRepeatableContent() ? "flags" : "codes";
        if (position.getCodes() != null && !position.getCodes().isEmpty()) {
          positionMap.put(codesOrFlags, extractCodes(position.getCodes()));
        } else if (position.getCodeList() != null) {
          referencesCodeLists.put(position.getCodeList().getUrl(), position.getCodeList().getCodes());
          positionMap.put(codesOrFlags, position.getCodeList().getUrl());
        } else if (position.getCodeListReference() != null) {
          String url = String.format("%s#%s", position.getCodeListReference().getDescriptionUrl(), position.getCodeListReference().getPositionStart());
          referencesCodeLists.put(url, position.getCodeListReference().getCodes());
          positionMap.put(codesOrFlags, url);
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
      addCodeOrRange(codes, code, false);
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

    Map<String, Map<String, Object>> codes = new LinkedHashMap<>();
    if (indicator.getCodes() != null) {
      for (EncodedValue code : indicator.getCodes()) {
        addCodeOrRange(codes, code, false);
      }
    }
    if (indicator.getHistoricalCodes() != null) {
      for (EncodedValue code : indicator.getHistoricalCodes()) {
        addCodeOrRange(codes, code, true);
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
