package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcField {
  private String code;
  private String type;
  private String label;
  private SubfieldDefinition indicator1;
  private SubfieldDefinition indicator2;
  private Map<String, SubfieldDefinition> subfields = new HashMap<>();
  private List<SubfieldDefinition> subfieldList = new ArrayList<>();
  private List<String> validSubfieldCodes = new ArrayList<>();

  public MarcField(String code, String type, String label) {
    this.code = code;
    this.type = type;
    this.label = label;
  }

  public void addSubfield(String code, String type, String label) {
    SubfieldDefinition subfield = new SubfieldDefinition(code, label, type);
    switch (code) {
      case "ind1": indicator1 = subfield; break;
      case "ind2": indicator2 = subfield; break;
      default:
        subfields.put(code, subfield);
        subfieldList.add(subfield);
        validSubfieldCodes.add(code);
        break;
    }
  }

  public String getCode() {
    return code;
  }

  public void setField(String field) {
    this.code = field;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public SubfieldDefinition getIndicator1() {
    return indicator1;
  }

  public void setIndicator1(SubfieldDefinition indicator1) {
    this.indicator1 = indicator1;
  }

  public SubfieldDefinition getIndicator2() {
    return indicator2;
  }

  public void setIndicator2(SubfieldDefinition indicator2) {
    this.indicator2 = indicator2;
  }

  public Map<String, SubfieldDefinition> getSubfields() {
    return subfields;
  }

  public List<SubfieldDefinition> getSubfieldList() {
    return subfieldList;
  }

  public void setSubfieldList(List<SubfieldDefinition> subfields) {
    this.subfieldList = subfields;
  }

  public List<String> getValidSubfieldCodes() {
    return validSubfieldCodes;
  }

  public void setValidSubfieldCodes(List<String> validSubfieldCodes) {
    this.validSubfieldCodes = validSubfieldCodes;
  }
}
