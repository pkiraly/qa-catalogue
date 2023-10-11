package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationErrorTypeTest {

  @Test
  public void test() {
    assertEquals(23, ValidationErrorType.values().length);
  }

  @Test
  public void testObsoleteIndicator() {
    ValidationErrorType errorType = ValidationErrorType.INDICATOR_OBSOLETE;
    assertEquals("INDICATOR_OBSOLETE", errorType.name());
    assertEquals("INDICATOR_OBSOLETE", errorType.toString());
    assertEquals("obsoleteIndicator", errorType.getCode());
    assertEquals("obsolete value", errorType.getMessage());
    assertEquals("indicator", errorType.getCategory().getName());
  }

  @Test
  public void testObsoleteControlSubfield() {
    ValidationErrorType errorType = ValidationErrorType.CONTROL_POSITION_OBSOLETE_CODE;
    assertEquals("CONTROL_POSITION_OBSOLETE_CODE", errorType.name());
    assertEquals("CONTROL_POSITION_OBSOLETE_CODE", errorType.toString());
    assertEquals("obsoleteControlPosition", errorType.getCode());
    assertEquals("obsolete code", errorType.getMessage());
    assertEquals("control field", errorType.getCategory().getName());
  }

  @Test
  public void testHasInvalidValue() {
    ValidationErrorType errorType = ValidationErrorType.SUBFIELD_INVALID_VALUE;
    assertEquals("SUBFIELD_INVALID_VALUE", errorType.name());
    assertEquals("SUBFIELD_INVALID_VALUE", errorType.toString());
    assertEquals("hasInvalidValue", errorType.getCode());
    assertEquals("invalid value", errorType.getMessage());
    assertEquals("subfield", errorType.getCategory().getName());
  }

  @Test
  public void testContainsInvalidValue() {
    ValidationErrorType errorType = ValidationErrorType.CONTROL_POSITION_INVALID_CODE;
    assertEquals("CONTROL_POSITION_INVALID_CODE", errorType.name());
    assertEquals("CONTROL_POSITION_INVALID_CODE", errorType.toString());
    assertEquals("controlValueContainsInvalidCode", errorType.getCode());
    assertEquals("invalid code", errorType.getMessage());
    assertEquals("control field", errorType.getCategory().getName());
  }
}
