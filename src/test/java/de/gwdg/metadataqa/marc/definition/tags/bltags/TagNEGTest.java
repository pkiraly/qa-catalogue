package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagNEGTest extends BLTagTest {

  public TagNEGTest() {
    super(TagNEG.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Negative");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Negative");
    invalidField("a", "Negative.");
    invalidField("b", "Negative");
  }
}
