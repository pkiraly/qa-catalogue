package de.gwdg.metadataqa.marc.utils.pica;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicaVocabularyManager {
  private JSONParser parser = new JSONParser(JSONParser.MODE_RFC4627);
  private Map<String, VocabularyEntry> map = new HashMap<>();
  private static final Pattern PATTERN = Pattern.compile("^\\^(\\w|\\[\\w+\\])(.*)$");

  public PicaVocabularyManager(String filename) throws FileNotFoundException, ParseException {
    read(filename);
  }

  private void read(String filename) throws FileNotFoundException, ParseException {
    Object obj = parser.parse(new FileReader(new File(filename)));
    JSONArray items = (JSONArray) obj;
    for (Object item : items) {
      JSONObject record = (JSONObject) item;
      VocabularyEntry entry = new VocabularyEntry();
      entry.setId(parseId((String) record.get("ID")));
      entry.setPica((String) record.get("PICA"));
      entry.setSrc(parseId((String) record.get("SRC")));
      entry.setVoc((String) record.get("VOC"));
      entry.setNotationPattern((String) record.get("notationPattern"));
      entry.setNamespace((String) record.get("namespace"));
      if (record.containsKey("prefLabel")) {
        JSONObject prefLabel = (JSONObject) record.get("prefLabel");
        if (prefLabel.containsKey("en"))
          entry.setPrefLabelEn((String) prefLabel.get("en"));
        if (prefLabel.containsKey("de"))
          entry.setPrefLabelDe((String) prefLabel.get("de"));
      }
      entry.setUri((String) record.get("uri"));
      System.err.println(entry.getId());
      System.err.println(entry.getSrc());
      map.put(entry.getPica(), entry);
    }
  }

  private VocabularyPattern parseId(String id) {
    if (id.startsWith("^")) {
      Matcher m = PATTERN.matcher(id);
      if (m.matches())
        return new VocabularyPattern(m.group(1), m.group(2));
    }
    return null;
  }

}
