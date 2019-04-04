package de.gwdg.metadataqa.marc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Control005Test {

  @Test
  public void testConstructor() {
    Control005 field = new Control005("20140808010151.9");

    assertEquals("20140808010151.9", field.getContent());
    assertEquals(1, field.getKeyValuePairs().size());
    assertEquals(1, field.getKeyValuePairs().get("005").size());
    assertEquals("20140808010151.9", field.getKeyValuePairs().get("005").get(0));
  }

  @Test
  public void testToString() {
    Control005 field = new Control005("20140808010151.9");
    assertEquals("Control005{content='20140808010151.9'}", field.toString());
  }
}
