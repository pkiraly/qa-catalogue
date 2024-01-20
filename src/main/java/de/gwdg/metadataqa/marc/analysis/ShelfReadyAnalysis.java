package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    // Calculate the score for each category
    for (Map.Entry<ShelfReadyFieldsBooks, Map<String, List<String>>> fieldEntry : marcRecord.getShelfReadyMap().entrySet()) {
      double score = calculateScoreForCategory(marcRecord, fieldEntry);

      scores.add(score);
      total += score;
    }

    total = total / scores.size();
    scores.add(total);
    return scores;
  }

  /**
   * Calculates the score for a given category.
   * If the category is a "one of" category, then the score is simply the score of the category, because "one of" means
   * that it's sufficient for there to be at least one of the selectors present.
   * If the category is not a "one of" category, then the score is calculated proportionally to the number of selectors
   * present in marcRecord.
   *
   * @param marcRecord The record to be analyzed
   * @param fieldEntry A map entry of the form "category -> map of selectors"
   * @return The score for the given category
   */
  private static double calculateScoreForCategory(BibliographicRecord marcRecord,
                                                  Map.Entry<ShelfReadyFieldsBooks, Map<String, List<String>>> fieldEntry) {

    ShelfReadyFieldsBooks category = fieldEntry.getKey();
    Map<String, List<String>> fieldSelectors = fieldEntry.getValue();

    // TODO This implementation was copied from the old code. If a category is a "one of" category, then the record isn't analyzed at all.
    if (category.isOneOf()) {
      return category.getScore();
    }

    double score = 0.0;
    int count = countSelectors(fieldSelectors);

    if (count == 0) {
      return 0.0;
    }

    for (Map.Entry<String, List<String>> selector : fieldSelectors.entrySet()) {
      score += calculateSelectorScore(marcRecord, category, selector);
    }

    double mean = score / count;
    score = mean * category.getScore();
    return score;
  }

  public static double calculateSelectorScore(BibliographicRecord marcRecord,
                                              ShelfReadyFieldsBooks category,
                                              Map.Entry<String, List<String>> fieldSelector) {
    String tag = fieldSelector.getKey();

    List<String> subfieldCodes = fieldSelector.getValue();

    double score;

    // If no subfield codes are specified in the selector,
    if (subfieldCodes == null || subfieldCodes.isEmpty()) {
      score = getScoreWhenNoCodes(marcRecord, category);
      return score;
    }

    Set<String> collector = new HashSet<>();
    if (!marcRecord.hasDatafield(tag)) {
      return 0.0;
    }

    List<DataField> dataFields = marcRecord.getDatafield(tag);

    // For all datafields with the given tag, check if they have the given subfield and add it to the collector if so
    for (DataField dataField : dataFields) {
      for (String code : subfieldCodes) {
        List<MarcSubfield> subfield = dataField.getSubfield(code);
        if (subfield != null && !subfield.isEmpty()) {
          collector.add(tag + "$" + code);
        }
      }
    }

    score = collector.size();

    return score;
  }

  private static double getScoreWhenNoCodes(BibliographicRecord marcRecord, ShelfReadyFieldsBooks category) {
    double score = 0.0;
    for (MarcSpec selector : category.getSelectors()) {
      List<String> values = marcRecord.select(selector);
      if (!values.isEmpty()) {
        score += 1.0;
      }
    }
    return score;
  }

  public static List<String> getHeaders() {
    return headers;
  }

  /**
   * Counts the number of selectors in the given map. If a selector has no subfield codes, it is counted as one.
   * E.g. if the map contains the following entries: "tag1" -> null, "tag2" -> ["a", "b"], "tag3" -> ["c", "d", "e"],
   * then the result is 6.
   * If the map contains the entry "tag1" -> [], then the result is 0 for some reason.
   * If the list is empty or null, the result is 0.
   * In other words, this method counts the number of subfield codes in the given map.
   * @param fieldSelectors A map "tag -> subfield codes"
   */
  private static int countSelectors(Map<String, List<String>> fieldSelectors) {
    if (fieldSelectors == null || fieldSelectors.isEmpty()) {
      return 0;
    }

    int count = 0;

    for (Map.Entry<String, List<String>> entry : fieldSelectors.entrySet()) {
      List<String> subfieldCodes = entry.getValue();
      if (subfieldCodes == null || subfieldCodes.isEmpty()) {
        count++;
      } else {
        count += subfieldCodes.size();
      }
    }
    return count;
  }
}
