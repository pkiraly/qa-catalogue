package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagAQNTest extends BLTagTest {

  public TagAQNTest() {
    super(TagAQN.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "See also _ 1423672 'Manual on drainage in urbanized areas'");
    validField("a", "1 pbk dup rec 10/12/02, dup rec 10/3/03");
    validField("a", "ISSN same as former title");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("5", "B");
    invalidField("b", "Card,");
  }
}
