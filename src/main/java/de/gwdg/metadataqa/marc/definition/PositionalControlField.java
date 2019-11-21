package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.Validatable;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.utils.keygenerator.PositionalControlFieldKeyGenerator;

import java.util.*;

public abstract class PositionalControlField extends ControlField implements Extractable, Validatable {

  protected MarcRecord marcRecord;
  protected Map<ControlSubfieldDefinition, String> valuesMap;
  protected List<ControlValue> valuesList;
  protected List<ValidationError> validationErrors;

  @Override
  public boolean validate(MarcVersion marcVersion) {
    boolean isValid = true;
    validationErrors = new ArrayList<>();
    for (ControlValue controlValue : valuesList) {
      if (!controlValue.validate(marcVersion)) {
        validationErrors.addAll(controlValue.getValidationErrors());
        isValid = false;
      }
    }
    return isValid;
  }

  public void setMarcRecord(MarcRecord marcRecord) {
    this.marcRecord = marcRecord;
    for (ControlValue value : valuesList) {
      value.setRecord(marcRecord);
    }
  }

  @Override
  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }

  @Override
  public Map<String, List<String>> getKeyValuePairs() {
    return getKeyValuePairs(SolrFieldType.MARC);
  }

  public Map<String, List<String>> getKeyValuePairs(
      String tag, String mqTag, SolrFieldType type) {
    Map<String, List<String>> map = new LinkedHashMap<>();
    PositionalControlFieldKeyGenerator keyGenerator = new PositionalControlFieldKeyGenerator(
      tag, mqTag, type);
    if (content != null) {
      map.put(keyGenerator.forTag(), Arrays.asList(content));
      for (Map.Entry<ControlSubfieldDefinition, String> entry : valuesMap.entrySet()) {
        ControlSubfieldDefinition controlSubfield = entry.getKey();
        String value = controlSubfield.resolve(entry.getValue());
        map.put(keyGenerator.forSubfield(controlSubfield), Arrays.asList(value));
      }
    }
    return map;
  }

  public Map<ControlSubfieldDefinition, String> getMap() {
    return valuesMap;
  }
}
