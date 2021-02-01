package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagLEOTest extends BLTagTest {

  public TagLEOTest() {
    super(TagLEO.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "MP13.0002486527");
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "Y");
    invalidField("e", "20050944");
  }
}
