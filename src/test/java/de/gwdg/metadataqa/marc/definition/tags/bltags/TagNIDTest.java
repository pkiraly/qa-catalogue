package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagNIDTest extends BLTagTest {

  public TagNIDTest() {
    super(TagNID.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "NID0000001");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Negative");
    invalidField("b", "Negative");
  }
}
