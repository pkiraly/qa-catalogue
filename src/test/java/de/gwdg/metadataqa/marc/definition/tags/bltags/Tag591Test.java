package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag591Test extends BLTagTest {

  public Tag591Test() {
    super(Tag591.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Papers. Based on those presented at the conference.");
  }

  @Test
  public void testInvalidFields() {
    invalidField("b", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
  }
}
