package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag037;
import org.junit.Test;

public class Tag037Test extends BLTagTest {

  public Tag037Test() {
    super(Tag037.getInstance());
  }

  @Test
  public void testValidFields() {
    // validField("a", "c7507160a");
  }

  @Test
  public void testInvalidFields() {
    invalidFieldInBL(" ", "c", "$175.00");
    invalidFieldInBL(" ", "f", "paperbound");
    invalidFieldInBL(" ", "g", "paperbound");
    invalidFieldInBL(" ", "6", "100-01/(N");
    invalidFieldInBL(" ", "8", "1.5\\a");
  }
}
