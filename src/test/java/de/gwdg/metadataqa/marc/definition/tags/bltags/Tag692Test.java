package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag692Test extends BLTagTest {

  public Tag692Test() {
    super(Tag692.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Rec", "p", "x");
    validField("a", "Rec", "p", "2(88/89)");
    validField("a", "Rec", "p", "2(88-89)");
  }

  @Test
  public void testInvalidFields() {
    invalidField("p", "x(88-89)");
  }
}
