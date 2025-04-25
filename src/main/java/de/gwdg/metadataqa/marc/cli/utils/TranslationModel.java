package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.rule.RuleCheckerOutput;
import de.gwdg.metadataqa.api.rule.RuleCheckingOutputStatus;
import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.cli.utils.placename.PlaceName;
import de.gwdg.metadataqa.marc.cli.utils.placename.PlaceNameNormaliser;
import de.gwdg.metadataqa.marc.cli.utils.translation.ContributorNormaliser;
import de.gwdg.metadataqa.marc.cli.utils.translation.PublicationYearNormaliser;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * To validate the result, and decide if it is a translation
 */
public class TranslationModel {
  private static final Logger logger = Logger.getLogger(TranslationModel.class.getCanonicalName());

  private static final Pattern yearPattern = Pattern.compile("^\\d{4}$");

  private static Map<String, String> languageCodes = TranslationModel.toMap(List.of(
    "jap", "Japanese", "fra", "French", "fr", "French", "hu", "Hungarian", "esp", "Spanish",
    "srb", "Serbian"
  ));


  private final BibSelector selector;
  private final Map<String, RuleCheckerOutput> resultMap;
  private final PlaceNameNormaliser placeNameNormaliser;
  private final PublicationYearNormaliser yearNormaliser;
  private final MarcVersion marcVersion;
  private final ContributorNormaliser contributorNormaliser;

  private boolean translation;
  private boolean translator;
  private boolean sourceLanguage;
  private boolean targetLanguage;
  private boolean originalTitle;
  private boolean originalPublication;

  /**
   * @param resultMap           The results of the measurement
   * @param selector            The data object that contains selected data elements of the bib record
   * @param placeNameNormaliser
   * @param marcVersion
   */
  public TranslationModel(Map<String, RuleCheckerOutput> resultMap,
                          BibSelector selector,
                          PlaceNameNormaliser placeNameNormaliser,
                          PublicationYearNormaliser yearNormaliser,
                          ContributorNormaliser contributorNormaliser,
                          MarcVersion marcVersion) {
    this.resultMap = resultMap;
    this.selector = selector;
    this.placeNameNormaliser = placeNameNormaliser;
    this.yearNormaliser = yearNormaliser;
    this.contributorNormaliser = contributorNormaliser;
    this.marcVersion = marcVersion;
    evaluate();
  }

  public static List<String> header() {
    return List.of(
      "translator", "sourceLanguage", "targetLanguage",
      "originalTitle", "originalPublication", "translation"
    );
  }

  public List<Integer> values() {
    return List.of(
      translator, sourceLanguage, targetLanguage,
      originalTitle, originalPublication, translation
    ).stream().map(s -> s.compareTo(false)).collect(Collectors.toList());
  }

  public Map<String, Object> extract() {
    Map<String, Object> extracted = new HashMap<>();
    // sourceLanguage
    // System.err.println(extract("041$h"));
    extracted.put("sourceLanguage", extract("041$h"));
    // targetLanguage
    // System.err.println(extract("041$a"));
    extracted.put("targetLanguage", extract("041$a"));
    // Language of a work
    // System.err.println(extract("240$l"));
    // System.err.println(extract("260$a"));
    extracted.put("author", extract("100$a"));
    extracted.put("publicationPlace", extract("260$a"));
    extracted.put("publicationYear", extract("260$c"));
    return extracted;
  }

  private List<? extends Object> extract(String path) {
    List<XmlFieldInstance> instances = new ArrayList<>();
    if (path.equals("100$a") && marcVersion == MarcVersion.HUNMARC) {
      instances = getAuthorsFromHunmarc();
    } else {
      instances = selector.get(path);
    }
    if (path.equals("260$a")) {
      instances.addAll(selector.get("264$a"));
    } else if (path.equals("260$c")) {
      instances.addAll(selector.get("264$c"));
    } else if (path.equals("100$a") && instances.size() == 0) {
      contributorNormaliser.process(selector.get("245$c"));
      instances.addAll(selector.get("245$c"));
    }
    List<String> extracted = new ArrayList<>();
    if (path == "041$h" || path == "041$a") {
      for (XmlFieldInstance instance : instances) {
        String value = instance.getValue();
        if (value != null) {
          value = value.trim();
          if (value.contains(", "))
            extracted.addAll(Arrays.asList(value.split(", "))
              .stream()
              .map(s -> resultLanguageCode(s.trim().toLowerCase()))
              .collect(Collectors.toList()));
          else if (!value.contains(" ")) {
            if (value.length() > 3) {
              for (int i = 0; i < value.length(); i += 3) {
                int end = i + 3;
                String abr = (value.length() >= end)
                  ? value.substring(i, end).toLowerCase()
                  : value.substring(i).toLowerCase();
                extracted.add(resultLanguageCode(abr));
              }
            } else {
              extracted.add(resultLanguageCode(value.toLowerCase()));
            }
          } else {
            logger.warning(String.format("%s - Unhandled language: '%s'", path, value));
          }
        }
      }
      extracted = extracted.stream()
        .distinct()
        .filter(s -> !s.equals("und"))
        .collect(Collectors.toList());
    } else {
      extracted = instances.stream()
        .map(XmlFieldInstance::getValue)
        .map(s -> Normalizer.normalize(s, Normalizer.Form.NFKC))
        .collect(Collectors.toList());
      if (path.equals("240$l")) {
        extracted = extracted.stream()
          .map(s -> s.replaceAll("\\.$", ""))
          .collect(Collectors.toList());
      } else if (path.equals("260$a") && placeNameNormaliser != null) {
        List<PlaceName> placeNames = processPlaceName(extracted);
        if (placeNames.isEmpty()) {
          return new ArrayList<>();
        } else {
          for (PlaceName p : placeNames) {
            if (p == null || p.getCity() == null) {
              logger.warning(String.format("%s - null in place name: '%s'", path, StringUtils.join(extracted, "' -- '")));
            }
          }
          return placeNames.stream()
            .filter(s -> s.getCity() != null)
            .map(PlaceName::getCity)
            .collect(Collectors.toList());
        }
      } else if (path.equals("260$c") && yearNormaliser != null) {
        return yearNormaliser.processYear(extracted);
      }
    }
    return extracted;
  }

  private List<XmlFieldInstance> getAuthorsFromHunmarc() {
    // System.err.println(selector.getClass());
    List<String> names = new ArrayList<>();
    MarcSpecSelector mSelector = (MarcSpecSelector) selector;
    for (Object f : mSelector.extract("100")) {
      StringBuilder name = new StringBuilder();
      DataField field = (DataField) f;
      List<MarcSubfield> a = field.getSubfield("a");
      if (a != null && !a.isEmpty()) {
        if (a.size() == 1) {
          name.append(a.get(0).getValue());
        } else {
          logger.warning("Multiple 100$a: " + a);
        }
      }
      List<MarcSubfield> j = field.getSubfield("j");
      if (j != null && !j.isEmpty()) {
        name.append(" ").append(j.get(0).getValue());
        if (j.size() > 1) {
          logger.warning("Multiple 100$j: " + j);
        }
      }
      if (name.length() > 0) {
        // logger.info(String.format("a: '%s', j: '%s' -> '%s'", a.toString(), j.toString(), name.toString()));
        names.add(name.toString());
      }
    }
    return new ArrayList<>();
  }

  private static String resultLanguageCode(String abbreviation) {
    EncodedValue code = LanguageCodes.getInstance().getCode(abbreviation);
    if (code != null) {
      return code.getLabel();
    } else if (languageCodes.containsKey(abbreviation)) {
      return languageCodes.get(abbreviation);
    } else {
      return abbreviation;
    }
  }

  private List<PlaceName> processPlaceName(List<String> input) {
    return placeNameNormaliser.normalise(input);
    /*
    if (!knownMultiwordCities.contains(result) && Pattern.matches("^.*[^a-zA-Zøäșóō].*$", result)) {
      System.err.println(String.format("'%s' -> '%s'",
        StringUtils.join(input, ", "), StringUtils.join(extracted, ", ")));
    }
     */
  }

  private void evaluate() {
    if (passed("041ind1"))
      translation = true;

    if (passed("041h")) {
      translation = true;
      sourceLanguage = true;
    }

    if (passed("041a")) {
      targetLanguage = true;
    }

    if (passed("245c")) {
      translation = true;
      translator = true;
    }

    if (passed("7004")) {
      translation = true;
      translator = true;
    }

    if (passed("700e")) {
      translation = true;
      translator = true;
    }

    if (passed("500a")) {
      translation = true;
      translator = true;
    }

    if (passed("240a")) {
      translation = true;
      originalTitle = true;
    }

    // TODO: maybe the value should check against other language code
    if (passed("240l")) {
      translation = true;
      originalTitle = true;
    }

    if (passed("765ind2")) {
      translation = true;
    }

    if (passed("765t")) {
      translation = true;
      originalTitle = true;
    }

    if (passed("765s")) {
      translation = true;
      originalTitle = true;
    }

    if (passed("765d")) {
      translation = true;
      originalPublication = true;
    }
  }

  /**
   * Check if the rule has been passed the test
   * @param path
   * @return
   */
  public boolean passed(String path) {
    return resultMap.containsKey(path) && resultMap.get(path).getStatus().equals(RuleCheckingOutputStatus.PASSED);
  }

  public boolean isTranslation() {
    return translation;
  }

  private static Map<String, String> toMap(List<String> input) {
    Map<String, String> map = new HashMap<>();
    for (int i = 0; i < input.size(); i+=2) {
      map.put(input.get(i), input.get(i+1));
    }
    return map;
  }
}
