package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.dao.Control005;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Control005Test {

  @Test
  public void testConstructor() {
    Control005 field = new Control005("20140808010151.9");

    assertEquals("20140808010151.9", field.getContent());
    assertEquals(1, field.getKeyValuePairs().size());
    System.err.println(field.getKeyValuePairs());
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

  @Test
  public void validateMonth() {
    Control005 field = new Control005("20141808010151.9");
    assertFalse(field.validate(MarcVersion.MARC21));
    assertEquals("invalid month: 18 in '20141808010151.9'", field.getValidationErrors().get(0).getMessage());
  }

  @Test
  public void validateDay() {
    Control005 field = new Control005("20140838010151.9");
    assertFalse(field.validate(MarcVersion.MARC21));
    assertEquals("invalid day: 38 in '20140838010151.9'", field.getValidationErrors().get(0).getMessage());
  }

  @Test
  public void validateHour() {
    Control005 field = new Control005("20140808310151.9");
    assertFalse(field.validate(MarcVersion.MARC21));
    assertEquals("invalid hour: 31 in '20140808310151.9'", field.getValidationErrors().get(0).getMessage());
  }

  @Test
  public void validateMinute() {
    Control005 field = new Control005("20140808016151.9");
    assertFalse(field.validate(MarcVersion.MARC21));
    assertEquals("invalid minute: 61 in '20140808016151.9'", field.getValidationErrors().get(0).getMessage());
  }

  @Test
  public void validateSecond() {
    Control005 field = new Control005("20140808015171.9");
    assertFalse(field.validate(MarcVersion.MARC21));
    assertEquals("invalid second: 71 in '20140808015171.9'", field.getValidationErrors().get(0).getMessage());
  }
}
