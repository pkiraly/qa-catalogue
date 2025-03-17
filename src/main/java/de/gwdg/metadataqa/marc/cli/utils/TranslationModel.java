package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.rule.RuleCheckerOutput;
import de.gwdg.metadataqa.api.rule.RuleCheckingOutputStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * To validate the result, and decide if it is a translation
 */
public class TranslationModel {
  private Map<String, RuleCheckerOutput> resultMap;
  private boolean translation;
  private boolean translator;
  private boolean sourceLanguage;
  private boolean targetLanguage;
  private boolean originalTitle;
  private boolean originalPublication;

  public TranslationModel(Map<String, RuleCheckerOutput> resultMap) {
    this.resultMap = resultMap;
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

}
