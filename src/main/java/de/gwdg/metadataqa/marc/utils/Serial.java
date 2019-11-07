package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;

import java.util.List;

/**
 * This class heavily based on the class published in
 * Jamie Carlstone (2017) Scoring the Quality of E-Serials MARC Records Using Java,
 * Serials Review, 43:3-4, pp. 271-277, DOI: 10.1080/00987913.2017.1350525
 * https://www.tandfonline.com/doi/full/10.1080/00987913.2017.1350525
 */
public class Serial {
  private MarcRecord record;

  public Serial(MarcRecord record) {
    this.record = record;
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

  public int determineRecordQualityScore() {
    int score = 0;
    // Date 1 is totally unknown
    if (record.getControl008().getTag008all07().getValue().equals("uuuu")) {
      score = score - 3;
    }

    // Country of publication is totally unknown
    if (record.getControl008().getTag008all15().getValue().matches("xx.+")) {
      score = score - 1;
    }

    // Publication language is totally unknown
    if (record.getControl008().getTag008all35().getValue().matches("xxx.+")) {
      score = score - 1;
    }

    // Authentication code (from the 042) is empty (the record is not pcc or nsdp)
    List<DataField> authenticationcode = record.getDatafield("042");
    if (!empty(authenticationcode)
        && !authenticationcode.get(0).getSubfield("a").isEmpty()
        && authenticationcode.get(0).getSubfield("a").get(0).getValue() != "") {
      score = score + 7;
    }

    // Encoding level is blank or I (fully cataloged)
    // OCLC: https://www.oclc.org/bibformats/en/fixedfield/elvl.html
    String encodingLevel = record.getLeader().getEncodingLevel().getValue();
    if (encodingLevel.equals(" ")     // Full level
        || encodingLevel.equals("1") // Full level, material not examined
        || encodingLevel.equals("I") // oclc: Full level input by OCLC participants
    ) {
      score = score + 5;
    }

    // Encoding level is M or L (not so fully cataloged, more likely to be a good record than K or 7)
    if (encodingLevel.equals("M")    // oclc: Added from a batch process
        || encodingLevel.equals("L") // oclc: ?
        || encodingLevel.equals("K") // oclc: Minimal level input by OCLC participants
        || encodingLevel.equals("7") // Minimal level
    ) {
      score = score + 1;
    }

    // 006 is present
    if (record.getControl006().getContent() != "") {
      score = score + 1;
    }

    // Record has publisher AACR2
    if (!empty(record.getDatafield("260"))) {
      score = score + 1;
    }

    // Record has publisher RDA
    if (!empty(record.getDatafield("264"))) {
      score = score + 1;
    }

    // Publication frequency
    if (!empty(record.getDatafield("310"))) {
      score = score + 1;
    }

    // RDA fields
    if (!empty(record.getDatafield("336"))) {
      score = score + 1;
    }

    // Begins with... (datesOfPublication362)
    if (!empty(record.getDatafield("362"))) {
      score = score + 1;
    }

    // Description based on/ Latest issue consulted notes (sourceOfDescription588)
    if (!empty(record.getDatafield("588"))) {
      score = score + 1;
    }

    // Has a Library of Congress subject heading (6XX_0)
    List<DataField> subjects = record.getSubject6xx();
    if (subjects.isEmpty()) {
      score = score - 5;
    } else {
      for (DataField subject : subjects) {
        if (subject.getInd2().equals("0") && subject.getSubfield("a") != null) {
          score = score + 2;
          break;
        }
      }
    }

    // Any PCC record should automatically be kept unless it is not online and/or a ceased title
    if (!empty(record.getDatafield("042"))
        && record.getDatafield("042").get(0).getSubfield("a").equals("pcc")) {
      score = score + 100;
    }

    // Automatic Discards:
    // Discard any that are not "o" for electronic
    if (!record.getControl008().getValueByPosition(23).equals("o")) {
      score = score * 0 - 100;
    }

    // Discard any that are not active titles
    if (record.getControl008().getTag008all11().getValue().matches("[0-8].+")
       || record.getControl008().getTag008all11().getValue().matches("u.+")) {
      score = score * 0 - 100;
    }

    // Discard any that are RECORD REPORTED FOR DELETION
    List<DataField> notes = record.getDatafield("936");
    if (!empty(notes) && notes.get(0).getSubfield("0").get(0).getValue().contains("DELETION")) {
      score = score * 0 - 100;
    }

    // Discard any with a first date of "0"
    if (record.getControl008().getTag008all07().getValue().matches("0.+")) {
      score = score * 0 - 100;
    }

    // Discard any with an encoding level of "3"
    if (encodingLevel.equals("3")) { // Abbreviated level
      score = score * 0 - 100;
    }
    System.out.print(
      record.getId()
      + ", form of item: " + record.getControl008().getValueByPosition(23) // record.getControl008().getControlValueByPosition(23).resolve()
      + ", issn: " + record.getDatafield("022").get(0).getSubfield("a").get(0).getValue()
      + ", date1: " + record.getControl008().getTag008all07().getValue()
      + ", date2: " + record.getControl008().getTag008all11().getValue()
      // Encoding level
      + ", encodingLevel: " + encodingLevel
      // Title
      // + ", " + record.getDatafield("245").get(0).toString()
      + ", " + score
    );
    return score;
  }
}
