package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import org.apache.commons.lang3.StringUtils;

public class PicaFieldDefinition extends DataFieldDefinition {

  private String modified;
  private String pica3;
  private String occurrence;
  private PicaRange range;
  private String id;
  private String counter;

  private PicaFieldDefinition() {}

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
    counter = picaTagDefinition.getCounter();
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

  public String getCounter() {
    return counter;
  }

  public PicaRange getRange() {
    return range;
  }

  public String getId() {
    return id;
  }

  public boolean inRange(String occurrence) {
    if (range != null && range.getUnitLength() == occurrence.length())
      if (range.isHasRange())
        return range.getStart().compareTo(occurrence) <= 0 && range.getEnd().compareTo(occurrence) >= 0;
      else
        return range.getStart().equals(occurrence);

    return false;
  }

  public String getTagWithOccurrence() {
    if (StringUtils.isBlank(occurrence))
      return tag;
    return tag + "/" + occurrence;
  }

  @Override
  public String getExtendedTag() {
    return getTagWithOccurrence();
  }

  public PicaFieldDefinition copyWithChangesId() {
    PicaFieldDefinition other = new PicaFieldDefinition();
    other.id = getId().replace("/00", "");
    other.tag = getTag();
    other.label = getLabel();
    other.cardinality = getCardinality();
    other.subfields = getSubfields();
    other.descriptionUrl = getDescriptionUrl();
    other.modified = getModified();
    other.pica3 = getPica3();
    other.occurrence = getOccurrence();
    other.counter = getCounter();
    other.range = getRange();
    other.indexSubfields();

    return other;
  }

  @Override
  public String toString() {
    return "PicaFieldDefinition{" +
      super.toString().replace("DataFieldDefinition{", "").replaceFirst(".$", ", ") +
      "modified='" + modified + '\'' +
      ", pica3='" + pica3 + '\'' +
      ", occurrence='" + occurrence + '\'' +
      ", range=" + range +
      ", id='" + id + '\'' +
      '}';
  }
}
