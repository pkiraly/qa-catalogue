package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.EncodedValue;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncodedValueFileReader {

  private static final Logger logger = Logger.getLogger(EncodedValueFileReader.class.getCanonicalName());

  public static List<EncodedValue> fileToCodeList(String fileName) {

    List<EncodedValue> codes = new ArrayList<>();
    try {
      LineIterator it = getLineIterator(fileName);
      while (it.hasNext()) {
        String line = it.nextLine();
        if (line.equals("") || line.startsWith("#"))
          continue;
        String[] parts = line.split(";", 2);
        codes.add((new EncodedValue(parts[0], parts[1])));
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "fileToCodeList", e);
    }

    return codes;
  }

  public static Map<String, String> fileToDict(String fileName) throws IOException, URISyntaxException {
    LineIterator it = getLineIterator(fileName);

    Map<String, String> dict = new HashMap<>();
    while (it.hasNext()) {
      String line = it.nextLine();
      if (line.equals("") || line.startsWith("#"))
        continue;
      String[] parts = line.split(";", 2);
      dict.put(parts[0], parts[1]);
    }

    return dict;
  }

  private static LineIterator getLineIterator(String fileName) throws IOException {
    ClassLoader classLoader = EncodedValueFileReader.class.getClassLoader();
    return IOUtils.lineIterator(classLoader.getResourceAsStream(fileName), Charset.defaultCharset());
  }
}
