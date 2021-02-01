package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagDRTTest extends BLTagTest {

  public TagDRTTest() {
    super(TagDRT.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "MSD");
    validField("a", "VDEP");
    validField("a", "ETHOS");
    validField("a", "LD-ebooks");
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "MSD");
    invalidField("a", "000093159.");
    invalidField("h", "B");
  }
}
