package de.gwdg.metadataqa.marc.analysis;

public enum SerialFields {
  ID("id", "id"),
  Date1Unknown("Date 1 is unknown", "date1-unknown"),
  CountryUnknown("Country of publication is unknown", "country-unknown"),
  Language("Publication language is unknown", "language"),
  Auth("Authentication code is empty", "auth"),
  EncodingLevelFull("Encoding level is full", "enc-full"),
  EncodingLevelMinimal("Encoding level is M, L, K or 7", "enc-mlk7"),
  Has006("006 is present", "006"),
  HasPublisher260("Publisher 260 (AACR2) is present", "260"),
  HasPublisher264("Publisher 264 (RDA) is present", "264"),
  HasPublicationFrequency310("Publication frequency is present", "310"),
  HasContentType336("Content Type (336) is present", "336"),
  HasDatesOfPublication362("Dates of Publication (362) is present", "362"),
  HasSourceOfDescription588("Source of Description Note (588) is present", "588"),
  HasNoSubject("No subject is present", "no-subject"),
  HasSubject("Subject is present", "has-subject"),
  PCC("Authentication Code is pcc", "pcc"),
  Date1StartsWith0("First date (008/07) startes with 0", "date1-0"),
  Abbreviated("Encoding level is abbreviated", "abbreviated"),
  TOTAL("total", "total")
  ;

  private String label;
  private String machine;
  private boolean isClassification = false;

  SerialFields(String label, String machine) {
    this.label = label;
    this.machine = machine;
  }

  SerialFields(String label, String machine, boolean isClassification) {
    this(label, machine);
    this.isClassification = isClassification;
  }

  public String getLabel() {
    return label;
  }

  public String getMachine() {
    return machine;
  }

  public boolean isClassification() {
    return isClassification;
  }

  @Override
  public String toString() {
    return "SerialFields{" +
      "label='" + label + '\'' +
      ", machine='" + machine + '\'' +
      ", isClassification=" + isClassification +
      '}';
  }
}
