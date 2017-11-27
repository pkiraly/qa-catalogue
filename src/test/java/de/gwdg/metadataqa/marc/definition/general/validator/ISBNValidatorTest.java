package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag020;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class ISBNValidatorTest {

	@Test
	public void testInvalid() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", "3p");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("a").get(0);
		assertNotNull(subfield);
		assertTrue(subfield.getDefinition().hasValidator());
		SubfieldValidator validator = subfield.getDefinition().getValidator();
		assertNotNull(validator);
		ValidatorResponse response = validator.isValid(subfield);
		assertFalse(response.isValid());
		ValidationError validationError = response.getValidationErrors().get(0);
		assertNotNull(validationError);
		assertEquals("test", validationError.getRecordId());
		assertEquals("020$a", validationError.getMarcPath());
		assertEquals(ValidationErrorType.ISBN, validationError.getType());
		assertEquals("'3p' is not a valid ISBN value: length should be either 10 or 13, but it is 2",
			validationError.getMessage());
		assertEquals("https://en.wikipedia.org/wiki/International_Standard_Book_Number", validationError.getUrl());
	}

	@Test
	public void test9992158107() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", "99921-58-10-7");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("a").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}

	@Test
	public void test9971502100() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", "9971-5-0210-0");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("a").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}

	@Test
	public void test9604250590() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", "960-425-059-0");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("a").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}

	@Test
	public void test8090273416() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", "80-902734-1-6");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("a").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}

	@Test
	public void test8535902775() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", "85-359-0277-5");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("a").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}

	@Test
	public void test1843560283() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", "1-84356-028-3");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("a").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}

	@Test
	public void test0684843285() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", "0684843285");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("a").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}

	@Test
	public void test080442957X() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", "0-8044-2957-X");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("a").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}

	@Test
	public void test0851310419() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", "0851310419");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("a").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}

	@Test
	public void test0943396042() {
		MarcRecord record = new MarcRecord("test");
		DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", "0943396042");
		field.setRecord(record);

		MarcSubfield subfield = field.getSubfield("a").get(0);
		ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
		assertTrue(response.isValid());
		assertEquals(0, response.getValidationErrors().size());
	}

	@Test
	public void testMultiple() {
		List<String> isbns = Arrays.asList("0-9752298-0-X", "0-9752298-0-X (fűzött)");

		for (String ISBN : isbns) {
			MarcRecord record = new MarcRecord("test");
			DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", ISBN);
			field.setRecord(record);

			MarcSubfield subfield = field.getSubfield("a").get(0);
			ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
			assertTrue(ISBN, response.isValid());
			assertEquals(0, response.getValidationErrors().size());
		}

	}

}
