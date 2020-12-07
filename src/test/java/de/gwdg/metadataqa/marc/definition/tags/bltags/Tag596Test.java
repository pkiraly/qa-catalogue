package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag596Test extends BLTagTest {

  public Tag596Test() {
    super(Tag596.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Publisher deposit filename:CTEERCS052018R04.pdf");
  }

  @Test
  public void testInvalidFields() {
    invalidField("b", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
  }
}
