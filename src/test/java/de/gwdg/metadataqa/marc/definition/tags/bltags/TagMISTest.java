package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagMISTest extends BLTagTest {

  public TagMISTest() {
    super(TagMIS.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "BS");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Y");
    invalidField("e", "20050944");
  }
}
