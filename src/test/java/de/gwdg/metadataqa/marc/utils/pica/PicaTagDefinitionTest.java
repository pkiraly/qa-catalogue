package de.gwdg.metadataqa.marc.utils.pica;

import org.junit.Test;

import static org.junit.Assert.*;

public class PicaTagDefinitionTest {

  @Test
  public void simpleTag() {
    PicaTagDefinition tag = new PicaTagDefinition("3010", "028C", true, false, "Person/Familie");
    assertFalse(tag.getTag().hasOccurrence());
  }

  @Test
  public void simpleOccurence() {
    PicaTagDefinition tag = new PicaTagDefinition("3010", "028C/00", true, false, "Person/Familie");
    assertTrue(tag.getTag().hasOccurrence());
    assertFalse(tag.getTag().hasOccurrenceRange());
  }

  @Test
  public void occurenceRange() {
    PicaTagDefinition tag = new PicaTagDefinition("3010", "028C/00-09", true, false, "Person/Familie");
    assertTrue(tag.getTag().hasOccurrence());
    assertTrue(tag.getTag().hasOccurrenceRange());
    OccurenceRage range = tag.getTag().getOccurenceRange();
    assertEquals(2, range.getUnitLength());
    assertEquals(0, range.getStart());
    assertEquals(9, range.getEnd());
    assertEquals("00-09", range.toString());
  }

  @Test
  public void occurenceValidation() {
    PicaTagDefinition tag = new PicaTagDefinition("3010", "028C/00-09", true, false, "Person/Familie");
    OccurenceRage range = tag.getTag().getOccurenceRange();
    assertTrue(range.validate("00"));
    assertTrue(range.validate("05"));
    assertTrue(range.validate("09"));
    assertFalse(range.validate("10"));
    assertFalse(range.validate("1X"));
  }

}
