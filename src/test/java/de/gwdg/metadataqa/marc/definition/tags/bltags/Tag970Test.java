package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag970Test extends BLTagTest {

  public Tag970Test() {
    super(Tag970.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Liz");
    validField("a", "Ba");
    validField("a", "Thomason Tracts");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("d", "Priority processing");
  }
}
