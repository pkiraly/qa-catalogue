package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class Tag955Test extends BLTagTest {

  public Tag955Test() {
    super(Tag955.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", " ",
      "a", "913", "b", "OH"));
    validField(new DataField(tag, " ", " ",
      "a", "13.c.f.", "b", "SS"));
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("b", "Priority processing");
  }
}
