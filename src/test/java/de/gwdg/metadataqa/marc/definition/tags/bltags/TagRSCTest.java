package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagRSCTest extends BLTagTest {

  public TagRSCTest() {
    super(TagRSC.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Y");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Y");
    invalidField("a", "X");
    invalidField("b", "a");
  }
}
