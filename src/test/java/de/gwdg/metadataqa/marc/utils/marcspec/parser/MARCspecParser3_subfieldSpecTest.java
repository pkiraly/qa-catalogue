package de.gwdg.metadataqa.marc.utils.marcspec.parser;

import de.gwdg.metadataqa.marc.utils.marcspec.MARCspec;
import de.gwdg.metadataqa.marc.utils.marcspec.MARCspecParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MARCspecParser3_subfieldSpecTest {

  @Test
  public void testMARCspecParser_Example11() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("245$a");

    assertEquals("245", marcSpec.getField().getTag());
    assertEquals(1, marcSpec.getSubfields().size());
    assertEquals("a", marcSpec.getSubfields().get(0).getTag());
  }

  @Test
  public void testMARCspecParser_Example12() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("245$a$b$c");

    assertEquals("245", marcSpec.getField().getTag());
    assertEquals(3, marcSpec.getSubfields().size());
    assertEquals("a", marcSpec.getSubfields().get(0).getTag());
    assertEquals("b", marcSpec.getSubfields().get(1).getTag());
    assertEquals("c", marcSpec.getSubfields().get(2).getTag());
  }

  @Test
  public void testMARCspecParser_Example13() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("245$a-c");

    assertEquals("245", marcSpec.getField().getTag());
    assertEquals(3, marcSpec.getSubfields().size());
    assertEquals("a", marcSpec.getSubfields().get(0).getTag());
    assertEquals("b", marcSpec.getSubfields().get(1).getTag());
    assertEquals("c", marcSpec.getSubfields().get(2).getTag());
  }

  @Test
  public void testMARCspecParser_Example14() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("...$_$$");

    assertEquals("...", marcSpec.getField().getTag());
    assertEquals(2, marcSpec.getSubfields().size());
    assertEquals("_", marcSpec.getSubfields().get(0).getTag());
    assertEquals("$", marcSpec.getSubfields().get(1).getTag());
  }
}
