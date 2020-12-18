package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagOBJTest extends BLTagTest {

  public TagOBJTest() {
    super(TagOBJ.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Y");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Y");
    invalidField("a", "X");
    invalidField("b", "Y");
  }
}
