package de.gwdg.metadataqa.marc.utils.pica;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicaVocabularyManager {
  private JSONParser parser = new JSONParser(JSONParser.MODE_RFC4627);
  private Map<String, VocabularyEntry> map = new HashMap<>();
  private static final Pattern PATTERN = Pattern.compile("^\\^(\\w|\\[\\w+\\])(.*)$");

  public PicaVocabularyManager(String filename) throws FileNotFoundException, ParseException {
    Object jsonObject = parser.parse(new FileReader(new File(filename)));
    read(jsonObject);
  }

  public PicaVocabularyManager(InputStream inputStream) throws FileNotFoundException, ParseException {
    try {
      Object jsonObject = parser.parse(new InputStreamReader(inputStream, "UTF-8"));
      read(jsonObject);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
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

  private void read(Object jsonObject) throws FileNotFoundException, ParseException {
    // Object obj = parser.parse(new FileReader(new File(filename)));
    JSONArray items = (JSONArray) jsonObject;
    for (Object item : items) {
      JSONObject record = (JSONObject) item;
      VocabularyEntry entry = new VocabularyEntry();
      entry.setId(parseId((String) record.get("ID")));
      entry.setPica((String) record.get("PICA"));
      entry.setSource(parseId((String) record.get("SRC")));
      entry.setVoc((String) record.get("VOC"));
      entry.setNotationPattern((String) record.get("notationPattern"));
      entry.setNamespace((String) record.get("namespace"));
      if (record.containsKey("COMMENT"))
        entry.setComment((String) record.get("COMMENT"));

      if (record.containsKey("prefLabel")) {
        JSONObject prefLabel = (JSONObject) record.get("prefLabel");
        if (prefLabel.containsKey("en"))
          entry.setPrefLabelEn((String) prefLabel.get("en"));
        if (prefLabel.containsKey("de"))
          entry.setPrefLabelDe((String) prefLabel.get("de"));
      }
      entry.setUri((String) record.get("uri"));
      map.put(entry.getPica(), entry);
    }
  }

  public VocabularyEntry get(String tag) {
    return map.getOrDefault(tag, null);
  }

  public Collection<VocabularyEntry> getAll() {
    return map.values();
  }
}
