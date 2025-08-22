package de.gwdg.metadataqa.marc.cli.utils.placename;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static de.gwdg.metadataqa.marc.cli.utils.placename.PlaceNameNormaliser.status.RESOLVED;
import static de.gwdg.metadataqa.marc.cli.utils.placename.PlaceNameNormaliser.status.UNRESOLVED;

public class PlaceNameNormaliser {
  private static final Logger logger = Logger.getLogger(PlaceNameNormaliser.class.getCanonicalName());

  enum status {RESOLVED, UNRESOLVED;}
  public static final String UNRESOLVED_PLACE_NAMES_FILE = "translations-unresolved-place-names.txt";

  /*
  private Set<String> knownMultiwordCities = Set.of(
    "New York", "Ithaca, NY", "Stamford, CN", "Tel Aviv", "Buenos Aires", "Middletown, CN",
    "Santiago de Chile", "Princeton, NJ", "Mount Vernon, NY", "Los Angeles", "Pôrto Alegre",
    "Berkeley, CA", "Philadelphia, PA", "'s-Gravenhage", "São Paulo", "Kentfield, CA", "Avon, CN"
  );
   */
  public static final List<String> UNKNOWN_PLACE_NAMES = List.of("Miejsce nieznane", "S.l.", "S.I.", "s.l.", "s.l", "S. l.", "S. I.", "n.p.", "s.n.");

  private final String translationPlaceNameDictionaryDir;
  private final String outputDir;

  private Map<String, PlaceName> coords;
  private Map<String, List<String>> synonyms;

  private Map<String, Integer> unresolvedPlaceNames = new HashMap<>();
  private Map<status, Integer> statistics = new HashMap<>();

  public PlaceNameNormaliser(String translationPlaceNameDictionaryDir,
                             String outputDir) {
    this.translationPlaceNameDictionaryDir = translationPlaceNameDictionaryDir;
    this.outputDir = outputDir;
    coords = new HashMap<>();
    synonyms = new HashMap<>();

    File errorFile = new File(outputDir, UNRESOLVED_PLACE_NAMES_FILE);
    if (errorFile.exists())
      errorFile.delete();

    try {
      processCoords(readCsvFile("coord.csv"));
      processSynonyms(readCsvFile("place-synonyms-normalized.csv"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (CsvException e) {
      throw new RuntimeException(e);
    }
  }

  public Map<String, PlaceName> getCoords() {
    return coords;
  }

  public Map<String, List<String>> getSynonyms() {
    return synonyms;
  }

  private void processSynonyms(List<String[]> rows) {
    for (String[] row : rows) {
      synonyms.computeIfAbsent(row[0], k -> new ArrayList<>()).add(row[1]);
    }
  }

  public void reportUnresolvedPlaceNames() {
    if (unresolvedPlaceNames.isEmpty())
      return;
    String summary = String.format("resolved: %d, unresolved: %d\n", statistics.get(RESOLVED), statistics.get(UNRESOLVED));
    String content = unresolvedPlaceNames.entrySet().stream()
      .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
      .map(e -> e.getKey() + ": " + e.getValue())
      .collect(Collectors.joining("\n"));

    try {

      FileUtils.writeStringToFile(new File(outputDir, UNRESOLVED_PLACE_NAMES_FILE), summary, StandardCharsets.UTF_8, false);
      FileUtils.writeStringToFile(new File(outputDir, UNRESOLVED_PLACE_NAMES_FILE), content, StandardCharsets.UTF_8, true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void processCoords(List<String[]> rows) {
    for (String[] row : rows) {
      coords.put(row[0], new PlaceName(row));
    }
  }

  private List<String[]> readCsvFile(String csvFile) throws IOException, CsvException {
    FileReader filereader = new FileReader(new File(translationPlaceNameDictionaryDir, csvFile));
    CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
    return csvReader.readAll();
  }

  public List<PlaceName> normalise(String placeName) {
    return resolve(clean(placeName));
  }

  public List<PlaceName> normalise(List<String> placeNames) {
    List<PlaceName> normalised = new ArrayList<>();
    for (String placeName : placeNames)
      normalised.addAll(resolve(clean(placeName)));
    return normalised;
  }

  public String clean(String input) {
    String output = input;
    // UTF-8 normalizer
    output = Normalizer.normalize(output, Normalizer.Form.NFKC);
    output = output.trim();

    // generic
    output = output.replaceAll("\\s*[,:;]+\\s*$", "");
    output = output.replaceAll("^\\[(.+)\\]$", "$1");
    output = output.replaceAll("^\\[(.+)$", "$1");
    output = output.replaceAll("^(.+)\\]$", "$1");
    output = output.replaceAll(",$", "");
    output = output.replaceAll("\\?$", "");
    output = output.replaceAll(" \\.\\.\\.$", "");
    output = output.replaceAll(" \\[etc\\.$", "");
    output = output.replaceAll(", cop\\.$", "");

    // States
    output = output.replaceAll(", (\\[?Ark\\.)$", ", AR");
    output = output.replaceAll(", Arizona$", ", AZ");
    output = output.replaceAll(", (\\[?D\\.C\\.)$", ", DC");
    output = output.replaceAll(", (California|\\[?Calif\\.|Calif)$", ", CA");
    output = output.replaceAll(", (\\[?Colo\\.)$", ", CO");         // Colorado
    output = output.replaceAll(", (Conn\\.|Connecticut|CT, USA)$", ", CT"); // Connecticut
    output = output.replaceAll(", (\\[?Fla\\.)$", ", FL");          // Florida
    output = output.replaceAll(", (Ga\\.)$", ", GA");               // Georgia
    output = output.replaceAll(", \\[?(Illinois|Ill\\.\\.?)$", ", IL");
    output = output.replaceAll(", (Ind\\.|Indiana)$", ", IN"); // Indiana
    output = output.replaceAll(", (\\[?Kans\\.)$", ", KS");
    output = output.replaceAll(", (La\\.)$", ", LA");               // Louisiana
    output = output.replaceAll(", (Massachusetts|\\[?Mass\\.|Ma\\.)$", ", MA"); // Massachusetts
    output = output.replaceAll(", (Michigan|Mich\\.)$", ", MI");
    output = output.replaceAll(", (Maryland|Md\\.|M\\.d\\.)$", ", MD");
    output = output.replaceAll(", (Minn\\.|Minnesota)$", ", MN");
    output = output.replaceAll(", (Miss\\.|Missouri)$", ", MO");            // Missouri
    output = output.replaceAll(", (\\[?Mt\\.|Mont\\.|Mo\\.)$", ", MT");
    output = output.replaceAll(", (\\[?Nebr\\.)$", ", NE");
    output = output.replaceAll(", (N\\. ?J\\.|New Jersey)$", ", NJ");
    output = output.replaceAll(", (\\[?N\\. ?Y\\.|N\\.Y|New York, USA)$", ", NY");
    output = output.replaceAll(", (Ohio)$", ", OH");
    output = output.replaceAll(", (Oklahoma)$", ", OK");
    output = output.replaceAll(", (Oreg\\.|Oregon)$", ", OR");     // Oregon
    output = output.replaceAll(", (\\[?Pa\\.|Pa|Pennsylvania)$", ", PA"); // Pennsylvania
    output = output.replaceAll(", (S\\.C\\.|SC\\.)$", ", SC");
    output = output.replaceAll(", (Tenn\\.)$", ", TN");            // Tennessee
    output = output.replaceAll(", (Texas|Tex\\.)$", ", TX");
    output = output.replaceAll(", (Virginia|\\[?Va\\.)$", ", VA"); // Virginia
    output = output.replaceAll(", (Vermont|Vt\\.)$", ", VT");      // Vermont
    output = output.replaceAll(", (Washington|Wash\\.)$", ", WA"); // Washington
    output = output.replaceAll(", (Wis\\.|Wisc\\.)$", ", WI");
    output = output.replaceAll(", \\[Vic\\.$", ", Vic.");   // Victoria

    if (UNKNOWN_PLACE_NAMES.contains(output))
      output = "UNKNOWN";

    return output;
  }

  public List<PlaceName> resolve(String originalNameForm) {
    if (coords.containsKey(originalNameForm)) {
      statistics.put(RESOLVED, statistics.computeIfAbsent(RESOLVED, k -> 0) + 1);
      return List.of(coords.get(originalNameForm));
    }
    List<PlaceName> placeNames = new ArrayList<>();
    boolean resolved = false;
    if (synonyms.containsKey(originalNameForm)) {
      statistics.put(RESOLVED, statistics.computeIfAbsent(RESOLVED, k -> 0) + 1);
      for (String synonym : synonyms.get(originalNameForm)) {
        placeNames.add(coords.get(synonym));
      }
    } else {
      statistics.put(UNRESOLVED, statistics.computeIfAbsent(UNRESOLVED, k -> 0) + 1);
      unresolvedPlaceNames.computeIfAbsent(originalNameForm, k -> 0);
      unresolvedPlaceNames.put(originalNameForm, unresolvedPlaceNames.get(originalNameForm)+1);
      // logger.info("Unresolved place name: " + originalNameForm);
    }
    return placeNames;
  }
}
