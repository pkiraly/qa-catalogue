package de.gwdg.metadataqa.marc.cli.utils.translation;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PublicationYearNormaliser {
  private static final Logger logger = Logger.getLogger(PublicationYearNormaliser.class.getCanonicalName());

  public static final String UNRESOLVED_YEARS_FILE = "translations-unresolved-years.txt";


  private static final Pattern yearPattern = Pattern.compile("^\\d{4}$");
  private final String outputDir;
  private Map<String, Integer> unresolvedYears;

  public PublicationYearNormaliser(String outputDir) {
    this.outputDir = outputDir;
    this.unresolvedYears = new HashMap<>();
  }

  public List<String> processYear(List<String> extracted) {
    List<String> normalizedYears = new ArrayList<>();
    for (String original : extracted) {
      if (!yearPattern.matcher(original).matches()) {
        String normalized = normalizeYear(original);
        if (Pattern.compile("^(\\d{4})-(\\d{4})$").matcher(normalized).matches()) {
          normalizedYears.addAll(Arrays.asList(normalized.split("-")));
        } else if (Pattern.compile("^(\\d{4}), ?(\\d{4})$").matcher(normalized).matches()) {
          normalizedYears.addAll(Arrays.asList(normalized.split(", ?")));
        } else if (normalized.equals("s.d.") || normalized.equals("n.d.") || normalized.equals("s.a.")) {
          //
        } else if (yearPattern.matcher(normalized).matches()) {
          normalizedYears.add(normalized);
        } else {
          String key = String.format("'%s' -> '%s'", original, normalized);
          unresolvedYears.put(key, unresolvedYears.getOrDefault(key, 0) + 1);
          // System.err.println("Unhandled case: " + original + " -> " + s);
        }
      } else {
        normalizedYears.add(original);
      }
    }
    return normalizedYears;
  }

  public static String normalizeYear(String year) {
    year = year.trim();

    year = year.replaceAll("^(?:sierpień|kwiecień|grudzień|febrer|février|maj|junio|July|septiembre|ottobre|octobre|Oktober|octubre de) (\\d{4})\\.?$", "$1");
    year = year.replaceAll("^dr\\.? ?(\\d{4})\\.?$", "$1"); // druk
    year = year.replaceAll("^(\\d{4}) :$", "$1");
    year = year.replaceAll("^(\\d{4})\\]\\.$", "$1");
    year = year.replaceAll("^\\d{4} \\[(\\d{4})\\]\\.?$", "$1");
    year = year.replaceAll("(\\d)\\.$", "$1");
    year = year.replaceAll("^c(\\d)", "$1");
    year = year.replaceAll("c(\\d{4})", "$1");
    year = year.replaceAll("\\d{4} \\[(\\d{4})\\]$", "$1");
    year = year.replaceAll("^\\[c?(\\d{4})\\]$", "$1");
    year = year.replaceAll("^\\[(.*?)\\]\\.?$", "$1");
    year = year.replaceAll("\\[(.*?)\\]", "$1");
    year = year.replaceAll("\\((.*?)\\)", "$1");
    year = year.replaceAll("\\[", "");
    year = year.replaceAll("\\]", "");
    year = year.replaceAll("^(ca\\.) ?", "");
    year = year.replaceAll("^(copyright|cop\\.|©|czerwiec|listopad|janvier|lipiec|wrzesień|październik|marzec|luty|januari|mars|maart|juin|juli|styczeń|grudzień|druk|styczeń) ", "");
    year = year.replaceAll("(\\d{4})\\?", "$1");
    year = year.replaceAll("^(\\d{3})\\?", "$10");
    year = year.replaceAll("-\\?$", "0");
    year = year.replaceAll("^(\\d{3})-$", "$10");
    year = year.replaceAll("^(\\d{4})-$", "$1");
    year = year.replaceAll("^(\\d{4})-(\\d{2})$", "$1");
    year = year.replaceAll("^.* i\\.e\\. (\\d{4})$", "$1");
    year = year.replaceAll("^(?:Shōwa|Shōwa|Minguo|Taishō) \\d+ (\\d{4})\\.?$", "$1");
    year = year.replaceAll("^(\\d{4}), cop\\. \\d{4}$", "$1");
    year = year.replaceAll("^\\d{4} !(\\d{4})$", "$1");
    year = year.replaceAll("^cop\\. ?(\\d{4})$", "$1");
    year = year.replaceAll("^ca ?(\\d{4})$", "$1");
    year = year.replaceAll("^(\\d{4}) (k\\.|körül)$", "$1");

    return year;
  }

  public void reportUnresolvedYears() {
    if (unresolvedYears.isEmpty())
      return;
    String content = unresolvedYears.entrySet().stream()
      .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
      .map(e -> e.getKey() + ": " + e.getValue())
      .collect(Collectors.joining("\n"));

    try {
      FileUtils.writeStringToFile(new File(outputDir, UNRESOLVED_YEARS_FILE), content, StandardCharsets.UTF_8, false);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
