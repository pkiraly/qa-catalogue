package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class Tag963Test extends BLTagTest {

  public Tag963Test() {
    super(Tag963.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", " ",
      "a", "No. 1, 1993- 20, 2000 *A=1",
      "b", "N",
      "c", "L415.B.104"
    ));
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
