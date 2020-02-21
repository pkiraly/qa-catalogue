package de.gwdg.metadataqa.marc.utils.pica;

import com.opencsv.CSVReader;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.Utils;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class PicaReaderTest {

  public static final Pattern SET = Pattern.compile("^SET: ");
  public static final Pattern EINGABE = Pattern.compile("^Eingabe: ");
  public static final Pattern WARNUNG = Pattern.compile("^Warnung: ");

  @Test
  public void readTags() {
    CSVReader reader = null;
    try {
      Path tagsFile = FileUtils.getPath("pica/pica-tags-2013.csv");
      reader = new CSVReader(new FileReader(tagsFile.toString()), ';');
      List<String[]> myEntries = reader.readAll();
      Map<String, List<PicaTagDefinition>> map = new HashMap<>();
      assertEquals(431, myEntries.size());
      for (int i = 0; i < myEntries.size(); i++) {
        String[] line = myEntries.get(i);
        if (line.length != 5) {
          System.err.println(StringUtils.join(line, ", "));
        } else {
          PicaTagDefinition tag = new PicaTagDefinition(line);
          addTag(map, tag);
        }
      }

      Map<String, Integer> counter = new HashMap<>();
      Path recordsFile = FileUtils.getPath("pica/picaplus-sample.txt");
      try (BufferedReader br = new BufferedReader(new FileReader(recordsFile.toString()))) {
        String line;
        while ((line = br.readLine()) != null) {
          if (SET.matcher(line).find()) {
            // System.err.println("----");
          } else if (!line.equals("")
              && !EINGABE.matcher(line).find()
              && !WARNUNG.matcher(line).find()) {
            PicaLine pl = new PicaLine(line);
            if (map.containsKey(pl.getTag())) {
              // System.err.println(map.get(pl.getTag()).getDescription() + ": " + pl.formatSubfields());
            } else {
              Utils.count(pl.getTag(), counter);
              // System.err.printf("unknown %s: %s\n", pl.getTag(), pl.formatSubfields());
            }
          }
        }
      }

      List<String> known_problems = Arrays.asList(
        "201U/001", "202D/001", "101U", "102D", "209A/001"
      );
      counter.entrySet()
        .stream()
        .sorted((e1, e2) -> {
            return e2.getValue().compareTo(e1.getValue());
          }
        )
        .forEach(
          entry -> {
            if (!known_problems.contains(entry.getKey()))
              System.err.printf("%s: %d%n", entry.getKey(), entry.getValue());
          }
        );
    } catch(FileNotFoundException e){
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void readAvramSchema() {
    CSVReader reader = null;
    JSONParser parser = new JSONParser();
    try {
      Map<String, List<PicaTagDefinition>> schemaDirectory = new HashMap<>();
      schemaDirectory.putAll(readSchema(parser, "pica/pica-schema.json"));
      schemaDirectory.putAll(readSchema(parser, "pica/pica-schema-extra.json"));

      Map<String, Integer> counter = new HashMap<>();
      Path recordsFile = FileUtils.getPath("pica/picaplus-sample.txt");
      try (BufferedReader br = new BufferedReader(new FileReader(recordsFile.toString()))) {
        String line;
        while ((line = br.readLine()) != null) {
          if (SET.matcher(line).find()) {
            // System.err.println("----");
          } else if (!line.equals("")
            && !EINGABE.matcher(line).find()
            && !WARNUNG.matcher(line).find()) {
            PicaLine pl = new PicaLine(line);
            if (!directoryContains(schemaDirectory, pl)) {
              Utils.count(pl.getQualifiedTag(), counter);
            }
          }
        }
      }
      List<String> known_problems = Arrays.asList(
        "036D", // only 036D/00
        "045H/01", // only 045H/00
        "091O/05",
        "101U", "102D",
        "201A/001", "201A/002", // only 201A
        "201U/001", "201U/002", "201U/003",
        "202D/001", "202D/002", "202D/003",
        "206X/001", "206X/002", // only 206X
        "209A/001", "209A/002", "209A/003", // only 209A $x0-9
        "209B/001", "209B/002", // only 209B/$x01
        "209C/001", // only 209C
        "209O/001", "209O/003", // only 209O
        "209R/001", // only 209R
        "220B/001", "220B/002", "220B/003", // only 220B
        "231@/001", "231@/002", // only 231@
        "231B/001", "231B/002", // only 231B
        "237A/001", "237A/002", // only 237A
        "245Z/001" // only 245Z $x00-99"
      );

      System.err.println("number of unhandled tags: " + counter.size());
      counter.entrySet()
        .stream()
        .sorted((e1, e2) -> {
            return e2.getValue().compareTo(e1.getValue());
          }
        )
        .forEach(
          entry -> {
            if (!known_problems.contains(entry.getKey()))
              System.err.printf("%s: %d%n", entry.getKey(), entry.getValue());
          }
        );

    } catch(FileNotFoundException e){
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private boolean directoryContains(Map<String, List<PicaTagDefinition>> schemaDirectory, PicaLine pl) {
    if (schemaDirectory.containsKey(pl.getTag())) {
      List<PicaTagDefinition> definitions = schemaDirectory.get(pl.getTag());
      for (PicaTagDefinition definition : definitions) {
        if (definition.getTag().validateOccurence(pl.getOccurrence()))
          return true;
      }
    }
    return false;
  }

  @NotNull
  private Map<String, List<PicaTagDefinition>> readSchema(JSONParser parser, String fileName) throws IOException, URISyntaxException, ParseException {
    Map<String, List<PicaTagDefinition>> map = new HashMap<>();

    Path tagsFile = FileUtils.getPath(fileName);
    Object obj = parser.parse(new FileReader(tagsFile.toString()));
    JSONObject jsonObject = (JSONObject) obj;
    JSONObject fields = (JSONObject) jsonObject.get("fields");
    for (String name : fields.keySet()) {
      JSONObject field = (JSONObject) fields.get(name);
      // System.err.println(field);
      PicaTagDefinition tag = new PicaTagDefinition(
        (String) field.get("pica3"),
        name,
        (boolean) field.get("repeatable"),
        false,
        (String) field.get("label")
      );
      addTag(map, tag);
    }

    return map;
  }

  private void addTag(Map<String, List<PicaTagDefinition>> map, PicaTagDefinition definition) {
    String tag = definition.getTag().getTag();
    if (!map.containsKey(tag)) {
      map.put(tag, new ArrayList<>());
    } else {
      System.err.println("Tag is already defined! " + definition.getTag().getRaw() + " "
        + map.get(tag).stream().map(a -> a.getTag().getRaw()).collect(Collectors.toSet()));
    }
    map.get(tag).add(definition);
  }
}
