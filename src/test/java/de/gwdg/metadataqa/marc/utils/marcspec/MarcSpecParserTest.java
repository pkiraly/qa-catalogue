package de.gwdg.metadataqa.marc.utils.marcspec;

import org.junit.Test;

import static org.junit.Assert.*;

public class MarcSpecParserTest {

  @Test
  public void field_alpha() {
    MarcSpec spec = MarcSpecParser.parse("ldr");
    assertEquals("ldr", spec.getTag());
    assertEquals(null, spec.getIndicator());
    assertEquals("ldr", spec.encode());
  }

  @Test
  public void field_ALPHA() {
    MarcSpec spec = MarcSpecParser.parse("LDR");
    assertEquals("LDR", spec.getTag());
    assertEquals(null, spec.getIndicator());
    assertEquals("LDR", spec.encode());
  }

  @Test
  public void field_00dot() {
    MarcSpec spec = MarcSpecParser.parse("00.");
    assertEquals("00.", spec.getTag());
    assertEquals(null, spec.getIndicator());
    assertEquals("00.", spec.encode());
  }

  @Test
  public void field_dots() {
    MarcSpec spec = MarcSpecParser.parse("7..");
    assertEquals("7..", spec.getTag());
    assertEquals(null, spec.getIndicator());
    assertEquals("7..", spec.encode());
  }

  @Test
  public void field_numbers() {
    MarcSpec spec = MarcSpecParser.parse("100");
    assertEquals("100", spec.getTag());
    assertEquals(null, spec.getIndicator());
    assertEquals("100", spec.encode());
  }

  @Test
  public void positionOrRange_field_start() {
    MarcSpec spec = MarcSpecParser.parse("LDR/6");
    assertEquals("LDR", spec.getTag());
    assertEquals(null, spec.getIndicator());
    assertEquals("6", spec.getPosition().getStart());
    assertNull(spec.getPosition().getEnd());
    assertEquals(false, spec.getPosition().isRange());
    assertEquals("LDR/6", spec.encode());
  }

  @Test
  public void indicator() {
    MarcSpec spec = MarcSpecParser.parse("880^1");
    assertEquals("880", spec.getTag());
    assertEquals("1", spec.getIndicator());
    assertEquals("880^1", spec.encode());
  }

  @Test
  public void parse2_indicator() {
    MarcSpec spec = MarcSpecParser.parse("880^1");
    assertEquals("880", spec.getTag());
    assertEquals("1", spec.getIndicator());
    assertEquals("880^1", spec.encode());
  }

  @Test
  public void parse2_position() {
    MarcSpec spec = MarcSpecParser.parse("LDR/6");
    assertEquals("LDR", spec.getTag());
    assertEquals("6", spec.getPosition().getStart());
    assertFalse(spec.getPosition().isRange());
    assertEquals("LDR/6", spec.encode());
  }

  @Test
  public void parse2_range() {
    MarcSpec spec = MarcSpecParser.parse("LDR/0-4");
    assertEquals("LDR", spec.getTag());
    assertEquals("0", spec.getPosition().getStart());
    assertEquals("4", spec.getPosition().getEnd());
    assertTrue(spec.getPosition().isRange());
    assertEquals("LDR/0-4", spec.encode());
  }

  @Test
  public void parse_position2() {
    MarcSpec spec = MarcSpecParser.parse("007/0");
    assertEquals("007", spec.getTag());
    assertEquals("0", spec.getPosition().getStart());
    assertNull(spec.getPosition().getEnd());
    assertFalse(spec.getPosition().isRange());
    assertEquals("007/0", spec.encode());
  }

  @Test
  public void parse_position3() {
    MarcSpec spec = MarcSpecParser.parse("007/1-#");
    assertEquals("007", spec.getTag());
    assertEquals("1", spec.getPosition().getStart());
    assertEquals("#", spec.getPosition().getEnd());
    assertTrue(spec.getPosition().isRange());
    assertEquals("007/1-#", spec.encode());
  }

  @Test
  public void parse_position4() {
    MarcSpec spec = MarcSpecParser.parse("007/#");
    assertEquals("007", spec.getTag());
    assertEquals("#", spec.getPosition().getStart());
    assertNull(spec.getPosition().getEnd());
    assertFalse(spec.getPosition().isRange());
    assertEquals("007/#", spec.encode());
  }

  @Test
  public void parse_subfield1() {
    MarcSpec spec = MarcSpecParser.parse("245$a");
    assertEquals("245", spec.getTag());
    assertEquals(1, spec.getSubfields().size());
    assertEquals("a", spec.getSubfields().get(0).getSubfield());
    assertEquals("245$a", spec.encode());
  }

  @Test
  public void parse_subfield2() {
    MarcSpec spec = MarcSpecParser.parse("245$a$b$c");
    assertEquals("245", spec.getTag());
    assertEquals(3, spec.getSubfields().size());
    assertEquals("a", spec.getSubfields().get(0).getSubfield());
    assertEquals("b", spec.getSubfields().get(1).getSubfield());
    assertEquals("c", spec.getSubfields().get(2).getSubfield());
    assertEquals("245$a$b$c", spec.encode());
  }

  @Test
  public void parse_subfield3() {
    MarcSpec spec = MarcSpecParser.parse("...$_$$");
    assertEquals("...", spec.getTag());
    assertEquals(2, spec.getSubfields().size());
    assertEquals("_", spec.getSubfields().get(0).getSubfield());
    assertEquals("$", spec.getSubfields().get(1).getSubfield());
    assertEquals("...$_$$", spec.encode());
  }

  @Test
  public void parse_index1() {
    MarcSpec spec = MarcSpecParser.parse("300[0]");
    assertEquals("300", spec.getTag());
    assertEquals("0", spec.getIndex().getStart());
    assertNull(spec.getIndex().getEnd());
    assertFalse(spec.getIndex().isRange());
    assertEquals("300[0]", spec.encode());
  }

  @Test
  public void parse_index2() {
    MarcSpec spec = MarcSpecParser.parse("300[1]");
    assertEquals("300", spec.getTag());
    assertEquals("1", spec.getIndex().getStart());
    assertNull(spec.getIndex().getEnd());
    assertFalse(spec.getIndex().isRange());
    assertEquals("300[1]", spec.encode());
  }

  @Test
  public void parse_index3() {
    MarcSpec spec = MarcSpecParser.parse("300[0-2]");
    assertEquals("300", spec.getTag());
    assertEquals("0", spec.getIndex().getStart());
    assertEquals("2", spec.getIndex().getEnd());
    assertTrue(spec.getIndex().isRange());
    assertEquals("300[0-2]", spec.encode());
  }

  @Test
  public void parse_index4() {
    MarcSpec spec = MarcSpecParser.parse("300[1-#]");
    assertEquals("300", spec.getTag());
    assertEquals("1", spec.getIndex().getStart());
    assertEquals("#", spec.getIndex().getEnd());
    assertTrue(spec.getIndex().isRange());
    assertEquals("300[1-#]", spec.encode());
  }

  @Test
  public void parse_index5() {
    MarcSpec spec = MarcSpecParser.parse("300[#]");
    assertEquals("300", spec.getTag());
    assertEquals("#", spec.getIndex().getStart());
    assertNull(spec.getIndex().getEnd());
    assertFalse(spec.getIndex().isRange());
    assertEquals("300[#]", spec.encode());
  }

  @Test
  public void parse_index6() {
    MarcSpec spec = MarcSpecParser.parse("300[#-1]");
    assertEquals("300", spec.getTag());
    assertEquals("#", spec.getIndex().getStart());
    assertEquals("1", spec.getIndex().getEnd());
    assertTrue(spec.getIndex().isRange());
    assertEquals("300[#-1]", spec.encode());
  }

  @Test
  public void parse_index7() {
    MarcSpec spec = MarcSpecParser.parse("300[0]$a");
    assertEquals("300", spec.getTag());
    assertEquals("0", spec.getIndex().getStart());
    assertNull(spec.getIndex().getEnd());
    assertFalse(spec.getIndex().isRange());
    assertEquals("a", spec.getSubfields().get(0).getSubfield());
    assertEquals("300[0]$a", spec.encode());
  }

  @Test
  public void parse_index8() {
    MarcSpec spec = MarcSpecParser.parse("300$a[0]");
    assertEquals("300", spec.getTag());
    assertNull(spec.getIndex());
    assertEquals("a", spec.getSubfields().get(0).getSubfield());
    assertEquals("0", spec.getSubfields().get(0).getIndex().getStart());
    assertNull(spec.getSubfields().get(0).getIndex().getEnd());
    assertFalse(spec.getSubfields().get(0).getIndex().isRange());
    assertEquals("300$a[0]", spec.encode());
  }

  @Test
  public void parse_index9() {
    MarcSpec spec = MarcSpecParser.parse("300$a[#]");
    assertEquals("300", spec.getTag());
    assertNull(spec.getIndex());
    assertEquals("a", spec.getSubfields().get(0).getSubfield());
    assertEquals("#", spec.getSubfields().get(0).getIndex().getStart());
    assertNull(spec.getSubfields().get(0).getIndex().getEnd());
    assertFalse(spec.getSubfields().get(0).getIndex().isRange());
    assertEquals("300$a[#]", spec.encode());
  }

  @Test
  public void parse_index10() {
    MarcSpec spec = MarcSpecParser.parse("300$a[#-1]");
    assertEquals("300", spec.getTag());
    assertNull(spec.getIndex());
    assertEquals("a", spec.getSubfields().get(0).getSubfield());
    assertEquals("#", spec.getSubfields().get(0).getIndex().getStart());
    assertEquals("1", spec.getSubfields().get(0).getIndex().getEnd());
    assertTrue(spec.getSubfields().get(0).getIndex().isRange());
    assertEquals("300$a[#-1]", spec.encode());
  }

  @Test
  public void parse_index11() {
    MarcSpec spec = MarcSpecParser.parse("880[1]^2");
    assertEquals("880", spec.getTag());
    assertEquals("1", spec.getIndex().getStart());
    assertEquals(0, spec.getSubfields().size());
    assertEquals("2", spec.getIndicator());
    assertEquals("880[1]^2", spec.encode());
  }
}