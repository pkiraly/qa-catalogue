package de.gwdg.metadataqa.marc.utils.marcspec;

import org.junit.Test;

import static org.junit.Assert.*;

public class MarcSpec2ParserTest {

  @Test
  public void field_numbers() {
    MarcSpec2 spec = MarcSpec2Parser.parse("880");
    assertEquals("880", spec.getTag());
    assertEquals(null, spec.getIndicator());
  }

  @Test
  public void field_dots() {
    MarcSpec2 spec = MarcSpec2Parser.parse("8..");
    assertEquals("8..", spec.getTag());
    assertEquals(null, spec.getIndicator());
  }

  @Test
  public void field_alpha() {
    MarcSpec2 spec = MarcSpec2Parser.parse("ldr");
    assertEquals("ldr", spec.getTag());
    assertEquals(null, spec.getIndicator());
  }

  @Test
  public void field_ALPHA() {
    MarcSpec2 spec = MarcSpec2Parser.parse("LDR");
    assertEquals("LDR", spec.getTag());
    assertEquals(null, spec.getIndicator());
  }

  @Test
  public void indicator() {
    MarcSpec2 spec = MarcSpec2Parser.parse("880^1");
    assertEquals("880", spec.getTag());
    assertEquals("1", spec.getIndicator());
  }
}