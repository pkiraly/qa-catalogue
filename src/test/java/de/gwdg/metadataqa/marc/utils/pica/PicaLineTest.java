package de.gwdg.metadataqa.marc.utils.pica;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PicaLineTest {

  @Test
  public void singleSubfield() {
    PicaLine line = new PicaLine("001A ƒ00206:06-09-18");
    assertEquals("001A", line.getTag());
    assertEquals(null, line.getOccurrence());
    assertEquals(1, line.getSubfields().size());
    assertSubfield(line.getSubfields().get(0), "0", "0206:06-09-18");
  }

  @Test
  public void multipleSubfields() {
    PicaLine line = new PicaLine("044A ƒN650ƒsBusiness enterprisesƒzDeveloping countriesƒxManagement");
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
    PicaLine line = new PicaLine("045D/00 ƒ9091393116Strategisches Management ; STW-ID: stw18029-0");
    assertEquals("045D", line.getTag());
    assertEquals("00", line.getOccurrence());
    assertEquals(1, line.getSubfields().size());

    assertSubfield(line.getSubfields().get(0), "9", "091393116Strategisches Management ; STW-ID: stw18029-0");
  }

  private void assertSubfield(PicaSubfield subfield, String code, String value) {
    assertEquals(code, subfield.getCode());
    assertEquals(value, subfield.getValue());
  }

}
