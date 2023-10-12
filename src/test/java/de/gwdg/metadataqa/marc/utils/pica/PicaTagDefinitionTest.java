package de.gwdg.metadataqa.marc.utils.pica;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PicaTagDefinitionTest {

  @Test
  public void simpleTag() {
    PicaTagDefinition tag = new PicaTagDefinition("3010", "028C", true, false, "Person/Familie");
    assertFalse(tag.getPicaplusTag().hasOccurrence());
  }

  @Test
  public void simpleOccurrence() {
    PicaTagDefinition tag = new PicaTagDefinition("3010", "028C/00", true, false, "Person/Familie");
    assertTrue(tag.getPicaplusTag().hasOccurrence());
    assertFalse(tag.getPicaplusTag().hasOccurrenceRange());
  }

  @Test
  public void occurrenceRange() {
    PicaTagDefinition tag = new PicaTagDefinition("3010", "028C/00-09", true, false, "Person/Familie");
    assertTrue(tag.getPicaplusTag().hasOccurrence());
    assertTrue(tag.getPicaplusTag().hasOccurrenceRange());
    OccurrenceRange range = tag.getPicaplusTag().getOccurrenceRage();
    assertEquals(2, range.getUnitLength());
    assertEquals(0, range.getStart());
    assertEquals(9, range.getEnd());
    assertEquals("00-09", range.toString());
  }

  @Test
  public void occurrenceValidation() {
    PicaTagDefinition tag = new PicaTagDefinition("3010", "028C/00-09", true, false, "Person/Familie");
    OccurrenceRange range = tag.getPicaplusTag().getOccurrenceRage();
    assertTrue(range.validate("00"));
    assertTrue(range.validate("05"));
    assertTrue(range.validate("09"));
    assertFalse(range.validate("10"));
    assertFalse(range.validate("1X"));
  }

}
