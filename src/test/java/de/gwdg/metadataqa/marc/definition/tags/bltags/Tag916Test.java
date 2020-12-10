package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag916Test extends BLTagTest {

  public Tag916Test() {
    super(Tag916.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "100=NOTNACO", "a", "700/2=NOTNACO");
  }

  @Test
  public void testInvalidFields() {
    invalidField("a", "949573831.");
    invalidField("b", "Awaiting upgrade with OCLC record – 20180123.");
  }
}
