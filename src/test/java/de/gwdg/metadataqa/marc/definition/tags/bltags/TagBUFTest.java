package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagBUFTest extends BLTagTest {

  public TagBUFTest() {
    super(TagBUF.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Y");
    validField("d", "20201216");
    validField("a", "Y", "d", "20050914");
    validField("a", "Y2", "d", "20160705");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "t");
    invalidField("a", "X");
    invalidField("d", "20201236");
  }
}
