package de.gwdg.metadataqa.marc.utils.pica.path;

public class PicaPath {

  private String path;
  private String tag = null;
  private String xtag = null;
  private Occurrence occurrence = null;
  private Subfields subfields = null;

  public PicaPath(String path, String tag, String xtag, Occurrence occurrence, Subfields subfields) {
    this.path = path;
    this.tag = tag;
    this.xtag = xtag;
    this.occurrence = occurrence;
    this.subfields = subfields;
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

  public Subfields getSubfields() {
    return subfields;
  }

  @Override
  public String toString() {
    return "PicaPath{" +
      "path='" + path + '\'' +
      ", tag='" + tag + '\'' +
      ", xtag='" + xtag + '\'' +
      ", occurrence=" + occurrence +
      ", subfields=" + subfields +
      '}';
  }
}
