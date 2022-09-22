package de.gwdg.metadataqa.marc.utils.pica;

public class VocabularyEntry {
  private VocabularyPattern id;
  private String pica;
  private VocabularyPattern source;
  private String voc;
  private String notationPattern;
  private String namespace;
  private String prefLabelEn;
  private String prefLabelDe;
  private String uri;

  public VocabularyPattern getId() {
    return id;
  }

  public void setId(VocabularyPattern id) {
    this.id = id;
  }

  public String getPica() {
    return pica;
  }

  public void setPica(String pica) {
    this.pica = pica;
  }

  public VocabularyPattern getSource() {
    return source;
  }

  public void setSource(VocabularyPattern src) {
    this.source = src;
  }

  public String getVoc() {
    return voc;
  }

  public void setVoc(String voc) {
    this.voc = voc;
  }

  public String getNotationPattern() {
    return notationPattern;
  }

  public void setNotationPattern(String notationPattern) {
    this.notationPattern = notationPattern;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getPrefLabelEn() {
    return prefLabelEn;
  }

  public void setPrefLabelEn(String prefLabelEn) {
    this.prefLabelEn = prefLabelEn;
  }

  public String getPrefLabelDe() {
    return prefLabelDe;
  }

  public void setPrefLabelDe(String prefLabelDe) {
    this.prefLabelDe = prefLabelDe;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}
