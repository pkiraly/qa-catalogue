package de.gwdg.metadataqa.marc.analysis.shelfready;

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

    // FIXME This implementation was copied from the old code and heavily refactored, but the logic is still completely the same.
    // If a category is a "one of" category, then the record isn't analyzed at all.
    // In the old code, the record was analyzed, but in the end the score was simply set to the score of the category
    // regardless of analysis results. That can be observed in the fact that the score is set to category.getScore() in the
    // original implementation, but the old score calculated in the for loop is never used.

    if (category.isOneOf()) {
      return category.getScore();
    }

    double score = 0.0;
    int count = countSelectors(fieldSelectors);

    if (count == 0) {
      return 0.0;
    }

    for (Map.Entry<String, List<String>> selector : fieldSelectors.entrySet()) {
      // It seems to me that if a certain category has some selectors with no subfield codes, then the score for that
      // selector without a subfield code is calculated by only taking the first selector of the category and checking if
      // it's present in the record. This is done in the getScoreWhenNoCodes method.
      score += calculateSelectorScore(marcRecord, category, selector);
    }

    double mean = score / count;
    score = mean * category.getScore();
    return score;
  }


  /**
   * Calculates the score for a given selector (tag and subfield codes) in the given category.
   * If the selector has no subfield codes, then the score is calculated as the number of selectors present in the record.
    * @param marcRecord The record to be analyzed
   * @param category The category to which the selector belongs. Used in case the selector has no subfield codes.
   * @param fieldSelector A map entry of the form "tag -> subfield codes"
   * @return The score for the given selector
   */
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

    List<DataField> dataFields = marcRecord.getDatafieldsByTag(tag);

    // For all datafields with the given tag, check if they have the given subfield and add it to the collector if they do.
    // This doesn't seem to be checking for the values of the subfields, only for their presence. That would be beneficial
    // In TA00600 for example.
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

  /**
   * Calculates the score for a given category when no subfield codes are specified.
   * The score is calculated as the number of selectors of the given category present in the record.
   * @param marcRecord The record to be analyzed
   * @param category The category to which the selectors belong
   * @return The score for the given category. The total score is the number of selectors present in the record.
   */
  private static double getScoreWhenNoCodes(BibliographicRecord marcRecord, ShelfReadyFieldsBooks category) {
    double score = 0.0;
    // FIXME This implementation was copied from the old code and heavily refactored, but the logic is still completely the same.
    //  It only takes the first selector of the category and checks if it's present in the record.
    MarcSpec selector = category.getSelectors().get(0);
    List<String> values = marcRecord.select(selector);
    if (!values.isEmpty()) {
      score += 1.0;
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
