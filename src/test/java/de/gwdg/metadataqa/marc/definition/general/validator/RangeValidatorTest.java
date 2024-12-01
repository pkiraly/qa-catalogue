package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag020;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RangeValidatorTest {

  @Test
  public void month() {
    RangeValidator validator = new RangeValidator(1, 12);
    assertTrue(validator.isValid(createMarcSubfield("1")).isValid());
    assertFalse(validator.isValid(createMarcSubfield("13")).isValid());
  }

  @Test
  public void day() {
    RangeValidator validator = new RangeValidator(1, 31);
    assertTrue(validator.isValid(createMarcSubfield("13")).isValid());
    assertFalse(validator.isValid(createMarcSubfield("33")).isValid());
  }

  private MarcSubfield createMarcSubfield(String value) {
    BibliographicRecord marcRecord = new Marc21BibliographicRecord("test");
    DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", value);
    field.setBibliographicRecord(marcRecord);

    return field.getSubfield("a").get(0);
  }
}