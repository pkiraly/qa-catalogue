package de.gwdg.metadataqa.marc.definition.tags.tags4xx;

import de.gwdg.metadataqa.marc.analysis.validator.Validator;
import de.gwdg.metadataqa.marc.analysis.validator.ValidatorConfiguration;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Marc21Leader;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.tags84x.Tag880;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class Tag490Test {

  @Test
  public void test() {
    Marc21Record marcRecord = new Marc21BibliographicRecord("000714573");
    marcRecord.setLeader(new Marc21Leader("01168cam a2200325 a 4500"));
    DataField field = new DataField(Tag490.getInstance(), "0", " ", "6", "880-03", "a", "ifriyat ha-Entsiḳlopedyah ha-Miḳraʼit ;", "v", "9");
    field.setMarcRecord(marcRecord);
    marcRecord.addDataField(field);
    Validator validator = new Validator(new ValidatorConfiguration().withMarcVersion(MarcVersion.GENT));
    boolean isValid = validator.validate(marcRecord);
    assertTrue(isValid);
    assertTrue(validator.getValidationErrors().isEmpty());
  }


  @Test
  public void test880() {
    Marc21Record marcRecord = new Marc21BibliographicRecord("000714573");
    marcRecord.setLeader(new Marc21Leader("01168cam a2200325 a 4500"));
    DataField field = new DataField(Tag880.getInstance(), " ", "0", "6", "490-03/(2/r", "a", "ספריית האנציקלופדיה המקראית ;", "v", "9");
    field.setMarcRecord(marcRecord);
    marcRecord.addDataField(field);

    Validator validator = new Validator(new ValidatorConfiguration().withMarcVersion(MarcVersion.GENT));
    boolean isValid = validator.validate(marcRecord);
    assertFalse(isValid);
    assertFalse(validator.getValidationErrors().isEmpty());
    assertEquals(2, validator.getValidationErrors().size());
    assertEquals("880->490$ind1", validator.getValidationErrors().get(0).getMarcPath());
    assertEquals("880->490$ind2", validator.getValidationErrors().get(1).getMarcPath());
  }
}
