package de.gwdg.metadataqa.marc;

import java.util.Arrays;
import java.util.List;
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
public class MarcFieldTest {

  private MarcField field;

  public MarcFieldTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
    field = new MarcField("016", "R", "NATIONAL BIBLIOGRAPHIC AGENCY CONTROL NUMBER");
    field.addSubfield("ind1", "b7", "National bibliographic agency");
    field.addSubfield("ind2", "blank", "Undefined");
    field.addSubfield("a", "NR", "Record control number");
    field.addSubfield("z", "R", "Canceled or invalid record control number");
    field.addSubfield("2", "NR", "Source");
    field.addSubfield("8", "R", "Field link and sequence number");
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testConstructor() {
    assertNotNull(field);
  }

  @Test
  public void testGetValidSubfieldcodes() {
    List<String> validSubfieldCodes = Arrays.asList("a", "z", "2", "8");
    assertEquals(validSubfieldCodes, field.getValidSubfieldCodes());
  }

  @Test
  public void testGetSubfields() {
    assertEquals("Record control number",
      field.getSubfields().get("a").getLabel());
    assertEquals("Canceled or invalid record control number",
      field.getSubfields().get("z").getLabel());
    assertEquals("Source",
      field.getSubfields().get("2").getLabel());
    assertEquals("Field link and sequence number",
      field.getSubfields().get("8").getLabel());
  }

  @Test
  public void testIndicator() {
    assertEquals("National bibliographic agency",
      field.getIndicator1().getLabel());
    assertEquals("Undefined",
      field.getIndicator2().getLabel());
  }
}
