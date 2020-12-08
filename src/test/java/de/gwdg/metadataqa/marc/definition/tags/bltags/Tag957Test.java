package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag957Test extends BLTagTest {

  public Tag957Test() {
    super(Tag957.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "800730p");
    validField("s", "15");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("s", "17");
    invalidField("e", "Priority processing");
  }
}
