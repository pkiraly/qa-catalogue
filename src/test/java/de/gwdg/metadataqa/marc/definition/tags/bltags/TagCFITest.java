package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.dao.DataField;
import org.junit.Test;

public class TagCFITest extends BLTagTest {

  public TagCFITest() {
    super(TagCFI.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, "0", " ", "a", "Current copyright fee: GBP24.97", "c", "22\\8", "e", "01-jan-2014", "f", "GBP24.97", "g", "0", "h", "P", "5", "Uk"));
    validField(new DataField(tag, "1", " ", "a", "Pending copyright fee: GBP24.21", "c", "22\\8", "e", "01-jul-2014", "f", "GBP24.21", "g", "0", "h", "P", "5", "Uk"));
    validField(new DataField(tag, "2", " ", "a", "Previous copyright fee: GBP25.77", "c", "22\\8", "e", "01-jul-2013", "f", "GBP25.77", "g", "0", "h", "P", "5", "Uk"));
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "t");
    invalidField("h", "B");
    invalidField("j", "20201236");
  }
}
