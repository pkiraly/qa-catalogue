package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

public class PicaFieldDefinition extends DataFieldDefinition {

  private String modified;
  private String pica3;
  private String occurence;

  public PicaFieldDefinition(PicaTagDefinition picaTagDefinition) {
    tag = picaTagDefinition.getTag();
    label = picaTagDefinition.getLabel();
    cardinality = picaTagDefinition.getCardinality();
    subfields = picaTagDefinition.getSubfields();
    descriptionUrl = picaTagDefinition.getDescriptionUrl();
    modified = picaTagDefinition.getModified();
    pica3 = picaTagDefinition.getPica3();
    occurence = picaTagDefinition.getOccurence();
    indexSubfields();
  }

  public String getModified() {
    return modified;
  }

  public String getPica3() {
    return pica3;
  }

  public String getOccurence() {
    return occurence;
  }
}
