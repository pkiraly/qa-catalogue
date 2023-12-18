package de.gwdg.metadataqa.marc.dao.record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.analysis.ThompsonTraillFields;
import de.gwdg.metadataqa.marc.cli.utils.IgnorableFields;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import de.gwdg.metadataqa.marc.utils.unimarc.UnimarcConverter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BibliographicRecord implements Extractable, Serializable { // Validatable,

  private static final Logger logger = Logger.getLogger(BibliographicRecord.class.getCanonicalName());
  protected static final Pattern dataFieldPattern = Pattern.compile("^(\\d\\d\\d)\\$(.*)$");
  private static final List<String> MARC21_SUBJECT_TAGS = Arrays.asList(
    "052", "055", "072", "080", "082", "083", "084", "085", "086",
    "600", "610", "611", "630", "647", "648", "650", "651",
    "653", "654", "655", "656", "657", "658", "662"
  );
  protected static final List<String> PICA_SUBJECT_TAGS = Arrays.asList("045A", "045B", "045F", "045R");

  private static final Map<String, Boolean> undefinedTags = new HashMap<>();

  protected List<DataField> datafields;
  protected Map<String, List<DataField>> datafieldIndex;
  protected Map<String, List<MarcControlField>> controlfieldIndex;
  Map<String, List<String>> mainKeyValuePairs;
  // private List<ValidationError> validationErrors = null;
  protected SchemaType schemaType = SchemaType.MARC21;

  protected String id;

  public enum RESOLVE {
    NONE,
    RESOLVE,
    BOTH;
  }

  private List<String> unhandledTags;

  public BibliographicRecord() {
    datafields = new ArrayList<>();
    datafieldIndex = new TreeMap<>();
    controlfieldIndex = new TreeMap<>();
    unhandledTags = new ArrayList<>();
  }

  public BibliographicRecord(String id) {
    this();
    this.id = id;
  }

  public void addDataField(DataField dataField) {
    dataField.setMarcRecord(this);
    indexField(dataField);
    datafields.add(dataField);
  }

  private void indexField(DataField dataField) {
    String tag = dataField.getTag();
    if (tag == null)
      logger.warning("null tag in indexField() " + dataField);

    datafieldIndex.computeIfAbsent(tag, s -> new ArrayList<>());
    datafieldIndex.get(tag).add(dataField);
  }

  public void addUnhandledTags(String tag) {
    unhandledTags.add(tag);
  }


  public String getId() {
    if (id != null)
      return id;
    return null;
  }

  public String getId(boolean trim) {
    String id = getId();
    if (trim && id != null)
      id = id.trim();
    return id;
  }

  public boolean hasDatafield(String tag) {
    return datafieldIndex.containsKey(tag);
  }

  public List<DataField> getDatafield(String tag) {
    return datafieldIndex.getOrDefault(tag, null);
  }

  public List<DataField> getDatafields() {
    return datafields;
  }

  public boolean exists(String tag) {
    List<DataField> fields = getDatafield(tag);
    return (fields != null && !fields.isEmpty());
  }

  public List<String> extract(String tag, String subfield) {
    return extract(tag, subfield, RESOLVE.NONE);
  }

  /**
   * Extact field value
   * @param tag
   * @param subfield
   * @param doResolve
   * @return
   */
  public List<String> extract(String tag, String subfield, RESOLVE doResolve) {
    List<String> values = new ArrayList<>();
    List<DataField> fields = getDatafield(tag);
    if (fields != null && !fields.isEmpty()) {
      for (DataField field : fields) {
        if (subfield.equals("ind1") || subfield.equals("ind2")) {
          String value;
          Indicator indicator;
          if (subfield.equals("ind1")) {
            value = field.getInd1();
            indicator = field.getDefinition().getInd1();
          } else {
            value = field.getInd2();
            indicator = field.getDefinition().getInd2();
          }
          if (indicator.getCode(value) == null) {
            values.add(value);
          } else {
            values.add(indicator.getCode(value).getLabel());
          }
        } else {
          List<MarcSubfield> subfieldInstances = field.getSubfield(subfield);
          if (subfieldInstances != null) {
            for (MarcSubfield subfieldInstance : subfieldInstances) {
              String value = null;
              switch (doResolve) {
                case RESOLVE: value = subfieldInstance.resolve(); break;
                case NONE: value = subfieldInstance.getValue(); break;
                case BOTH: value = subfieldInstance.resolve() + "##" + subfieldInstance.getValue(); break;
              }
              values.add(value);
            }
          }
        }
      }
    }
    return values;
  }

  public List<String> getUnhandledTags() {
    return unhandledTags;
  }

  public String format() {
    StringBuffer output = new StringBuffer();
    for (DataField field : datafields) {
      output.append(field.format());
    }
    return output.toString();
  }

  public String formatAsText() {
    StringBuffer output = new StringBuffer();
    for (DataField field : datafields) {
      output.append(field.formatAsText());
    }
    return output.toString();
  }

  public String formatAsMarc() {
    StringBuffer output = new StringBuffer();
    for (DataField field : datafields) {
      output.append(field.formatAsMarc());
    }
    return output.toString();
  }

  public String formatForIndex() {
    StringBuffer output = new StringBuffer();
    for (DataField field : datafields) {
      output.append(field.formatForIndex());
    }
    return output.toString();
  }

  public Map<String, List<String>> getKeyValuePairs() {
    return getKeyValuePairs(SolrFieldType.MARC);
  }

  public Map<String, List<String>> getKeyValuePairs(SolrFieldType type) {
    return getKeyValuePairs(type, false, MarcVersion.MARC21);
  }

  @Override
  public Map<String, List<String>> getKeyValuePairs(SolrFieldType type, MarcVersion marcVersion) {
    return getKeyValuePairs(type, false, marcVersion);
  }

  public Map<String, List<String>> getKeyValuePairs(SolrFieldType type,
                                                    boolean withDeduplication,
                                                    MarcVersion marcVersion) {
    if (mainKeyValuePairs == null) {
      mainKeyValuePairs = new LinkedHashMap<>();

      getKeyValuePairsForDatafields(type, withDeduplication, marcVersion);
    }

    return mainKeyValuePairs;
  }

  protected void getKeyValuePairsForDatafields(SolrFieldType type, boolean withDeduplication, MarcVersion marcVersion) {
    for (DataField field : datafields) {
      Map<String, List<String>> keyValuePairs = field.getKeyValuePairs(type, marcVersion);
      for (Map.Entry<String, List<String>> entry : keyValuePairs.entrySet()) {
        String key = entry.getKey();
        List<String> values = entry.getValue();
        if (mainKeyValuePairs.containsKey(key)) {
          mainKeyValuePairs.put(
            key,
            mergeValues(new ArrayList<>(mainKeyValuePairs.get(key)), values, withDeduplication)
          );
        } else {
          mainKeyValuePairs.put(key, values);
        }
      }
    }
  }

  protected List<String> mergeValues(List<String> existingValues,
                                   List<String> values,
                                   boolean withDeduplication) {
    if (withDeduplication) {
      for (String value : values) {
        if (!existingValues.contains(value)) {
          existingValues.add(value);
        }
      }
    } else {
      existingValues.addAll(values);
    }
    return existingValues;
  }

  public String asJson() {
    ObjectMapper mapper = new ObjectMapper();

    Map<String, Object> map = new LinkedHashMap<>();
    datafieldsAsJson(map);
    return transformMapToJson(mapper, map);
  }

  protected static String transformMapToJson(ObjectMapper mapper, Map<String, Object> map) {
    String json = null;
    try {
      json = mapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      logger.log(Level.WARNING, "error in asJson()", e);
    }
    return json;
  }

  protected void datafieldsAsJson(Map<String, Object> map) {
    for (DataField field : datafields) {
      if (field != null) {
        Map<String, Object> fieldMap = new LinkedHashMap<>();

        if (!schemaType.equals(SchemaType.PICA)) {
          fieldMap.put("ind1", field.getInd1());
          fieldMap.put("ind2", field.getInd2());
        }

        fieldMap.put("subfields", exportSubfieldsToJson(field));

        String tag = field.getOccurrence() != null
          ? field.getTag() + "/" + field.getOccurrence()
          : (field.getDefinition() != null
            ? field.getDefinition().getTag()
            : field.getTag());

        map.computeIfAbsent(tag, s -> new ArrayList<Map<String, Object>>());
        ((ArrayList) map.get(tag)).add(fieldMap);
      }
    }
  }

  private static Map<String, Object> exportSubfieldsToJson(DataField field) {
    Map<String, Object> subfields = new LinkedHashMap<>();
    for (MarcSubfield subfield : field.getSubfields()) {
      if (!subfields.containsKey(subfield.getCode()))
        subfields.put(subfield.getCode(), subfield.getValue());
      else {
        if (subfields.get(subfield.getCode()) instanceof String) {
          String storedValue = (String) subfields.get(subfield.getCode());
          List<String> list = new ArrayList<>();
          list.add(storedValue);
          subfields.put(subfield.getCode(), list);
        }
        ((List)subfields.get(subfield.getCode())).add(subfield.getValue());
      }
    }
    return subfields;
  }

  public boolean isIgnorableField(String tag, IgnorableFields ignorableFields) {
    if (ignorableFields == null)
      return false;
    return ignorableFields.contains(tag);
  }

  public List<String> search(String path, String query) {
    List<String> results = new ArrayList<>();
    Matcher matcher = dataFieldPattern.matcher(path);
    if (matcher.matches()) {
      String tag = matcher.group(1);
      String subfieldCode = matcher.group(2);
      if (datafieldIndex.containsKey(tag)) {
        for (DataField field : datafieldIndex.get(tag)) {
          if (searchDatafield(query, results, subfieldCode, field)) break;
        }
      }
    }
    return results;
  }

  public List<String> select(MarcSpec selector) {
    List<String> results = new ArrayList<>();
    if (datafieldIndex.containsKey(selector.getFieldTag())) {
      selectDatafields(selector, results);
    }
    return results;
  }

  protected void selectDatafields(MarcSpec selector, List<String> results) {
    for (DataField field : datafieldIndex.get(selector.getFieldTag())) {
      if (field == null)
        continue;
      List<String> codes = selector.getSubfieldsAsList();
      if (codes.isEmpty()) {
        results.add(joinAllSubfields(field));
      } else {
        for (String subfieldCode : codes) {
          List<MarcSubfield> subfields = field.getSubfield(subfieldCode);
          if (subfields == null)
            continue;
          for (MarcSubfield subfield : subfields)
            results.add(subfield.getValue());
        }
      }
    }
  }

  protected static String joinAllSubfields(DataField field) {
    List<String> values = new ArrayList<>();
    for (MarcSubfield subfield : field.getSubfields()) {
      values.add(subfield.getValue());
    }
    return StringUtils.join(values," ");
  }

  public List<String> select(PicaPath selector) {
    if (!schemaType.equals(SchemaType.PICA))
      throw new IllegalArgumentException("The record is not a PICA record");

    List<String> results = new ArrayList<>();
    List<DataField> dataFields = getDatafield(selector.getTag());
    if (dataFields != null) {
      for (DataField dataField : dataFields) {
        for (String code : selector.getSubfields().getCodes()) {
          List<MarcSubfield> dubfields = dataField.getSubfield(code);
          if (dubfields != null) {
            for (MarcSubfield subfield : dubfields) {
              results.add(subfield.getValue());
            }
          }
        }
      }
    }

    return results;
  }


  protected boolean searchDatafield(String query, List<String> results,
                                  String subfieldCode, DataField field) {
    if (subfieldCode.equals("ind1") && field.getInd1().equals(query)) {
      results.add(field.getInd1());
      return true;
    } else if (subfieldCode.equals("ind2") && field.getInd2().equals(query)) {
      results.add(field.getInd2());
      return true;
    } else {
      List<MarcSubfield> subfields = field.getSubfield(subfieldCode);
      if (subfields != null) {
        for (MarcSubfield subfield : subfields) {
          if (subfield.getValue().equals(query)) {
            results.add(subfield.getValue());
            return true;
          }
        }
      }
    }
    return false;
  }


  public List<DataField> getAuthorityFields(List<String> tags) {
    List<DataField> subjects = new ArrayList<>();
    for (String tag : tags) {
      List<DataField> fields = getDatafield(tag);
      if (fields != null && !fields.isEmpty())
        subjects.addAll(fields);
    }
    return subjects;
  }


  public Map<DataField, AuthorityCategory> getAuthorityFields(Map<AuthorityCategory, List<String>> tags) {
    Map<DataField, AuthorityCategory> subjects = new LinkedHashMap<>();
    for (Map.Entry<AuthorityCategory, List<String>> entry : tags.entrySet()) {
      AuthorityCategory category = entry.getKey();
      for (String tag : entry.getValue()) {
        List<DataField> fields = getDatafield(tag);
        if (fields != null && !fields.isEmpty()) {
          for (DataField field : fields)
            subjects.put(field, category);
        }
      }
    }
    return subjects;
  }

  public abstract List<DataField> getAuthorityFields();
  public abstract Map<DataField, AuthorityCategory> getAuthorityFieldsMap();
  public abstract boolean isAuthorityTag(String tag);
  public abstract boolean isSkippableAuthoritySubfield(String tag, String code);
  public abstract boolean isSubjectTag(String tag);
  public abstract boolean isSkippableSubjectSubfield(String tag, String code);
  public abstract Map<ShelfReadyFieldsBooks, Map<String, List<String>>> getShelfReadyMap();
  public abstract Map<ThompsonTraillFields, List<String>> getThompsonTraillTagsMap();

  public List<DataField> getSubjects() {
    List<DataField> subjects = new ArrayList<>();
    List<String> tags;
    switch (schemaType) {
      case PICA:
        tags = PICA_SUBJECT_TAGS; break;
      case MARC21:
      default:
        tags = MARC21_SUBJECT_TAGS; break;
    }
    for (String tag : tags) {
      List<DataField> fields = getDatafield(tag);
      if (fields != null && !fields.isEmpty())
        subjects.addAll(fields);
    }
    return subjects;
  }

  public List<DataField> getSubject6xx() {
    List<DataField> subjects = new ArrayList<>();
    List<String> tags = Arrays.asList("600", "610", "611", "630", "648", "650", "651");
    for (String tag : tags) {
      List<DataField> fields = getDatafield(tag);
      if (fields != null && !fields.isEmpty())
        subjects.addAll(fields);
    }
    return subjects;
  }

  public void setField(String tag, String content) {
    setField(tag, content, MarcVersion.MARC21);
  }

  public void setField(String tag, String content, MarcVersion marcVersion) {
    if (marcVersion.equals(MarcVersion.UNIMARC)) {
      content = UnimarcConverter.contentFromUnimarc(tag, content);
      tag = UnimarcConverter.tagFromUnimarc(tag);
    }

    DataFieldDefinition definition = MarcFactory.getDataFieldDefinition(tag, marcVersion);
    if (definition == null) {
      addUnhandledTags(tag);
    }

    DataField dataField = new DataField(tag, content, marcVersion);
    addDataField(dataField);
  }

  public void setField(String tag, String ind1, String ind2, String content, MarcVersion marcVersion) {

    DataFieldDefinition definition = MarcFactory.getDataFieldDefinition(tag, marcVersion);
    if (definition == null) {
      addUnhandledTags(tag);
    }
    addDataField(new DataField(tag, ind1, ind2, content, marcVersion));
  }

  public SchemaType getSchemaType() {
    return schemaType;
  }

  public void setSchemaType(SchemaType schemaType) {
    this.schemaType = schemaType;
  }
}
