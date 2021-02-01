package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagONXTest extends BLTagTest {

  public TagONXTest() {
    super(TagONX.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "AU", "c", "NED");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "1");
    // invalidField("a", "1.");
    // invalidField("b", "1");
    invalidField("d", "1");
  }
}
