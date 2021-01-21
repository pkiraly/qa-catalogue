package de.gwdg.metadataqa.marc.definition.controlpositions;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ControlfieldPositionList {

  protected Map<String, List<ControlfieldPositionDefinition>> positions = new TreeMap<>();

  public Map<String, List<ControlfieldPositionDefinition>> getPositions() {
    return positions;
  }

  public List<ControlfieldPositionDefinition> get(String category) {
    return positions.get(category);
  }
}
