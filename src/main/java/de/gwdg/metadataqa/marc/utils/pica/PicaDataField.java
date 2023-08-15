package de.gwdg.metadataqa.marc.utils.pica;

import org.marc4j.marc.Subfield;
import org.marc4j.marc.impl.DataFieldImpl;

public class PicaDataField extends DataFieldImpl {

  private String occurrence;

  /**
   * Creates a new <code>DataField</code> and sets the tag name and the first and second indicator.
   *
   * @param tag  The tag name
   */
  public PicaDataField(String tag) {
    super(tag, ' ', ' ');
  }

  public PicaDataField(String tag, String occurrence) {
    this(tag);
    this.occurrence = occurrence;
  }

  public String getOccurrence() {
    return occurrence;
  }

  public void setOccurrence(String occurrence) {
    this.occurrence = occurrence;
  }

  /**
   * Get tag with occurrence (if exists)
   * @return tag with occurrence (if exists)
   */
  public String getFullTag() {
    final StringBuilder sb = new StringBuilder();
    sb.append(getTag());
    if (occurrence != null)
      sb.append("/" + getOccurrence());
    return sb.toString();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append(getTag());
    if (occurrence != null)
      sb.append("/" + getOccurrence());
    if (!getSubfields().isEmpty())
      sb.append(' ');
    // sb.append(getIndicator1());
    // sb.append(getIndicator2());

    for (final Subfield sf : getSubfields()) {
      sb.append(sf.toString());
    }
    return sb.toString();
  }
}
