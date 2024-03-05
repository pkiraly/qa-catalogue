package de.gwdg.metadataqa.marc.definition.controlpositions;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.List;

public class DefaultControlfieldPositionList extends ControlfieldPositionList {

  public DefaultControlfieldPositionList(String name, List<ControlfieldPositionDefinition> list) {
    positions.put(name, list);
  }
}
