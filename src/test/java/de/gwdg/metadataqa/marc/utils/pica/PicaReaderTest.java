package de.gwdg.metadataqa.marc.utils.pica;

import com.opencsv.CSVReader;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.Utils;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
          if (map.containsKey(tag.getPicaplus())) {
            System.err.println("Tag is already defined! " + tag.getPicaplus());
          } else {
            map.put(tag.getPicaplus(), tag);
          }
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
      Path tagsFile = FileUtils.getPath("pica/pica-schema.json");
      Object obj = parser.parse(new FileReader(tagsFile.toString()));
      JSONObject jsonObject = (JSONObject) obj;
      JSONObject fields = (JSONObject) jsonObject.get("fields");
      Map<String, PicaTagDefinition> map = new HashMap<>();
      for (String name : fields.keySet()) {
        JSONObject field = (JSONObject) fields.get(name);
        // System.err.println(field);
        PicaTagDefinition tag = new PicaTagDefinition((String) field.get("pica3"), name, (boolean) field.get("repeatable"), false, (String) field.get("label"));
        if (map.containsKey(tag.getPicaplus())) {
          System.err.println("Tag is already defined! " + tag.getPicaplus());
        } else {
          map.put(tag.getPicaplus(), tag);
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
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
