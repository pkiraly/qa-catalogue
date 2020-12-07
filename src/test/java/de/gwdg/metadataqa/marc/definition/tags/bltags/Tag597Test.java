package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class Tag597Test extends BLTagTest {

  public Tag597Test() {
    super(Tag597.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "MP13/700");
    validField("b", "ISSN");
    validField(new DataField(tag, " ", " ", "a", "Enhanced", "b", "ISSN"));
  }

  @Test
  public void testInvalidFields() {
    invalidField("c", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
  }
}
