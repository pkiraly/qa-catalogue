package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag980Test extends BLTagTest {

  public Tag980Test() {
    super(Tag980.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Card");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("5", "B");
    invalidField("a", "Card,");
  }
}
