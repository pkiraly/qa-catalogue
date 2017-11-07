package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ValidationError;
import de.gwdg.metadataqa.marc.definition.ValidationErrorType;
import de.gwdg.metadataqa.marc.definition.controlsubfields.tag007.Tag007nonprojected01;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidationErrorTest {

	@Test
	public void test() {
		ValidationError error = new ValidationError("123", "007/01", ValidationErrorType.Obsolete,
			"c", Tag007nonprojected01.getInstance().getDescriptionUrl());
		assertEquals("123", error.getRecordId());
		assertEquals("007/01", error.getMarcPath());
		assertEquals(ValidationErrorType.Obsolete, error.getType());
		assertEquals("c", error.getMessage());
		assertEquals("https://www.loc.gov/marc/bibliographic/bd007k.html", error.getUrl());
	}

}
