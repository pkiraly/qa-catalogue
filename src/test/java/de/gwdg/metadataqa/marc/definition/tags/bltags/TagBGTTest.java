package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagBGTTest extends BLTagTest {

  public TagBGTTest() {
    super(TagBGT.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "t");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "t");
    invalidField("5", "B");
    invalidField("b", "Card,");
  }
}
