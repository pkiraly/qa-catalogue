package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag595Test extends BLTagTest {

  public Tag595Test() {
    super(Tag595.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "See also 1570.9005 [no] 7 2000 for further selected papers.");
  }

  @Test
  public void testInvalidFields() {
    invalidField("b", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
    invalidField("a", "See also 1570.9005 [no] 7 2000 for further selected papers");
  }

}
