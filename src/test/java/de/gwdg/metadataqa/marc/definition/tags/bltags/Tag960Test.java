package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag960Test extends BLTagTest {

  public Tag960Test() {
    super(Tag960.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("0", "a", "Warsaw");
    validField("1", "a", "Warsaw");
    validField("0", "a", "Lund");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("b", "Priority processing");
  }
}
