package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.analysis.validator.DataFieldValidator;
import de.gwdg.metadataqa.marc.analysis.validator.ValidatorConfiguration;
import de.gwdg.metadataqa.marc.dao.Control001;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
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
    BibliographicRecord marcRecord = new Marc21BibliographicRecord("test");

    DataField field = new DataField(Tag090.getInstance(), " ", " ", "n", "sm");
    field.setMarcRecord(marcRecord);

    DataFieldValidator validator = new DataFieldValidator();
    assertFalse("090$n should be invalid in normal case", validator.validate(field));

    validator = new DataFieldValidator(new ValidatorConfiguration().withMarcVersion(MarcVersion.DNB));
    assertTrue("090$n should be valid in DNB.", validator.validate(field));
  }

  @Test
  public void testVersionSpecificSubfieldWithWrongValue() {
    Marc21Record marcRecord = new Marc21BibliographicRecord();
    marcRecord.setControl001(new Control001("test"));

    DataField field = new DataField(Tag090.getInstance(), " ", " ", "n", "sk");
    field.setMarcRecord(marcRecord);

    DataFieldValidator validator = new DataFieldValidator(new ValidatorConfiguration().withMarcVersion(MarcVersion.DNB));
    assertFalse("090$n value sk should be invalid in DNB.", validator.validate(field));
    assertEquals(ValidationErrorType.SUBFIELD_INVALID_VALUE,
      validator.getValidationErrors().get(0).getType());
    assertEquals("090$n", validator.getValidationErrors().get(0).getMarcPath());
    assertEquals("sk", validator.getValidationErrors().get(0).getMessage());
  }
}
