package de.gwdg.metadataqa.marc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag007.Tag007nonprojected00;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag007.Tag007nonprojected01;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class UtilsTest {

  public UtilsTest() {
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
  public void testGenerateCodes() {
    List<EncodedValue> codes = Utils.generateCodes("a", "dummy");
    assertEquals(1, codes.size());
    assertEquals("a", codes.get(0).getCode());
    assertEquals("dummy", codes.get(0).getLabel());

    codes = Utils.generateCodes("a", "dummy1", "b", "dummy2");
    assertEquals(2, codes.size());
    assertEquals("a", codes.get(0).getCode());
    assertEquals("dummy1", codes.get(0).getLabel());
    assertEquals("b", codes.get(1).getCode());
    assertEquals("dummy2", codes.get(1).getLabel());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGenerateImpairCodes() {
    List<EncodedValue> codes = Utils.generateCodes("a", "dummy", "b");
  }

  @Test
  public void testGenerateControlSubfieldList() {
    List<ControlfieldPositionDefinition> list = Utils.generateControlPositionList(
      Tag007nonprojected00.getInstance(),
      Tag007nonprojected01.getInstance());
    assertNotNull(list);
    assertEquals(2, list.size());
    assertEquals(Tag007nonprojected00.getInstance(), list.get(0));
  }

  @Test
  public void testConstructorIsPrivate() throws NoSuchMethodException,
    IllegalAccessException,
    InvocationTargetException,
    InstantiationException {
    Constructor<Utils> constructor = Utils.class.getDeclaredConstructor();
    assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    constructor.setAccessible(true);
    constructor.newInstance();
  }

  @Test
  public void testReplace() {
    String text = "tags80x/810$9 (Series Added Entry > Series Added Entry - Corporate Name > 9)";
    String result = text.replaceAll("^[^/]+/", "");
    assertEquals("810$9 (Series Added Entry > Series Added Entry - Corporate Name > 9)", result);
  }

  @Test
  public void testSolarize() {
    assertEquals("kssb_5", Utils.solarize("kssb/5"));
    assertEquals("kssb_8__machine_generated_", Utils.solarize("kssb/8 (machine generated)"));
    assertEquals("generikus_targyszo", Utils.solarize("Generikus tárgyszó"));
  }

  @Test
  public void testCreateRow() {
    assertEquals("a,b,c\n", Utils.createRow("a", "b", "c"));
    assertEquals("a;b;c\n", Utils.createRow(';', "a", "b", "c"));
  }

  @Test
  public void testRange() {
    assertEquals("c", Utils.substring("abcd", 2, 3));
    assertEquals("cd", Utils.substring("abcd", 2, 4));
    assertEquals("cd", Utils.substring("abcd", 2, 5));
    assertEquals("cd", Utils.substring("abcd", 2, 6));
  }

  @Test
  public void testRange_withException() {
    assertThrows(StringIndexOutOfBoundsException.class, () -> Utils.substring("abcd", 4, 8));
  }

  @Test
  public void testCount() {
    Map<String, Integer> names = new HashMap<>();
    assertFalse(names.containsKey("Mary"));
    Utils.count("Mary", names);
    assertTrue(names.containsKey("Mary"));
    assertEquals(1, names.get("Mary").intValue());
  }


  @Test
  public void package2version() {
    assertEquals(MarcVersion.KBR, Utils.package2version("kbrtags"));
  }

  @Test
  public void quote() {
    assertEquals(List.of("\"a\"", "\"b\""), Utils.quote(List.of("a", "b")));
    assertEquals(1, Utils.quote(1));
  }

  @Test
  public void base36Encode() {
    assertEquals("c", Utils.base36Encode("12"));
    assertEquals("c", Utils.base36Encode(12));
    assertEquals("7ps", Utils.base36Encode("10E+3"));
  }

  @Test
  public void base64decode() {
    assertEquals(
      "002@.0 !~ \"^L\" && 002@.0 !~ \"^..[iktN]\" && (002@.0 !~ \"^.v\" || 021A.a?)\n",
      Utils.base64decode("MDAyQC4wICF+ICJeTCIgJiYgMDAyQC4wICF+ICJeLi5baWt0Tl0iICYmICgwMDJALjAgIX4gIl4udiIgfHwgMDIxQS5hPykK"));
  }
}
