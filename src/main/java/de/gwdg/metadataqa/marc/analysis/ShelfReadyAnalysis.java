package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShelfReadyAnalysis {

  private ShelfReadyAnalysis() {
    throw new IllegalStateException("This is a utility class");
  }

  private static List<String> headers = new LinkedList<>();
  static {
    for (ShelfReadyFieldsBooks field : ShelfReadyFieldsBooks.values()) {
      headers.add(field.name());
    }
  }

  public static List<Double> getScores(BibliographicRecord marcRecord) {
    List<Double> scores = new ArrayList<>();

    var total = 0.0;
    for (ShelfReadyFieldsBooks fieldEntry : ShelfReadyFieldsBooks.values()) {
      var score = 0.0;
      double count = (double) fieldEntry.getSelectors().size();
      for (MarcSpec selector : fieldEntry.getSelectors()) {
        List<String> values = marcRecord.select(selector);
        if (!values.isEmpty()) {
          score += 1.0;
          if (fieldEntry.isOneOf())
            break;
        }
      }
      if (fieldEntry.isOneOf()) {
        score = fieldEntry.getScore();
      } else {
        double mean = score / count;
        score = mean * fieldEntry.getScore();
      }
      scores.add(score);
      total += score;
    }
    total = total / scores.size();
    scores.add(total);
    return scores;
  }

  public static List<String> getHeaders() {
    return headers;
  }
}
