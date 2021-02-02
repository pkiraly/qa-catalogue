package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag917Test extends BLTagTest {

  public Tag917Test() {
    super(Tag917.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Priority processing");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Priority processing");
    invalidField("b", "Priority processing");
  }
}
