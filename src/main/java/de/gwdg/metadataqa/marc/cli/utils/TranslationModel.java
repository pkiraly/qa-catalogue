package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.rule.RuleCheckerOutput;
import de.gwdg.metadataqa.api.rule.RuleCheckingOutputStatus;
import de.gwdg.metadataqa.marc.cli.utils.placename.PlaceName;
import de.gwdg.metadataqa.marc.cli.utils.placename.PlaceNameNormaliser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * To validate the result, and decide if it is a translation
 */
public class TranslationModel {
  private static final Logger logger = Logger.getLogger(TranslationModel.class.getCanonicalName());

  private final BibSelector selector;
  private final Map<String, RuleCheckerOutput> resultMap;
  private final PlaceNameNormaliser placeNameNormaliser;

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
   */
  public TranslationModel(Map<String, RuleCheckerOutput> resultMap,
                          BibSelector selector,
                          PlaceNameNormaliser placeNameNormaliser) {
    this.resultMap = resultMap;
    this.selector = selector;
    this.placeNameNormaliser = placeNameNormaliser;
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
    return extracted;
  }

  private List<? extends Object> extract(String path) {
    List<XmlFieldInstance> instances = selector.get(path);
    if (path.equals("260$a")) {
      instances.addAll(selector.get("264$a"));
    } else if (path.equals("100$a") && instances.size() == 0) {
      instances.addAll(selector.get("245$c"));
    }
    List<String> extracted = new ArrayList<>();
    if (path == "041$h" || path == "041$a") {
      for (XmlFieldInstance instance : instances) {
        String value = instance.getValue();
        if (value != null) {
          if (value.contains(", "))
            extracted.addAll(Arrays.asList(value.split(", "))
              .stream()
              .map(s -> s.trim().toLowerCase())
              .collect(Collectors.toList()));
          else if (!value.contains(" ")) {
            if (value.length() > 3) {
              for (int i = 0; i < value.length(); i += 3) {
                extracted.add(value.substring(i, i + 3).toLowerCase());
              }
            } else {
              extracted.add(value.toLowerCase());
            }
          } else {
            logger.warning(path + " - Unhandled case: " + value);
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
        .collect(Collectors.toList());
      if (path.equals("240$l")) {
        extracted = extracted.stream()
          .map(s -> s.replaceAll("\\.$", ""))
          .collect(Collectors.toList());
      } else if (path.equals("260$a") && placeNameNormaliser != null) {
        return processPlaceName(extracted).stream().map(PlaceName::getCity).collect(Collectors.toList());
      }
    }
    return extracted;
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
}
