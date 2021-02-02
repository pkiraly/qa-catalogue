package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag968Test extends BLTagTest {

  public Tag968Test() {
    super(Tag968.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "C");
    validField("b", "x");
    validField("c", "BR");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("d", "Priority processing");
    invalidField("a", "Vol. 41(312), 1913- DEC. 1918 *A=");
    invalidField("a", "Vol. 41(312), 1913- DEC. 1918 *A=.");
    invalidField("a", "Vol. 41(312), 1913- DEC. 1918 *A=5");
  }
}
