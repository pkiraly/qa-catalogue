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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
      Map<String, PicaTagDefinition> map = new HashMap<>();
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
      counter.entrySet()
        .stream()
        .sorted((e1, e2) -> {
            return e2.getValue().compareTo(e1.getValue());
          }
        )
        .forEach(
          entry -> System.err.printf("%s: %d%n", entry.getKey(), entry.getValue())
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
      Map<String, PicaTagDefinition> schemaDirectory = new HashMap<>();
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
            if (schemaDirectory.containsKey(pl.getQualifiedTag())) {
              // System.err.println(map.get(pl.getTag()).getDescription() + ": " + pl.formatSubfields());
            } else {
              Utils.count(pl.getQualifiedTag(), counter);
              // System.err.printf("unknown %s: %s\n", pl.getTag(), pl.formatSubfields());
            }
          }
        }
      }
      System.err.println("number of unhandled tags: " + counter.size());
      counter.entrySet()
        .stream()
        .sorted((e1, e2) -> {
            return e2.getValue().compareTo(e1.getValue());
          }
        )
        .forEach(
          entry -> System.err.printf("%s: %d%n", entry.getKey(), entry.getValue())
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

  @NotNull
  private Map<String, PicaTagDefinition> readSchema(JSONParser parser, String fileName) throws IOException, URISyntaxException, ParseException {
    Map<String, PicaTagDefinition> map = new HashMap<>();

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
      if (tag.getPicaplus().contains("/") && (tag.getPicaplus().contains("-") || tag.getPicaplus().endsWith("X"))) {
        String p3 = tag.getPicaplus();
        if (tag.getPicaplus().contains("-")) {
          Pattern pattern = Pattern.compile("^(.*)/(\\d+)-(\\d+)$");
          Matcher m = pattern.matcher(p3);
          if (m.find()) {
            String base = m.group(1);
            int len = m.group(2).length();
            int num1 = Integer.parseInt(m.group(2));
            int num2 = Integer.parseInt(m.group(3));
            for (int i = num1; i<= num2; i++) {
              String format = "%0" + len + "d";
              String num = base + "/" + String.format(format, i);;
              // System.err.println(num);
              PicaTagDefinition tagX = new PicaTagDefinition(tag.getPica3(), num, tag.isRepeatable(), false, tag.getDescription());
              addTag(map, tagX);
            }
          }
        } else if (tag.getPicaplus().endsWith("X")) {
          Pattern pattern = Pattern.compile("^(.*)/(\\d)X$");
          Matcher m = pattern.matcher(p3);
          if (m.find()) {
            String base = m.group(1);
            int num1 = Integer.parseInt(m.group(2));
            for (int i = 0; i<= 9; i++) {
              String num = base + "/" + String.format("%02d", i);
              PicaTagDefinition tagX = new PicaTagDefinition(tag.getPica3(), num, tag.isRepeatable(), false, tag.getDescription());
              addTag(map, tagX);
            }
          } else {
            System.err.println("Not found X: " + p3);
          }
        } else {
          System.err.println("Unhandled type: " + p3);
        }
      } else {
        addTag(map, tag);
      }
    }

    return map;
  }

  private void addTag(Map<String, PicaTagDefinition> map, PicaTagDefinition tag) {
    if (map.containsKey(tag.getPicaplus())) {
      System.err.println("Tag is already defined! " + tag.getPicaplus());
    } else {
      map.put(tag.getPicaplus(), tag);
    }
  }
}
