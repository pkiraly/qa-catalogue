package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagSSDTest extends BLTagTest {

  public TagSSDTest() {
    super(TagSSD.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "25500$a1st (1936) - 31st ed. (1983); 50300$aContinued as \"International ABC aerospace directory\", q.v");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Y");
    invalidField("c", "a");
  }
}
