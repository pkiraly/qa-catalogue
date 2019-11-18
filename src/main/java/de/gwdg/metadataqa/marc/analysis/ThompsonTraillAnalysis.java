package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.Control008;
import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Implementation of the scoring algorithm described in
 * Leveraging Python to improve ebook metadata selection, ingest, and management
 * by Kelly Thompson and Stacie Traill
 * Code4Lib Journal, Issue 38, 2017-10-18
 * http://journal.code4lib.org/articles/12828
 */
public class ThompsonTraillAnalysis {

  private static final Logger logger = Logger.getLogger(ThompsonTraillAnalysis.class.getCanonicalName());
  private static final Pattern datePattern = Pattern.compile(
    "^(14[5-9]\\d|1[5-9]\\d\\d|200\\d|201[0-7])$"
  );
  private static Map<String, String> fields = new LinkedHashMap<>();
  private static List<String> headers = new LinkedList<>();
  static {
    fields.put("id", "id");
    fields.put("ISBN", "isbn");
    fields.put("Authors", "authors");
    fields.put("Alternative Titles", "alternative-titles");
    fields.put("Edition", "edition");
    fields.put("Contributors", "contributors");
    fields.put("Series", "series");
    fields.put("Table of Contents and Abstract", "toc-and-abstract");
    fields.put("Date 008", "date-008");
    fields.put("Date 26X", "date-26x");
    fields.put("LC/NLM Classification", "classification-lc-nlm");
    fields.put("Subject Headings: Library of Congress", "classification-loc");
    fields.put("Subject Headings: Mesh", "classification-mesh");
    fields.put("Subject Headings: Fast", "classification-fast");
    fields.put("Subject Headings: GND", "classification-gnd");
    fields.put("Subject Headings: Other", "classification-other");
    fields.put("Online", "online");
    fields.put("Language of Resource", "language-of-resource");
    fields.put("Country of Publication", "country-of-publication");
    fields.put("Language of Cataloging", "no-language-or-english");
    fields.put("Descriptive cataloging standard is RDA", "rda");
    fields.put("total", "total");

    for (Map.Entry<String, String> field : fields.entrySet()) {
      headers.add(field.getValue());
    }
  }

  public static List<String> getHeader() {
    return headers;
  }

  public static Map<String, String> getFields() {
    return fields;
  }

  public static List<Integer> getScores(MarcRecord marcRecord) {
    ThompsonTrailScores ttScores = new ThompsonTrailScores(fields);
    List<Integer> scores = new ArrayList<>();

    Control008 control008 = marcRecord.getControl008();

    ttScores.set("isbn", countFields(marcRecord, Arrays.asList("020")));
    ttScores.set("authors", countFields(marcRecord, Arrays.asList("100", "110", "111")));
    ttScores.set("alternative-titles", countFields(marcRecord, Arrays.asList("246")));
    ttScores.set("edition", countFields(marcRecord, Arrays.asList("250")));
    ttScores.set("contributors",
      countFields(marcRecord, Arrays.asList("700", "710", "711", "720")));
    ttScores.set("series",
      countFields(marcRecord, Arrays.asList("440", "490", "800", "810", "830")));
    ttScores.set("toc-and-abstract", calculateTocAndAbstract(marcRecord));

    String date008 = extractDate008(control008);
    ttScores.set("date-008", calculateDate008(date008));
    ttScores.set("date-26x", calculateDate26x(marcRecord, date008));

    ttScores.set("classification-lc-nlm", calculateClassificationLcNlm(marcRecord));

    calculateClassifications(marcRecord, ttScores);

    ttScores.set("online", calculateIsOnlineResource(marcRecord, control008));
    ttScores.set("language-of-resource", calculateLanguageOfResource(control008));
    ttScores.set("country-of-publication", calculateCountryOfPublication(control008));
    calculateLanguageAndRda(marcRecord, ttScores);

    ttScores.calculateTotal();
    return ttScores.asList();
  }

  // Language of Cataloging  040$b  1 point if either no language is specified,
  // or if English is specified
  // Descriptive cataloging standard  040$e  1 point if value is “rda”
  private static void calculateLanguageAndRda(MarcRecord marcRecord,
                                              ThompsonTrailScores ttScores) {
    List<DataField> fields040 = marcRecord.getDatafield("040");
    boolean noLanguageOrEnglish = false;
    boolean isRDA = false;
    if (fields040 != null && !fields040.isEmpty()) {
      for (DataField language : fields040) {
        List<MarcSubfield> subfields = language.getSubfield("b");
        if (subfields != null && !subfields.isEmpty()) {
          for (MarcSubfield subfield : subfields) {
            if (!noLanguageOrEnglish && subfield.getValue().equals("eng"))
              noLanguageOrEnglish = true;
          }
        }
        subfields = language.getSubfield("e");
        if (subfields != null && !subfields.isEmpty())
          for (MarcSubfield subfield : subfields)
            if (!isRDA && subfield.getValue().equals("rda"))
              isRDA = true;
      }
    }
    ttScores.set("no-language-or-english", (noLanguageOrEnglish ? 1 : 0));
    ttScores.set("rda", (isRDA ? 1 : 0));
  }

  // LC/NLM Classification  050, 060, 090  1 point if any field exists
  private static int calculateClassificationLcNlm(MarcRecord marcRecord) {
    int score = (
      exists(marcRecord, "050") ||
      exists(marcRecord, "060") ||
      exists(marcRecord, "090")) ? 1 : 0;
    return score;
  }

  // Date (MARC 26X)
  //   260$c or 264$c
  //   1 point if 4-digit date exists; 1 point if matches 008 date.
  private static int calculateDate26x(MarcRecord marcRecord, String date008) {
    int score = 0;
    if (exists(marcRecord, "260")) {
      List<DataField> fields = marcRecord.getDatafield("260");
      for (DataField field : fields) {
        List<MarcSubfield> subfields = field.getSubfield("c");
        if (subfields != null && !subfields.isEmpty()) {
          for (MarcSubfield subfield : subfields) {
            if (score == 0)
              score = 1;
            if (score < 2
                && date008 != ""
                && subfield.getValue().contains(date008))
              score = 2;
          }
        }
      }
    }
    if (exists(marcRecord, "264")) {
      List<DataField> fields = marcRecord.getDatafield("264");
      for (DataField field : fields) {
        List<MarcSubfield> subfields = field.getSubfield("c");
        if (subfields != null && !subfields.isEmpty()) {
          for (MarcSubfield subfield : subfields) {
            if (score == 0)
              score = 1;
            if (score < 2 && date008 != "" && subfield.getValue().contains(date008))
              score = 2;
          }
        }
      }
    }
    return score;
  }

  private static String extractDate008(Control008 control008) {
    // Date (MARC 008)  008/7-10  1 point if valid coded date exists
    String date008 = "";
    if (control008 != null
      && control008.getTag008all07() != null) {
      date008 = control008.getTag008all07().getValue();
    }
    return date008;
  }

  private static int calculateDate008(String date008) {
    // Date (MARC 008)  008/7-10  1 point if valid coded date exists
    int score = datePattern.matcher(date008).matches() ? 1 : 0;
    return score;
  }

  // Table of Contents and Abstract
  // 505, 520  2 points if both fields exist; 1 point if either field exists
  private static int calculateTocAndAbstract(MarcRecord marcRecord) {
    int score = 0;
    score += exists(marcRecord, "505") ? 1 : 0;
    score += exists(marcRecord, "520") ? 1 : 0;
    return score;
  }

  private static void calculateClassifications(MarcRecord marcRecord,
                                               ThompsonTrailScores ttScores) {
    // 600 - Personal Name
    // 610 - Corporate Name
    // 611 - Meeting Name
    // 630 - Uniform Title
    // 650 - Topical Term
    // 651 - Geographic Name
    // 653 - Uncontrolled Index Term
    // Subject Headings: Library of Congress
    // 600, 610, 611, 630, 650, 651 second indicator 0
    //   1 point for each field up to 10 total points
    // Subject Headings: MeSH  600, 610, 611, 630, 650, 651 second indicator 2  1 point for each field up to 10 total points
    // Subject Headings: FAST  600, 610, 611, 630, 650, 651 second indicator 7, $2 fast  1 point for each field up to 10 total points
    // Subject Headings: Other  600, 610, 611, 630, 650, 651, 653 if above criteria are not met  1 point for each field up to 5 total points
    for (String tag : Arrays.asList("600", "610", "611", "630", "650", "651", "653")) {
      if (exists(marcRecord, tag)) {
        List<DataField> fields = marcRecord.getDatafield(tag);
        for (DataField field : fields) {
          if (field.getInd2().equals("0"))
            ttScores.count("classification-lc-nlm");
          else if (field.getInd2().equals("2"))
            ttScores.count("classification-mesh");
          else if (field.getInd2().equals("7")) {
            List<MarcSubfield> subfield2 = field.getSubfield("2");
            if (subfield2 == null) {
              logger.severe(String.format(
                "Error in %s: ind2 = 7, but there is no $2",
                marcRecord.getControl001().getContent()));
            } else
              switch (field.getSubfield("2").get(0).getValue()) {
                case "fast": ttScores.count("classification-fast"); break;
                case "gnd": ttScores.count("classification-gnd"); break;
                default: ttScores.count("classification-other"); break;
              }
          }
          else {
            ttScores.count("classification-other");
          }
        }
      }
    }
  }

  private static int calculateIsOnlineResource(MarcRecord marcRecord, Control008 control008) {
    int score008 = calculateIsOnlineFrom008(marcRecord, control008);
    int score300a = calculateIsOnlineFrom300a(marcRecord);
    return score008 + score300a;
  }

  private static int calculateIsOnlineFrom300a(MarcRecord marcRecord) {
    List<DataField> fields300 = marcRecord.getDatafield("300");
    boolean isOnlineResource = false;
    if (fields300 != null && !fields300.isEmpty()) {
      for (DataField field : fields300) {
        List<MarcSubfield> subfields = field.getSubfield("a");
        if (subfields != null && !subfields.isEmpty())
          for (MarcSubfield subfield : subfields)
            if (!isOnlineResource && subfield.getValue().equals("online resource"))
              isOnlineResource = true;
      }
    }
    int score = isOnlineResource ? 1 : 0;
    return score;
  }

  private static int calculateIsOnlineFrom008(MarcRecord marcRecord, Control008 control008) {
    // Description  008/23=o and 300$a “online resource”  2 points if both elements exist; 1 point if either exists
    String formOfItem = null;
    if (control008 != null) {
      switch (marcRecord.getType()) {
        case BOOKS:
          formOfItem = control008.getTag008book23().getValue(); break;
        case COMPUTER_FILES:
          formOfItem = control008.getTag008computer23().getValue(); break;
        case CONTINUING_RESOURCES:
          formOfItem = control008.getTag008continuing23().getValue(); break;
        case MAPS:
          formOfItem = control008.getTag008map29().getValue(); break;
        case MIXED_MATERIALS:
          formOfItem = control008.getTag008mixed23().getValue(); break;
        case MUSIC:
          formOfItem = control008.getTag008music23().getValue(); break;
        case VISUAL_MATERIALS:
          formOfItem = control008.getTag008visual29().getValue(); break;
      }
    }
    int score = formOfItem != null && formOfItem.equals("o") ? 1 : 0;
    return score;
  }

  // Language of Resource  008/35-37  1 point if likely language code exists
  private static int calculateLanguageOfResource(Control008 control008) {
    int score;
    if (control008 != null && control008.getTag008all35() != null)
      score = LanguageCodes.getInstance().isValid(control008.getTag008all35().getValue()) ? 1 : 0;
    else
      score = 0;
    return score;
  }

  // Country of Publication Code  008/15-17  1 point if likely country code exists
  private static int calculateCountryOfPublication(Control008 control008) {
    int score;
    if (control008 != null && control008.getTag008all15() != null)
      score = CountryCodes.getInstance().isValid(control008.getTag008all15().getValue()) ? 1 : 0;
    else
      score = 0;
    return score;
  }

  private static Integer getTotal(List<Integer> scores) {
    int total = 0;
    for (Integer score : scores) {
      total += score;
    }
    return total;
  }

  private static boolean exists(MarcRecord marcRecord, String tag) {
    List<DataField> fields = marcRecord.getDatafield(tag);
    return (fields != null && !fields.isEmpty());
  }

  private static int countFields(MarcRecord marcRecord, List<String> tags) {
    int counter = 0;
    for (String tag : tags) {
      if (exists(marcRecord, tag))
        counter += marcRecord.getDatafield(tag).size();
    }
    return counter;
  }

  class BoardEntry {
    String recordElement;
    Integer count;
  }
}
