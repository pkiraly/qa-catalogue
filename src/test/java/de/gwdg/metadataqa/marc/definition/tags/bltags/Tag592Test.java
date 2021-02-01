package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag592Test extends BLTagTest {

  public Tag592Test() {
    super(Tag592.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "ARGUS collaboration.");
  }

  @Test
  public void testInvalidFields() {
    invalidField("b", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
  }
}
