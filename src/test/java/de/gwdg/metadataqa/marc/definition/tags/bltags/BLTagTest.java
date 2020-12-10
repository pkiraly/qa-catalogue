package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class BLTagTest {

  protected DataFieldDefinition tag;

  public BLTagTest(DataFieldDefinition tag) {
    this.tag = tag;
  }

  public void validField(String subfield, String value) {
    validField(" ", subfield, value);
  }

  public void validField(String ind1, String subfield, String value) {
    validField(new DataField(tag, ind1, " ", subfield, value));
  }

  public void validField(String... arguments) {
    if (arguments.length == 2)
      validField(arguments[0], arguments[1]);
    else if (arguments.length == 3)
      validField(arguments[0], arguments[1], arguments[2]);
    else if (arguments.length % 2 == 0)
      validField(new DataField(tag, " ", " ", arguments));
  }

  public void validField(DataField field) {
    MarcRecord record = new MarcRecord("test");
    field.setRecord(record);

    MarcSubfield subfield = field.getSubfields().get(0);

    assertFalse(
      String.format("%s$%s=%s should be invalid in normal case",
        tag.getTag(), subfield.getCode(), subfield.getValue()),
      field.validate(MarcVersion.MARC21));
    assertTrue(
      String.format("%s$%s=%s should be valid in BL case (%s)",
        tag.getTag(), subfield.getCode(), subfield.getValue(),
        field.getValidationErrors()),
      field.validate(MarcVersion.BL));
  }

  public void invalidField(String subfield, String value) {
    invalidField(" ", subfield, value);
  }

  public void invalidField(String ind1, String subfield, String value) {
    DataField field = new DataField(tag, ind1, " ", subfield, value);

    MarcRecord record = new MarcRecord("test");
    field.setRecord(record);

    assertFalse(
      String.format("%s$%s=%s should be invalid in normal case", tag.getTag(), subfield, value),
      field.validate(MarcVersion.MARC21));
    assertFalse(
      String.format("%s$%s=%s should be invalid in BL", tag.getTag(), subfield, value),
      field.validate(MarcVersion.BL));
  }

  public void invalidFieldInBL(String ind1, String subfield, String value) {
    DataField field = new DataField(tag, ind1, " ", subfield, value);

    MarcRecord record = new MarcRecord("test");
    field.setRecord(record);

    assertTrue(
      String.format("%s$%s=%s should be invalid in normal case", tag.getTag(), subfield, value),
      field.validate(MarcVersion.MARC21));
    assertFalse(
      String.format("%s$%s=%s should be invalid in BL", tag.getTag(), subfield, value),
      field.validate(MarcVersion.BL));
  }

}
