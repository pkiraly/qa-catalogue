package de.gwdg.metadataqa.marc.utils.pica;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PicaLineTest {

  @Test
  public void singleSubfield() {
    PicaLine line = new PicaLine("001A ƒ00206:06-09-18", "ƒ");
    assertEquals("001A", line.getTag());
    assertEquals(null, line.getOccurrence());
    assertEquals(1, line.getSubfields().size());
    assertSubfield(line.getSubfields().get(0), "0", "0206:06-09-18");
  }

  @Test
  public void multipleSubfields() {
    PicaLine line = new PicaLine("044A ƒN650ƒsBusiness enterprisesƒzDeveloping countriesƒxManagement", "ƒ");
    assertEquals("044A", line.getTag());
    assertEquals(null, line.getOccurrence());
    assertEquals(4, line.getSubfields().size());

    assertSubfield(line.getSubfields().get(0), "N", "650");
    assertSubfield(line.getSubfields().get(1), "s", "Business enterprises");
    assertSubfield(line.getSubfields().get(2), "z", "Developing countries");
    assertSubfield(line.getSubfields().get(3), "x", "Management");
  }

  @Test
  public void occurrence() {
    PicaLine line = new PicaLine("045D/00 ƒ9091393116Strategisches Management ; STW-ID: stw18029-0", "ƒ");
    assertEquals("045D", line.getTag());
    assertEquals("00", line.getOccurrence());
    assertEquals(1, line.getSubfields().size());

    assertSubfield(line.getSubfields().get(0), "9", "091393116Strategisches Management ; STW-ID: stw18029-0");
  }

  private void assertSubfield(PicaSubfield subfield, String code, String value) {
    assertEquals(code, subfield.getCode());
    assertEquals(value, subfield.getValue());
  }

  @Test
  public void formatSubfields_single() {
    PicaLine line = new PicaLine("001A $00206:06-09-18", "$");
    assertEquals("0) 0206:06-09-18", line.formatSubfields());
  }

  @Test
  public void formatSubfields_single_with_default_subfield_separator() {
    PicaLine line = new PicaLine("001A $00206:06-09-18");
    assertEquals("0) 0206:06-09-18", line.formatSubfields());
  }

  @Test
  public void formatSubfields_multiple() {
    PicaLine line = new PicaLine("001A $00206:06-09-18$ba", "$");
    assertEquals("0) 0206:06-09-18, b) a", line.formatSubfields());
  }

  @Test
  public void formatSubfields_none() {
    PicaLine line = new PicaLine("001A", "$");
    assertEquals("", line.formatSubfields());
  }

  @Test
  public void getContent() {
    PicaLine line = new PicaLine("001A $00206:06-09-18$ba", "$");
    assertEquals("$00206:06-09-18$ba", line.getContent());
  }

  @Test
  public void presentation() {
    PicaLine line = new PicaLine("001A $00206:06-09-18$ba", "$");
    assertEquals(
      "PicaLine{tag='001A', occurrence='null', subfields=[PicaSubfield{code='0', value='0206:06-09-18'}, PicaSubfield{code='b', value='a'}]}",
      line.toString());
  }

  @Test
  public void defaultSeparator() {
    assertEquals("$", PicaLine.DEFAULT_SEPARATOR);
  }

}
