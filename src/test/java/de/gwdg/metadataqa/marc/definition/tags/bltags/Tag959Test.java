package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag959Test extends BLTagTest {

  public Tag959Test() {
    super(Tag959.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("f", "Held but not currently received");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("s", "17");
    invalidField("e", "Priority processing");
  }
}
