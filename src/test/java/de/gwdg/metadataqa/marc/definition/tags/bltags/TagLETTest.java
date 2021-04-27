package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.dao.DataField;
import org.junit.Test;

public class TagLETTest extends BLTagTest {

  public TagLETTest() {
    super(TagLET.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", "0", "a", "British farmer and grower. West Midlands ed."));
    validField(new DataField(tag, " ", "1", "a", "British farmer and grower. West Midlands ed."));
    validField(new DataField(tag, " ", "2", "a", "British farmer and grower. West Midlands ed."));
    validField(new DataField(tag, " ", "3", "a", "British farmer and grower. West Midlands ed."));
    validField(new DataField(tag, " ", "4", "a", "British farmer and grower. West Midlands ed."));
    validField(new DataField(tag, " ", "5", "a", "British farmer and grower. West Midlands ed."));
    validField(new DataField(tag, " ", "6", "a", "British farmer and grower. West Midlands ed."));
    validField(new DataField(tag, " ", "7", "a", "British farmer and grower. West Midlands ed."));
    validField(new DataField(tag, " ", "8", "a", "British farmer and grower. West Midlands ed."));
    validField(new DataField(tag, " ", "9", "a", "British farmer and grower. West Midlands ed."));
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Y");
    invalidField("e", "20050944");
  }
}
