package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag020;
import de.gwdg.metadataqa.marc.definition.tags.tags4xx.Tag411;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ISSNValidatorTest {

	@Test
	public void test03785955() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag411.getInstance(), " ", " ", "x", "0378-5955");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("x").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}

	@Test
	public void test03785954() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag411.getInstance(), " ", " ", "x", "0378-5954");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("x").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertFalse(response.isValid());
		assertEquals(1, response.getValidationErrors().size());
		assertEquals("'0378-5954' is not a valid ISSN value", response.getValidationErrors().get(0).getMessage());
	}

	@Test
	public void test00249319() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag411.getInstance(), " ", " ", "x", "0024-9319");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("x").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}
}
