package de.gwdg.metadataqa.marc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.validator.ClassificationReferenceValidator;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;

import de.gwdg.metadataqa.marc.definition.tags.control.Control001Definition;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarcRecord implements Extractable, Validatable, Serializable {

  private static final Logger logger = Logger.getLogger(MarcRecord.class.getCanonicalName());
  private static final Pattern dataFieldPattern = Pattern.compile("^(\\d\\d\\d)\\$(.*)$");
  private static final Pattern positionalPattern = Pattern.compile("^(Leader|00[678])/(.*)$");
  private static final List<String> simpleControlTags = Arrays.asList("001", "003", "005");

  private Leader leader;
  private MarcControlField control001;
  private MarcControlField control003;
  private MarcControlField control005;
  private Control006 control006;
  private Control007 control007;
  private Control008 control008;
  private List<DataField> datafields;
  private Map<String, List<DataField>> datafieldIndex;
  private Map<String, List<MarcControlField>> controlfieldIndex;
  Map<String, List<String>> mainKeyValuePairs;
  private List<String> errors = null;
  private List<ValidationError> validationErrors = null;

  public enum RESOLVE {
    NONE,
    RESOLVE,
    BOTH;
  }

  private List<String> unhandledTags;

  public MarcRecord() {
    datafields = new ArrayList<>();
    datafieldIndex = new TreeMap<>();
    controlfieldIndex = new TreeMap<>();
    unhandledTags = new ArrayList<>();
  }

  public MarcRecord(String id) {
    this();
    control001 = new MarcControlField(Control001Definition.getInstance(), id);
  }

  public void addDataField(DataField dataField) {
    dataField.setRecord(this);
    indexField(dataField);
    datafields.add(dataField);
  }

  private void indexField(DataField dataField) {
    String tag = dataField.getTag();

    if (!datafieldIndex.containsKey(tag))
      datafieldIndex.put(tag, new ArrayList<>());

    datafieldIndex.get(tag).add(dataField);
  }

  public void addUnhandledTags(String tag) {
    unhandledTags.add(tag);
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
    leader.setMarcRecord(this);
  }

  public Leader getLeader() {
    return leader;
  }

  public Leader.Type getType() {
    return leader.getType();
  }

  public MarcControlField getControl001() {
    return control001;
  }

  public MarcRecord setControl001(MarcControlField control001) {
    this.control001 = control001;
    controlfieldIndex.put(control001.definition.getTag(), Arrays.asList(control001));
    return this;
  }

  public MarcControlField getControl003() {
    return control003;
  }

  public void setControl003(MarcControlField control003) {
    this.control003 = control003;
    controlfieldIndex.put(control003.definition.getTag(), Arrays.asList(control003));
  }

  public MarcControlField getControl005() {
    return control005;
  }

  public void setControl005(MarcControlField control005) {
    this.control005 = control005;
    controlfieldIndex.put(control005.definition.getTag(), Arrays.asList(control005));
  }

  public Control006 getControl006() {
    return control006;
  }

  public void setControl006(Control006 control006) {
    this.control006 = control006;
    control006.setMarcRecord(this);
    controlfieldIndex.put(control006.definition.getTag(), Arrays.asList(control006));
  }

  public Control007 getControl007() {
    return control007;
  }

  public void setControl007(Control007 control007) {
    this.control007 = control007;
    control007.setMarcRecord(this);
    controlfieldIndex.put(control007.definition.getTag(), Arrays.asList(control007));
  }

  public Control008 getControl008() {
    return control008;
  }

  public void setControl008(Control008 control008) {
    this.control008 = control008;
    control008.setMarcRecord(this);
    controlfieldIndex.put(control008.definition.getTag(), Arrays.asList(control008));
  }

  public String getId() {
    return control001.getContent();
  }

  public List<MarcControlField> getControlfields() {
    return Arrays.asList(
      control001, control003, control005, control006, control007, control008
    );
  }

  public List<MarcControlField> getSimpleControlfields() {
    return Arrays.asList(
      control001, control003, control005
    );
  }

  public List<MarcPositionalControlField> getPositionalControlfields() {
    return Arrays.asList(
      control006, control007, control008
    );
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
    return getKeyValuePairs(type, false);
  }

  public Map<String, List<String>> getKeyValuePairs(SolrFieldType type,
                                                    boolean withDeduplication) {
    if (mainKeyValuePairs == null) {
      mainKeyValuePairs = new LinkedHashMap<>();

      mainKeyValuePairs.put("type", Arrays.asList(getType().getValue()));
      mainKeyValuePairs.putAll(leader.getKeyValuePairs(type));

      for (MarcControlField controlField : getControlfields()) {
        if (controlField != null)
          mainKeyValuePairs.putAll(controlField.getKeyValuePairs(type));
      }

      for (DataField field : datafields) {
        Map<String, List<String>> keyValuePairs = field.getKeyValuePairs(type);
        for (Map.Entry<String, List<String>> entry : keyValuePairs.entrySet()) {
          String key = entry.getKey();
          List<String> values = entry.getValue();
          if (mainKeyValuePairs.containsKey(key)) {
            mainKeyValuePairs.put(key, mergeValues(
              new ArrayList<String>(mainKeyValuePairs.get(key)),
              values,
              withDeduplication
            ));
          } else {
            mainKeyValuePairs.put(key, values);
          }
        }
      }
    }

    return mainKeyValuePairs;
  }

  @NotNull
  private List<String> mergeValues(List<String> existingValues,
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

    StringBuilder text = new StringBuilder();
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("leader", leader.getContent());
    for (MarcControlField field : getControlfields()) {
      if (field != null)
        map.put(field.getDefinition().getTag(), field.getContent());
    }
    for (DataField field : datafields) {
      if (field != null) {
        Map<String, Object> fieldMap = new LinkedHashMap<>();
        fieldMap.put("ind1", field.getInd1());
        fieldMap.put("ind2", field.getInd2());

        Map<String, String> subfields = new LinkedHashMap<>();
        for (MarcSubfield subfield : field.getSubfields()) {
          subfields.put(subfield.getCode(), subfield.getValue());
        }
        fieldMap.put("subfields", subfields);
        String tag = field.getDefinition().getTag();
        if (!map.containsKey(tag)) {
          map.put(tag, new ArrayList<Map<String, Object>>());
        }
        ((ArrayList)map.get(tag)).add(fieldMap);
      }
    }

    String json = null;
    try {
      json = mapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return json;
  }

  @Override
  public boolean validate(MarcVersion marcVersion) {
    return validate(marcVersion, false);
  }

  public boolean validate(MarcVersion marcVersion, boolean isSummary) {
    errors = new ArrayList<>();
    validationErrors = new ArrayList<>();
    boolean isValidRecord = true;
    boolean isValidComponent;

    isValidComponent = leader.validate(marcVersion);
    if (!isValidComponent) {
      errors.addAll(leader.getErrors());
      List<ValidationError> leaderErrors = leader.getValidationErrors();
      for (ValidationError leaderError : leaderErrors)
        if (leaderError.getRecordId() == null)
          leaderError.setRecordId(getId());
      validationErrors.addAll(leaderErrors);
      isValidRecord = isValidComponent;
    }

    if (!unhandledTags.isEmpty()) {
      if (isSummary) {
        for (String tag : unhandledTags) {
          validationErrors.add(
            new ValidationError(getId(), tag,
              ValidationErrorType.FIELD_UNDEFINED, tag, null));
          errors.add(String.format("Unhandled tag: %s", tag));
        }
      } else {
        Map<String, Integer> tags = new LinkedHashMap<>();
        for (String tag : unhandledTags) {
          if (!tags.containsKey(tag))
            tags.put(tag, 0);
          tags.put(tag, tags.get(tag) + 1);
        }
        List<String> unhandledTagsList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : tags.entrySet()) {
          String tag = entry.getKey();
          if (entry.getValue() == 1)
            unhandledTagsList.add(tag);
          else
            unhandledTagsList.add(String.format("%s (%d*)", tag, entry.getValue()));
        }
        for (String tag : unhandledTagsList) {
          validationErrors.add(new ValidationError(
            getId(), tag, ValidationErrorType.FIELD_UNDEFINED, tag, null));
          errors.add(String.format("Unhandled tag: %s", tag));
        }
      }

      isValidRecord = false;
    }

    // TODO: use reflection to get all validator class
    ValidatorResponse validatorResponse;
    /*
    validatorResponse = ClassificationReferenceValidator.validate(this);
    if (!validatorResponse.isValid()) {
      errors.addAll(validatorResponse.getErrors());
      isValidRecord = false;
    }
    */

    for (MarcControlField controlField : getControlfields()) {
      if (controlField != null) {
        // System.err.println(controlField.definition.getTag());
        isValidComponent = ((Validatable)controlField).validate(marcVersion);
        if (!isValidComponent) {
          validationErrors.addAll(((Validatable)controlField).getValidationErrors());
          errors.addAll(((Validatable)controlField).getErrors());
          isValidRecord = isValidComponent;
        }
      }
    }

    Map<DataFieldDefinition, Integer> repetitionCounter = new HashMap<>();
    for (DataField field : datafields) {
      if (!repetitionCounter.containsKey(field.getDefinition())) {
        repetitionCounter.put(field.getDefinition(), 0);
      }
      repetitionCounter.put(field.getDefinition(), repetitionCounter.get(field.getDefinition()) + 1);
      if (!field.validate(marcVersion)) {
        isValidRecord = false;
        validationErrors.addAll(field.getValidationErrors());
        errors.addAll(field.getErrors());
      }

      validatorResponse = ClassificationReferenceValidator.validate(field);
      if (!validatorResponse.isValid()) {
        validationErrors.addAll(validatorResponse.getValidationErrors());
        errors.addAll(validatorResponse.getErrors());
        isValidRecord = false;
      }
    }

    for (Map.Entry<DataFieldDefinition, Integer> entry : repetitionCounter.entrySet()) {
      DataFieldDefinition fieldDefinition = entry.getKey();
      Integer count = entry.getValue();
      if (count > 1
        && fieldDefinition.getCardinality().equals(Cardinality.Nonrepeatable)) {
        validationErrors.add(new ValidationError(getId(), fieldDefinition.getTag(),
          ValidationErrorType.FIELD_NONREPEATABLE,
          String.format("there are %d instances", count),
          fieldDefinition.getDescriptionUrl()
        ));
        errors.add(String.format(
          "%s is not repeatable, however there are %d instances (%s)",
          fieldDefinition.getTag(),
          count, fieldDefinition.getDescriptionUrl()
        ));
        isValidRecord = false;
      }
    }

    return isValidRecord;
  }

  @Override
  public List<String> getErrors() {
    return errors;
  }

  @Override
  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }

  public List<String> search(String path, String query) {
    List<String> results = new ArrayList<>();
    if (path.equals("001") || path.equals("003") || path.equals("005")) {
      searchControlField(path, query, results);
    } else if (path.startsWith("tag006")) {
      searchPositionalControlField(control006, path, query, results);
    } else if (path.startsWith("tag007")) {
      searchPositionalControlField(control007, path, query, results);
    } else if (path.startsWith("tag008")) {
      searchPositionalControlField(control008, path, query, results);
    } else {
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
      matcher = positionalPattern.matcher(path);
      if (matcher.matches()) {
        searchByPosition(query, results, matcher);
      }
    }
    return results;
  }

  public List<String> select(MarcSpec selector) {
    List<String> results = new ArrayList<>();
    if (controlfieldIndex.containsKey(selector.getFieldTag())) {
      for (MarcControlField field : controlfieldIndex.get(selector.getFieldTag())) {
        if (field == null)
          continue;
        if (!simpleControlTags.contains(field.definition.getTag())) {
          // TODO: check control subfields
        }
        results.add(field.getContent());
      }

    } else if (datafieldIndex.containsKey(selector.getFieldTag())) {
      for (DataField field : datafieldIndex.get(selector.getFieldTag())) {
        if (field == null)
          continue;
        for (String subfieldCode : selector.getSubfieldsAsList()) {
          List<MarcSubfield> subfields = field.getSubfield(subfieldCode);
          if (subfields == null)
            continue;
          for (MarcSubfield subfield : subfields) {
            results.add(subfield.getValue());
          }
        }
      }
    }
    else if (selector.getFieldTag().equals("008")) {
      if (selector.getCharStart() != null) {
        ControlSubfieldDefinition definition = control008.getSubfieldByPosition(selector.getCharStart());
        results.add(control008.getMap().get(definition));
      } else {
        results.add(control008.getContent());
      }
    }
    return results;
  }

  private void searchByPosition(String query, List<String> results, Matcher matcher) {
    String tag = matcher.group(1);
    String position = matcher.group(2);
    int start, end;
    if (position.contains("-")) {
      String[] parts = position.split("-", 2);
      start = Integer.parseInt(parts[0]);
      end = Integer.parseInt(parts[1]);
    } else {
      start = Integer.parseInt(position);
      end = start + 1;
    }
    String content = null;
    if (tag.equals("Leader")) {
      content = leader.getLeaderString();
    } else {
      MarcControlField controlField = null;
      switch (tag) {
        case "006": controlField = control006; break;
        case "007": controlField = control007; break;
        case "008": controlField = control008; break;
        default: break;
      }
      if (controlField != null)
        content = controlField.getContent();
    }

    if (content != null && content.substring(start, end).equals(query)) {
      results.add(content.substring(start, end));
    }
  }

  private boolean searchDatafield(String query, List<String> results,
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

  private void searchControlField(String path, String query, List<String> results) {
    MarcControlField controlField = null;
    switch (path) {
      case "001": controlField = control001; break;
      case "003": controlField = control003; break;
      case "005": controlField = control005; break;
      default: break;
    }
    if (controlField != null && controlField.getContent().equals(query))
      results.add(controlField.getContent());
  }

  private void searchPositionalControlField(MarcPositionalControlField controlField,
                                            String path, String query, List<String> results) {
    if (controlField != null) {
      Map<ControlSubfieldDefinition, String> map = controlField.getMap();
      for (ControlSubfieldDefinition subfield : controlField.getMap().keySet()) {
        if (subfield.getId().equals(path)) {
          if (map.get(subfield).equals(query))
            results.add(map.get(subfield));
          break;
        }
      }
    }
  }
}
