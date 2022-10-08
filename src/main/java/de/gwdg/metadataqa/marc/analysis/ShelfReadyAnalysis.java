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
    for (Map.Entry<ShelfReadyFieldsBooks, Map<String, List<String>>> fieldEntry : marcRecord.getShelfReadyMap().entrySet()) {
      ShelfReadyFieldsBooks category = fieldEntry.getKey();
      boolean debug = category.equals(ShelfReadyFieldsBooks.LDR06);

      var score = 0.0;
      double count = (double) countSelectors(fieldEntry.getValue());

      for (Map.Entry<String, List<String>> selector : fieldEntry.getValue().entrySet()) {
        String tag = selector.getKey();
        List<String> codes = selector.getValue();

        if (codes != null && ! codes.isEmpty()) {
          Set<String> collector = new HashSet<>();
          if (marcRecord.hasDatafield(tag)) {
            List<DataField> dataFields = marcRecord.getDatafield(tag);
            for (DataField dataField : dataFields) {
              for (String code : codes) {
                List<MarcSubfield> subfield = dataField.getSubfield(code);
                if (subfield != null && !subfield.isEmpty())
                  collector.add(tag + "$" + code);
              }
              if (category.isOneOf())
                break;
            }
          }
          score += (double) collector.size();
        } else {
          // no code
          count = 1;
          List<String> values = marcRecord.select(category.getSelectors().get(0));
          if (!values.isEmpty()) {
            score += 1.0;
            if (category.isOneOf())
              break;
          }
        }
      }

      if (category.isOneOf()) {
        score = category.getScore();
      } else {
        double mean = score / count;
        score = mean * category.getScore();
      }
      scores.add(score);
      total += score;
    }
    /*
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
  */
    total = total / scores.size();
    scores.add(total);
    return scores;
  }

  public static List<String> getHeaders() {
    return headers;
  }

  private static int countSelectors(Map<String, List<String>> value) {
    int count = 0;
    if (value != null && !value.isEmpty()) {
      for (Map.Entry<String, List<String>> entry : value.entrySet()) {
        if (entry.getValue() == null)
          count++;
        else
          count += entry.getValue().size();
      }
    }
    return count;
  }
}

