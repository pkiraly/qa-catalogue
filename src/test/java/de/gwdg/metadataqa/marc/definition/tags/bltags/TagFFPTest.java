package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagFFPTest extends BLTagTest {

  public TagFFPTest() {
    super(TagFFP.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Y");
    validField("a", "Y", "b", "DATASETS");
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "Y");
    invalidField("a", "y");
    invalidField("b", "dataset");
    invalidField("c", "y");
  }
}
