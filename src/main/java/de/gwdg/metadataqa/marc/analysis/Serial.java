package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.Control006;
import de.gwdg.metadataqa.marc.Control008;
import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * This class heavily based on the class published in
 * Jamie Carlstone (2017) Scoring the Quality of E-Serials MARC Records Using Java,
 * Serials Review, 43:3-4, pp. 271-277, DOI: 10.1080/00987913.2017.1350525
 * https://www.tandfonline.com/doi/full/10.1080/00987913.2017.1350525
 */
public class Serial {
  private MarcRecord record;
  private SerialScores scores;
  private Control008 control008;

  private static List<String> headers = new LinkedList<>();
  static {
    for (SerialFields field : SerialFields.values()) {
      headers.add(field.getMachine());
    }
  }

  public Serial(MarcRecord record) {
    this.record = record;
    scores = new SerialScores();
  }

  public static List<String> getHeader() {
    return headers;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();

    // datesOfPublication
    result = prime * result + ((record.getDatafield("362") == null)
      ? 0
      : record.getDatafield("362").hashCode());

    // frequency
    result = prime * result + ((record.getDatafield("310") == null)
      ? 0
      : record.getDatafield("310").hashCode());

    // issn
    result = prime * result + ((record.getDatafield("022") == null)
      ? 0
      :record.getDatafield("022").hashCode());

    // sourceOfDescription
    result = prime * result + ((record.getDatafield("588") == null)
      ? 0
      : record.getDatafield("588").hashCode());
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
    return record.getDatafield("022");
  }

  public List<DataField> getFrequency310() {
    return record.getDatafield("310");
  }

  public List<DataField> getDatesOfPublication362() {
    return record.getDatafield("362");
  }

  public List<DataField> getSourceOfDescription588() {
    return record.getDatafield("588");
  }

  public String getEncodingLevel() {
    return record.getLeader().getEncodingLevel().getValue();
  }

  public List<Integer> determineRecordQualityScore() {
    control008 = record.getControl008();

    // Date 1 is totally unknown
    if (control008 != null
        && control008.getTag008all07() != null
        && control008.getTag008all07().getValue().equals("uuuu")) {
      scores.set(SerialFields.Date1Unknown, -3);
    }

    // Country of publication is totally unknown
    if (control008 != null
        && control008.getTag008all15() != null
        && control008.getTag008all15().getValue().matches("xx.+")) {
      scores.set(SerialFields.CountryUnknown, -1);
    }

    // Publication language is totally unknown
    if (control008 != null
      && control008.getTag008all35() != null
      && control008.getTag008all35().getValue().matches("xxx.+")) {
      scores.set(SerialFields.Language, -1);
    }

    // Authentication code (from the 042) is empty (the record is not pcc or nsdp)
    List<DataField> authenticationcode = record.getDatafield("042");
    if (!empty(authenticationcode)
        && authenticationcode.get(0) != null
        && authenticationcode.get(0).getSubfield("a") != null
        && !authenticationcode.get(0).getSubfield("a").isEmpty()
        && !authenticationcode.get(0).getSubfield("a").get(0).getValue().equals("")) {
      scores.set(SerialFields.Auth, 7);
    }

    // Encoding level is blank or I (fully cataloged)
    // OCLC: https://www.oclc.org/bibformats/en/fixedfield/elvl.html
    String encodingLevel = getEncodingLevel();
    if (encodingLevel.equals(" ")     // Full level
        || encodingLevel.equals("1") // Full level, material not examined
        || encodingLevel.equals("I") // oclc: Full level input by OCLC participants
    ) {
      scores.set(SerialFields.EncodingLevelFull, 5);
    }

    // Encoding level is M or L (not so fully cataloged, more likely to be a good record than K or 7)
    if (encodingLevel.equals("M")    // oclc: Added from a batch process
        || encodingLevel.equals("L") // oclc: ?
        || encodingLevel.equals("K") // oclc: Minimal level input by OCLC participants
        || encodingLevel.equals("7") // Minimal level
    ) {
      scores.set(SerialFields.EncodingLevelMinimal, 1);
    }

    // 006 is present
    if (record.getControl006() != null && !record.getControl006().isEmpty()) {
      boolean hasContent = false;
      for (Control006 control006 : record.getControl006()) {
        if (control006.getContent() != null && !control006.getContent().equals("")) {
          hasContent = true;
          break;
        }
      }
      if (hasContent)
        scores.set(SerialFields.Has006, 1);
    }

    // Record has publisher AACR2
    if (!empty(record.getDatafield("260"))) {
      scores.set(SerialFields.HasPublisher260, 1);
    }

    // Record has publisher RDA
    if (!empty(record.getDatafield("264"))) {
      scores.set(SerialFields.HasPublisher264, 1);
    }

    // Publication frequency
    if (!empty(record.getDatafield("310"))) {
      scores.set(SerialFields.HasPublicationFrequency310, 1);
    }

    // Content Type (RDA) fields
    if (!empty(record.getDatafield("336"))) {
      scores.set(SerialFields.HasContentType336, 1);
    }

    // Begins with... (datesOfPublication362)
    if (!empty(record.getDatafield("362"))) {
      scores.set(SerialFields.HasDatesOfPublication362, 1);
    }

    // Description based on/ Latest issue consulted notes (sourceOfDescription588)
    if (!empty(record.getDatafield("588"))) {
      scores.set(SerialFields.HasSourceOfDescription588, 1);
    }

    // Has a Library of Congress subject heading (6XX_0)
    List<DataField> subjects = record.getSubjects();
    if (subjects.isEmpty()) {
      scores.set(SerialFields.HasNoSubject, -5);
    } else {
      int subjectCount = 0;
      for (DataField subject : subjects) {
        // if (subject.getInd2().equals("0") && subject.getSubfield("a") != null) {
        subjectCount++;
        // }
      }
      scores.set(SerialFields.HasSubject, subjectCount);
    }

    // Any PCC record should automatically be kept unless it is not online and/or a ceased title
    if (!empty(record.getDatafield("042"))
        && record.getDatafield("042").get(0) != null
        && record.getDatafield("042").get(0).getSubfield("a") != null
        && !record.getDatafield("042").get(0).getSubfield("a").isEmpty()
        && record.getDatafield("042").get(0).getSubfield("a").get(0).equals("pcc")) {
      scores.set(SerialFields.PCC, 100);
    }

    // Automatic Discards:
    // Discard any that are not "o" for electronic
    if (control008 != null
        && control008.getValueByPosition(23) != null
        && !control008.getValueByPosition(23).equals("o")) {
      // scores.add(new Tuple2("not-online", -100));
      // score = score * 0 - 100;
    }

    // Discard any that are not active titles
    if (control008 != null
       && control008.getTag008all11() != null
       && (control008.getTag008all11().getValue().matches("[0-8].+")
           || control008.getTag008all11().getValue().matches("u.+"))) {
      // scores.add(new Tuple2("not-active", -100));
      // score = score * 0 - 100;
    }

    // Discard any that are RECORD REPORTED FOR DELETION
    List<DataField> notes = record.getDatafield("936");
    if (!empty(notes)
        && notes.get(0).getSubfield("0") != null
        && notes.get(0).getSubfield("0").get(0) != null
        && notes.get(0).getSubfield("0").get(0).getValue() != null
        && notes.get(0).getSubfield("0").get(0).getValue().contains("DELETION")) {
      // scores.add(new Tuple2("deletion", -100));
      // score = score * 0 - 100;
    }

    // Discard any with a first date of "0"
    if (control008 != null
        && control008.getTag008all07() != null
        && control008.getTag008all07().getValue().matches("0.+")) {
      scores.set(SerialFields.Date1StartsWith0, -100);
    }

    // Discard any with an encoding level of "3"
    if (encodingLevel.equals("3")) { // Abbreviated level
      scores.set(SerialFields.Abbreviated, -100);
    }

    scores.calculateTotal();
    return scores.asList();
  }

  public void print() {
    System.out.print(
      record.getId()
        + ", form of item: " + record.getControl008().getValueByPosition(23) // record.getControl008().getControlValueByPosition(23).resolve()
        + ", issn: " + record.getDatafield("022").get(0).getSubfield("a").get(0).getValue()
        + ", date1: " + record.getControl008().getTag008all07().getValue()
        + ", date2: " + record.getControl008().getTag008all11().getValue()
        + ", encodingLevel: " + getEncodingLevel()
        // + ", title: " + record.getDatafield("245").get(0).toString()
        + ", " + scores.get(SerialFields.TOTAL)
    );
  }

  public SerialScores getScores() {
    return scores;
  }
}
