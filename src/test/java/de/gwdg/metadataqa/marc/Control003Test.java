package de.gwdg.metadataqa.marc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Control003Test {

  @Test
  public void testConstructor() {
    Control003 field = new Control003("OCoLC");

    assertEquals("OCoLC", field.getContent());
    assertEquals(1, field.getKeyValuePairs().size());
    assertEquals(1, field.getKeyValuePairs().get("003").size());
    assertEquals("OCoLC", field.getKeyValuePairs().get("003").get(0));
  }

  @Test
  public void testToString() {
    Control003 field = new Control003("OCoLC");
    assertEquals("Control003{content='OCoLC'}", field.toString());
  }
}
