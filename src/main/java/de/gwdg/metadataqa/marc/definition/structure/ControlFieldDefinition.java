package de.gwdg.metadataqa.marc.definition.structure;

import de.gwdg.metadataqa.marc.definition.controlpositions.ControlfieldPositionList;

import java.util.List;
import java.util.Map;

public abstract class ControlFieldDefinition extends DataFieldDefinition {

  protected ControlfieldPositionList controlfieldPositions;

  public Map<String, List<ControlfieldPositionDefinition>> getControlfieldPositions() {
    return controlfieldPositions.getPositions();
  }

  public List<ControlfieldPositionDefinition> getControlfieldPosition(String key) {
    return controlfieldPositions.get(key);
  }
}
