package de.gwdg.metadataqa.marc.cli.utils.translation;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.gwdg.metadataqa.marc.cli.utils.translation.ContributorNormaliser.TYPE.TRANSLATOR;

public class ContributorNormaliser {
  private static final Logger logger = Logger.getLogger(ContributorNormaliser.class.getCanonicalName());

  public static final String UNRESOLVED_CONTRIBUTORS_FILE = "translations-unresolved-contributors.csv";
  public static final Pattern LOWER = Pattern.compile("\\p{Lower}");
  public static final List<String> NAME_ELEMENTS = List.of(
    "de", "dos", "és", "von", "írta", "irta", "szerk.", "ill.", "zenéjét", "szerezte",
    "by", "du", "et", "al.", "di", "van", "v.", "hrsg.", "szöveg", "le", "vál.", "összeáll.", "rajz.",
    "la", "da", "der", "and", "del", "of", "d'"
  );
  private static final List<String> TRANSLATION_ELEMENTS = List.of(
    "ford.", "fordította"
  );
  private static final List<String> TRANSLATOR_PREFIXES = List.of(
    "ford.", "írta és ford.", "vál. és ford.", "ford. és magy.", "... ford.", "vál., szerk. és ford.",
    "fordította", "magyar szöveg", "ford., az utószót és a jegyzeteket írta", "vál., ford. és az utószót írta",
    "ford. és jegyzetekkel ell.", "összeáll. és ford.", "ford., a bevezetőt és a jegyzeteket írta",
    "ford. és szerk.", "ford., a bevezetőt és a jegyzeteket írta", "vál., ford.", "ford. és az utószót írta",
    "ford., a jegyzeteket és az utószót írta", "válogatta, fordította és az utószót írta",
    "vál., ford., a jegyzeteket és az utószót írta", "vál., ford., az utószót és a jegyzeteket írta",
    "vál., ford. és szerk.", "válogatta és fordította", "transl. by", "transl.", "a köt. fordítói",
    "ford., bev. és jegyz. ellátta", "a m. változatot kész.", "... fordítói", "ford. ...", "vál., ford. és bev.",
    "ford. és ... jegyzetekkel ell.", "németből ford.", "ford., bev. és jegyz.", "ford., vál. és az utószót írta",
    "ford., sajtó alá rend.", "ford., utószó és jegyz.", "vál., ford. és a jegyzeteket írta",
    "vál., ford., a kísérő tanulmányt és a jegyz. írta", "ford., az életrajzokat és a jegyz. írta",
    "vál., ford., előszó", "vál., ford., utószóval és életrajzi jegyzetekkel ell.",
    "összekötő szövegeket írta és a verseket ford.", "összeáll., az elbeszéléseket ford. és a tanulmányt írta",
    "angolból ford.", "ford. és bev.", "franciából ford.", "... magyar szöveg", "... vál. és ford.",
    "... összeáll. és ford.", "vál., ford. és utószó", "vál., ford. és utószó", "ford. és bev. ell.",
    "ford. és magy. ell.", "ford. és magy. ell.", "bev., vál., ford.", "bev., vál. és ford.",
    "vál., ford. és az életrajzi jegyzeteket írta", "a kötet fordítói", "görögből ford. és magyarázta",
    "ford. és bev. ellátta", "vál. és németből ford.", "a kötetet vál. és ford.", "a kötetet összeáll. és ford.",
    "a verseket vál. és ford.", "a tanulmányokat írta, a szövegeket vál., ford. és jegyzetekkel ell.",
    "vál., ford. és az előszót írta", "vál., ford. és jegyz.", "... vál., ford. és a jegyzeteket írta",
    "fordította és átdolgozta", "a bevezetőt írta, ford. és jegyzetekkel ell.",
    "összeáll., ford., bev. és jegyz.", "összeáll., vál., ford.", "transl., ed. by",
    "összeállította, a bevezetést és a jegyzeteket írta, a latin szövegeket fordította",
    "ford. és sajtó alá rend.", "ford. és a bevezetést írta", "ford., bev és jegyz. ellátta",
    "transl. from the Hungarian by", "vál., ford., bev., jegyz.", "vál., nyersford., utószó és jegyz.",
    "transl. and ed. by", "ford., bev. és magy.", "német nyelvre és nyelvterületre adaptálta és ford.",
    "német nyelvterületre adaptálta és ford.", "ford., bev. és magy.", "ford. és magyarázta",
    "trad. de", "ausgew. und aus dem Ungarischen übers. von", "ausgew. und aus dem Ung. übers. von",
    "ford. és átd.", "sel. and transl. by", "ford. és az ifjúság számára összeállították",
    "transl. ... by", "fordította, az utószót és a jegyzeteket írta", "ford., jegyz.",
    "übers. von", "műfordításai", "ford., bev.", "trad.", "ford., átd.", "vál., ford. és a bevezetőt írta",
    "aus dem Ungarischen von", "aus dem Ung. von", "ford. és az előszót írta", "szerk. és ford.",
    "francziából ford.", "angolból átd.", "szerk. és ford.", "a meséket ford.",
    "válogatta, fordította, az utószót és a jegyzeteket írta", "ford., szerk.",
    "... ford., az előszót és a jegyzeteket írta", "bev. és ford.", "vál., ford., utószó",
    "introd., transl.", "... übertr. von"
  );
  private static final List<String> TRANSLATOR_SUFFIXES = List.of(
    "fordításában", "átköltésében", "ford.", "fordításai", "műfordításai", "fordításában és utószavával"
  );

  private static final List<String> EDITOR_PREFIXES = List.of(
    "főszerk.", "vál. és az előszót írta", "vál., az utószót és az életrajzi jegyzeteket írta",
    "vál. és az életrajzi jegyzeteket írta", "... vál. és átd.", "vál., szerk. és az utószót írta",
    "sajtó alá rend.", "válogatta és szerkesztette", "szerkesztette", "vál. és az utószót írta", "ed. by",
    "válogatta", "vál. és a jegyzeteket írta", "ed. by", "közread.", "vál. és jegyz.", "vál., utószó és jegyz.",
    "vál., utószó és jegyz.", "vál. és utószó", "bev.", "vál., ford., utószó és jegyz.", "magyarázta",
    "... vál. és az előszót írta", "... közread.", "... fel. szerk.", "vál., bev.", "fel. szerk.",
    "... vál., az utószót és az életrajzi jegyzeteket írta", "... vál. és az utószót írta", "közread. a",
    "ed.-in-chief", "szerk., bev. és magy.", "szerk., bev. és magy. ellátta", "... szerk. és a tanulmányt írta",
    "vál., átd.", "összeáll. és átd.", "bev. és magyarázatokkal ellátta", "összeáll., bev., magy. és jegyz.",
    "vál., bev. és jegyz.", "sel. and ed. by", "válogatta és a jegyzeteket készítette",
    "válogatta és a gyerekeknek átdolgozta", "vál., sajtó alá rend. és az utószót írta",
    "válogatta és a jegyzeteket írta", "válogatta és a jegyzeteket összeállította", "ed. by ...",
    "főszerk. ...", "sajtó alá rend. ...", "comp. and ed. by", "szerk. és magyarázta", "szerk. és átd.",
    "válogatta, szerkesztette és a bevezető tanulmányt írta", "közread. az", "sel. and ed. by",
    "bev. és magyarázta", "iskolai használatra magyarázta", "szerk., utószó és jegyz.",
    "Válogatta és szerkesztette", "válogatta és az utószót írta", "sajtó alá rend. és bev.",
    "bev. és magy", "bev. és magy. ellátta", "ed.", "bev. és magy.", "comp. by", "magy.", "sel. by",
    "összegyűjt.", "utószó", "vál., szerk. és az előszót írta", "vál., ford., az előszót és a jegyzeteket írta",
    "válogatta és szerkesztette, az előszót és a jegyzeteket írta", "vál., utószó", "hrsg. von",
    "kiadta és magyarázta", "összeáll., bev., magyarázatokkal és jegyzetekkel ell.",
    "az ifjúság számára összevál.", "vál., bev. és a függ. összeáll.", "compil.",
    "sajtó alá rend., vál., bev. és jegyz. ell.", "sel., introd., ed.", "ausgew. von",
    "válogatta, az utószót és a jegyzeteket írta", "szerkesztette, az utószót és a jegyzeteket írta",
    "sajtó alá rend., az utószót és a jegyzeteket írta", "vál., utószó, jegyz.", "intr.", "vál., szerk.",
    "előszó", "vál., szerk., az utószót és az életrajzi jegyzeteket írta",
    "vál., szerk., jegyz.", "zsgest. von", "zost.", "ured.", "red.", "összeáll. és magyarázatokkal ellátta",
    "with a pref. and ill. by", "vál., életrajzi jegyz.", "... az utószót ... írta",
    "intr. by", "a bev. tanulmányt írta", "a cura di"
  );
  private static final List<String> EDITOR_SUFFIXES = List.of(
    "válogatása", "válogatásában", "összeállítása", "főszerk.", "fel. szerk."
  );
  private static final List<String> AUTHOR_PREFIXES = List.of(
    "szerző", "szerzők", "átd.", "... átd.", "vál. és átd.", "újrameséli", "írták", "elmeséli az",
    "Text und Ill.", "written by", "írta és rajzolta", "text", "az ifjúság számára átd.",
    "az ifjúság számára átdolg.", "alapján", "adaptációja", "művei", "eredeti regényét átd.", "meséit átd.",
    "ötlete alapján írta", "nyomán írta", "regénye", "ötletéből ... írta", "meséjét átd.", "meséje",
    "nacherzählt von", "írásait átd.", "adapted by", "munkája", "tollából", "texte et photos", "par",
    "forgatókönyve alapján írta", "sorozata alapján írta", "a meséket átd."
  );

  private static final List<String> ILLUSTRATOR_PREFIXES = List.of(
    "rajz", "... összefirkálta", "fot.", "fotó", "photos by", "phot."
  );
  private static final List<String> ILLUSTRATOR_SUFFIXES = List.of(
    "illusztrációival", "rajzaival"
  );


  public HashMap<TYPE, List<String>> getContributors() {
    return contributors;
  }

  enum TYPE {
    AUTHOR, TRANSLATOR, EDITOR, ILLUSTRATOR;
  }

  private final String outputDir;
  private final Map<String, Integer> unresolvedContributors;
  private final Map<String, List<String>> unresolvedContributorsSamples;
  private final Pattern compiledPattern;
  private Map<String, Pattern> patterns;
  private HashMap<TYPE, List<String>> contributors;
  private final String translatorPrefixes;
  private final String translatorSuffixes;
  private final String editorPrefixes;
  private final String editorSuffixes;
  private final String authorPrefixes;
  private final String illustratorPrefixes;
  private final String illustratorSuffixes;

  public ContributorNormaliser(String outputDir, Pattern compiledPattern) {
    this.outputDir = outputDir;
    this.compiledPattern = compiledPattern;
    this.unresolvedContributors = new HashMap<>();
    this.unresolvedContributorsSamples = new HashMap<>();
    this.patterns = new HashMap<>();
    translatorPrefixes = "(?:" + StringUtils.join(TRANSLATOR_PREFIXES, "|").replaceAll("\\.", "\\\\.") + ")";
    translatorSuffixes = "(?:" + StringUtils.join(TRANSLATOR_SUFFIXES, "|").replaceAll("\\.", "\\\\.") + ")";
    editorPrefixes = "(?:" + StringUtils.join(EDITOR_PREFIXES, "|").replaceAll("\\.", "\\\\.") + ")";
    editorSuffixes = "(?:" + StringUtils.join(EDITOR_SUFFIXES, "|").replaceAll("\\.", "\\\\.") + ")";
    authorPrefixes = "(?:" + StringUtils.join(AUTHOR_PREFIXES, "|").replaceAll("\\.", "\\\\.") + ")";
    illustratorPrefixes = "(?:" + StringUtils.join(ILLUSTRATOR_PREFIXES, "|").replaceAll("\\.", "\\\\.") + ")";
    illustratorSuffixes = "(?:" + StringUtils.join(ILLUSTRATOR_SUFFIXES, "|").replaceAll("\\.", "\\\\.") + ")";
  }

  public boolean hasLowercase(String contributorString) {
    boolean hasLowercase = false;
    Scanner scanner = new Scanner(contributorString.trim().replaceAll("^\\[", ""));
    while (scanner.hasNext()) {
      String token = scanner.next();
      if (Character.isLowerCase(token.charAt(0)) && !NAME_ELEMENTS.contains(token)) {
        hasLowercase = true;
        // System.err.println("lower: " + token);
      }
    }
    return hasLowercase;
  }

  public boolean hasContributor(String contributorString) {
    Matcher matcher = compiledPattern.matcher(contributorString);
    boolean found = matcher.find();
    if (found) {
      // System.err.println("before: " + contributorString.substring(0, matcher.start()));
      // System.err.println("after: " + contributorString.substring(matcher.end()));
    }
    return found;
  }

  public void process(List<XmlFieldInstance> list) {
    contributors = new HashMap<>();
    for (XmlFieldInstance xmlFieldInstance : list) {
      process(xmlFieldInstance.getValue());
    }
  }

  public void process(String text) {
    text = Normalizer.normalize(text.trim(), Normalizer.Form.NFKC);
    text = text.replaceAll("(\\[|\\])", "").replaceAll("\\s+", " ");

    if (hasContributor(text) || hasLowercase(text)) {
      String translationPattern = extractPattern(text);
      if (!translationPattern.equals("")) {
        // System.err.println(text);
        unresolvedContributors.put(
          translationPattern,
          unresolvedContributors.getOrDefault(translationPattern, 0) + 1
        );
        if (   !translationPattern.equals("TRANSLATOR")
            && !translationPattern.equals("EDITOR")
            && !translationPattern.equals("AUTHOR")
            && !translationPattern.equals("ILLUSTRATOR")
        ) {
          if (!unresolvedContributorsSamples.containsKey(translationPattern))
            unresolvedContributorsSamples.put(translationPattern, new ArrayList());
          if (    unresolvedContributorsSamples.get(translationPattern).size() < 5
              && !unresolvedContributorsSamples.get(translationPattern).contains(text))
            unresolvedContributorsSamples.get(translationPattern).add(text);
        }
      }
    }
  }

  private boolean matches(String text, TYPE type, String pattern) {
    Matcher matcher = getPattern(pattern).matcher(text);
    if (matcher.matches()) {
      addContributor(type, matcher.group(1));
      if (matcher.groupCount() == 8)
        addContributor(type, matcher.group(5));
      return true;
    }
    return false;
  }

  private void addContributor(TYPE type, String contributor) {
    if (!contributors.containsKey(type))
      contributors.put(type, new ArrayList<>());

    if (getPattern("(, | és | and | u\\. | valamint )").matcher(contributor).find()) {
      for (String localContributor : contributor.split("(, | és | and | u\\. | valamint )")) {
        contributors.get(type).add(localContributor);
      }
    } else {
      contributors.get(type).add(contributor);
    }
  }

  private String extractPattern(String text) {

    if (matches(text, TRANSLATOR, "^" + translatorPrefixes + " ([\\p{javaUpperCase}][\\p{javaLowerCase}\\.]*(( |-|\\. | és |, | and | u\\. | valamint )[\\p{javaUpperCase}]([\\p{javaLowerCase}]+|\\.))+)(?: et al\\.|\\.| \\.\\.\\. et al\\.)?$")) {
      return "TRANSLATOR";
    }

    if (matches(text, TRANSLATOR, "^([\\p{javaUpperCase}][\\p{javaLowerCase}\\.]*(( |-|\\. | és |, | and | u\\. | valamint )[\\p{javaUpperCase}]([\\p{javaLowerCase}]+|\\.))+)(?: et al\\.|\\.| \\.\\.\\. et al\\.)? " + translatorSuffixes + "$")) {
      return "TRANSLATOR";
    }

    if (matches(text, TYPE.EDITOR, "^" + editorPrefixes + " ([\\p{javaUpperCase}][\\p{javaLowerCase}\\.]*(( |-|\\. | és |, | and | u\\. | valamint )[\\p{javaUpperCase}]([\\p{javaLowerCase}]+|\\.))+)(?: et al\\.|\\.| \\.\\.\\. et al\\.)?$")) {
      return "EDITOR";
    }

    if (matches(text, TYPE.EDITOR, "^([\\p{javaUpperCase}][\\p{javaLowerCase}\\.]*(( |-|\\. | és |, | and | u\\. | valamint )[\\p{javaUpperCase}]([\\p{javaLowerCase}]+|\\.))+)(?: et al\\.|\\.| \\.\\.\\. et al\\.)? " + editorSuffixes + "$")) {
      return "EDITOR";
    }

    if (matches(text, TYPE.AUTHOR, "^" + authorPrefixes + " ([\\p{javaUpperCase}][\\p{javaLowerCase}\\.]*(( |-|\\. | és |, | and | u\\. | valamint )[\\p{javaUpperCase}]([\\p{javaLowerCase}]+|\\.))+)(?: et al\\.|\\.| \\.\\.\\. et al\\.)?$")) {
      return "AUTHOR";
    }

    if (matches(text, TYPE.AUTHOR, "^([\\p{javaUpperCase}][\\p{javaLowerCase}\\.]*(( |-|\\. | és |, | and | u\\. | valamint )[\\p{javaUpperCase}]([\\p{javaLowerCase}]+|\\.))+)(?: et al\\.|\\.| \\.\\.\\. et al\\.)? " + authorPrefixes + "$")) {
      return "AUTHOR";
    }

    if (matches(text, TYPE.AUTHOR, "^([\\p{javaUpperCase}][\\p{javaLowerCase}\\.]*(( |-|\\. | és |, | and | u\\. | valamint )[\\p{javaUpperCase}]([\\p{javaLowerCase}]+|\\.))+)(?: et al\\.|\\.| \\.\\.\\. et al\\.)? " + authorPrefixes + " ([\\p{javaUpperCase}][\\p{javaLowerCase}\\.]*(( |-|\\. | és |, | and | u\\. | valamint )[\\p{javaUpperCase}]([\\p{javaLowerCase}]+|\\.))+)$")) {
      return "AUTHOR";
    }

    if (matches(text, TYPE.ILLUSTRATOR, "^" + illustratorPrefixes + " ([\\p{javaUpperCase}][\\p{javaLowerCase}\\.]*(( |-|\\. | és |, | and | u\\. | valamint )[\\p{javaUpperCase}]([\\p{javaLowerCase}]+|\\.))+)(?: et al\\.|\\.| \\.\\.\\. et al\\.)?$")) {
      return "ILLUSTRATOR";
    }

    if (matches(text, TYPE.ILLUSTRATOR, "^([\\p{javaUpperCase}][\\p{javaLowerCase}\\.]*(( |-|\\. | és |, | and | u\\. | valamint )[\\p{javaUpperCase}]([\\p{javaLowerCase}]+|\\.))+)(?: et al\\.|\\.| \\.\\.\\. et al\\.)? " + illustratorSuffixes + "$")) {
      return "ILLUSTRATOR";
    }

    text = text.replace("ford. és magyarázta", "FORD");
    text = text.replace("ford., bev. és jegyz. ellátta", "FORD");
    text = text.replace("vál., ford. és az utószót írta", "FORD");
    text = text.replace("válogatta, fordította és az utószót írta", "FORD");
    text = text.replace("ford. és az utószót írta", "FORD");
    text = text.replace("fordította és az utószót írta", "FORD");
    text = text.replace("vál. és ford.", "FORD");
    text = text.replace("ford. és magy.", "FORD");
    text = text.replace("vál., szerk. és ford.", "FORD");
    text = text.replace("ford. és szerk.", "FORD");
    text = text.replace("összeáll. és ford.", "FORD");
    text = text.replace("ford., az utószót és a jegyzeteket írta", "FORD");
    text = text.replace("ford. és bev.", "FORD");
    text = text.replace("németből ford.", "FORD");
    text = text.replace("fordítói", "FORD");
    text = text.replace("magyar szöveg", "FORD");
    text = text.replace("a köt. fordítói", "FORD");
    text = text.replace("ford. és ... jegyzetekkel ell.", "FORD");
    text = text.replace("ford., a jegyzeteket és az utószót írta", "FORD");
    text = text.replace("vál., ford. és bev.", "FORD");
    text = text.replace("válogatta és fordította", "FORD");
    text = text.replace("vál., ford.", "FORD");
    text = text.replace("vál., fordította", "FORD");
    text = text.replace("a m. változatot kész.", "FORD");
    text = text.replace("angolból ford.", "FORD");
    text = text.replace("angolból átd.", "FORD");
    text = text.replace("angolból szabadon átd.", "FORD");
    text = text.replace("német nyelvre és nyelvterületre adaptálta és ford.", "FORD");
    text = text.replace("német nyelvterületre adaptálta és ford.", "FORD");
    text = text.replace("franciából ford.", "FORD");
    text = text.replace("ford. és jegyzetekkel ell.", "FORD");
    text = text.replace("ford., a bevezetőt és a jegyzeteket írta", "FORD");
    text = text.replace("ford., bev. és jegyz.", "FORD");
    text = text.replace("ford., bev. és magy.", "FORD");
    text = text.replace("írta és ford.", "FORD");
    text = text.replace("fordításában", "FORD");
    text = text.replace("fordította", "FORD");
    text = text.replace("transl. by", "transl.");

    text = text.replace("vál. és az utószót írta", "SZERK");
    text = text.replace("vál. és az előszót írta", "SZERK");
    text = text.replace("vál., szerk. és az utószót írta", "SZERK");
    text = text.replace("vál., utószó és jegyz.", "SZERK");
    text = text.replace("vál. és jegyz.", "SZERK");
    text = text.replace("vál. és utószó", "SZERK");
    text = text.replace("utószó és jegyz.", "SZERK");
    text = text.replace("vál., az utószót és az életrajzi jegyzeteket írta", "SZERK");
    text = text.replace("sajtó alá rend.", "SZERK");
    text = text.replace("vál., átd.", "SZERK");
    text = text.replace("vál., bev.", "SZERK");
    text = text.replace("... vál. és átd.", "SZERK");
    text = text.replace("bev. és magy. ellátta", "SZERK");
    text = text.replace("bev. és magy.", "SZERK");
    text = text.replace("bev. és magyarázatokkal ellátta", "SZERK");
    text = text.replace("összeáll. és átd.", "SZERK");
    text = text.replace("kiadta és magyarázta", "SZERK");
    text = text.replace("vál. és a jegyzeteket írta", "SZERK");
    text = text.replace("főszerk.", "SZERK");
    text = text.replace("ed.-in-chief", "SZERK");
    text = text.replace("fel. szerk.", "SZERK");
    text = text.replace("ed. by", "SZERK");
    text = text.replace("sel., introd., ed.", "SZERK");
    text = text.replace("válogatta és szerkesztette", "SZERK");
    text = text.replace("összeáll., bev., magyarázatokkal és jegyzetekkel ell.", "SZERK");
    text = text.replace("vál., bev. és jegyz.", "SZERK");
    text = text.replace("vál. és az életrajzi jegyzeteket írta", "SZERK");
    text = text.replace("válogatta", "SZERK");
    text = text.replace("szerkesztette", "SZERK");
    text = text.replace("magyarázta", "SZERK");
    text = text.replace("közread.", "SZERK");

    Scanner scanner = new Scanner(text);
    List<String> patterns = new ArrayList<>();
    while (scanner.hasNext()) {
      String token = scanner.next();
      if (token.equals("FORD"))
        patterns.add("<FORD>");
      else if (token.equals("SZERK"))
        patterns.add("<SZERK>");
      else if (TRANSLATION_ELEMENTS.contains(token)) {
        patterns.add(String.format("<FORD|%s>", token));
      } else {
        if (Character.isUpperCase(token.charAt(0))) {
          patterns.add("X");
        } else {
          patterns.add(token);
        }
      }
    }
    return String.join(" ", patterns);
  }

  private Pattern getPattern(String text) {
    if (!patterns.containsKey(text))
      patterns.put(text, Pattern.compile(text));
    return patterns.get(text);
  }

  public void reportUnresolvedContributors() {
    logger.info(String.format("reportUnresolvedContributors(): %d - %d", unresolvedContributors.size(), unresolvedContributorsSamples.size()));
    if (unresolvedContributors.isEmpty())
      return;

    String content = unresolvedContributors.entrySet().stream()
      .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
      .map(e -> String.format("%s: %d (%s)", e.getKey(), e.getValue(), StringUtils.join(unresolvedContributorsSamples.get(e.getKey()), " --- ")))
      .collect(Collectors.joining("\n"));

    try {
      FileUtils.writeStringToFile(new File(outputDir, UNRESOLVED_CONTRIBUTORS_FILE), "contributor,count\n", StandardCharsets.UTF_8, false);
      FileUtils.writeStringToFile(new File(outputDir, UNRESOLVED_CONTRIBUTORS_FILE), content, StandardCharsets.UTF_8, true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
