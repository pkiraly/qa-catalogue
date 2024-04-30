package de.gwdg.metadataqa.marc.utils.marcspec.legacy;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class MarcSpecTest {

  public void decoder(String arg) {
    MarcSpec ms = new MarcSpec();
    ms.decode(arg);
  }

  public MarcSpec marcspec() {
    return new MarcSpec();
  }

  @Test(expected=IllegalArgumentException.class)
  public void testInvalidMarcSpec1() {
    decoder(" 24 ");
  }

  @Test(expected=IllegalArgumentException.class)
  public void testInvalidMarcSpec2() {
    decoder("24/");
  }

  @Test(expected=IllegalArgumentException.class)
  public void testInvalidMarcSpec3() {
    decoder("24x");
  }

  @Test
  public void testValidMarcSpec1() {
    MarcSpec marcSpec = new MarcSpec();
    marcSpec.decode("LDR");
    assertEquals("LDR", marcSpec.getFieldTag());

    marcSpec.decode("245");
    assertEquals("245", marcSpec.getFieldTag());

    marcSpec.decode("XXX");
    assertEquals("XXX", marcSpec.getFieldTag());

    marcSpec.decode("245abc");
    assertEquals("245", marcSpec.getFieldTag());
    assertEquals("a,b,c", StringUtils.join(marcSpec.getSubfields().keySet(), ","));

    // marcSpec.decode("245!\"#$%&\\()*+-./:;<=>?");
    // assertEquals("a,b,c", StringUtils.join(marcSpec.getSubfieldList().keySet(), ","));

    marcSpec.decode("245ab_1a");
    assertEquals("1", marcSpec.getIndicator1());
    assertEquals("a", marcSpec.getIndicator2());
  }

  @Test(expected=IllegalArgumentException.class)
  public void testInvalidMarcSpec4() {
    decoder("007~");
  }

  @Test(expected=IllegalArgumentException.class)
  public void testInvalidMarcSpec5() {
    decoder("007~1-2-");
  }

  @Test
  public void testValidMarcSpec2() {
    MarcSpec marcSpec = new MarcSpec();
    marcSpec.decode("LDR~0-3");
    assertEquals("LDR", marcSpec.getFieldTag());
    assertEquals(0, (int)marcSpec.getCharStart());
    assertEquals(3, (int)marcSpec.getCharEnd());
    assertEquals(4, (int)marcSpec.getCharLength());
    assertTrue(marcSpec.hasRangeSelector());
  }

  @Test(expected=IllegalArgumentException.class)
  public void testInvalidMarcSpec6() {
    decoder("245aX");
  }

  @Test(expected=IllegalArgumentException.class)
  public void testInvalidMarcSpec7() {
    decoder("245ab_1+");
  }

  @Test(expected=IllegalArgumentException.class)
  public void testInvalidMarcSpec8() {
    decoder("245ab_123");
  }

  @Test
  public void testSetAndGet() {
    MarcSpec marcSpec = new MarcSpec();
    marcSpec.setFieldTag("LDR");
    marcSpec.setCharStart(0);
    marcSpec.setCharEnd(3);
    assertEquals("LDR", marcSpec.getFieldTag());
    assertEquals(0, (int)marcSpec.getCharStart());
    assertEquals(3, (int)marcSpec.getCharEnd());
    assertEquals(4, (int)marcSpec.getCharLength());

    marcSpec = new MarcSpec();
    marcSpec.setFieldTag("245");
    marcSpec.addSubfields("abc");
    marcSpec.setIndicator1("x");
    marcSpec.setIndicator2("0");
    assertEquals("245", marcSpec.getFieldTag());
    assertEquals("x", marcSpec.getIndicator1());
    assertEquals("0", marcSpec.getIndicator2());
    // assertEquals(array("a"=>"a","b"=>"b","c"=>"c"), marcSpec.getSubfieldList());
  }

  @Test
  public void testEncode() {
    MarcSpec marcSpec;
    marcSpec = new MarcSpec("245");
    assertEquals("245", marcSpec.encode());

    marcSpec = new MarcSpec("245a");
    assertEquals("245a", marcSpec.encode());

    marcSpec = new MarcSpec("245_1");
    assertEquals("245_1", marcSpec.encode());

    marcSpec = new MarcSpec("245__0");
    assertEquals("245__0", marcSpec.encode());

    marcSpec = new MarcSpec("245_1_");
    assertEquals("245_1", marcSpec.encode());

    marcSpec = new MarcSpec("007~1");
    assertEquals("007~1-1", marcSpec.encode());

    marcSpec = new MarcSpec("007~1-3");
    assertEquals("007~1-3", marcSpec.encode());
  }

  @Test
  public void testValidity() {
    MarcSpec marcSpec = new MarcSpec();
    assertTrue(marcSpec.validate("245"));
    assertTrue(marcSpec.validate("245ab"));
    assertTrue(marcSpec.validate("XXXab"));
    assertTrue(marcSpec.validate("245ab_1"));
    assertTrue(marcSpec.validate("245ab__0"));
    assertTrue(marcSpec.validate("245ab_10"));
    assertTrue(marcSpec.validate("245ab_1_"));
    assertTrue(marcSpec.validate("245$a"));
    assertTrue(marcSpec.validate("LDR/0-4"));
    assertTrue(marcSpec.validate("880_1"));
  }

  @Test
  public void testInValidity() {
    MarcSpec marcSpec = new MarcSpec();
    assertThrows(IllegalArgumentException.class, () -> marcSpec.validate("24"));
    assertThrows(IllegalArgumentException.class, () -> marcSpec.validate("LXR"));
    assertThrows(IllegalArgumentException.class, () -> marcSpec.validate("245ab_123"));
    assertThrows(IllegalArgumentException.class, () -> marcSpec.validate("245ab__."));
    assertThrows(IllegalArgumentException.class, () -> marcSpec.validate("004ab~1"));
    assertThrows(IllegalArgumentException.class, () -> marcSpec.validate("004~-1"));
  }


  @Test
  public void testCharConsistency1() {
    MarcSpec marcSpec = new MarcSpec("650");
    assertNull(marcSpec.getCharStart());
    assertNull(marcSpec.getCharEnd());
    assertNull(marcSpec.getCharLength());
    assertEquals("650", marcSpec.encode());
  }

  @Test
  public void testGetters_tag() {
    MarcSpec marcSpec = new MarcSpec("650");
    assertEquals("650", marcSpec.getFieldTag());
  }

  @Test
  public void testGetters_subfield() {
    MarcSpec marcSpec = new MarcSpec("650a");
    assertEquals("650", marcSpec.getFieldTag());
    List<String> list = new ArrayList<>(marcSpec.getSubfields().keySet());
    assertEquals(1, list.size());
    assertEquals("a", list.get(0));
  }

  @Test
  public void testGetters_subfields() {
    MarcSpec marcSpec = new MarcSpec("650ab");
    assertEquals("650", marcSpec.getFieldTag());
    List<String> list = new ArrayList<>(marcSpec.getSubfields().keySet());
    assertEquals(2, list.size());
    assertEquals("a", list.get(0));
    assertEquals("b", list.get(1));
  }

  @Test
  public void testMultiple() {


    String input = "650ab;651x";
    String[] paths = input.split(";");

    MarcSpec marcSpec;
    List<String> list;

    marcSpec = new MarcSpec(paths[0]);
    assertEquals("650", marcSpec.getFieldTag());
    list = new ArrayList<>(marcSpec.getSubfields().keySet());
    assertEquals(2, list.size());
    assertEquals("a", list.get(0));
    assertEquals("b", list.get(1));

    marcSpec = new MarcSpec(paths[1]);
    assertEquals("651", marcSpec.getFieldTag());
    list = new ArrayList<>(marcSpec.getSubfields().keySet());
    assertEquals(1, list.size());
    assertEquals("x", list.get(0));
  }

  @Test
  public void testCharPosition() {
    MarcSpec marcSpec = new MarcSpec("008~0-5");
    System.err.println(marcSpec);
    assertEquals(0, (int)marcSpec.getCharStart());
    assertEquals(5, (int)marcSpec.getCharEnd());
    assertEquals(6, (int)marcSpec.getCharLength());
    assertEquals("008~0-5", marcSpec.encode());
  }

  @Test
  public void range_correct() {
    MarcSpec selector = new MarcSpec("LDR~0-3");
    assertTrue(selector.hasRangeSelector());
    assertEquals("abcd", selector.selectRange("abcdef"));
  }

  @Test
  public void range_shorter() {
    MarcSpec selector = new MarcSpec("LDR~0-3");
    assertTrue(selector.hasRangeSelector());
    assertEquals("abc", selector.selectRange("abc"));
  }

  @Test
  public void range_outOfRange() {
    MarcSpec selector = new MarcSpec("LDR~8-9");
    assertTrue(selector.hasRangeSelector());
    assertEquals("", selector.selectRange("abc"));
  }
}
