package de.gwdg.metadataqa.marc.utils.marcspec.parser;

import de.gwdg.metadataqa.marc.utils.marcspec.MARCspec;
import de.gwdg.metadataqa.marc.utils.marcspec.MARCspecParser;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MARCspecParser5_indicatorSpecTest {

  @Test
  public void testMARCspecParserB_Example25() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("880^1");

    assertEquals("880", marcSpec.getField().getTag());
    assertTrue(marcSpec.getField().hasIndicator1());
  }

  @Test
  public void testMARCspecParserB_Example26() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("880[1]^2");

    assertEquals("880", marcSpec.getField().getTag());
    assertEquals(1, marcSpec.getField().getStartIndex().value());
    assertNull(marcSpec.getField().getEndIndex());
    assertFalse(marcSpec.getField().hasIndicator1());
    assertTrue(marcSpec.getField().hasIndicator2());
  }
}
