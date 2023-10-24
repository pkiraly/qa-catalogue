package de.gwdg.metadataqa.marc.utils.pica.path;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SubfieldsTest {

  @Test
  public void getType() {
    Subfields subfields = new Subfields(Subfields.Type.MULTI, "abc");
    assertEquals(Subfields.Type.MULTI, subfields.getType());
  }

  @Test
  public void getInput() {
    Subfields subfields = new Subfields(Subfields.Type.MULTI, "abc");
    assertEquals("abc", subfields.getInput());
  }

  @Test
  public void getCodes() {
    Subfields subfields = new Subfields(Subfields.Type.MULTI, "abc");
    assertEquals(List.of("a", "b", "c"), subfields.getCodes());
  }

  @Test
  public void testToString() {
    Subfields subfields = new Subfields(Subfields.Type.MULTI, "abc");
    assertEquals("Subfields{type=MULTI, input='abc', codes=[a, b, c]}", subfields.toString());
  }
}