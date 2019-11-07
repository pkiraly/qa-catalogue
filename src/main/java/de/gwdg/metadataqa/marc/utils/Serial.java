package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class heavily based on the class published in
 * Jamie Carlstone (2017) Scoring the Quality of E-Serials MARC Records Using Java,
 * Serials Review, 43:3-4, pp. 271-277, DOI: 10.1080/00987913.2017.1350525
 * https://www.tandfonline.com/doi/full/10.1080/00987913.2017.1350525
 */
public class Serial {
  private MarcRecord record;
  private int score;
  private List<Tuple2> scores;

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

  public String getEncodingLevel() {
    return record.getLeader().getEncodingLevel().getValue();
  }

  public int determineRecordQualityScore() {
    scores = new ArrayList<>();
    int score = 0;
    // Date 1 is totally unknown
    if (record.getControl008().getTag008all07().getValue().equals("uuuu")) {
      scores.add(new Tuple2("date1-u", -3));
      score = score - 3;
    }

    // Country of publication is totally unknown
    if (record.getControl008().getTag008all15().getValue().matches("xx.+")) {
      scores.add(new Tuple2("country", -1));
      score = score - 1;
    }

    // Publication language is totally unknown
    if (record.getControl008().getTag008all35().getValue().matches("xxx.+")) {
      scores.add(new Tuple2("language", -1));
      score = score - 1;
    }

    // Authentication code (from the 042) is empty (the record is not pcc or nsdp)
    List<DataField> authenticationcode = record.getDatafield("042");
    if (!empty(authenticationcode)
        && !authenticationcode.get(0).getSubfield("a").isEmpty()
        && authenticationcode.get(0).getSubfield("a").get(0).getValue() != "") {
      scores.add(new Tuple2("auth", 7));
      score = score + 7;
    }

    // Encoding level is blank or I (fully cataloged)
    // OCLC: https://www.oclc.org/bibformats/en/fixedfield/elvl.html
    String encodingLevel = getEncodingLevel();
    if (encodingLevel.equals(" ")     // Full level
        || encodingLevel.equals("1") // Full level, material not examined
        || encodingLevel.equals("I") // oclc: Full level input by OCLC participants
    ) {
      scores.add(new Tuple2("enc-1", 5));
      score = score + 5;
    }

    // Encoding level is M or L (not so fully cataloged, more likely to be a good record than K or 7)
    if (encodingLevel.equals("M")    // oclc: Added from a batch process
        || encodingLevel.equals("L") // oclc: ?
        || encodingLevel.equals("K") // oclc: Minimal level input by OCLC participants
        || encodingLevel.equals("7") // Minimal level
    ) {
      scores.add(new Tuple2("enc-2", 1));
      score = score + 1;
    }

    // 006 is present
    if (record.getControl006() != null
        && record.getControl006().getContent() != "") {
      scores.add(new Tuple2("006", 1));
      score = score + 1;
    }

    // Record has publisher AACR2
    if (!empty(record.getDatafield("260"))) {
      scores.add(new Tuple2("260", 1));
      score = score + 1;
    }

    // Record has publisher RDA
    if (!empty(record.getDatafield("264"))) {
      scores.add(new Tuple2("264", 1));
      score = score + 1;
    }

    // Publication frequency
    if (!empty(record.getDatafield("310"))) {
      scores.add(new Tuple2("310", 1));
      score = score + 1;
    }

    // RDA fields
    if (!empty(record.getDatafield("336"))) {
      scores.add(new Tuple2("336", 1));
      score = score + 1;
    }

    // Begins with... (datesOfPublication362)
    if (!empty(record.getDatafield("362"))) {
      scores.add(new Tuple2("332", 1));
      score = score + 1;
    }

    // Description based on/ Latest issue consulted notes (sourceOfDescription588)
    if (!empty(record.getDatafield("588"))) {
      scores.add(new Tuple2("588", 1));
      score = score + 1;
    }

    // Has a Library of Congress subject heading (6XX_0)
    List<DataField> subjects = record.getSubject6xx();
    if (subjects.isEmpty()) {
      scores.add(new Tuple2("no-subject", -5));
      score = score - 5;
    } else {
      for (DataField subject : subjects) {
        if (subject.getInd2().equals("0") && subject.getSubfield("a") != null) {
          scores.add(new Tuple2("subject", 2));
          score = score + 2;
          break;
        }
      }
    }

    // Any PCC record should automatically be kept unless it is not online and/or a ceased title
    if (!empty(record.getDatafield("042"))
        && record.getDatafield("042").get(0).getSubfield("a").equals("pcc")) {
      scores.add(new Tuple2("pcc", 100));
      score = score + 100;
    }

    // Automatic Discards:
    // Discard any that are not "o" for electronic
    if (!record.getControl008().getValueByPosition(23).equals("o")) {
      scores.add(new Tuple2("not-online", -100));
      score = score * 0 - 100;
    }

    // Discard any that are not active titles
    if (record.getControl008().getTag008all11().getValue().matches("[0-8].+")
       || record.getControl008().getTag008all11().getValue().matches("u.+")) {
      scores.add(new Tuple2("not-active", -100));
      score = score * 0 - 100;
    }

    // Discard any that are RECORD REPORTED FOR DELETION
    List<DataField> notes = record.getDatafield("936");
    if (!empty(notes) && notes.get(0).getSubfield("0").get(0).getValue().contains("DELETION")) {
      scores.add(new Tuple2("deletion", -100));
      score = score * 0 - 100;
    }

    // Discard any with a first date of "0"
    if (record.getControl008().getTag008all07().getValue().matches("0.+")) {
      scores.add(new Tuple2("date1-0", -100));
      score = score * 0 - 100;
    }

    // Discard any with an encoding level of "3"
    if (encodingLevel.equals("3")) { // Abbreviated level
      scores.add(new Tuple2("abbreviated", -100));
      score = score * 0 - 100;
    }

    this.score = score;

    return score;
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
        + ", " + score
    );
  }

  public List<Tuple2> getScores() {
    return scores;
  }
  public List<String> getFormattedScores() {
    List<String> scores = new ArrayList<>();
    for (Tuple2 score : this.scores) {
      scores.add(score._1.toString() + "=" + score._2.toString());
    }
    return scores;
  }
}
