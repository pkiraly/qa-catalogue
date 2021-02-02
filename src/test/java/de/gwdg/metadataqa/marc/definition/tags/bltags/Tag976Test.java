package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag976Test extends BLTagTest {

  public Tag976Test() {
    super(Tag976.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "p");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("d", "Priority processing");
    invalidField("a", "D");
    invalidField("b", "B");
  }
}
