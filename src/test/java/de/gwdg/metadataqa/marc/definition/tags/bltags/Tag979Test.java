package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag979Test extends BLTagTest {

  public Tag979Test() {
    super(Tag979.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "British Library", "b", "DSC", "j", "0900.324840N");
    validField("j", "MN");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("5", "B");
  }
}
