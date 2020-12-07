package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class Tag692Test extends BLTagTest {

  public Tag692Test() {
    super(Tag692.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", " ", "a", "Rec", "p", "x"));
    validField(new DataField(tag, " ", " ", "a", "Rec", "p", "2(88/89)"));
    validField(new DataField(tag, " ", " ", "a", "Rec", "p", "2(88-89)"));
  }

  @Test
  public void testInvalidFields() {
    invalidField("p", "x(88-89)");
  }
}
