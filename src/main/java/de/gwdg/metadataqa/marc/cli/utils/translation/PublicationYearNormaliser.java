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
    List<String> normalized = new ArrayList<>();
    for (String s : extracted) {
      if (!yearPattern.matcher(s).matches()) {
        String original = s;
        s = s.replaceAll("(\\d)\\.$", "$1");
        s = s.replaceAll("^c(\\d)", "$1");
        s = s.replaceAll("c(\\d{4})", "$1");
        s = s.replaceAll("\\d{4} \\[(\\d{4})\\]$", "$1");
        s = s.replaceAll("^\\[c?(\\d{4})\\]$", "$1");
        s = s.replaceAll("^\\[(.*?)\\]\\.?$", "$1");
        s = s.replaceAll("\\[(.*?)\\]", "$1");
        s = s.replaceAll("\\((.*?)\\)", "$1");
        s = s.replaceAll("\\[", "");
        s = s.replaceAll("\\]", "");
        s = s.replaceAll("^(ca\\.) ?", "");
        s = s.replaceAll("^(copyright|cop\\.|©|czerwiec|listopad|janvier|lipiec|wrzesień|październik|marzec|luty|januari|mars|maart|juin|juli|styczeń|grudzień|druk|styczeń) ", "");
        s = s.replaceAll("(\\d{4})\\?", "$1");
        s = s.replaceAll("^(\\d{3})\\?", "$10");
        s = s.replaceAll("-\\?$", "0");
        s = s.replaceAll("^(\\d{3})-$", "$10");
        s = s.replaceAll("^(\\d{4})-$", "$1");
        s = s.replaceAll("^(\\d{4})-(\\d{2})$", "$1");
        s = s.replaceAll("^.* i\\.e\\. (\\d{4})$", "$1");
        s = s.replaceAll("Shōwa \\d+ (\\d{4})$", "$1");
        s = s.replaceAll("^(\\d{4}), cop\\. \\d{4}$", "$1");
        s = s.replaceAll("^\\d{4} !(\\d{4})$", "$1");
        s = s.replaceAll("^cop\\. ?(\\d{4})$", "$1");
        s = s.replaceAll("^ca ?(\\d{4})$", "$1");
        s = s.replaceAll("^(\\d{4}) (k\\.|körül)$", "$1");
        if (Pattern.compile("^(\\d{4})-(\\d{4})$").matcher(s).matches()) {
          normalized.addAll(Arrays.asList(s.split("-")));
        } else if (Pattern.compile("^(\\d{4}), ?(\\d{4})$").matcher(s).matches()) {
          normalized.addAll(Arrays.asList(s.split(", ?")));
        } else if (s.equals("s.d.") || s.equals("n.d.") || s.equals("s.a.")) {
          //
        } else if (yearPattern.matcher(s).matches()) {
          normalized.add(s);
        } else {
          String key = String.format("'%s' -> '%s'", original, s);
          unresolvedYears.put(key, unresolvedYears.getOrDefault(key, 0) + 1);
          // System.err.println("Unhandled case: " + original + " -> " + s);
        }
      } else {
        normalized.add(s);
      }
    }
    return normalized;
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
