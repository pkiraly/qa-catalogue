package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag594Test extends BLTagTest {

  public Tag594Test() {
    super(Tag594.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "First BL ed.");
    validField("b", "114.");
    validField("b", "tys.");
  }

  @Test
  public void testInvalidFields() {
    invalidField("c", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
  }
}
