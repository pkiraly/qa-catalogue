package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag955Test extends BLTagTest {

  public Tag955Test() {
    super(Tag955.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "913", "b", "OH");
    validField("a", "13.c.f.", "b", "SS");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("b", "Priority processing");
  }
}
