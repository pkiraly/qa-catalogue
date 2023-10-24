package de.gwdg.metadataqa.marc.utils.marcspec.parser;

import de.gwdg.metadataqa.marc.utils.marcspec.MARCspec;
import de.gwdg.metadataqa.marc.utils.marcspec.MARCspecParser;
import de.gwdg.metadataqa.marc.utils.marcspec.Positions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Testing characterSpec
 * characterSpec: /[0-9#]-[0-9#]
 */
public class MARCspecParser2_characterSpecTest {

  @Test
  public void testMARCspecParser_Example5() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("LDR/0-4");

    assertEquals("LDR", marcSpec.getField().getTag());
    Positions characterPositions = marcSpec.getField().getCharacterPositions();
    assertTrue(characterPositions.getStart().isNumeric());
    assertFalse(characterPositions.getStart().isWildcard());
    assertEquals(0, characterPositions.getStart().value());
    assertEquals(4, characterPositions.getEnd().value());

    assertTrue(characterPositions.getEnd().isNumeric());
    assertFalse(characterPositions.getEnd().isWildcard());
    assertEquals(5, characterPositions.getLength().intValue());
    assertEquals(0, marcSpec.getSubfields().size());

    assertEquals("01107", characterPositions.extract("01107nam a22004211  4500"));
  }

  @Test
  public void testMARCspecParser_Example6() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("LDR/6");

    assertEquals("LDR", marcSpec.getField().getTag());
    Positions characterPositions = marcSpec.getField().getCharacterPositions();
    assertTrue(characterPositions.getStart().isNumeric());
    assertEquals(6, characterPositions.getStart().value());
    assertNull(characterPositions.getEnd());

    assertEquals("a", characterPositions.extract("01107nam a22004211  4500"));
  }

  @Test
  public void testMARCspecParser_Example7() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("007/0");

    assertEquals("007", marcSpec.getField().getTag());
    Positions characterPositions = marcSpec.getField().getCharacterPositions();
    assertFalse(characterPositions.isRange());
    assertEquals(0, characterPositions.getStart().value());
    assertNull(characterPositions.getEnd());
    assertEquals(1, characterPositions.getLength().intValue());

    assertEquals("0", characterPositions.extract("01107nam a22004211  4500"));
  }

  @Test
  public void testMARCspecParser_Example8() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("007/1-#");

    assertEquals("007", marcSpec.getField().getTag());
    Positions characterPositions = marcSpec.getField().getCharacterPositions();
    assertTrue(characterPositions.isRange());
    assertTrue(characterPositions.getStart().isNumeric());
    assertEquals(1, characterPositions.getStart().value());
    assertFalse(characterPositions.getEnd().isNumeric());
    assertEquals("#", characterPositions.getEnd().asString());

    assertEquals("1107nam a22004211  4500", characterPositions.extract("01107nam a22004211  4500"));
  }

  @Test
  public void testMARCspecParser_Example9() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("007/#");

    assertEquals("007", marcSpec.getField().getTag());
    Positions characterPositions = marcSpec.getField().getCharacterPositions();
    assertEquals("#", characterPositions.getStart().getPositionString());
    assertNull(characterPositions.getEnd());

    assertEquals("0", characterPositions.extract("01107nam a22004211  4500"));
  }

  @Test
  public void testMARCspecParser_Example10() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("245$a/#-1");

    assertEquals("245", marcSpec.getField().getTag());
    assertEquals(null, marcSpec.getField().getCharacterPositions());

    assertEquals(1, marcSpec.getSubfields().size());
    assertEquals("a", marcSpec.getSubfields().get(0).getTag());

    Positions characterPositions = marcSpec.getSubfields().get(0).getCharacterPositions();
    assertEquals("#", characterPositions.getStart().getPositionString());
    assertEquals(1, characterPositions.getEnd().value());

    assertEquals("0", characterPositions.extract("01107nam a22004211  4500"));
    assertEquals("g", characterPositions.extract("abcdefg"));
  }
}
