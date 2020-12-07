package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class Tag859Test extends BLTagTest {

  public Tag859Test() {
    super(Tag859.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", " ", "a", "ELD", "b", "ebook"));
    validField(new DataField(tag, " ", " ", "a", "ORR", "b", "Alexander Street Press"));
    validField(new DataField(tag, " ", " ", "a", "XLD", "b", "EUBookshop"));
    validField(new DataField(tag, " ", " ", "a", "XLD",
      "b", "GOOGLEBOOKS",
      "b", "France in the Americas Project"));
  }

  @Test
  public void testInvalidFields() {
    invalidField("c", "x(88-89)");
  }
}
