package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag590Test extends BLTagTest {

  public Tag590Test() {
    super(Tag590.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Spine title: Pottery from the Nile valley");
  }

  @Test
  public void testInvalidFields() {
    invalidField("b", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
  }
}
