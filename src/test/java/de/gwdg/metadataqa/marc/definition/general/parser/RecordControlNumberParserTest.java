package de.gwdg.metadataqa.marc.definition.general.parser;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RecordControlNumberParserTest {

  @Test
  public void test() throws ParserException {
    SubfieldContentParser parser = RecordControlNumberParser.getInstance();
    Map<String, String> extra = parser.parse("(DE-576)025087622");
    assertNotNull(extra);
    assertEquals(3, extra.size());
    assertEquals("DE-576", extra.get("organizationCode"));
    assertEquals("025087622", extra.get("recordNumber"));
    assertEquals("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)", extra.get("organization"));
  }
}
