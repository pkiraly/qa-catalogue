package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagA02Test extends BLTagTest {

  public TagA02Test() {
    super(TagA02.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "3008873", "z", "0");
    validField("a", "1451916", "z", "51916");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("5", "B");
    invalidField("b", "Card,");
  }
}
