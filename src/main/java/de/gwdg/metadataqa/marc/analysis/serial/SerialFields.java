package de.gwdg.metadataqa.marc.analysis.serial;

public enum SerialFields {
  ID("id", "id"),
  DATE_1_UNKNOWN("Date 1 is unknown", "date1-unknown"),
  COUNTRY_UNKNOWN("Country of publication is unknown", "country-unknown"),
  LANGUAGE("Publication language is unknown", "language"),
  AUTH("Authentication code is empty", "auth"),
  ENCODING_LEVEL_FULL("Encoding level is full", "enc-full"),
  ENCODING_LEVEL_MINIMAL("Encoding level is M, L, K or 7", "enc-mlk7"),
  HAS_006("006 is present", "006"),
  HAS_PUBLISHER_260("Publisher 260 (AACR2) is present", "260"),
  HAS_PUBLISHER_264("Publisher 264 (RDA) is present", "264"),
  HAS_PUBLICATION_FREQUENCY_310("Publication frequency is present", "310"),
  HAS_CONTENT_TYPE_336("Content Type (336) is present", "336"),
  HAS_DATES_OF_PUBLICATION_362("Dates of Publication (362) is present", "362"),
  HAS_SOURCE_OF_DESCRIPTION_588("Source of Description Note (588) is present", "588"),
  HAS_NO_SUBJECT("No subject is present", "no-subject"),
  HAS_SUBJECT("Subject is present", "has-subject"),
  PCC("Authentication Code is pcc", "pcc"),
  DATE_1_STARTS_WITH_0("First date (008/07) startes with 0", "date1-0"),
  INACTIVE_TITLE("Title is inactive - no date2", "date2-empty"), // TODO check the machine field. Not sure what it is
  ABBREVIATED("Encoding level is abbreviated", "abbreviated"),
  TOTAL("total", "total");

  private final String label;

  // FIXME The name "machine" isn't very descriptive. It's not clear what it means.
  private final String machine;

  // FIXME This field is not set anywhere. It's always false.
  private final boolean isClassification = false;

  SerialFields(String label, String machine) {
    this.label = label;
    this.machine = machine;
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
