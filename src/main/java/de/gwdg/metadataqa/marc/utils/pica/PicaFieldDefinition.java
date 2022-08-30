package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

public class PicaFieldDefinition extends DataFieldDefinition {

  private String modified;
  private String pica3;
  private String occurrence;
  private PicaRange range;
  private String id;

  public PicaFieldDefinition(PicaTagDefinition picaTagDefinition) {
    tag = picaTagDefinition.getTag();
    label = picaTagDefinition.getLabel();
    cardinality = picaTagDefinition.getCardinality();
    subfields = picaTagDefinition.getSubfields();
    descriptionUrl = picaTagDefinition.getDescriptionUrl();
    modified = picaTagDefinition.getModified();
    pica3 = picaTagDefinition.getPica3();
    occurrence = picaTagDefinition.getOccurrence();
    id = picaTagDefinition.getId();
    range = picaTagDefinition.getRange();
    indexSubfields();
  }

  public String getModified() {
    return modified;
  }

  public String getPica3() {
    return pica3;
  }

  public String getOccurrence() {
    return occurrence;
  }

  public PicaRange getRange() {
    return range;
  }

  public String getId() {
    return id;
  }

  public boolean inRange(String occurence) {
    if (range != null) {
      if (range.getUnitLength() == occurence.length()) {
        if (range.isHasRange()) {
          if (range.getStart().compareTo(occurence) == 1 || range.getEnd().compareTo(occurence) == -1)
            return false;
          return true;
        } else {
          return range.getStart().equals(occurence);
        }
      }
    }
    return false;
  }
}
