package de.gwdg.metadataqa.marc.code;

import de.gwdg.metadataqa.marc.codes.StandardIdentifierReader;
import de.gwdg.metadataqa.marc.codes.StandardIdentifier;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StandardIdentifierReaderTest {

  @Test
  public void test() throws IOException, URISyntaxException {
    Map<String, StandardIdentifier> map = StandardIdentifierReader.readStandardIdentifiers();
    assertEquals(106, map.size());
  }
}
