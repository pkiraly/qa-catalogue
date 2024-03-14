package de.gwdg.metadataqa.marc.analysis.serial;

import de.gwdg.metadataqa.marc.dao.record.MarcRecord;

import java.util.LinkedList;
import java.util.List;

public abstract class MarcSerial {

  protected MarcRecord marcRecord;
  protected SerialScores scores;
  private static final List<String> headers = new LinkedList<>();

  static {
    for (SerialFields field : SerialFields.values()) {
      headers.add(field.getMachine());
    }
  }

  protected MarcSerial(MarcRecord marcRecord) {
    this.marcRecord = marcRecord;
    // Create empty scores
    scores = new SerialScores();
  }

  public abstract List<Integer> determineRecordQualityScore();

  public SerialScores getScores() {
    return scores;
  }

  public static List<String> getHeaders() {
    return headers;
  }
}
