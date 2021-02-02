package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag945Test extends BLTagTest {

  public Tag945Test() {
    super(Tag945.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(" ", "a", "Tagungsbericht- DGMK Deutsche Wissenschaftliche" +
      " Geseellschaft fur Erdol Erdgas und Kohle, ISSN 1433-9013 ; no 1");
    validField("1", "a", "The politics of international diffusion");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("b", "Priority processing");
  }
}
