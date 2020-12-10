package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag992Test extends BLTagTest {

  public Tag992Test() {
    super(Tag992.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "C01");
    validField("a", "C02", "a", "C03");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("5", "B");
    invalidField("b", "Card,");
  }
}
