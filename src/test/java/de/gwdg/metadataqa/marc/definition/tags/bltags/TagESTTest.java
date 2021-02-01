package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagESTTest extends BLTagTest {

  public TagESTTest() {
    super(TagEST.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "1");
    validField("a", "2");
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "1");
    invalidField("a", "000093159.");
    invalidField("h", "B");
  }
}
