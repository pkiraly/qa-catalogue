package de.gwdg.metadataqa.marc.analysis.thompsontraill;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Implementation of the scoring algorithm described in
 * Leveraging Python to improve ebook metadata selection, ingest, and management
 * by Kelly Thompson and Stacie Traill
 * Code4Lib Journal, Issue 38, 2017-10-18
 * <a href="http://journal.code4lib.org/articles/12828">http://journal.code4lib.org/articles/12828</a>
 */
public class UnimarcThompsonTraillAnalysis extends ThompsonTraillAnalysis {
  private final Pattern datePattern = Pattern.compile(
      "^(14[5-9]\\d|1[5-9]\\d\\d|200\\d|201[0-7])$"
  );


  public List<Integer> getScores(BibliographicRecord marcRecord) {
    var ttScores = new ThompsonTraillScores();

    // Count fields
    ttScores.set(ThompsonTraillFields.ISBN, countFields(marcRecord, List.of("010")));
    ttScores.set(ThompsonTraillFields.AUTHORS, countFields(marcRecord, List.of("700", "720", "710")));
    ttScores.set(ThompsonTraillFields.ALTERNATIVE_TITLES, countFields(marcRecord, List.of("510", "512", "513", "514", "515", "516", "517")));
    ttScores.set(ThompsonTraillFields.EDITION, countFields(marcRecord, List.of("205")));
    ttScores.set(ThompsonTraillFields.CONTRIBUTORS, countFields(marcRecord, List.of("701", "702", "711", "712", "730")));
    ttScores.set(ThompsonTraillFields.SERIES, countFields(marcRecord, List.of("225", "410")));

    // Calculate toc and abstract
    int tocAndAbstractScore = calculateTocAndAbstract(marcRecord);
    ttScores.set(ThompsonTraillFields.TOC, tocAndAbstractScore);

    // Date008 is different in Unimarc, but it will fall into the same category as MARC21 until further discussion
    // Get Date008 aka 100$a/09-12 in Unimarc

    String date1 = getDate1(marcRecord);

    ttScores.set(ThompsonTraillFields.DATE_008, calculateDate008(date1));

    // Get Date26x$c aka 21x$d in Unimarc
    ttScores.set(ThompsonTraillFields.DATE_26X, calculateDate21xd(marcRecord, date1));

    ttScores.set(ThompsonTraillFields.LC_NLM, calculateClassificationLcNlm(marcRecord));

    calculateClassifications(marcRecord, ttScores);

    // Online resource part seems not to be possible in UNIMARC, as we can only specify if the media is electronic
    ttScores.set(ThompsonTraillFields.LANGUAGE_OF_RESOURCE, calculateLanguageOfResource(marcRecord));
    ttScores.set(ThompsonTraillFields.COUNTRY_OF_PUBLICATION, calculateCountryOfPublication(marcRecord));
    ttScores.set(ThompsonTraillFields.LANGUAGE_OF_CATALOGING, calculateLanguageOfCataloguing(marcRecord));

    // I'm not sure what RDA should be. There's a field 801$g which doesn't contain RDA. In the leader, there's a
    // position 18 which records "(Blank) record is in full ISBD form" for instance. TODO: check this


    ttScores.calculateTotal();
    return ttScores.asList();
  }

  /**
   * Returns 1 if the langauge of cataloguing is unspecified or English, 0 otherwise.
   */
  private int calculateLanguageOfCataloguing(BibliographicRecord marcRecord) {
    // Get the values for 100$a
    List<String> subfield100aValues = marcRecord.extract("100", "a");
    if (subfield100aValues == null || subfield100aValues.size() != 1) {
      // This should be an invalid field because the field 100$a is mandatory
      return 1;
    }
    String subfieldValue = subfield100aValues.get(0);
    // Get the string from the position 22 to 24
    String languageOfCataloguing = subfieldValue.substring(22, 25);

    // If the language is unspecified, return 1
    if (languageOfCataloguing.isBlank()) {
      return 1;
    }

    // If the language is English, return 1
    if (languageOfCataloguing.equals("eng")) {
      return 1;
    }

    return 0;
  }

  /**
   * Checks if the field 680 exists.
   * Analogous to the MARC21 implementation of checking whether the fields 050, 060, 090 exist.
   * @param marcRecord The record to be analyzed
   * @return 1 if the field 680 exists, 0 otherwise
   */
  private int calculateClassificationLcNlm(BibliographicRecord marcRecord) {
    return tagExists(marcRecord, "680") ? 1 : 0;
  }

  /**
   * The following is specified for MARC21:
   * 260$c or 264$c 	1 point if 4-digit date exists; 1 point if matches 008 date.
   * In this case, analogously, the fields 210$d and 214$d are used.
   * @param marcRecord The record to be analyzed
   * @param date1 The date1 from the 100$a field
   * @return The score for the 21x$d fields
   */
  private int calculateDate21xd(BibliographicRecord marcRecord, String date1) {
    int score260 = calculateDate21xScore("210", marcRecord, date1);
    int score264 = calculateDate21xScore("210", marcRecord, date1);

    // return greater of score260 and score264
    return Math.max(score260, score264);
  }

  private int calculateDate008(String date008) {
    return datePattern.matcher(date008).matches() ? 1 : 0;
  }

  // Table of Contents and Abstract
  // 505, 520  2 points if both fields exist; 1 point if either field exists
  private int calculateTocAndAbstract(BibliographicRecord marcRecord) {
    var score = 0;
    score += tagExists(marcRecord, "327") ? 1 : 0;
    score += tagExists(marcRecord, "330") ? 1 : 0;
    return score;
  }

  private void calculateClassifications(BibliographicRecord marcRecord,
                                        ThompsonTraillScores ttScores) {
    /*
      ---- For these we should check that $2 code is related to the respective category
      600 = 600, 602, 604
      610 = 601
      611 = 601
      630 = 605
      650 = 606
      651 = 607
     */

    for (String tag : Arrays.asList("600", "601", "602", "604", "605", "606", "607")) {
      if (!tagExists(marcRecord, tag)) {
        continue;
      }

      List<DataField> fields = marcRecord.getDatafield(tag);
      for (DataField field : fields) {
        calculateClassificationScore(field, ttScores);
      }
    }
  }

  private int calculateLanguageOfResource(BibliographicRecord marcRecord) {
    return tagExists(marcRecord, "101") ? 1 : 0;
  }

  private int calculateCountryOfPublication(BibliographicRecord marcRecord) {
    return tagExists(marcRecord, "102") ? 1 : 0;
  }

  private int countFields(BibliographicRecord marcRecord, List<String> tags) {
    var counter = 0;
    for (String tag : tags) {
      if (tagExists(marcRecord, tag))
        counter += marcRecord.getDatafield(tag).size();
    }
    return counter;
  }

  private void calculateClassificationScore(DataField field, ThompsonTraillScores ttScores) {
    // Get the value of the subfield $2
    List<MarcSubfield> subfields = field.getSubfield("2");

    // Iterate over the subfields and check if the value is:
    // lcc lctgm lcch lc - then add 1 to the score for ThompsonTraillFields.LOC
    // mesh - then add 1 to the score for ThompsonTraillFields.MESH
    // ast - then add 1 to the score for ThompsonTraillFields.FAST
    // there doesn't seem to be a GND code
    // otherwise add 1 to the score for ThompsonTraillFields.OTHER

    if (subfields == null || subfields.isEmpty()) {
      return;
    }

    for (MarcSubfield subfield : subfields) {
      String value = subfield.getValue();
      switch (value) {
        case "lcc":
        case "lctgm":
        case "lcch":
        case "lc":
          ttScores.set(ThompsonTraillFields.LOC, 1);
          break;
        case "mesh":
          ttScores.set(ThompsonTraillFields.MESH, 1);
          break;
        case "ast":
          ttScores.set(ThompsonTraillFields.FAST, 1);
          break;
        default:
          ttScores.set(ThompsonTraillFields.OTHER, 1);
          break;
      }
    }

  }

  private int calculateDate21xScore(String fieldTag, BibliographicRecord marcRecord, String date1) {

    if (!tagExists(marcRecord, fieldTag)) {
      return 0;
    }

    int score = 0;

    List<String> date21xdValues = marcRecord.extract(fieldTag, "d");
    for (String date21x$d : date21xdValues) {
      if (score == 0) {
        score = 1;
      }
      if (score < 2 && !date1.isEmpty() && date21x$d.contains(date1)) {
        score = 2;
      }
    }

    return score;
  }
  private String getDate1(BibliographicRecord marcRecord) {
    List<String> field100aValues = marcRecord.extract("100", "a");
    if (field100aValues == null || field100aValues.size() != 1) {
      return "";
    }
    String subfieldValue = field100aValues.get(0);
    // Get the string from the position 9 to 12
    return subfieldValue.substring(9, 13);
  }

  @Override
  public Map<ThompsonTraillFields, List<String>> getThompsonTraillTagsMap() {

    /*

    https://www.loc.gov/marc/unimarctomarc21_0xx1xx.pdf
    https://www.loc.gov/marc/unimarctomarc21_2xx5xx.pdf
    https://www.loc.gov/marc/unimarctomarc21_6xx8xx.pdf

    020 = 010

    100 = 700, ?720?
    110 = 710

    246 = 510, 512, 513, 514, 515, 516, 517
    250 = 205

    700 = 701, 702, 721, 722
    710 = 711, 712
    711 = 711, 712
    710 = 730

    440 = 225?
    490 = 225
    800 = ???
    810 = ???
    830 = 410 https://knowledge.exlibrisgroup.com/Alma/Product_Documentation/010Alma_Online_Help_(English)/Metadata_Management/040Working_with_Bibliographic_Records/090Metadata_Format_Conversions

    505 = 327
    520 = 330

    008/07-10 = 100$a/09-12

    260$c = 210$d
    264$c = 214$d I think. No source

    050 = 680
    060 = ???
    090 = Not found even in MARC21

    ---- For these we should check that $2 code is related to the respective category
    600 = 600, 602, 604
    610 = 601
    611 = 601
    630 = 605
    650 = 606
    651 = 607

    ---- The online part seems to not be possible in UNIMARC, as we can only specify if the media is electronic

    ---- RDA
    801$g == 'RDA'

     */
    Map<ThompsonTraillFields, List<String>> thompsonTraillTagMap = new LinkedHashMap<>();

    thompsonTraillTagMap.put(ThompsonTraillFields.ISBN, List.of("010"));
    thompsonTraillTagMap.put(ThompsonTraillFields.AUTHORS, List.of("700", "720", "710"));
    thompsonTraillTagMap.put(ThompsonTraillFields.ALTERNATIVE_TITLES, List.of("510", "512", "513", "514", "515", "516", "517"));
    thompsonTraillTagMap.put(ThompsonTraillFields.EDITION, List.of("205"));
    thompsonTraillTagMap.put(ThompsonTraillFields.CONTRIBUTORS, List.of("701", "702", "711", "712", "730"));
    thompsonTraillTagMap.put(ThompsonTraillFields.SERIES, List.of("225", "410")); // Maybe even 411 SUBSERIES
    thompsonTraillTagMap.put(ThompsonTraillFields.TOC, List.of("327", "330"));
    thompsonTraillTagMap.put(ThompsonTraillFields.DATE_008, List.of("100$a/09-12")); // Maybe change the category name?
    thompsonTraillTagMap.put(ThompsonTraillFields.DATE_26X, List.of("210$d", "214$d"));
    thompsonTraillTagMap.put(ThompsonTraillFields.LC_NLM, List.of("680"));
    thompsonTraillTagMap.put(ThompsonTraillFields.LOC, List.of("600", "601", "602", "604", "605", "606", "607"));
    thompsonTraillTagMap.put(ThompsonTraillFields.MESH, List.of("600", "601", "602", "604", "605", "606", "607"));
    thompsonTraillTagMap.put(ThompsonTraillFields.FAST, List.of("600", "601", "602", "604", "605", "606", "607"));
    thompsonTraillTagMap.put(ThompsonTraillFields.GND, List.of("600", "601", "602", "604", "605", "606", "607"));
    thompsonTraillTagMap.put(ThompsonTraillFields.OTHER, List.of("600", "601", "602", "604", "605", "606", "607"));
    thompsonTraillTagMap.put(ThompsonTraillFields.ONLINE, List.of());
    thompsonTraillTagMap.put(ThompsonTraillFields.LANGUAGE_OF_RESOURCE, List.of("101"));
    thompsonTraillTagMap.put(ThompsonTraillFields.COUNTRY_OF_PUBLICATION, List.of("102"));
    thompsonTraillTagMap.put(ThompsonTraillFields.LANGUAGE_OF_CATALOGING, List.of("100$a/22-24"));
    thompsonTraillTagMap.put(ThompsonTraillFields.RDA, List.of("801$g"));

    return thompsonTraillTagMap;
  }

}
