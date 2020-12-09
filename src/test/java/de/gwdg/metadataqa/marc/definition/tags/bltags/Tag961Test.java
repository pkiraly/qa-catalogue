package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class Tag961Test extends BLTagTest {

  public Tag961Test() {
    super(Tag961.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", " ",
      "a", "PLEASE CONSULT SHEET INDEX IN READING ROOM TO IDENTIFY REQUIRED SHEET WITHIN SERIES",
      "b", "Maps Index 1./OR.4264."));
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("c", "Priority processing");
  }
}
