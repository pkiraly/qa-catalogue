package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagMPXTest extends BLTagTest {

  public TagMPXTest() {
    super(TagMPX.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "1");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Y");
    invalidField("a", "20050944");
    invalidField("a", ",");
    invalidField("b", "1");
  }
}
