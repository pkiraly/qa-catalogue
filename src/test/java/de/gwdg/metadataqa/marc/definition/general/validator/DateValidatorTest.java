package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag020;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateValidatorTest {

  @Test
  public void yyyyMMdd() {
    DateValidator validator = new DateValidator("yyyyMMdd");
    assertTrue(validator.isValid(createMarcSubfield("20291011")).isValid());
    assertFalse(validator.isValid(createMarcSubfield("20291311")).isValid());
  }

  @Test
  public void yyyy_MM_dd() {
    DateValidator validator = new DateValidator("yyyy-MM-dd");
    assertTrue(validator.isValid(createMarcSubfield("2029-10-11")).isValid());
    assertFalse(validator.isValid(createMarcSubfield("2029-13-11")).isValid());
  }

  private MarcSubfield createMarcSubfield(String value) {
    BibliographicRecord marcRecord = new Marc21BibliographicRecord("test");
    DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", value);
    field.setMarcRecord(marcRecord);

    return field.getSubfield("a").get(0);
  }
}