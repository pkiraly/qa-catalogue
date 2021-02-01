package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag091Test extends BLTagTest {

  public Tag091Test() {
    super(Tag091.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "c7507160a");
  }

  @Test
  public void testInvalidFields() {
    invalidField("a", "c7507160b");
    invalidField("b", "c7507160b");
  }
}
