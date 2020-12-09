package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class Tag962Test extends BLTagTest {

  public Tag962Test() {
    super(Tag962.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", " ",
      "a", "Vol. 41(312), 1913- DEC. 1918 *A=?",
      "c", "COL",
      "f", "Held but not currently received"
    ));
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("c", "Priority processing");
    invalidField("a", "Vol. 41(312), 1913- DEC. 1918 *A=");
    invalidField("a", "Vol. 41(312), 1913- DEC. 1918 *A=.");
    invalidField("a", "Vol. 41(312), 1913- DEC. 1918 *A=5");
  }
}
