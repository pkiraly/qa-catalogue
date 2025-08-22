package de.gwdg.metadataqa.marc.dao.record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.analysis.contextual.authority.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.shelfready.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.cli.utils.IgnorableFields;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.SchemaSpec;
import de.gwdg.metadataqa.marc.utils.marcspec.MarcSpec;
import de.gwdg.metadataqa.marc.utils.marcspec.MarcSpecExtractor;
import de.gwdg.metadataqa.marc.utils.unimarc.UnimarcConverter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class BibliographicRecord implements Extractable, Serializable { // Validatable,

  protected static final Pattern dataFieldPattern = Pattern.compile("^(\\d\\d\\d)\\$(.*)$");
  private static final Logger logger = Logger.getLogger(BibliographicRecord.class.getCanonicalName());
  private final List<String> unhandledTags;
  protected transient List<DataField> datafields;
  /**
   * Key-value pairs of a tag and list of datafields that have the tag. Gets populated when the record is created.
   */
  protected transient Map<String, List<DataField>> datafieldIndex;
  protected transient Map<String, List<MarcControlField>> controlfieldIndex;
  protected transient Map<String, List<String>> mainKeyValuePairs;
  protected SchemaType schemaType = SchemaType.MARC21;
  protected String id;
  protected List<String> authorityTags;
  /**
   * Key-value pairs of tags and a boolean value indicating if the tag is an authority tag.
   */
  protected Map<String, Boolean> authorityTagsIndex;
  /**
   * Key-value pairs of AuthorityCategory and tags
   */
  protected Map<AuthorityCategory, List<String>> authorityTagsMap;
  protected Map<String, Map<String, Boolean>> skippableAuthoritySubfields;

  protected Map<String, Map<String, Boolean>> skippableSubjectSubfields;
  protected Map<String, Boolean> subjectTagIndex;
  protected BibliographicRecord() {
    datafields = new ArrayList<>();
    datafieldIndex = new TreeMap<>();
    controlfieldIndex = new TreeMap<>();
    unhandledTags = new ArrayList<>();
  }

  protected BibliographicRecord(String id) {
    this();
    this.id = id;
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

  protected static String joinAllSubfields(DataField field) {
    List<String> values = new ArrayList<>();
    for (MarcSubfield subfield : field.getSubfields()) {
      values.add(subfield.getValue());
    }
    return StringUtils.join(values," ");
  }

  private static Predicate<DataField> getDataFieldPredicate(String subfieldCode, String subfieldValue) {
    Predicate<DataField> filterPredicate;
    // ind1 and ind2 should also be considered as subfield codes
    if (subfieldCode.equals("ind1")) {
      filterPredicate = dataField -> dataField.getInd1().equals(subfieldValue);
    } else if (subfieldCode.equals("ind2")) {
      filterPredicate = dataField -> dataField.getInd2().equals(subfieldValue);
    } else {
      filterPredicate = dataField -> dataField.getSubfield(subfieldCode).stream()
        .anyMatch(subfield -> subfield.getValue().equals(subfieldValue));
    }
    return filterPredicate;
  }

  public void addDataField(DataField dataField) {
    dataField.setBibliographicRecord(this);
    indexField(dataField);
    datafields.add(dataField);
  }

  protected void indexField(DataField dataField) {
    String tag = dataField.getTag();
    if (tag == null)
      logger.warning(() -> "null tag in indexField() " + dataField);

    datafieldIndex.computeIfAbsent(tag, s -> new ArrayList<>());
    datafieldIndex.get(tag).add(dataField);
  }

  public void addUnhandledTags(String tag) {
    unhandledTags.add(tag);
  }

  public String getId() {
    return id;
  }

  public String getId(boolean trim) {
    String trimmedId = getId();
    if (trim && trimmedId != null) {
      trimmedId = trimmedId.trim();
    }
    return trimmedId;
  }

  public boolean hasDatafield(String tag) {
    return datafieldIndex.containsKey(tag);
  }

  /**
   * Returns a list of data fields that have the specified tag.
   * @param tag The tag of the data fields to return.
   *            The tag can also contain a subfield value test, e.g. "600$a=Test".
   * @return A list of data fields with the specified tag.
   */
  public List<DataField> getDatafieldsByTag(String tag) {
    // For performance reasons, just check if there's a $ sign first
    if (!tag.contains("$")) {
      return datafieldIndex.getOrDefault(tag, null);
    }

    String[] parts = tag.split("[=$]");
    String fieldTag = parts[0];
    String subfieldCode = parts.length > 1 ? parts[1] : null;
    String subfieldValue = parts.length > 2 ? parts[2] : null;

    List<DataField> fields = datafieldIndex.getOrDefault(fieldTag, null);

    if (subfieldCode == null) {
      return fields;
    }

    Predicate<DataField> filterPredicate = getDataFieldPredicate(subfieldCode, subfieldValue);

    if (fields != null) {
      fields = fields.stream().filter(filterPredicate).collect(Collectors.toList());
    }

    return fields;
  }

  public List<DataField> getDatafields() {
    return datafields;
  }

  public boolean exists(String tag) {
    List<DataField> fields = getDatafieldsByTag(tag);
    return (fields != null && !fields.isEmpty());
  }

  public List<String> extract(String tag, String subfield) {
    return extract(tag, subfield, RESOLVE.NONE);
  }

  /**
   * Extracts the values of a specified subfield from all data fields with a specified tag.
   * If the subfield path is "ind1" or "ind2", the method will extract the indicator value.
   * Otherwise, it will extract the subfield value.
   * <br/>
   * If doResolve is set to RESOLVE, the method will resolve the subfield value. In other words, it will return the label
   * that corresponds to the subfield value. If doResolve is set to NONE, the method will return the subfield value as is.
   * If doResolve is set to BOTH, the method will return both the resolved label and the subfield value.
   * @param tag The tag of the data fields to extract from.
   * @param subfieldPath The path of the subfield to extract.
   * @param doResolve The resolve option to use when extracting subfield values. Can be set to RESOLVE, NONE, or BOTH.
   * @return A list of extracted values.
   */
  public List<String> extract(String tag, String subfieldPath, RESOLVE doResolve) {
    List<String> extractedValues = new ArrayList<>();
    List<DataField> fields = getDatafieldsByTag(tag);
    if (fields == null || fields.isEmpty()) {
      return extractedValues;
    }

    for (DataField field : fields) {
      if (subfieldPath.equals("ind1") || subfieldPath.equals("ind2")) {
        String extractedIndicator = extractIndicator(field, subfieldPath);
        extractedValues.add(extractedIndicator);
      } else {
        List<String> extractedSubfields = extractSubfield(field, subfieldPath, doResolve);
        extractedValues.addAll(extractedSubfields);
      }
    }
    return extractedValues;
  }

  public List<String> getUnhandledTags() {
    return unhandledTags;
  }

  public String format() {
    StringBuilder output = new StringBuilder();
    for (DataField field : datafields) {
      output.append(field.format());
    }
    return output.toString();
  }

  public String formatAsText() {
    StringBuilder output = new StringBuilder();
    for (DataField field : datafields) {
      output.append(field.formatAsText());
    }
    return output.toString();
  }

  public String formatAsMarc() {
    StringBuilder output = new StringBuilder();
    for (DataField field : datafields) {
      output.append(field.formatAsMarc());
    }
    return output.toString();
  }

  public String formatForIndex() {
    StringBuilder output = new StringBuilder();
    for (DataField field : datafields) {
      output.append(field.formatForIndex());
    }
    return output.toString();
  }

  /**
   * This method can produce wrong results due to the fact that it doesn't take into account the MarcVersion that the
   * record is in. The method should be used with caution.
   * @return A map of key-value pairs for the specified Solr field type.
   */
  public Map<String, List<String>> getKeyValuePairs() {
    return getKeyValuePairs(SolrFieldType.MARC);
  }

  /**
   * This method can produce wrong results due to the fact that it doesn't take into account the MarcVersion that the
   * record is in. The method should be used with caution.
   * @param type The type of the Solr field to get the key-value pairs for. Can be set to MARC, HUMAN, or MIXED.
   * @return A map of key-value pairs for the specified Solr field type.
   */
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
      // Get the key value pairs for the data field and add them to the mainKeyValuePairs map
      Map<String, List<String>> keyValuePairs = field.getKeyValuePairs(type, marcVersion);
      for (Map.Entry<String, List<String>> entry : keyValuePairs.entrySet()) {
        String key = entry.getKey();
        List<String> values = entry.getValue();
        // If mainKeyValuePairs already contains the key, merge the values into the existing list. Essentially extending
        // the mainKeyValuePairs with the new key value pairs.
        if (mainKeyValuePairs.containsKey(key)) {
          ArrayList<String> existingPairs = new ArrayList<>(mainKeyValuePairs.get(key));
          List<String> mergedPairs = mergeValues(existingPairs, values, withDeduplication);
          mainKeyValuePairs.put(key, mergedPairs);
        } else {
          mainKeyValuePairs.put(key, values);
        }
      }
    }
  }

  protected List<String> mergeValues(List<String> existingValues,
                                     List<String> values,
                                     boolean withDeduplication) {
    if (!withDeduplication) {
      existingValues.addAll(values);
      return existingValues;
    }

    for (String value : values) {
      if (!existingValues.contains(value)) {
        existingValues.add(value);
      }
    }
    return existingValues;
  }

  public String asJson() {
    ObjectMapper mapper = new ObjectMapper();

    Map<String, Object> fieldMap = new LinkedHashMap<>();
    datafieldsAsJson(fieldMap);
    return transformMapToJson(mapper, fieldMap);
  }

  protected void datafieldsAsJson(Map<String, Object> tagFieldMap) {
    for (DataField field : datafields) {
      if (field == null) {
        continue;
      }

      Map<String, Object> fieldMap = new LinkedHashMap<>();

      if (!schemaType.equals(SchemaType.PICA)) {
        fieldMap.put("ind1", field.getInd1());
        fieldMap.put("ind2", field.getInd2());
      }

      fieldMap.put("subfields", exportSubfieldsToJson(field));

      String tag;
      if (field.getOccurrence() != null) {
        tag = field.getTag() + "/" + field.getOccurrence();
      } else if (field.getDefinition() != null) {
        tag = field.getDefinition().getTag();
      } else {
        tag = field.getTag();
      }

      tagFieldMap.computeIfAbsent(tag, s -> new ArrayList<Map<String, Object>>());
      ((List) tagFieldMap.get(tag)).add(fieldMap);
    }
  }

  public boolean isIgnorableField(String tag, IgnorableFields ignorableFields) {
    if (ignorableFields == null)
      return false;
    return ignorableFields.contains(tag);
  }

  public List<String> search(String path, String query) {
    List<String> results = new ArrayList<>();
    Matcher matcher = dataFieldPattern.matcher(path);
    if (!matcher.matches()) {
      return results;
    }

    String tag = matcher.group(1);
    String subfieldCode = matcher.group(2);
    if (!datafieldIndex.containsKey(tag)) {
      return results;
    }

    for (DataField field : datafieldIndex.get(tag)) {
      if (searchDatafield(query, results, subfieldCode, field)) {
        break;
      }
    }
    return results;
  }


  /**
   * Selects the datafields of the record that match the selector. The selected datafields are added to the list of
   * selected results and returned.
   * @param selector The selector that lists the field and its subfields to select
   * @return The final result of the selection
   */
  public List<String> select(SchemaSpec selector) {
    /*
    if (selector instanceof MarcSpec) {
      MarcSpec spec = (MarcSpec) selector;
      if (!datafieldIndex.containsKey(spec.getFieldTag())) {
        return new ArrayList<>();
      }
      return selectDatafields(spec);
    }
    else if (selector instanceof MarcSpec2) {

     */
      MarcSpec spec = (MarcSpec) selector;
      if (!datafieldIndex.containsKey(spec.getTag())) {
        return new ArrayList<>();
      }
      return selectDatafields(spec);
    // }
    // return new ArrayList<>();
  }

  /**
   * Selects the datafield's subfields that are listed in the selector. If no subfields are listed in the selector,
   * then all subfields of the field are selected. The selected subfields are added to the list of selected results.
   * <br/>
   * If no subfields are specified in the selector, then all subfields of the field get selected.
   * @param field The datafield to select from.
   * @param selector The selector that lists subfields to select.
   * @return The final result of the selection.
   */
  /*
  protected List<String> selectDatafield(DataField field, MarcSpec selector) {
    List<String> selectedResults = new ArrayList<>();
    if (field == null) {
      return selectedResults;
    }

    List<String> selectorSubfieldCodes = selector.getSubfieldsAsList();

    // If the subfield list of the selector is empty, then we want to select all subfields of the field
    if (selectorSubfieldCodes.isEmpty()) {
      selectedResults.add(joinAllSubfields(field));
      return selectedResults;
    }

    // Otherwise, we select the field's subfields that are listed in the selector
    for (String subfieldCode : selectorSubfieldCodes) {
      List<MarcSubfield> subfields = field.getSubfield(subfieldCode);
      if (subfields == null) {
        continue;
      }
      for (MarcSubfield subfield : subfields) {
        selectedResults.add(subfield.getValue());
      }
    }

    return selectedResults;
  }

   */

  /*
  protected List<String> selectDatafields(MarcSpec selector) {
    List<String> selectedResults = new ArrayList<>();

    String selectorFieldTag = selector.getFieldTag();
    List<DataField> selectedDatafields = datafieldIndex.get(selectorFieldTag);

    for (DataField field : selectedDatafields) {
      List<String> selectedFromDatafield = selectDatafield(field, selector);
      selectedResults.addAll(selectedFromDatafield);
    }

    return selectedResults;
  }
  */

  protected <T extends Object>  List<T> selectDatafields(MarcSpec selector) {
    return (List<T>) MarcSpecExtractor.extract((Marc21Record) this, selector);
    /*
    List<String> selectedResults = new ArrayList<>();

    String selectorFieldTag = selector.getTag();
    List<DataField> selectedDatafields = datafieldIndex.get(selectorFieldTag);

    for (DataField field : selectedDatafields) {
      List<String> selectedFromDatafield = selectDatafield(field, selector);
      selectedResults.addAll(selectedFromDatafield);
    }

    return selectedResults;
    */
  }


  protected boolean searchDatafield(String query, List<String> results,
                                  String subfieldCode, DataField field) {
    if (subfieldCode.equals("ind1") && field.getInd1().equals(query)) {
      results.add(field.getInd1());
      return true;
    }
    if (subfieldCode.equals("ind2") && field.getInd2().equals(query)) {
      results.add(field.getInd2());
      return true;
    }

    List<MarcSubfield> subfields = field.getSubfield(subfieldCode);
    if (subfields == null) {
      return false;
    }

    for (MarcSubfield subfield : subfields) {
      if (subfield.getValue().equals(query)) {
        results.add(subfield.getValue());
        return true;
      }
    }

    return false;
  }

  /**
   * Given the tags of the record, returns the datafields that have the specified tags.
   * @param tags The tags of the datafields to return.
   * @return A list of datafields with the specified tags.
   */
  public List<DataField> getFieldsFromTags(List<String> tags) {
    List<DataField> allFields = new ArrayList<>();
    for (String tag : tags) {
      List<DataField> fields = getDatafieldsByTag(tag);
      if (fields != null && !fields.isEmpty())
        allFields.addAll(fields);
    }
    return allFields;
  }

  /**
   * Returns a map of datafields and their corresponding authority categories. Essentially, produces a map of
   * fields and their categories, such that the fields can only correspond to the tags that are listed in the
   * authority categories.
   * <br>
   * E.g. if an authority category "1" has tags "600" and "610", then the map will contain the fields that have
   * the tags "600" and "610" as keys, and the authority category "1" as the value. Not that it isn't necessarily
   * only two fields that will be returned, but all fields that have the tags "600" and "610".
   * @param categoryTags The tags of the datafields to return.
   * @return A map of datafields and their corresponding authority categories.
   */
  public Map<DataField, AuthorityCategory> getAuthorityFields(Map<AuthorityCategory, List<String>> categoryTags) {
    Map<DataField, AuthorityCategory> subjects = new LinkedHashMap<>();
    for (Map.Entry<AuthorityCategory, List<String>> entry : categoryTags.entrySet()) {
      AuthorityCategory category = entry.getKey();
      List<String> tags = entry.getValue();
      for (String tag : tags) {
        List<DataField> fields = getDatafieldsByTag(tag);
        if (fields == null || fields.isEmpty()) {
          continue;
        }
        for (DataField field : fields) {
          subjects.put(field, category);
        }
      }
    }
    return subjects;
  }

  public List<DataField> getAuthorityFields() {
    if (authorityTags == null) {
      initializeAuthorityTags();
    }
    return getFieldsFromTags(authorityTags);
  }

  public boolean isAuthorityTag(String tag) {
    if (authorityTagsIndex == null) {
      initializeAuthorityTags();
    }
    return authorityTagsIndex.getOrDefault(tag, false);
  }

  /**
   * Returns a map of a datafield of the record and the authority category of the field. That map is produced from the
   * previously defined authorityTagsMap which maps authority categories to tags.
   */
  public Map<DataField, AuthorityCategory> getAuthorityFieldsMap() {
    if (authorityTags == null) {
      initializeAuthorityTags();
    }
    return getAuthorityFields(authorityTagsMap);
  }

  protected void initializeAuthorityTags() {
    authorityTags = new ArrayList<>();
    authorityTagsIndex = new HashMap<>();
    skippableAuthoritySubfields = new HashMap<>();
    authorityTagsMap = new EnumMap<>(AuthorityCategory.class);
    subjectTagIndex = new HashMap<>();
    skippableSubjectSubfields = new HashMap<>();
  }

  public boolean isSkippableSubjectSubfield(String tag, String code) {
    if (subjectTagIndex == null) {
      initializeAuthorityTags();
    }

    if (!skippableSubjectSubfields.containsKey(tag)) {
      return false;
    }

    return skippableSubjectSubfields.get(tag).getOrDefault(code, false);
  }

  public boolean isSkippableAuthoritySubfield(String tag, String code) {
    if (authorityTagsIndex == null)
      initializeAuthorityTags();

    if (!skippableAuthoritySubfields.containsKey(tag))
      return false;

    return skippableAuthoritySubfields.get(tag).getOrDefault(code, false);
  }

  public boolean isSubjectTag(String tag) {
    if (subjectTagIndex == null) {
      initializeAuthorityTags();
    }
    return subjectTagIndex.getOrDefault(tag, false);
  }

  public abstract List<String> getAllowedControlFieldTags();

  public abstract Map<ShelfReadyFieldsBooks, Map<String, List<String>>> getShelfReadyMap();

  protected abstract List<String> getSubjectTags();

  public List<DataField> getSubjects() {
    List<DataField> subjects = new ArrayList<>();
    List<String> tags = getSubjectTags();

    for (String tag : tags) {
      List<DataField> fields = getDatafieldsByTag(tag);
      if (fields != null && !fields.isEmpty()) {
        subjects.addAll(fields);
      }
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

  private String extractIndicator(DataField field, String subfieldPath) {
    String value;
    Indicator indicator;

    // Check which indicator to extract
    if (subfieldPath.equals("ind1")) {
      value = field.getInd1();
      indicator = field.getDefinition().getInd1();
    } else {
      value = field.getInd2();
      indicator = field.getDefinition().getInd2();
    }

    if (indicator.getCode(value) == null) {
      return value;
    } else {
      return indicator.getCode(value).getLabel();
    }
  }

  /**
   * Extracts the values of a specified subfield from a data field of the record. If doResolve is set to RESOLVE,
   * the method will resolve the subfield value. In other words, it will return the label that corresponds to the
   * subfield value.
   * <br/>
   * If doResolve is set to NONE, the method will return the subfield value as is.
   * <br/>
   * If doResolve is set to BOTH, the method will return both the resolved label and the subfield value.
   * @param field The data field to extract from
   * @param subfieldPath The path of the subfield to extract
   * @param doResolve The resolve option to use when extracting subfield values. Can be set to RESOLVE, NONE, or BOTH.
   * @return A list of extracted values or labels.
   */
  private List<String> extractSubfield(DataField field, String subfieldPath, RESOLVE doResolve) {
    List<MarcSubfield> subfieldInstances = field.getSubfield(subfieldPath);
    if (subfieldInstances == null) {
      return new ArrayList<>();
    }

    List<String> extractedValues = new ArrayList<>();

    for (MarcSubfield subfieldInstance : subfieldInstances) {
      String value = null;
      switch (doResolve) {
        case RESOLVE: value = subfieldInstance.resolve(); break;
        case NONE: value = subfieldInstance.getValue(); break;
        case BOTH: value = subfieldInstance.resolve() + "##" + subfieldInstance.getValue(); break;
      }
      extractedValues.add(value);
    }

    return extractedValues;
  }

  public enum RESOLVE {
    NONE,
    RESOLVE,
    BOTH;
  }
}
