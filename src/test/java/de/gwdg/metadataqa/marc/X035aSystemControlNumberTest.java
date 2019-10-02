package de.gwdg.metadataqa.marc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class X035aSystemControlNumberTest {

  @Test
  public void testConstructor_withSingleString() {
    X035aSystemControlNumber field = new X035aSystemControlNumber("(OCoLC)255973508");
    assertEquals("OCoLC", field.getCode());
    assertEquals("255973508", field.getNumber());

    field = new X035aSystemControlNumber("(DE-599)GBV010016783");
    assertEquals("DE-599", field.getCode());
    assertEquals("GBV010016783", field.getNumber());
  }

  @Test
  public void testConstructor_withCodeAndNumber() {
    X035aSystemControlNumber field = new X035aSystemControlNumber("DE-599", "GBV010016783");
    assertEquals("DE-599", field.getCode());
    assertEquals("GBV010016783", field.getNumber());
  }
}
