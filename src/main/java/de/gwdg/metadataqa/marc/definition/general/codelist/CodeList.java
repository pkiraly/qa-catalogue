package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.definition.Validator;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeList implements Validator, Serializable {
  protected String name;
  protected String url;
  protected List<EncodedValue> codes;
  protected Map<String, EncodedValue> index = new HashMap<>();

  protected void indexCodes() {
    for (EncodedValue code : codes) {
      index.put(code.getCode(), code);
    }
  }

  public List<EncodedValue> getCodes() {
    return codes;
  }

  public EncodedValue getCode(String code) {
    return index.getOrDefault(code, null);
  }

  public boolean isValid(String code) {
    return index.containsKey(code);
  }

  public boolean isValid(String code, MarcSubfield field) {
    return index.containsKey(code);
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public List<ValidationError> getValidationErrors() {
    return Collections.emptyList();
  }
}
