package de.gwdg.metadataqa.marc.utils.unimarc;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.controlpositions.ControlfieldPositionList;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.List;

public class UnimarcLeaderDefinition extends ControlFieldDefinition {

  public UnimarcLeaderDefinition() {
    tag = "Leader";
    label = "Leader";
    cardinality = Cardinality.Nonrepeatable;
  }

  public void setControlfieldPositions(List<ControlfieldPositionDefinition> controlfieldPositions) {
    ControlfieldPositionList positionList = new ControlfieldPositionList();
    positionList.setPositions(controlfieldPositions);
    this.controlfieldPositions = positionList;
  }
}
