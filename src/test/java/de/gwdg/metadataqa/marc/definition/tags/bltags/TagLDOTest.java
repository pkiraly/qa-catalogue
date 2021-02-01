package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagLDOTest extends BLTagTest {

  public TagLDOTest() {
    super(TagLDO.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "CIP", "b", "hbk");
    validField("a", "CIP", "b", "pbk");
    validField("a", "LDO", "b", "pack", "c", "12/6/02");
    validField("a", "ALDL", "b", "pbk", "c", "12/10/05", "d", "3982641");
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "Y");
    invalidField("e", "20050944");
  }
}
