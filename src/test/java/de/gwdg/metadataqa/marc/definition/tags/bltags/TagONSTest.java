package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.dao.DataField;
import org.junit.Test;

public class TagONSTest extends BLTagTest {

  public TagONSTest() {
    super(TagONS.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", "7", "a", "STU", "x", "026000", "2", "bisacsh"));
    validField(new DataField(tag, " ", "7", "a", "YF", "2", "bicssc"));
    validField(new DataField(tag, " ", " ",
      "t", "Cold War; Espionage; USSR; Soviets; John le Carré; Len Deighton; Chernobyl; Nagasaki; Hiroshima; Nuclear Explosion; M.A.D.; Mutually Assured Destruction",
      "2", "Subject Keywords"));
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "1");
    invalidField("b", "1.");
    invalidField("b", "1");
  }
}
