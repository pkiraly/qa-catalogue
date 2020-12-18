package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagOHCTest extends BLTagTest {

  public TagOHCTest() {
    super(TagOHC.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "1");
    validField("a", "11");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "1");
    invalidField("a", "1.");
    invalidField("b", "1");
  }
}
