package de.gwdg.metadataqa.marc.utils.pica.path;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PicaPathParserTest {

  @Test
  public void tag() {
    PicaPath path = PicaPathParser.parse("003@");
    assertEquals("003@", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertNull(path.getSubfields());
  }

  @Test
  public void xtag() {
    PicaPath path = PicaPathParser.parse("203@x1");
    assertEquals("203@x1", path.getPath());
    assertEquals(null, path.getTag());
    assertEquals("203@x1", path.getXtag());
    assertNull(path.getSubfields());
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalTag() {
    PicaPath path = PicaPathParser.parse("203!");
    assertEquals("203@x1", path.getPath());
    assertEquals(null, path.getTag());
    assertEquals("203@x1", path.getXtag());
    assertNull(path.getSubfields());
  }

  @Test
  public void occurenceNumber() {
    PicaPath path = PicaPathParser.parse("003@/2");
    assertEquals("003@/2", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(Occurrence.Type.SINGLE, path.getOccurrence().getType());
    assertEquals(2, path.getOccurrence().getStart().intValue());
    assertEquals(null, path.getOccurrence().getEnd());
    assertNull(path.getSubfields());
  }

  @Test
  public void occurenceRange() {
    PicaPath path = PicaPathParser.parse("003@/2-3");
    assertEquals("003@/2-3", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(Occurrence.Type.RANGE, path.getOccurrence().getType());
    assertEquals(2, path.getOccurrence().getStart().intValue());
    assertEquals(3, path.getOccurrence().getEnd().intValue());
    assertNull(path.getSubfields());
  }

  @Test
  public void occurenceAsteriks() {
    PicaPath path = PicaPathParser.parse("003@/*");
    assertEquals("003@/*", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(Occurrence.Type.ALL, path.getOccurrence().getType());
    assertNull(path.getSubfields());
  }

  @Test
  public void occurenceAsteriks_withsubfield() {
    PicaPath path = PicaPathParser.parse("003@/**");
    assertEquals("003@/**", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(Occurrence.Type.ALL, path.getOccurrence().getType());
    assertEquals(Subfields.Type.ALL, path.getSubfields().getType());
    assertEquals("*", path.getSubfields().getInput());
    assertEquals(Arrays.asList("*"), path.getSubfields().getCodes());
  }

  @Test
  public void subfieldsTag() {
    PicaPath path = PicaPathParser.parse("003@$a");
    assertEquals("003@$a", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(null, path.getOccurrence());
    assertEquals(Subfields.Type.SINGLE, path.getSubfields().getType());
    assertEquals("a", path.getSubfields().getInput());
    assertEquals(Arrays.asList("a"), path.getSubfields().getCodes());
  }

  @Test
  public void subfieldsTag_multiple() {
    PicaPath path = PicaPathParser.parse("003@$abc");
    assertEquals("003@$abc", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(null, path.getOccurrence());
    assertEquals(Subfields.Type.MULTI, path.getSubfields().getType());
    assertEquals("abc", path.getSubfields().getInput());
    assertEquals(Arrays.asList("a", "b", "c"), path.getSubfields().getCodes());
  }

  @Test
  public void subfieldsTag_multiple_mixed() {
    PicaPath path = PicaPathParser.parse("003@$aBc");
    assertEquals("003@$aBc", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(null, path.getOccurrence());
    assertEquals(Subfields.Type.MULTI, path.getSubfields().getType());
    assertEquals("aBc", path.getSubfields().getInput());
    assertEquals(Arrays.asList("a", "B", "c"), path.getSubfields().getCodes());
  }

  @Test
  public void subfieldsTag_asteriks() {
    PicaPath path = PicaPathParser.parse("003@$*");
    assertEquals("003@$*", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(null, path.getOccurrence());
    assertEquals(Subfields.Type.ALL, path.getSubfields().getType());
    assertEquals("*", path.getSubfields().getInput());
    assertEquals(Arrays.asList("*"), path.getSubfields().getCodes());
  }

  @Test
  public void subfieldsTag_asteriks_nodollar() {
    PicaPath path = PicaPathParser.parse("003@*");
    assertEquals("003@*", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(null, path.getOccurrence());
    assertEquals(Subfields.Type.ALL, path.getSubfields().getType());
    assertEquals("*", path.getSubfields().getInput());
    assertEquals(Arrays.asList("*"), path.getSubfields().getCodes());
  }
}