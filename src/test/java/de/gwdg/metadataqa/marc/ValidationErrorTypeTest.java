package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationErrorTypeTest {

	@Test
	public void test() {
		assertEquals(13, ValidationErrorType.values().length);
	}

	@Test
	public void testObsolete() {
		ValidationErrorType obsolete = ValidationErrorType.Obsolete;
		assertEquals("Obsolete", obsolete.name());
		assertEquals(0, obsolete.ordinal());
		assertEquals("Obsolete", obsolete.toString());
		assertEquals("obsolete", obsolete.getCode());
		assertEquals("obsolete value", obsolete.getMessage());
	}

	@Test
	public void testHasInvalidValue() {
		ValidationErrorType errorType = ValidationErrorType.HasInvalidValue;
		assertEquals("HasInvalidValue", errorType.name());
		assertEquals(1, errorType.ordinal());
		assertEquals("HasInvalidValue", errorType.toString());
		assertEquals("hasInvalidValue", errorType.getCode());
		assertEquals("invalid value", errorType.getMessage());
	}

	@Test
	public void testContainsInvalidValue() {
		ValidationErrorType errorType = ValidationErrorType.ContainsInvalidCode;
		assertEquals("ContainsInvalidCode", errorType.name());
		assertEquals(2, errorType.ordinal());
		assertEquals("ContainsInvalidCode", errorType.toString());
		assertEquals("containsInvalidCode", errorType.getCode());
		assertEquals("contains invalid code", errorType.getMessage());
	}
}
