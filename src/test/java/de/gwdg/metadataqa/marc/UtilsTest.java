package de.gwdg.metadataqa.marc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.controlsubfields.tag007.Tag007nonprojected00;
import de.gwdg.metadataqa.marc.definition.controlsubfields.tag007.Tag007nonprojected01;
import de.gwdg.metadataqa.marc.utils.BasicStatistics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

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
    List<Code> codes = Utils.generateCodes("a", "dummy");
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
    List<Code> codes = Utils.generateCodes("a", "dummy", "b");
  }

  @Test
  public void testGenerateControlSubfieldList() {
    List<ControlSubfieldDefinition> list = Utils.generateControlSubfieldList(
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
}
