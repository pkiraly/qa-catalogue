package de.gwdg.metadataqa.marc.utils.pica;

import org.junit.Test;

import static org.junit.Assert.*;

public class PicaplusTagTest {

  @Test
  public void hasOccurrence() {
    assertFalse(new PicaplusTag("002@").hasOccurrence());
    assertTrue(new PicaplusTag("002@/2").hasOccurrence());
  }

  @Test
  public void hasOccurrenceRange() {
    assertFalse(new PicaplusTag("002@/2").hasOccurrenceRange());
    assertTrue(new PicaplusTag("002@/2-3").hasOccurrenceRange());
  }

  @Test
  public void getRaw() {
    assertEquals("002@/2", new PicaplusTag("002@/2").getRaw());
  }

  @Test
  public void getTag() {
    assertEquals("002@", new PicaplusTag("002@").getTag());
  }

  @Test
  public void getOccurrence() {
    assertEquals("1", new PicaplusTag("002@/1").getOccurrence());
    assertEquals("1-2", new PicaplusTag("002@/1-2").getOccurrence());
  }

  @Test
  public void getOccurrenceRage() {
    assertEquals(null, new PicaplusTag("002@/1").getOccurrenceRage());
    assertEquals(1, new PicaplusTag("002@/1-2").getOccurrenceRage().getStart());
    assertEquals(2, new PicaplusTag("002@/1-2").getOccurrenceRage().getEnd());
  }

  @Test
  public void getBasetag() {
    assertEquals(null, new PicaplusTag("002@").getBasetag());
    assertEquals("002@", new PicaplusTag("002@/1").getBasetag());
  }
}