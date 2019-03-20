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
  public void testBasicStatistics() {
    Map<Integer, Integer> histogram = new TreeMap<Integer, Integer>() {{
      put(1, 71429);
      put(2, 7033);
      put(3, 1362);
      put(4, 423);
      put(5, 180);
      put(6, 85);
      put(7, 56);
      put(8, 29);
      put(9, 39);
      put(10, 13);
      put(11, 12);
      put(12, 2);
      put(13, 3);
      put(14, 1);
      put(15, 4);
      put(16, 1);
      put(17, 1);
      put(20, 1);
    }};

    BasicStatistics statistics = new BasicStatistics(histogram);
    assertEquals(1, (int) statistics.getMin());
    assertEquals(20, (int) statistics.getMax());
    assertEquals(1.16654, (double) statistics.getMean(), 0.0001);
  }

}
