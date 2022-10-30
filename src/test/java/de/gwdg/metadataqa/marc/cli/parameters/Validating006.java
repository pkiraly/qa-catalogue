package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.dao.Control006;
import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class Validating006 {

  @Test
  public void test() {
    BibliographicRecord marcRecord = new Marc21Record("001441164");
    marcRecord.setLeader(new Leader("02945nam a22005657a 4500"));
    marcRecord.setControl006(new Control006("jccnn           n", Leader.Type.BOOKS));
    boolean isValid = marcRecord.validate(MarcVersion.MARC21);
    assertFalse(isValid);
    assertEquals(6, marcRecord.getValidationErrors().size());
    assertEquals("006/01-04 (006book01)", marcRecord.getValidationErrors().get(0).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_CODE, marcRecord.getValidationErrors().get(0).getType());
    assertEquals("006/01-04 (006book01)", marcRecord.getValidationErrors().get(1).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_CODE, marcRecord.getValidationErrors().get(1).getType());
    assertEquals("006/12 (006book12)", marcRecord.getValidationErrors().get(2).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE, marcRecord.getValidationErrors().get(2).getType());
    assertEquals("006/13 (006book13)", marcRecord.getValidationErrors().get(3).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE, marcRecord.getValidationErrors().get(3).getType());
    assertEquals("006/14 (006book14)", marcRecord.getValidationErrors().get(4).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE, marcRecord.getValidationErrors().get(4).getType());
    assertEquals("006/16 (006book16)", marcRecord.getValidationErrors().get(5).getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE, marcRecord.getValidationErrors().get(5).getType());

    /*
    003 BE-GnUNI
    005 20170502114956.0
    007 sd fsngnn|m|ee
    008 101123s2010    be aefh  b    101 0 mul d
    */

  }
}
