package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagTOCTest extends BLTagTest {

  public TagTOCTest() {
    super(TagTOC.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "ETOC");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Y");
    invalidField("c", "a");
  }
}
