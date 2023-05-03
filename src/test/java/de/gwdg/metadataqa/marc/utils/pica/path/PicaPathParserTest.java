package de.gwdg.metadataqa.marc.utils.pica.path;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class PicaPathParserTest {

  @Test
  public void tag() {
    PicaPath path = PicaPathParser.parse("003@");
    assertEquals("003@", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertNull(path.getSubfields());
    assertEquals(
      "PicaPath{path='003@', tag='003@', xtag='null', occurrence=null, subfields=null}",
      path.toString());
  }

  @Test
  public void xtag() {
    PicaPath path = PicaPathParser.parse("203@x1");
    assertEquals("203@x1", path.getPath());
    assertEquals(null, path.getTag());
    assertEquals("203@x1", path.getXtag());
    assertNull(path.getSubfields());
    assertEquals(
      "PicaPath{path='203@x1', tag='null', xtag='203@x1', occurrence=null, subfields=null}",
      path.toString());
  }

  @Test
  public void illegalTag() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> PicaPathParser.parse("203!"));
    assertEquals("The input does not fit to rules: '203!'", exception.getMessage());
  }

  @Test
  public void occurrenceNumber() {
    PicaPath path = PicaPathParser.parse("003@/2");
    assertEquals("003@/2", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(Occurrence.Type.SINGLE, path.getOccurrence().getType());
    assertEquals(2, path.getOccurrence().getStart().intValue());
    assertEquals(null, path.getOccurrence().getEnd());
    assertNull(path.getSubfields());
    assertEquals(
      "PicaPath{path='003@/2', tag='003@', xtag='null', occurrence=Occurrence{type=SINGLE, start=2, end=null}, subfields=null}",
      path.toString());
  }

  @Test
  public void occurrenceRange() {
    PicaPath path = PicaPathParser.parse("003@/2-3");
    assertEquals("003@/2-3", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(Occurrence.Type.RANGE, path.getOccurrence().getType());
    assertEquals(2, path.getOccurrence().getStart().intValue());
    assertEquals(3, path.getOccurrence().getEnd().intValue());
    assertNull(path.getSubfields());
    assertEquals(
      "PicaPath{path='003@/2-3', tag='003@', xtag='null', occurrence=Occurrence{type=RANGE, start=2, end=3}, subfields=null}",
      path.toString());
  }

  @Test
  public void occurrenceAsteriks() {
    PicaPath path = PicaPathParser.parse("003@/*");
    assertEquals("003@/*", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(Occurrence.Type.ALL, path.getOccurrence().getType());
    assertNull(path.getSubfields());
    assertEquals(
      "PicaPath{path='003@/*', tag='003@', xtag='null', occurrence=Occurrence{type=ALL, start=null, end=null}, subfields=null}",
      path.toString());
  }

  @Test
  public void occurrenceAsteriks_withsubfield() {
    PicaPath path = PicaPathParser.parse("003@/**");
    assertEquals("003@/**", path.getPath());
    assertEquals("003@", path.getTag());
    assertEquals(null, path.getXtag());
    assertEquals(Occurrence.Type.ALL, path.getOccurrence().getType());
    assertEquals(Subfields.Type.ALL, path.getSubfields().getType());
    assertEquals("*", path.getSubfields().getInput());
    assertEquals(Arrays.asList("*"), path.getSubfields().getCodes());
    assertEquals(Arrays.asList("*"), path.getSubfieldCodes());
    assertEquals(
      "PicaPath{path='003@/**', tag='003@', xtag='null', occurrence=Occurrence{type=ALL, start=null, end=null}, subfields=Subfields{type=ALL, input='*', codes=[*]}}",
      path.toString());
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
    assertEquals(Arrays.asList("a"), path.getSubfieldCodes());
    assertEquals(
      "PicaPath{path='003@$a', tag='003@', xtag='null', occurrence=null, subfields=Subfields{type=SINGLE, input='a', codes=[a]}}",
      path.toString());
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
    assertEquals(List.of("a", "b", "c"), path.getSubfieldCodes());
    assertEquals(
      "PicaPath{path='003@$abc', tag='003@', xtag='null', occurrence=null, subfields=Subfields{type=MULTI, input='abc', codes=[a, b, c]}}",
      path.toString());
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
    assertEquals(List.of("a", "B", "c"), path.getSubfieldCodes());
    assertEquals(
      "PicaPath{path='003@$aBc', tag='003@', xtag='null', occurrence=null, subfields=Subfields{type=MULTI, input='aBc', codes=[a, B, c]}}",
      path.toString());
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
    assertEquals(
      "PicaPath{path='003@$*', tag='003@', xtag='null', occurrence=null, subfields=Subfields{type=ALL, input='*', codes=[*]}}",
      path.toString());
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
    assertEquals(
      "PicaPath{path='003@*', tag='003@', xtag='null', occurrence=null, subfields=Subfields{type=ALL, input='*', codes=[*]}}",
      path.toString());
  }
}