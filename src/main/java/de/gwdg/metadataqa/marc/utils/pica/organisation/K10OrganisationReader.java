package de.gwdg.metadataqa.marc.utils.pica.organisation;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class K10OrganisationReader {

  private static final Logger logger = Logger.getLogger(K10OrganisationReader.class.getCanonicalName());

  public static Map<String, K10Organisation> fileToCodeList(String fileName) {

    // protected Map<String, EncodedValue> index = new HashMap<>();

    Map<String, K10Organisation> codes = new HashMap<>();
    try {
      LineIterator it = getLineIterator(fileName);
      while (it.hasNext()) {
        String line = it.nextLine();
        if (line.equals("") || line.startsWith("#") || line.startsWith("--"))
          continue;
        String[] parts = line.split("\t", 3);
        if (parts.length > 1) {
          String id = removeLeadingZeros(parts[0]);
          codes.put(id, new K10Organisation(id, parts[1], parts[2]));
        }
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "fileToCodeList", e);
    }

    return codes;
  }

  private static String removeLeadingZeros(String id) {
    return id.replaceAll("^0+", "");
  }

  private static LineIterator getLineIterator(String fileName) throws IOException {
    return IOUtils.lineIterator(new FileInputStream(fileName), Charset.defaultCharset());
  }
}
