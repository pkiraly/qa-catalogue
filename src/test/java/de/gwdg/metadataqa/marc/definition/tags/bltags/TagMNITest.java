package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagMNITest extends BLTagTest {

  public TagMNITest() {
    super(TagMNI.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "0894-069X");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Y");
    invalidField("e", "20050944");
  }
}
