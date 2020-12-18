package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagPLRTest extends BLTagTest {

  public TagPLRTest() {
    super(TagPLR.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Y");
    validField("a", "Y", "b", "007188719,013745028,013745031");
    validField("a", "Y", "b", "007188719");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "1");
    invalidField("a", "X");
    invalidField("b", "a");
    invalidField("b", "1,");
  }
}
