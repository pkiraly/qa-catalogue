package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.dao.Control001;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.oclctags.Tag090;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class Tag090Test {

  @Test
  public void testVersionSpecificSubfield() {
    MarcRecord record = new MarcRecord("test");

    DataField field = new DataField(Tag090.getInstance(), " ", " ", "n", "sm");
    field.setRecord(record);

    assertFalse("090$n should be invalid in normal case", field.validate(MarcVersion.MARC21));
    assertTrue("090$n should be valid in DNB.", field.validate(MarcVersion.DNB));
  }

  @Test
  public void testVersionSpecificSubfieldWithWrongValue() {
    MarcRecord record = new MarcRecord();
    record.setControl001(new Control001("test"));

    DataField field = new DataField(Tag090.getInstance(), " ", " ", "n", "sk");
    field.setRecord(record);

    assertFalse("090$n value sk should be invalid in DNB.", field.validate(MarcVersion.DNB));
    assertEquals(ValidationErrorType.SUBFIELD_INVALID_VALUE,
      field.getValidationErrors().get(0).getType());
    assertEquals("090$n", field.getValidationErrors().get(0).getMarcPath());
    assertEquals("sk", field.getValidationErrors().get(0).getMessage());
  }
}
