package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class Tag966Test extends BLTagTest {

  public Tag966Test() {
    super(Tag966.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", " ",
      "u", "o",
      "l", "SPACQ 02/01/98"
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
