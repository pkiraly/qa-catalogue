package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.analysis.validator.Validator;
import de.gwdg.metadataqa.marc.analysis.validator.ValidatorConfiguration;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Marc21Leader;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class Tag533Test {

  @Test
  public void test_valid() {
    Marc21Record marcRecord = new Marc21BibliographicRecord("000714573");
    marcRecord.setLeader(new Marc21Leader("01168cam a2200325 a 4500"));
    DataField field = new DataField(Tag533.getInstance(), " ", " ",
      "a", "Microfilm.",
      "b", "Washington, D.C. :",
      "c", "Library of Congress, Photoduplication Service,",
      "d", "1990.",
      "e", "1 microfilm reel ; 35 mm.",
      "7", "s1990    dcun a"
    );
    field.setMarcRecord(marcRecord);
    marcRecord.addDataField(field);
    Validator validator = new Validator(new ValidatorConfiguration().withMarcVersion(MarcVersion.MARC21));
    boolean isValid = validator.validate(marcRecord);
    assertTrue(isValid);
    assertTrue(validator.getValidationErrors().isEmpty());
  }

  @Test
  public void test_invalid() {
    Marc21Record marcRecord = new Marc21BibliographicRecord("000714573");
    marcRecord.setLeader(new Marc21Leader("01168cam a2200325 a 4500"));
    DataField field = new DataField(Tag533.getInstance(), " ", " ",
      "a", "Microfilm.",
      "b", "Washington, D.C. :",
      "c", "Library of Congress, Photoduplication Service,",
      "d", "1990.",
      "e", "1 microfilm reel ; 35 mm.",
      "7", "s1990    dcunaa"
    );
    field.setMarcRecord(marcRecord);
    marcRecord.addDataField(field);
    Validator validator = new Validator(new ValidatorConfiguration().withMarcVersion(MarcVersion.MARC21));
    boolean isValid = validator.validate(marcRecord);
    assertFalse(isValid);
    assertTrue(!validator.getValidationErrors().isEmpty());
    assertEquals("ValidationError{recordId='000714573', marcPath='533$7/13', type=SUBFIELD_INVALID_VALUE, message='invalid code for 'Regularity': 'a' at position 13 in 's1990    dcunaa'', url='https://www.loc.gov/marc/bibliographic/bd533.html'}", validator.getValidationErrors().get(0).toString());
  }
}