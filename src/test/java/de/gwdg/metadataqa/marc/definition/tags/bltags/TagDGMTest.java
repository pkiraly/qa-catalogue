package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagDGMTest extends BLTagTest {

  public TagDGMTest() {
    super(TagDGM.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "000093159");
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "t");
    invalidField("a", "000093159.");
    invalidField("h", "B");
  }
}
