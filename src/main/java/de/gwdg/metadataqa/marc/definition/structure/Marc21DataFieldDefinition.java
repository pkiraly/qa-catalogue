package de.gwdg.metadataqa.marc.definition.structure;

import de.gwdg.metadataqa.marc.definition.Cardinality;

import java.util.List;

public class Marc21DataFieldDefinition extends DataFieldDefinition {
  boolean fixed;
  List<ControlfieldPositionDefinition> positions;

  public Marc21DataFieldDefinition(String tag, String label, boolean repeatable, boolean fixed) {
    this.tag = tag;
    this.label = label;
    this.fixed = fixed;
    cardinality = repeatable ? Cardinality.Repeatable : Cardinality.Nonrepeatable;
  }

  public void setInd1(Indicator indicator) {
    this.ind1 = indicator;
    this.ind1.indexCodes();
  }

  public void setInd2(Indicator indicator) {
    this.ind2 = indicator;
    this.ind1.indexCodes();
  }

  public void setSubfields(List<SubfieldDefinition> subfields) {
    this.subfields = subfields;
    indexSubfields();
  }

  public void setPositions(List<ControlfieldPositionDefinition> positions) {
    this.positions = positions;
  }

  public List<ControlfieldPositionDefinition> getPositions() {
    return positions;
  }

  public boolean isFixed() {
    return fixed;
  }
}
