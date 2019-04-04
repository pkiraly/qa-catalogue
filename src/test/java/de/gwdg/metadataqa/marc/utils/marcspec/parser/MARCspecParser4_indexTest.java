package de.gwdg.metadataqa.marc.utils.marcspec.parser;

import de.gwdg.metadataqa.marc.utils.marcspec.MARCspec;
import de.gwdg.metadataqa.marc.utils.marcspec.MARCspecParser;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class MARCspecParser4_indexTest {

  @Test
  public void testMARCspecParserB_Example15() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("300[0]");

    assertEquals("300", marcSpec.getField().getTag());
    assertEquals(0, marcSpec.getField().getStartIndex().value());
  }

  @Test
  public void testMARCspecParserB_Example16() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("300[1]");

    assertEquals("300", marcSpec.getField().getTag());
    assertEquals(1, marcSpec.getField().getStartIndex().value());
  }

  @Test
  public void testMARCspecParserB_Example17() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("300[0-2]");

    assertEquals("300", marcSpec.getField().getTag());
    assertEquals(0, marcSpec.getField().getStartIndex().value());
    assertEquals(2, marcSpec.getField().getEndIndex().value());
  }

  @Test
  public void testMARCspecParserB_Example18() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("300[1-#]");

    assertEquals("300", marcSpec.getField().getTag());
    assertTrue(marcSpec.getField().getStartIndex().isNumeric());
    assertEquals(1, (int)marcSpec.getField().getStartIndex().getPositionInt());
    assertTrue(marcSpec.getField().getEndIndex().isWildcard());
    assertEquals("#", marcSpec.getField().getEndIndex().asString());
  }

  @Test
  public void testMARCspecParserB_Example19() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("300[#]");

    assertEquals("300", marcSpec.getField().getTag());
    assertTrue(marcSpec.getField().getStartIndex().isWildcard());
    assertEquals("#", marcSpec.getField().getStartIndex().asString());
    assertNull(marcSpec.getField().getEndIndex());
  }

  @Test
  public void testMARCspecParserB_Example20() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("300[#-1]");

    assertEquals("300", marcSpec.getField().getTag());
    assertEquals("#", marcSpec.getField().getStartIndex().getPositionString());
    assertEquals(1, (int)marcSpec.getField().getEndIndex().getPositionInt());
  }

  @Test
  public void testMARCspecParserB_Example21() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("300[0]$a");

    assertEquals("300", marcSpec.getField().getTag());
    assertEquals(0, (int)marcSpec.getField().getStartIndex().getPositionInt());
    assertNull(marcSpec.getField().getEndIndex());
    assertEquals(1, marcSpec.getSubfields().size());
    assertEquals("a", marcSpec.getSubfields().get(0).getTag());
  }

  @Test
  public void testMARCspecParserB_Example22() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("300$a[0]");

    assertEquals("300", marcSpec.getField().getTag());
    assertEquals(null, marcSpec.getField().getStartIndex());
    assertEquals(null, marcSpec.getField().getEndIndex());
    assertEquals(1, marcSpec.getSubfields().size());
    assertEquals("a", marcSpec.getSubfields().get(0).getTag());
    assertEquals(0, (int)marcSpec.getSubfields().get(0).getIndexPositions().getStart().getPositionInt());
  }

  @Test
  public void testMARCspecParserB_Example23() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("300$a[#]");

    assertEquals("300", marcSpec.getField().getTag());
    assertEquals(null, marcSpec.getField().getStartIndex());
    assertEquals(null, marcSpec.getField().getEndIndex());
    assertEquals(1, marcSpec.getSubfields().size());
    assertEquals("a", marcSpec.getSubfields().get(0).getTag());
    assertEquals("#", marcSpec.getSubfields().get(0).getIndexPositions().getStart().getPositionString());
  }

  @Test
  public void testMARCspecParserB_Example24() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("300$a[#-1]");

    assertEquals("300", marcSpec.getField().getTag());
    assertEquals(null, marcSpec.getField().getStartIndex());
    assertEquals(null, marcSpec.getField().getEndIndex());
    assertEquals(1, marcSpec.getSubfields().size());
    assertEquals("a", marcSpec.getSubfields().get(0).getTag());
    assertEquals("#", marcSpec.getSubfields().get(0).getIndexPositions().getStart().getPositionString());
    assertEquals(1, (int)marcSpec.getSubfields().get(0).getIndexPositions().getEnd().getPositionInt());
  }
}
