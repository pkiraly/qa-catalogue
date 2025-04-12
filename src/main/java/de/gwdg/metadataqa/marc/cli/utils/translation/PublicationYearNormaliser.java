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
  public static final String PATTERNS_FILE = "translations-year-patterns.txt";

  private static final Pattern yearPattern = Pattern.compile("^\\d{4}$");
  private final String outputDir;
  private Map<String, Integer> unresolvedYears;
  private Map<String, PatternCounter> patterns;

  public PublicationYearNormaliser(String outputDir) {
    this.outputDir = outputDir;
    this.unresolvedYears = new HashMap<>();
    this.patterns = new HashMap<>();
  }

  public List<String> processYear(List<String> extracted) {
    List<String> normalizedYears = new ArrayList<>();
    for (String original : extracted) {
      if (!yearPattern.matcher(original).matches()) {
        String normalized = normalizeYear(original);
        boolean handled = true;
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
          handled = false;
        }
        addPattern(original, handled);

      } else {
        normalizedYears.add(original);
      }
    }
    return normalizedYears;
  }

  private void addPattern(String original, boolean handled) {
    String pattern = original.replaceAll("\\d", "N");
    if (!patterns.containsKey(pattern)) {
      patterns.put(pattern, new PatternCounter(handled));
    } else {
      patterns.get(pattern).increment();
    }
  }

  public static String normalizeYear(String year) {
    year = year.trim();
    year = year.replaceAll(" +", " ");

    // roman
    year = year.replaceAll("^(?:(?:anno|annô|an\\. Dom\\.|a|im Jahr|gedruckt im Jahr,|an\\.|anno Christi) )?(?:M[MDCLXVI\\.]+)\\.? \\[(\\d{4})\\]$", "$1");
    // year = year.replaceAll("^(?:M[MDCLXVI\\.]+)\\. \\[(\\d{4})\\]$", "$1");

    year = year.replaceAll("^\\[?(?:sierpień|kwiecień|grudzień|druk janvier|January|enero de|gennaio|febrer|février|druk février|februari|febrer de|febrero de|Fevrouários|febreiro,|druk febbraio|marzo de|druk mars|març del|março|druk marzo|März|avril|abril|abril de|april|druk aprile|Aprílios|druk avril|April|maj|mai|mayo de|Mai|druk mai|druk mayo de|maggio|mei|druk maggio|junio|junio de|juny|juni|druk juin|junho|juny del|junho de|July|julio de|luglio|julho|july|ioúlios|juliol,|agosto de|août|septembre|septiembre|settembre|druk settembre|September|septiembre de|september|setembro de|setembre de|ottobre|octobre|Oktober|octubre de|octubre|outubro|oktober|October|druk octobre|druk ottobre|november|druk novembre|novembre|noviembre de|noviembre|diciembre de|druk décembre|Dezember|december|dicembre|décembre|dezembro|December|druk Dekémvrio toû|ok\\.|an\\.|aōa\\.|año de|Im Jahr) (\\d{4})\\]?\\.?$", "$1");

    year = year.replaceAll("^\\d{4}\\[!(\\d{4})\\]$", "$1");

    year = year.replaceAll("^dr\\.? ?(\\d{4})\\.?$", "$1"); // druk
    year = year.replaceAll("^(\\d{4}) :$", "$1");
    year = year.replaceAll("^(\\d{4})\\]\\.$", "$1");
    year = year.replaceAll("^\\d{4} \\[(\\d{4})\\]\\.?$", "$1");
    year = year.replaceAll("(\\d)\\.$", "$1");
    year = year.replaceAll("^\\[?(?:©|c|℗|ccop\\.|cp\\.|cop|copyright,|copyright ©|op\\.|copryright|copyrright|copyrighr) ?(\\d{4})\\]?\\.?", "$1");
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
    year = year.replaceAll("^(\\d{2})--$", "$100");
    year = year.replaceAll("^(\\d{2})\\?\\?$", "$100");
    year = year.replaceAll("^(\\d{3})-$", "$10");
    year = year.replaceAll("^(\\d{3})\\?$", "$10");
    year = year.replaceAll("^(\\d{4})-$", "$1");
    year = year.replaceAll("^(\\d{4})-(\\d{2})$", "$1");
    year = year.replaceAll("^.* i\\.e\\. (\\d{4})$", "$1");
    year = year.replaceAll("^(?:Shōwa|Shōwa|Showa|Minguo|Min|Taishō|Shō|min kuo|Min kuo|Min-kuo|Min guo|Meiji|Kuang-hsü|Heisei) \\d+ \\[?(\\d{4})\\]?\\.?$", "$1");
    year = year.replaceAll("^(\\d{4}), cop\\. \\d{4}$", "$1");
    year = year.replaceAll("^\\d{4} !(\\d{4})$", "$1");
    year = year.replaceAll("^cop\\. ?(\\d{4})$", "$1");
    year = year.replaceAll("^(?:ca|anno) ?(\\d{4})$", "$1");

    // Hungarian
    year = year.replaceAll("^(\\d{4})\\.? (k\\.|körül|után|előtt|eszt\\.|esztend\\.)$", "$1");

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

  public void reportPatterns() {
    if (patterns.isEmpty())
      return;
    String content = patterns.entrySet().stream()
      .sorted((o1, o2) -> o2.getValue().counter.compareTo(o1.getValue().counter))
      .map(e -> String.format("'%s': %s (%d)", e.getKey(), e.getValue().handled, e.getValue().counter))
      .collect(Collectors.joining("\n"));

    try {
      FileUtils.writeStringToFile(new File(outputDir, PATTERNS_FILE), content, StandardCharsets.UTF_8, false);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private class PatternCounter {
    boolean handled = false;
    Integer counter = 0;

    public PatternCounter(boolean handled) {
      this.handled = handled;
      this.counter = 1;
    }

    public void increment() {
      counter++;
    }
  }
}
