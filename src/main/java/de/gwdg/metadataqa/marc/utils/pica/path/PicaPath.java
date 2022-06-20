package de.gwdg.metadataqa.marc.utils.pica.path;

public class PicaPath {

  public enum SubfieldType {
    SINGLE,
    MULTI,
    ALL
  }


  private String path;
  private String tag = null;
  private String xtag = null;
  private Occurrence occurrence = null;
  private String subfields = null;
  private SubfieldType subfieldType = null;

  public PicaPath(String path, String tag, String xtag, Occurrence occurrence, String subfields, SubfieldType subfieldType) {
    this.path = path;
    this.tag = tag;
    this.xtag = xtag;
    this.occurrence = occurrence;
    this.subfields = subfields;
    this.subfieldType = subfieldType;
  }

  public String getPath() {
    return path;
  }

  public String getTag() {
    return tag;
  }

  public String getXtag() {
    return xtag;
  }

  public Occurrence getOccurrence() {
    return occurrence;
  }

  public String getSubfields() {
    return subfields;
  }

  public SubfieldType getSubfieldType() {
    return subfieldType;
  }
}
