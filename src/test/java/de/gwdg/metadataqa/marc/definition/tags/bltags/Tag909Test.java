package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag909Test extends BLTagTest {

  public Tag909Test() {
    super(Tag909.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "949573831");
    validField("b", "Awaiting upgrade with OCLC record – 20180123");
  }

  @Test
  public void testInvalidFields() {
    invalidField("a", "949573831.");
    invalidField("b", "Awaiting upgrade with OCLC record – 20180123.");
    invalidField("c", "x(88-89)");
  }
}
