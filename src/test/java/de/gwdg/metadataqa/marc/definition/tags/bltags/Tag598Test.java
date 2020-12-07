package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag598Test extends BLTagTest {

  public Tag598Test() {
    super(Tag598.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "unable to replace out of print 18.11.94");
  }

  @Test
  public void testInvalidFields() {
    invalidField("c", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
  }
}
