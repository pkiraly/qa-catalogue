package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagLCSTest extends BLTagTest {

  public TagLCSTest() {
    super(TagLCS.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("0", "a", "Great railway adventures. Series 1. Adventure 2");
    validField("0", "a", "Oscar narrativa ;", "v", "1714");
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "Y");
    invalidField("d", "20050944");
  }
}
