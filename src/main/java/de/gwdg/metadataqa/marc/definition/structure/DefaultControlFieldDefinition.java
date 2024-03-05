package de.gwdg.metadataqa.marc.definition.structure;

import de.gwdg.metadataqa.marc.definition.controlpositions.ControlfieldPositionList;
import de.gwdg.metadataqa.marc.definition.controlpositions.DefaultControlfieldPositionList;

import java.util.List;

public class DefaultControlFieldDefinition extends ControlFieldDefinition {

  public static final String COMMON = "common";

  public DefaultControlFieldDefinition(DataFieldDefinition definition, ControlfieldPositionList controlfieldPositions) {
    this.tag = definition.getTag();
    this.label = definition.getLabel();
    this.cardinality = definition.getCardinality();
    this.controlfieldPositions = controlfieldPositions;
  }
  public DefaultControlFieldDefinition(Marc21DataFieldDefinition definition) {
    this.tag = definition.getTag();
    this.label = definition.getLabel();
    this.cardinality = definition.getCardinality();
    this.controlfieldPositions = new DefaultControlfieldPositionList(COMMON, definition.getPositions());
  }

  public List<ControlfieldPositionDefinition> getPositions() {
    return controlfieldPositions.get(COMMON);
  }
}
