package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.definition.controlsubfields.ControlSubfieldList;

import java.util.List;
import java.util.Map;

public abstract class ControlFieldDefinition extends DataFieldDefinition {

  protected ControlSubfieldList controlSubfields;

  public Map<String, List<ControlSubfieldDefinition>> getControlSubfields() {
    return controlSubfields.getSubfields();
  }

  public List<ControlSubfieldDefinition> getControlSubfields(String key) {
    return controlSubfields.get(key);
  }
}
