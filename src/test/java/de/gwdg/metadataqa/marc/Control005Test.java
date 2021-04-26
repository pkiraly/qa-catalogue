package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Control005Test {

  @Test
  public void testConstructor() {
    Control005 field = new Control005("20140808010151.9");

    assertEquals("20140808010151.9", field.getContent());
    assertEquals(1, field.getKeyValuePairs().size());
    assertEquals(1, field.getKeyValuePairs().get("005").size());
    assertEquals("20140808010151.9", field.getKeyValuePairs().get("005").get(0));

    assertEquals(2014, field.getYear().intValue());
    assertEquals(8, field.getMonth().intValue());
    assertEquals(8, field.getDay().intValue());
    assertEquals(1, field.getHour().intValue());
    assertEquals(1, field.getMin().intValue());
    assertEquals(51, field.getSec().intValue());
    assertEquals(9, field.getMs().intValue());

    assertTrue(field.validate(MarcVersion.MARC21));
  }

  @Test
  public void testToString() {
    Control005 field = new Control005("20140808010151.9");
    assertEquals("Control005{content='20140808010151.9'}", field.toString());
  }
}
