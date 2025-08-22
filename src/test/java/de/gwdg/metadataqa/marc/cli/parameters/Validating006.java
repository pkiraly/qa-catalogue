package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.analysis.validator.Validator;
import de.gwdg.metadataqa.marc.dao.Control006;
import de.gwdg.metadataqa.marc.dao.Marc21Leader;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class Validating006 {

  @Test
  public void test() {
    Marc21Record marcRecord = new Marc21BibliographicRecord("001441164");
    marcRecord.setLeader(new Marc21Leader("02945nam a22005657a 4500"));
    marcRecord.setControl006(new Control006("jccnn           n", MarcLeader.Type.BOOKS));
    Validator validator = new Validator();
    boolean isValid = validator.validate(marcRecord);
    assertFalse(isValid);
    assertEquals(6, validator.getValidationErrors().size());
    assertEquals("006/01-04 (006book01)", validator.getValidationErrors().get(0).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_CODE, validator.getValidationErrors().get(0).getType());
    assertEquals("006/01-04 (006book01)", validator.getValidationErrors().get(1).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_CODE, validator.getValidationErrors().get(1).getType());
    assertEquals("006/12 (006book12)", validator.getValidationErrors().get(2).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE, validator.getValidationErrors().get(2).getType());
    assertEquals("006/13 (006book13)", validator.getValidationErrors().get(3).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE, validator.getValidationErrors().get(3).getType());
    assertEquals("006/14 (006book14)", validator.getValidationErrors().get(4).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE, validator.getValidationErrors().get(4).getType());
    assertEquals("006/16 (006book16)", validator.getValidationErrors().get(5).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE, validator.getValidationErrors().get(5).getType());

    /*
    003 BE-GnUNI
    005 20170502114956.0
    007 sd fsngnn|m|ee
    008 101123s2010    be aefh  b    101 0 mul d
    */

  }
}
