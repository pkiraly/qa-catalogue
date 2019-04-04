package de.gwdg.metadataqa.marc.utils.marcspec.parser;

import de.gwdg.metadataqa.marc.utils.marcspec.MARCspec;
import de.gwdg.metadataqa.marc.utils.marcspec.MARCspecParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testing fieldSpec
 */
public class MARCspecParser1_fieldSpecTest {

  @Test
  public void testMARCspecParser_Example1() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("LDR");

    assertEquals("LDR", marcSpec.getField().getTag());
    assertEquals(0, marcSpec.getSubfields().size());
  }

  @Test
  public void testMARCspecParser_Example2() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("00.");

    assertEquals("00.", marcSpec.getField().getTag());
    assertEquals(0, marcSpec.getSubfields().size());
    assertTrue(marcSpec.getField().hasWildchar());
  }

  @Test
  public void testMARCspecParser_Example3() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("7..");

    assertEquals("7..", marcSpec.getField().getTag());
    assertEquals(0, marcSpec.getSubfields().size());
    assertTrue(marcSpec.getField().hasWildchar());
  }

  @Test
  public void testMARCspecParser_Example4() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("100");

    assertEquals("100", marcSpec.getField().getTag());
    assertEquals(0, marcSpec.getSubfields().size());
    assertFalse(marcSpec.getField().hasWildchar());
  }
}
