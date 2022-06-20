package de.gwdg.metadataqa.marc.utils.pica.path;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PicaPathParserTest {

  @Test
  public void tag() {
    PicaPath path = PicaPathParser.parse("003@");
    assertEquals("003@", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
  }

  @Test
  public void xtag() {
    PicaPath path = PicaPathParser.parse("203@x1");
    assertEquals("203@x1", path.getPath());
    assertEquals(null, path.getTag());
    assertEquals("203@x1", path.getXtag());
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalTag() {
    PicaPath path = PicaPathParser.parse("203!");
    assertEquals("203@x1", path.getPath());
    assertEquals(null, path.getTag());
    assertEquals("203@x1", path.getXtag());
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
  }

  @Test
  public void occurenceAsteriks() {
    PicaPath path = PicaPathParser.parse("003@/*");
    assertEquals("003@/*", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(Occurrence.Type.ALL, path.getOccurrence().getType());
  }

  @Test
  public void occurenceAsteriks_withsubfield() {
    PicaPath path = PicaPathParser.parse("003@/**");
    assertEquals("003@/**", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(Occurrence.Type.ALL, path.getOccurrence().getType());
    assertEquals(PicaPath.SubfieldType.ALL, path.getSubfieldType());
    assertEquals("*", path.getSubfields());
  }

  @Test
  public void subfieldsTag() {
    PicaPath path = PicaPathParser.parse("003@$a");
    assertEquals("003@$a", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(null, path.getOccurrence());
    assertEquals(PicaPath.SubfieldType.SINGLE, path.getSubfieldType());
    assertEquals("a", path.getSubfields());
  }

  @Test
  public void subfieldsTag_multiple() {
    PicaPath path = PicaPathParser.parse("003@$abc");
    assertEquals("003@$abc", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(null, path.getOccurrence());
    assertEquals(PicaPath.SubfieldType.MULTI, path.getSubfieldType());
    assertEquals("abc", path.getSubfields());
  }

  @Test
  public void subfieldsTag_multiple_mixed() {
    PicaPath path = PicaPathParser.parse("003@$aBc");
    assertEquals("003@$aBc", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(null, path.getOccurrence());
    assertEquals(PicaPath.SubfieldType.MULTI, path.getSubfieldType());
    assertEquals("aBc", path.getSubfields());
  }

  @Test
  public void subfieldsTag_asteriks() {
    PicaPath path = PicaPathParser.parse("003@$*");
    assertEquals("003@$*", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(null, path.getOccurrence());
    assertEquals(PicaPath.SubfieldType.ALL, path.getSubfieldType());
    assertEquals("*", path.getSubfields());
  }

  @Test
  public void subfieldsTag_asteriks_nodollar() {
    PicaPath path = PicaPathParser.parse("003@*");
    assertEquals("003@*", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(null, path.getOccurrence());
    assertEquals(PicaPath.SubfieldType.ALL, path.getSubfieldType());
    assertEquals("*", path.getSubfields());
  }
}