package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.dao.Control006;
import de.gwdg.metadataqa.marc.dao.Control008;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;

import java.util.LinkedList;
import java.util.List;

/**
 * This class heavily based on the class published in
 * Jamie Carlstone (2017) Scoring the Quality of E-Serials MARC Records Using Java,
 * Serials Review, 43:3-4, pp. 271-277, DOI: 10.1080/00987913.2017.1350525
 * https://www.tandfonline.com/doi/full/10.1080/00987913.2017.1350525
 */
public class Serial {
  private Marc21Record marcRecord;
  private SerialScores scores;

  private static List<String> headers = new LinkedList<>();
  static {
    for (SerialFields field : SerialFields.values()) {
      headers.add(field.getMachine());
    }
  }

  public Serial(Marc21Record marcRecord) {
    this.marcRecord = marcRecord;
    scores = new SerialScores();
  }

  public static List<String> getHeader() {
    return headers;
  }

  @Override
  public int hashCode() {
    final var prime = 31;
    int result = super.hashCode();

    // datesOfPublication
    result = prime * result + ((marcRecord.getDatafield("362") == null)
      ? 0
      : marcRecord.getDatafield("362").hashCode());

    // frequency
    result = prime * result + ((marcRecord.getDatafield("310") == null)
      ? 0
      : marcRecord.getDatafield("310").hashCode());

    // issn
    result = prime * result + ((marcRecord.getDatafield("022") == null)
      ? 0
      : marcRecord.getDatafield("022").hashCode());

    // sourceOfDescription
    result = prime * result + ((marcRecord.getDatafield("588") == null)
      ? 0
      : marcRecord.getDatafield("588").hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    Serial other = (Serial) obj;
    if (getDatesOfPublication362() == null) {
      if (other.getDatesOfPublication362() != null)
        return false;
    } else if (!getDatesOfPublication362().equals(other.getDatesOfPublication362()))
      return false;
    if (getFrequency310() == null) {
      if (other.getFrequency310() != null)
        return false;
    } else if (!getFrequency310().equals(other.getFrequency310()))
      return false;

    if (getIssn022() == null) {
      if (other.getIssn022() != null)
        return false;
    } else if (!getIssn022().equals(other.getIssn022()))
      return false;

    if (getSourceOfDescription588() == null) {
      if (other.getSourceOfDescription588() != null)
        return false;
    } else if (!getSourceOfDescription588().equals(other.getSourceOfDescription588()))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Serial [issn022=" + getIssn022()
      + ", frequency310=" + getFrequency310()
      + ", datesOfPublication362=" + getDatesOfPublication362()
      + ", sourceOfDescription588=" + getSourceOfDescription588()
      + "]";
  }

  private boolean empty(List<DataField> list) {
    if (list == null || list.isEmpty())
      return true;
    return list.get(0).getSubfields().isEmpty();
  }

  private String first(List<DataField> list) {
    if (list == null || list.isEmpty())
      return null;
    return list.get(0).toString();
  }

  public List<DataField> getIssn022() {
    return marcRecord.getDatafield("022");
  }

  public List<DataField> getFrequency310() {
    return marcRecord.getDatafield("310");
  }

  public List<DataField> getDatesOfPublication362() {
    return marcRecord.getDatafield("362");
  }

  public List<DataField> getSourceOfDescription588() {
    return marcRecord.getDatafield("588");
  }

  public String getEncodingLevel() {
    return marcRecord.getLeader().getEncodingLevel().getValue();
  }

  public List<Integer> determineRecordQualityScore() {
    var control008 = marcRecord.getControl008();

    if (control008 instanceof Control008) {
      detectUnknownDate1((Control008) control008);
      detectUnknownCountry((Control008) control008);
      detectUnkownLanguage((Control008) control008);
      detectAutomaticDiscards((Control008) control008);
      detectInactiveTitles((Control008) control008);
      detectDateStartsWith0((Control008) control008);
    }
    detectAuthenticationCode();
    detectEncodingLevel();
    detect008();
    detectPublisher();
    detectPublisherRDA();
    detectPublicationFrequency();
    detectContentTypeRDA();
    detectDateOfPublication();
    detectDescriptionSource();
    detectLocSubjectHeadings();
    detectPPC();
    detectDeletion();

    scores.calculateTotal();
    return scores.asList();
  }

  private void detectDateStartsWith0(Control008 control008) {
    // Discard any with a first date of "0"
    if (control008 != null
        && control008.getTag008all07() != null
        && control008.getTag008all07().getValue().matches("0.+")) {
      scores.set(SerialFields.DATE_1_STARTS_WITH_0, -100);
    }
  }

  private void detectDeletion() {
    // Discard any that are RECORD REPORTED FOR DELETION
    List<DataField> notes = marcRecord.getDatafield("936");
    if (!empty(notes)
        && notes.get(0).getSubfield("0") != null
        && notes.get(0).getSubfield("0").get(0) != null
        && notes.get(0).getSubfield("0").get(0).getValue() != null
        && notes.get(0).getSubfield("0").get(0).getValue().contains("DELETION")) {
      // scores.add(new Tuple2("deletion", -100));
      // score = score * 0 - 100;
    }
  }

  private static void detectInactiveTitles(Control008 control008) {
    // Discard any that are not active titles
    if (control008 != null
       && control008.getTag008all11() != null
       && (control008.getTag008all11().getValue().matches("[0-8].+")
           || control008.getTag008all11().getValue().matches("u.+"))) {
      // scores.add(new Tuple2("not-active", -100));
      // score = score * 0 - 100;
    }
  }

  private static void detectAutomaticDiscards(Control008 control008) {
    // Automatic Discards:
    // Discard any that are not "o" for electronic
    if (control008 != null
        && control008.getValueByPosition(23) != null
        && !control008.getValueByPosition(23).equals("o")) {
      // scores.add(new Tuple2("not-online", -100));
      // score = score * 0 - 100;
    }
  }

  private void detectPPC() {
    // Any PCC record should automatically be kept unless it is not online and/or a ceased title
    if (!empty(marcRecord.getDatafield("042"))
        && marcRecord.getDatafield("042").get(0) != null
        && marcRecord.getDatafield("042").get(0).getSubfield("a") != null
        && !marcRecord.getDatafield("042").get(0).getSubfield("a").isEmpty()
        && marcRecord.getDatafield("042").get(0).getSubfield("a").get(0).getCode().equals("pcc")) {
      scores.set(SerialFields.PCC, 100);
    }
  }

  private void detectLocSubjectHeadings() {
    // Has a Library of Congress subject heading (6XX_0)
    List<DataField> subjects = marcRecord.getSubjects();
    if (subjects.isEmpty()) {
      scores.set(SerialFields.HAS_NO_SUBJECT, -5);
    } else {
      scores.set(SerialFields.HAS_SUBJECT, subjects.size());
    }
  }

  private void detectDescriptionSource() {
    // Description based on/ Latest issue consulted notes (sourceOfDescription588)
    if (!empty(marcRecord.getDatafield("588"))) {
      scores.set(SerialFields.HAS_SOURCE_OF_DESCRIPTION_588, 1);
    }
  }

  private void detectDateOfPublication() {
    // Begins with... (datesOfPublication362)
    if (!empty(marcRecord.getDatafield("362"))) {
      scores.set(SerialFields.HAS_DATES_OF_PUBLICATION_362, 1);
    }
  }

  private void detectContentTypeRDA() {
    // Content Type (RDA) fields
    if (!empty(marcRecord.getDatafield("336"))) {
      scores.set(SerialFields.HAS_CONTENT_TYPE_336, 1);
    }
  }

  private void detectPublicationFrequency() {
    // Publication frequency
    if (!empty(marcRecord.getDatafield("310"))) {
      scores.set(SerialFields.HAS_PUBLICATION_FREQUENCY_310, 1);
    }
  }

  private void detectPublisherRDA() {
    // Record has publisher RDA
    if (!empty(marcRecord.getDatafield("264"))) {
      scores.set(SerialFields.HAS_PUBLISHER_264, 1);
    }
  }

  private void detectPublisher() {
    // Record has publisher AACR2
    if (!empty(marcRecord.getDatafield("260"))) {
      scores.set(SerialFields.HAS_PUBLISHER_260, 1);
    }
  }

  private void detect008() {
    // 006 is present
    if (marcRecord.getControl006() != null && !marcRecord.getControl006().isEmpty()) {
      boolean hasContent = false;
      for (Control006 control006 : marcRecord.getControl006()) {
        if (control006.getContent() != null && !control006.getContent().equals("")) {
          hasContent = true;
          break;
        }
      }
      if (hasContent)
        scores.set(SerialFields.HAS_006, 1);
    }
  }

  private void detectEncodingLevel() {
    // Encoding level is blank or I (fully cataloged)
    // OCLC: https://www.oclc.org/bibformats/en/fixedfield/elvl.html
    String encodingLevel = getEncodingLevel();
    if (encodingLevel.equals(" ")     // Full level
        || encodingLevel.equals("1") // Full level, material not examined
        || encodingLevel.equals("I") // oclc: Full level input by OCLC participants
    ) {
      scores.set(SerialFields.ENCODING_LEVEL_FULL, 5);
    }

    // Encoding level is M or L (not so fully cataloged, more likely to be a good record than K or 7)
    if (encodingLevel.equals("M")    // oclc: Added from a batch process
        || encodingLevel.equals("L") // oclc: ?
        || encodingLevel.equals("K") // oclc: Minimal level input by OCLC participants
        || encodingLevel.equals("7") // Minimal level
    ) {
      scores.set(SerialFields.ENCODING_LEVEL_MINIMAL, 1);
    }

    // Discard any with an encoding level of "3"
    if (encodingLevel.equals("3")) { // Abbreviated level
      scores.set(SerialFields.ABBREVIATED, -100);
    }
  }

  private void detectAuthenticationCode() {
    // Authentication code (from the 042) is empty (the record is not pcc or nsdp)
    List<DataField> authenticationcode = marcRecord.getDatafield("042");
    if (!empty(authenticationcode)
        && authenticationcode.get(0) != null
        && authenticationcode.get(0).getSubfield("a") != null
        && !authenticationcode.get(0).getSubfield("a").isEmpty()
        && !authenticationcode.get(0).getSubfield("a").get(0).getValue().equals("")) {
      scores.set(SerialFields.AUTH, 7);
    }
  }

  private void detectUnkownLanguage(Control008 control008) {
    // Publication language is totally unknown
    if (control008 != null
      && control008.getTag008all35() != null
      && control008.getTag008all35().getValue().matches("xxx.+")) {
      scores.set(SerialFields.LANGUAGE, -1);
    }
  }

  private void detectUnknownCountry(Control008 control008) {
    // Country of publication is totally unknown
    if (control008 != null
        && control008.getTag008all15() != null
        && control008.getTag008all15().getValue().matches("xx.+")) {
      scores.set(SerialFields.COUNTRY_UNKNOWN, -1);
    }
  }

  private void detectUnknownDate1(Control008 control008) {
    // Date 1 is totally unknown
    if (control008 != null
        && control008.getTag008all07() != null
        && control008.getTag008all07().getValue().equals("uuuu")) {
      scores.set(SerialFields.DATE_1_UNKNOWN, -3);
    }
  }

  public void print() {
    System.out.print(
      marcRecord.getId()
        + ", form of item: " + ((Control008)marcRecord.getControl008()).getValueByPosition(23) // record.getControl008().getControlValueByPosition(23).resolve()
        + ", issn: " + marcRecord.getDatafield("022").get(0).getSubfield("a").get(0).getValue()
        + ", date1: " + ((Control008)marcRecord.getControl008()).getTag008all07().getValue()
        + ", date2: " + ((Control008)marcRecord.getControl008()).getTag008all11().getValue()
        + ", encodingLevel: " + getEncodingLevel()
        // + ", title: " + record.getDatafield("245").get(0).toString()
        + ", " + scores.get(SerialFields.TOTAL)
    );
  }

  public SerialScores getScores() {
    return scores;
  }
}
