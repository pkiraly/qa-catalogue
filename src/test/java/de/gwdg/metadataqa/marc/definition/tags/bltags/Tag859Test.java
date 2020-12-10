package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag859Test extends BLTagTest {

  public Tag859Test() {
    super(Tag859.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "ELD", "b", "ebook");
    validField("a", "ORR", "b", "Alexander Street Press");
    validField("a", "XLD", "b", "EUBookshop");
    validField("a", "XLD",
      "b", "GOOGLEBOOKS",
      "b", "France in the Americas Project");
  }

  @Test
  public void testInvalidFields() {
    invalidField("c", "x(88-89)");
  }
}
