package de.gwdg.metadataqa.marc.utils;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EncodedValueFileReaderTest {

  @Test
  public void test() {
    Map<String, String> dict = null;
    try {
      dict = EncodedValueFileReader.fileToDict("general/csv-test.csv");
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    assertNotNull(dict);
    assertEquals(3, dict.size());
    assertEquals("früher: Mödling; Theologische Hochschule St. Gabriel, Bibliothek St. Gabriel", dict.get("AT-3:BStG"));
    assertEquals("früher: Wien; Österreichisches Ost- und Südosteuropa-Institut, Bibliothek", dict.get("AT-9:OeOSI"));
    assertEquals("Bundesanstalt für Agrarwirtschaft, Bibliothek", dict.get("AT-AGWI"));
  }
}
