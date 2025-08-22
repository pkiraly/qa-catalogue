package de.gwdg.metadataqa.marc.analysis.thompsontraill;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.Control008;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Implementation of the scoring algorithm described in
 * Leveraging Python to improve ebook metadata selection, ingest, and management
 * by Kelly Thompson and Stacie Traill
 * Code4Lib Journal, Issue 38, 2017-10-18
 * <a href="http://journal.code4lib.org/articles/12828">http://journal.code4lib.org/articles/12828</a>
 */
public class Marc21ThompsonTraillAnalysis extends ThompsonTraillAnalysis {

  private final Logger logger = Logger.getLogger(ThompsonTraillAnalysis.class.getCanonicalName());
  private final Pattern datePattern = Pattern.compile(
      "^(14[5-9]\\d|1[5-9]\\d\\d|200\\d|201[0-7])$"
  );


  public List<Integer> getScores(BibliographicRecord marcRecord) {

    // If it isn't a MARC21 record, return an empty list
    if (!(marcRecord instanceof Marc21Record)) {
      return List.of();
    }

    Marc21Record marc21Record = (Marc21Record) marcRecord;

    var ttScores = new ThompsonTraillScores();

    // countFields
    ttScores.set(ThompsonTraillFields.ISBN, countFields(marc21Record, List.of("020")));
    ttScores.set(ThompsonTraillFields.AUTHORS, countFields(marc21Record, List.of("100", "110", "111")));
    ttScores.set(ThompsonTraillFields.ALTERNATIVE_TITLES, countFields(marc21Record, List.of("246")));
    ttScores.set(ThompsonTraillFields.EDITION, countFields(marc21Record, List.of("250")));
    ttScores.set(ThompsonTraillFields.CONTRIBUTORS,
        countFields(marc21Record, List.of("700", "710", "711", "720")));
    ttScores.set(ThompsonTraillFields.SERIES,
        countFields(marc21Record, List.of("440", "490", "800", "810", "830")));

    // calculateTocAndAbstract
    ttScores.set(ThompsonTraillFields.TOC, calculateTocAndAbstract(marc21Record));

    var control008 = (Control008) marc21Record.getControl008();
    String date008 = extractDate008(control008);
    ttScores.set(ThompsonTraillFields.DATE_008, calculateDate008(date008));
    ttScores.set(ThompsonTraillFields.DATE_26X, calculateDate26x(marc21Record, date008));

    ttScores.set(ThompsonTraillFields.LC_NLM, calculateClassificationLcNlm(marc21Record));

    calculateClassifications(marc21Record, ttScores);

    // calculateIsOnlineResource
    ttScores.set(ThompsonTraillFields.ONLINE, calculateIsOnlineResource(marc21Record, control008));
    ttScores.set(ThompsonTraillFields.LANGUAGE_OF_RESOURCE, calculateLanguageOfResource(control008));
    ttScores.set(ThompsonTraillFields.COUNTRY_OF_PUBLICATION, calculateCountryOfPublication(control008));
    calculateLanguageAndRda(marc21Record, ttScores);


    ttScores.calculateTotal();
    return ttScores.asList();
  }

  // Language of Cataloging  040$b  1 point if either no language is specified,
  // or if English is specified
  // Descriptive cataloging standard  040$e  1 point if value is “rda”
  private void calculateLanguageAndRda(Marc21Record marcRecord,
                                       ThompsonTraillScores ttScores) {
    List<DataField> fields040 = marcRecord.getDatafieldsByTag("040");
    var noLanguageOrEnglish = false;
    var isRDA = false;

    if (fields040 == null || fields040.isEmpty()) {
      ttScores.set(ThompsonTraillFields.LANGUAGE_OF_CATALOGING, 0);
      ttScores.set(ThompsonTraillFields.RDA, 0);
      return;
    }

    for (DataField language : fields040) {
      // TODO discuss that this wasn't checking that the no language is specified
      if (!noLanguageOrEnglish) {
        noLanguageOrEnglish = isFieldLanguageEnglish(language);
      }

      if (!isRDA) {
        isRDA = isFieldRda(language);
      }
    }

    ttScores.set(ThompsonTraillFields.LANGUAGE_OF_CATALOGING, (noLanguageOrEnglish ? 1 : 0));
    ttScores.set(ThompsonTraillFields.RDA, (isRDA ? 1 : 0));
  }

  // LC/NLM Classification  050, 060, 090  1 point if any field exists
  private int calculateClassificationLcNlm(Marc21Record marcRecord) {
    return (
        tagExists(marcRecord, "050") ||
            tagExists(marcRecord, "060") ||
            tagExists(marcRecord, "090")) ? 1 : 0;
  }

  // Date (MARC 26X)
  //   260$c or 264$c
  //   1 point if 4-digit date exists; 1 point if matches 008 date.
  private int calculateDate26x(Marc21Record marcRecord, String date008) {
    int score260 = calculateDate26xScore("260", marcRecord, date008);
    int score264 = calculateDate26xScore("264", marcRecord, date008);

    // return greater of score260 and score264
    return Math.max(score260, score264);
  }

  private String extractDate008(Control008 control008) {
    // Date (MARC 008)  008/7-10  1 point if valid coded date exists
    var date008 = "";
    if (control008 != null
        && control008.getTag008all07() != null) {
      date008 = control008.getTag008all07().getValue();
    }
    return date008;
  }

  private int calculateDate008(String date008) {
    // Date (MARC 008)  008/7-10  1 point if valid coded date exists
    return datePattern.matcher(date008).matches() ? 1 : 0;
  }

  // Table of Contents and Abstract
  // 505, 520  2 points if both fields exist; 1 point if either field exists
  private int calculateTocAndAbstract(Marc21Record marcRecord) {
    var score = 0;
    score += tagExists(marcRecord, "505") ? 1 : 0;
    score += tagExists(marcRecord, "520") ? 1 : 0;
    return score;
  }

  private void calculateClassifications(Marc21Record marcRecord,
                                        ThompsonTraillScores ttScores) {
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
      if (!tagExists(marcRecord, tag)) {
        continue;
      }

      List<DataField> fields = marcRecord.getDatafieldsByTag(tag);
      for (DataField field : fields) {
        calculateClassificationScore(field, marcRecord, ttScores);
      }
    }
  }

  private int calculateIsOnlineResource(Marc21Record marcRecord, Control008 control008) {
    var score008 = calculateIsOnlineFrom008(marcRecord, control008);
    var score300a = calculateIsOnlineFrom300a(marcRecord);
    return score008 + score300a;
  }

  private int calculateIsOnlineFrom300a(Marc21Record marcRecord) {
    List<DataField> fields300 = marcRecord.getDatafieldsByTag("300");
    var isOnlineResource = false;

    if (fields300 == null || fields300.isEmpty()) {
      return 0;
    }

    for (DataField field : fields300) {
      isOnlineResource = is300aOnlineResource(field);
      if (isOnlineResource) {
        break;
      }
    }

    return isOnlineResource ? 1 : 0;
  }

  private int calculateIsOnlineFrom008(Marc21Record marcRecord, Control008 control008) {
    // Description  008/23=o and 300$a “online resource”  2 points if both elements exist; 1 point if either exists

    if (control008 == null) {
      return 0;
    }

    String formOfItem = null;

    switch (marcRecord.getType()) {
      case BOOKS:
        if (control008.getTag008book23() != null)
          formOfItem = control008.getTag008book23().getValue();
        break;
      case COMPUTER_FILES:
        if (control008.getTag008computer23() != null)
          formOfItem = control008.getTag008computer23().getValue();
        break;
      case CONTINUING_RESOURCES:
        if (control008.getTag008continuing23() != null)
          formOfItem = control008.getTag008continuing23().getValue();
        break;
      case MAPS:
        if (control008.getTag008map29() != null)
          formOfItem = control008.getTag008map29().getValue();
        break;
      case MIXED_MATERIALS:
        if (control008.getTag008mixed23() != null)
          formOfItem = control008.getTag008mixed23().getValue();
        break;
      case MUSIC:
        if (control008.getTag008music23() != null)
          formOfItem = control008.getTag008music23().getValue();
        break;
      case VISUAL_MATERIALS:
        if (control008.getTag008visual29() != null)
          formOfItem = control008.getTag008visual29().getValue();
        break;
    }
    return (formOfItem != null && formOfItem.equals("o")) ? 1 : 0;
  }

  // Language of Resource  008/35-37  1 point if likely language code exists
  private int calculateLanguageOfResource(Control008 control008) {
    int score;
    if (control008 != null && control008.getTag008all35() != null)
      score = LanguageCodes.getInstance().isValid(control008.getTag008all35().getValue()) ? 1 : 0;
    else
      score = 0;
    return score;
  }

  // Country of Publication Code  008/15-17  1 point if likely country code exists
  private int calculateCountryOfPublication(Control008 control008) {
    int score;
    if (control008 != null && control008.getTag008all15() != null)
      score = CountryCodes.getInstance().isValid(control008.getTag008all15().getValue()) ? 1 : 0;
    else
      score = 0;
    return score;
  }

  private int countFields(Marc21Record marcRecord, List<String> tags) {
    var counter = 0;
    for (String tag : tags) {
      if (tagExists(marcRecord, tag))
        counter += marcRecord.getDatafieldsByTag(tag).size();
    }
    return counter;
  }

  private boolean is300aOnlineResource(DataField field) {
    List<MarcSubfield> subfields = field.getSubfield("a");
    if (subfields == null || subfields.isEmpty()) {
      return false;
    }

    for (MarcSubfield subfield : subfields) {
      if (subfield.getValue().equals("online resource")) {
        return true;
      }
    }

    return false;
  }

  private void calculateClassificationScore(DataField field, Marc21Record marcRecord, ThompsonTraillScores ttScores) {
    if (field.getInd2().equals("0")) {
      ttScores.count(ThompsonTraillFields.LC_NLM);
      return;
    }
    if (field.getInd2().equals("2")) {
      ttScores.count(ThompsonTraillFields.MESH);
      return;
    }

    // This seems to be a bug in the original code. TODO discuss
    if (!field.getInd2().equals("7")) {
      ttScores.count(ThompsonTraillFields.OTHER);
      return;
    }

    List<MarcSubfield> subfield2 = field.getSubfield("2");
    if (subfield2 == null) {
      logger.log(Level.SEVERE, "Error in {0}: ind2 = 7, but there is no $2", new Object[]{marcRecord.getControl001().getContent()});
      return;
    }

    String subfield2Code = field.getSubfield("2").get(0).getValue();
    switch (subfield2Code) {
      case "fast":
        ttScores.count(ThompsonTraillFields.FAST);
        break;
      case "gnd":
        ttScores.count(ThompsonTraillFields.GND);
        break;
      default:
        ttScores.count(ThompsonTraillFields.OTHER);
        break;
    }

  }

  private int calculateDate26xScore(String tag, Marc21Record marcRecord, String date008) {

    if (!tagExists(marcRecord, tag)) {
      return 0;
    }
    int score = 0;

    List<DataField> fields = marcRecord.getDatafieldsByTag(tag);
    for (DataField field : fields) {
      List<MarcSubfield> subfields = field.getSubfield("c");
      if (subfields == null || subfields.isEmpty()) {
        continue;
      }

      for (MarcSubfield subfield : subfields) {
        if (score == 0) {
          score = 1;
        }
        if (score < 2 && !date008.isEmpty() && subfield.getValue().contains(date008)) {
          score = 2;
        }
      }
    }

    return score;
  }

  private boolean isFieldLanguageEnglish(DataField languageField) {
    List<MarcSubfield> subfields = languageField.getSubfield("b");
    if (subfields == null || subfields.isEmpty()) {
      return false;
    }

    for (MarcSubfield subfield : subfields) {
      if (subfield.getValue().equals("eng")) {
        return true;
      }
    }
    return false;
  }

  private boolean isFieldRda(DataField languageField) {
    List<MarcSubfield> subfields = languageField.getSubfield("e");
    if (subfields == null || subfields.isEmpty()) {
      return false;
    }

    for (MarcSubfield subfield : subfields) {
      if (subfield.getValue().equals("rda")) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Map<ThompsonTraillFields, List<String>> getThompsonTraillTagsMap() {
    Map<ThompsonTraillFields, List<String>> thompsonTraillTagMap = new LinkedHashMap<>();

    thompsonTraillTagMap.put(ThompsonTraillFields.ISBN, Arrays.asList("020"));
    thompsonTraillTagMap.put(ThompsonTraillFields.AUTHORS, Arrays.asList("100", "110", "111"));
    thompsonTraillTagMap.put(ThompsonTraillFields.ALTERNATIVE_TITLES, Arrays.asList("246"));
    thompsonTraillTagMap.put(ThompsonTraillFields.EDITION, Arrays.asList("250"));
    thompsonTraillTagMap.put(ThompsonTraillFields.CONTRIBUTORS, Arrays.asList("700", "710", "711", "720"));
    thompsonTraillTagMap.put(ThompsonTraillFields.SERIES, Arrays.asList("440", "490", "800", "810", "830"));
    thompsonTraillTagMap.put(ThompsonTraillFields.TOC, Arrays.asList("505", "520"));
    thompsonTraillTagMap.put(ThompsonTraillFields.DATE_008, Arrays.asList("008/07"));
    thompsonTraillTagMap.put(ThompsonTraillFields.DATE_26X, Arrays.asList("260$c", "264$c"));
    thompsonTraillTagMap.put(ThompsonTraillFields.LC_NLM, Arrays.asList("050", "060", "090"));
    thompsonTraillTagMap.put(ThompsonTraillFields.LC_NLM, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    thompsonTraillTagMap.put(ThompsonTraillFields.MESH, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    thompsonTraillTagMap.put(ThompsonTraillFields.FAST, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    thompsonTraillTagMap.put(ThompsonTraillFields.GND, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    thompsonTraillTagMap.put(ThompsonTraillFields.OTHER, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    thompsonTraillTagMap.put(ThompsonTraillFields.ONLINE, Arrays.asList("008/23", "300$a")); // 29
    thompsonTraillTagMap.put(ThompsonTraillFields.LANGUAGE_OF_RESOURCE, Arrays.asList("008/35"));
    thompsonTraillTagMap.put(ThompsonTraillFields.COUNTRY_OF_PUBLICATION, Arrays.asList("008/15"));

    return thompsonTraillTagMap;
  }

}
