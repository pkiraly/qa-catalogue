package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class Tag996Test extends BLTagTest {

  public Tag996Test() {
    super(Tag996.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "MSD");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("5", "B");
    invalidField("b", "Card,");
    invalidField(new DataField(tag, " ", " ", "a", "MSD", "a", "KB"));
  }
}
