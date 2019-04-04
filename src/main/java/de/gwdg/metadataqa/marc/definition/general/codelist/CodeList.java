package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.definition.Validator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeList implements Validator, Serializable {
  protected String name;
  protected String url;
  protected List<Code> codes;
  protected Map<String, Code> index = new HashMap<>();

  protected void indexCodes() {
    for (Code code : codes) {
      index.put(code.getCode(), code);
    }
  }

  public List<Code> getCodes() {
    return codes;
  }

  public Code getCode(String code) {
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
  public List<String> getErrors() {
    return null;
  }

  @Override
  public List<ValidationError> getValidationErrors() {
    return null;
  }
}
