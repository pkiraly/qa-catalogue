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

public class PlaceNameNormaliser {
  private static final Logger logger = Logger.getLogger(PlaceNameNormaliser.class.getCanonicalName());
  public static final String UNRESOLVED_PLACE_NAMES_FILE = "translations-unresolved-place-names.txt";

  /*
  private Set<String> knownMultiwordCities = Set.of(
    "New York", "Ithaca, NY", "Stamford, CN", "Tel Aviv", "Buenos Aires", "Middletown, CN",
    "Santiago de Chile", "Princeton, NJ", "Mount Vernon, NY", "Los Angeles", "Pôrto Alegre",
    "Berkeley, CA", "Philadelphia, PA", "'s-Gravenhage", "São Paulo", "Kentfield, CA", "Avon, CN"
  );
   */

  private final String translationPlaceNameDictionaryDir;
  private final String outputDir;

  private Map<String, PlaceName> coords;
  private Map<String, List<String>> synonyms;

  private Map<String, Integer> unresolvedPlaceNames = new HashMap<>();

  public PlaceNameNormaliser(String translationPlaceNameDictionaryDir, String outputDir) {
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
    String content = unresolvedPlaceNames.entrySet().stream()
      .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
      .map(e -> e.getKey() + ": " + e.getValue())
      .collect(Collectors.joining("\n"));

    try {
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
    output = Normalizer.normalize(output, Normalizer.Form.NFKC);
    output = output.replaceAll("\\s*[,:;]\\s*$", "");
    output = output.replaceAll("^\\[(.+)\\]$", "$1");
    output = output.replaceAll("^\\[(.+)$", "$1");
    output = output.replaceAll("^(.+)\\]$", "$1");
    output = output.replaceAll(" \\[etc\\.$", "");
    output = output.replaceAll(", Calif\\.$", ", CA");
    output = output.replaceAll(", California$", ", CA");
    output = output.replaceAll(", (Conn\\.|Connecticut)$", ", CT");
    output = output.replaceAll(", N\\. ?Y\\.$", ", NY");
    output = output.replaceAll(", N\\. ?J\\.$", ", NJ");
    output = output.replaceAll(", Massachusetts$", ", MA");
    output = output.replaceAll(", Arizona$", ", AZ");
    output = output.replaceAll(", Pa\\.$", ", PA");
    output = output.replaceAll(", Ill\\.$", ", IL");
    output = output.replaceAll(", Illinois$", ", IL");
    output = output.replaceAll(", Texas$", ", TX");
    output = output.replaceAll(", Maryland$", ", MD");
    return output;
  }

  public List<PlaceName> resolve(String originalNameForm) {
    if (coords.containsKey(originalNameForm)) {
      return List.of(coords.get(originalNameForm));
    }
    List<PlaceName> placeNames = new ArrayList<>();
    if (synonyms.containsKey(originalNameForm)) {
      for (String synonym : synonyms.get(originalNameForm)) {
        placeNames.add(coords.get(synonym));
      }
    } else {
      unresolvedPlaceNames.computeIfAbsent(originalNameForm, k -> 0);
      unresolvedPlaceNames.put(originalNameForm, unresolvedPlaceNames.get(originalNameForm)+1);
      // logger.info("Unresolved place name: " + originalNameForm);
    }
    return placeNames;
  }
}
