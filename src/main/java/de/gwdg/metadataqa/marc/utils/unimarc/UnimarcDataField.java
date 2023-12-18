package de.gwdg.metadataqa.marc.utils.unimarc;

import org.marc4j.marc.impl.DataFieldImpl;

/**
 * Represents a data field in a UNIMARC record. A data field consists of a tag, two indicators and
 * variable length list of subfields.
 * A schema counterpart of this class is {@link de.gwdg.metadataqa.marc.utils.unimarc.UnimarcFieldDefinition},
 * which represents a definition of a UNIMARC field in the scehma.
 */
public class UnimarcDataField extends DataFieldImpl {
  private String occurrence;

  /**
   * Creates a new <code>DataField</code> and sets the tag name and the first and second indicator codes.
   *
   * @param tag A UNIMARC tag name (e.g. 100)
   * @param ind1 The first indicator code
   * @param ind2 The second indicator code
   */
  public UnimarcDataField(String tag, char ind1, char ind2) {
    super(tag, ind1, ind2);
  }
}
