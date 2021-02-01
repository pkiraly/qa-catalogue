package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag997Test extends BLTagTest {

  public Tag997Test() {
    super(Tag997.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "100: another author?");
    validField("a", "100: another author?", "a", "another message");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("5", "B");
    invalidField("b", "Card,");
  }
}
