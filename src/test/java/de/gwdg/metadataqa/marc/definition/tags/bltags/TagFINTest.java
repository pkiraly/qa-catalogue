package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagFINTest extends BLTagTest {

  public TagFINTest() {
    super(TagFIN.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Y");
    validField("a", "Y", "d", "20050914");
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "Y");
    invalidField("a", "y");
    invalidField("d", "20050944");
    invalidField("c", "y");
  }
}
