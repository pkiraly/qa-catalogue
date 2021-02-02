package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag975Test extends BLTagTest {

  public Tag975Test() {
    super(Tag975.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "d");
    validField("a", "F", "b", "A");
    validField("a", "G");
    validField("a", "F", "b", "A");
    validField("b", "J");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("d", "Priority processing");
    invalidField("a", "D");
    invalidField("b", "B");
  }
}
