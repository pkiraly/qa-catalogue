package de.gwdg.metadataqa.marc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class X035aSystemControlNumberTest {

  public X035aSystemControlNumberTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testConstructor() {

    X035aSystemControlNumber field = new X035aSystemControlNumber("(OCoLC)255973508");
    assertEquals("OCoLC", field.getCode());
    assertEquals("255973508", field.getNumber());

    field = new X035aSystemControlNumber("(DE-599)GBV010016783");
    assertEquals("DE-599", field.getCode());
    assertEquals("GBV010016783", field.getNumber());

  }
}
