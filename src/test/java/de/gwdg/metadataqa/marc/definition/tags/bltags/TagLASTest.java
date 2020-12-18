package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class TagLASTest extends BLTagTest {

  public TagLASTest() {
    super(TagLAS.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", " ",
      "a", "BATCH", "b", "00", "c", "20091125", "l", "BLL01", "h", "2204"));
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "Y");
    invalidField("d", "20050944");
  }
}
