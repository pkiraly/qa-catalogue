package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagEXPTest extends BLTagTest {

  public TagEXPTest() {
    super(TagEXP.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "X");
    validField("a", "X", "d", "20070727");
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "1");
    invalidField("a", "000093159.");
    invalidField("h", "B");
    invalidField("d", "20070737");
  }
}
