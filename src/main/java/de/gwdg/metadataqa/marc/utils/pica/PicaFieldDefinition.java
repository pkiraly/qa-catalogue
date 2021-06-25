package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

public class PicaFieldDefinition extends DataFieldDefinition {

  public PicaFieldDefinition(PicaTagDefinition picaTagDefinition) {
    tag = picaTagDefinition.getTag();
    label = picaTagDefinition.getLabel();
    cardinality = picaTagDefinition.getCardinality();
    subfields = picaTagDefinition.getSubfields();
  }
}
