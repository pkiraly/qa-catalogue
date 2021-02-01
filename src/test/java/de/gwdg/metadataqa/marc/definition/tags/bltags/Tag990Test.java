package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag990Test extends BLTagTest {

  public Tag990Test() {
    super(Tag990.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "I");
    validField("a", "R", "a", "T");
    validField("a", "X");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("5", "B");
    invalidField("b", "Card,");
  }
}
