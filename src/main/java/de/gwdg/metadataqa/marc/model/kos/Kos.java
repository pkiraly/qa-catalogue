package de.gwdg.metadataqa.marc.model.kos;

public class Kos {

  private String abbreviation;
  private int bartocId;
  private KosType type;
  private String bartocUrl;

  public Kos(String abbreviation, int bartocId, KosType type) {
    this.abbreviation = abbreviation;
    this.bartocId = bartocId;
    this.type = type;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public int getBartocId() {
    return bartocId;
  }

  public KosType getType() {
    return type;
  }

  public String getBartocUrl() {
    return "http://bartoc.org/en/node/" + bartocId;
  }
}
