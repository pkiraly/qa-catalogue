package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class Tag964Test extends BLTagTest {

  public Tag964Test() {
    super(Tag964.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", " ",
      "a", "62/65, 1962- *A=1",
      "c", "557:551.510",
      "d", "Q",
      "e", "TC06995"
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
