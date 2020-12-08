package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag954Test extends BLTagTest {

  public Tag954Test() {
    super(Tag954.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Greek title transliterated");
    validField("a", "Hebrew word in title transliterated");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("b", "Priority processing");
  }
}
