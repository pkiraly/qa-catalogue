package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.utils.keygenerator.PositionalControlFieldKeyGenerator;

import java.util.*;

public abstract class MarcPositionalControlField extends MarcControlField {

  protected ControlFieldDefinition definition;
  protected MarcRecord marcRecord;
  protected Map<ControlfieldPositionDefinition, String> valuesMap;
  protected List<ControlValue> valuesList;
  private Map<Integer, ControlValue> valuesByPosition = new LinkedHashMap<>();
  protected Leader.Type recordType;

  public MarcPositionalControlField(ControlFieldDefinition definition, String content) {
    this(definition, content, null);
  }

  public MarcPositionalControlField(ControlFieldDefinition definition,
                                    String content,
                                    Leader.Type recordType) {
    super(definition, content);
    this.definition = definition;
    this.recordType = recordType;
    valuesMap = new LinkedHashMap<>();
    valuesList = new ArrayList<>();
  }

  public void setMarcRecord(MarcRecord record) {
    this.marcRecord = record;
    for (ControlValue value : valuesList) {
      value.setRecord(marcRecord);
    }
  }

  protected abstract void processContent();

  public Map<String, List<String>> getKeyValuePairs(SolrFieldType type) {
    return getKeyValuePairs(definition.getTag(), definition.getMqTag(), type);
  }

  public Map<String, List<String>> getKeyValuePairs(String tag,
                                    String mqTag,
                                    SolrFieldType type) {
    Map<String, List<String>> map = new LinkedHashMap<>();
    PositionalControlFieldKeyGenerator keyGenerator =
      new PositionalControlFieldKeyGenerator(tag, mqTag, type);
    if (content != null) {
      map.put(keyGenerator.forTag(), Arrays.asList(content));
      for (Map.Entry<ControlfieldPositionDefinition, String> entry : valuesMap.entrySet()) {
        ControlfieldPositionDefinition position = entry.getKey();
        String value = position.resolve(entry.getValue());
        map.put(keyGenerator.forSubfield(position), Arrays.asList(value));
      }
    }
    return map;
  }

  public Map<ControlfieldPositionDefinition, String> getMap() {
    return valuesMap;
  }

  public List<ControlValue> getValuesList() {
    return valuesList;
  }

  public String getLabel() {
    return definition.getLabel();
  }

  public String getTag() {
    return definition.getTag();
  }

  public String getMqTag() {
    return definition.getMqTag();
  }

  public Cardinality getCardinality() {
    return definition.getCardinality();
  }

  @Override
  public boolean validate(MarcVersion marcVersion) {
    boolean isValid = true;
    validationErrors = new ArrayList<>();
    if (!initializationErrors.isEmpty()) {
      validationErrors.addAll(initializationErrors);
    }
    for (ControlValue controlValue : valuesList) {
      if (!controlValue.validate(marcVersion)) {
        validationErrors.addAll(controlValue.getValidationErrors());
        isValid = false;
      }
    }
    return isValid;
  }

  @Override
  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }

  protected void registerControlValue(ControlValue controlValue) {
    valuesList.add(controlValue);
    valuesByPosition.put(controlValue.getDefinition().getPositionStart(), controlValue);
  }

  public ControlValue getControlValueByPosition(int position) {
    return valuesByPosition.get(position);
  }
}
