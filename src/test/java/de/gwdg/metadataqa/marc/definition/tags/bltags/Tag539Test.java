package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag539Test extends BLTagTest {

  public Tag539Test() {
    super(Tag539.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "O.");
    validField("a", "L.");
  }

  @Test
  public void testInvalidFields() {
    invalidField("b", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
    invalidField("a", "L");
  }
}
