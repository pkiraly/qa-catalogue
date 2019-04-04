package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationErrorTypeTest {

  @Test
  public void test() {
    assertEquals(22, ValidationErrorType.values().length);
  }

  @Test
  public void testObsoleteIndicator() {
    ValidationErrorType obsolete = ValidationErrorType.INDICATOR_OBSOLETE;
    assertEquals("INDICATOR_OBSOLETE", obsolete.name());
    assertEquals("INDICATOR_OBSOLETE", obsolete.toString());
    assertEquals("obsoleteIndicator", obsolete.getCode());
    assertEquals("indicator: obsolete value", obsolete.getMessage());
  }

  @Test
  public void testObsoleteControlSubfield() {
    ValidationErrorType obsolete = ValidationErrorType.CONTROL_SUBFIELD_OBSOLETE_CODE;
    assertEquals("CONTROL_SUBFIELD_OBSOLETE_CODE", obsolete.name());
    assertEquals("CONTROL_SUBFIELD_OBSOLETE_CODE", obsolete.toString());
    assertEquals("obsoleteControlSubfield", obsolete.getCode());
    assertEquals("control subfield: obsolete code", obsolete.getMessage());
  }

  @Test
  public void testHasInvalidValue() {
    ValidationErrorType errorType = ValidationErrorType.SUBFIELD_INVALID_VALUE;
    assertEquals("SUBFIELD_INVALID_VALUE", errorType.name());
    assertEquals("SUBFIELD_INVALID_VALUE", errorType.toString());
    assertEquals("hasInvalidValue", errorType.getCode());
    assertEquals("subfield: invalid value", errorType.getMessage());
  }

  @Test
  public void testContainsInvalidValue() {
    ValidationErrorType errorType = ValidationErrorType.CONTROL_SUBFIELD_INVALID_CODE;
    assertEquals("CONTROL_SUBFIELD_INVALID_CODE", errorType.name());
    assertEquals("CONTROL_SUBFIELD_INVALID_CODE", errorType.toString());
    assertEquals("controlValueContainsInvalidCode", errorType.getCode());
    assertEquals("control subfield: invalid code", errorType.getMessage());
  }
}
