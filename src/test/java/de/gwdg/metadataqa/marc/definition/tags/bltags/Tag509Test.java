package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag509Test extends BLTagTest {

  public Tag509Test() {
    super(Tag509.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Verify presence and nature of initial leaf (sig. A1), not present in L copy");
    validField("a", "Verify presence of English title page and text. CLU-C's copy entirely in Latin");
    validField("a", "NLS copy dimensions: 16 cm.");
  }

  @Test
  public void testInvalidFields() {
    invalidField("b", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
  }
}
