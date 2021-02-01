package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag599Test extends BLTagTest {

  public Tag599Test() {
    super(Tag599.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Imperfect: wanting pt. 2 (N.T.), item contains {206} plates only; " +
      "plate {13} of pt. 1 is mutilated, top right hand corner is missing.");
  }

  @Test
  public void testInvalidFields() {
    invalidField("c", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
  }
}
