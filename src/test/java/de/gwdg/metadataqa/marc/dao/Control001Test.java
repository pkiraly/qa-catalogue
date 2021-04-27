package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.dao.Control001;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Control001Test {

  @Test
  public void testConstructor() {
    Control001 field = new Control001("010000178");

    assertEquals(1, field.getKeyValuePairs().size());
    assertEquals(1, field.getKeyValuePairs().get("001").size());
    assertEquals("010000178", field.getKeyValuePairs().get("001").get(0));
  }

  @Test
  public void testToString() {
    Control001 field = new Control001("010000178");
    assertEquals("Control001{content='010000178'}", field.toString());
  }
}
