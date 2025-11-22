package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class ControlPositionTest {

  public ControlPositionTest() {
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
  public void constructorTest() {
    ControlfieldPositionDefinition position = new ControlfieldPositionDefinition("Category of material", 0, 1);
    assertEquals(0, position.getPositionStart());
    assertEquals(1, position.getPositionEnd());
    assertEquals("Category of material", position.getLabel());
    assertTrue(position.getCodes().isEmpty());
    /*
    map.put("Specific material designation", content.substring(1, 2));
    map.put("Undefined", content.substring(2, 3));
    map.put("Color", content.substring(3, 4));
    map.put("Videorecording format", content.substring(4, 5));
    map.put("Sound on medium or separate", content.substring(5, 6));
    map.put("Medium for sound", content.substring(6, 7));
    map.put("Dimensions", content.substring(7, 8));
    map.put("Configuration of playback channels", content.substring(8, 9));
    */
  }

  @Test
  public void validationTest() {
    ControlfieldPositionDefinition position = new ControlfieldPositionDefinition("Category of material", 0, 1,
      Utils.generateCodes("a", "date 1", "b", "date 2"));
    assertTrue(position.validate("a"));
    assertFalse(position.validate("c"));
  }

  @Test
  public void repeatableContentTest() {
    ControlfieldPositionDefinition position = new ControlfieldPositionDefinition("Category of material", 0, 1,
      Utils.generateCodes("a", "date 1", "b", "date 2"))
      .setUnitLength(1).setRepeatableContent(true);
    assertTrue(position.validate("a"));
    assertFalse(position.validate("c"));
    assertTrue(position.validate("aa"));
    assertFalse(position.validate("ac"));
  }

  @Test
  public void testResolve() {
    ControlfieldPositionDefinition position = new ControlfieldPositionDefinition("Category of material", 0, 1,
      Utils.generateCodes("a", "date 1", "b", "date 2"))
      .setUnitLength(1).setRepeatableContent(true);
    assertEquals("date 1", position.resolve("a"));
    assertEquals("c", position.resolve("c"));
    assertEquals("date 1", position.resolve("aa"));
    assertEquals("date 1, date 2", position.resolve("ab"));
    assertEquals("date 2, date 1", position.resolve("ba"));
    assertEquals("date 1, date 2", position.resolve("aaaab"));
    assertEquals("date 1, c", position.resolve("ac"));
  }

}
