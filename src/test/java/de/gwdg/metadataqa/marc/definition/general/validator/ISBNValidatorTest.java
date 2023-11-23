package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag020;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ISBNValidatorTest {

  @Test
  public void testInvalid() {
    MarcSubfield subfield = createMarcSubfield("3p");
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
    assertEquals(ValidationErrorType.SUBFIELD_ISBN, validationError.getType());
    assertEquals("ISBN does not fit the pattern \\d[\\d-]+[\\dxX].",
      validationError.getMessage());
    /*
    assertEquals("'3p' does not a have an ISBN value, it does not fit the pattern \\d[\\d-]+[\\dxX].",
      validationError.getMessage());
    */
    assertEquals("https://en.wikipedia.org/wiki/International_Standard_Book_Number", validationError.getUrl());
  }

  @Test
  public void test9992158107() {
    MarcSubfield subfield = createMarcSubfield("99921-58-10-7");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void test9971502100() {
    MarcSubfield subfield = createMarcSubfield("9971-5-0210-0");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void test9604250590() {
    MarcSubfield subfield = createMarcSubfield("960-425-059-0");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void test8090273416() {
    MarcSubfield subfield = createMarcSubfield("80-902734-1-6");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void test8535902775() {
    MarcSubfield subfield = createMarcSubfield("85-359-0277-5");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void test1843560283() {
    MarcSubfield subfield = createMarcSubfield("1-84356-028-3");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void test0684843285() {
    MarcSubfield subfield = createMarcSubfield("0684843285");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void test080442957X() {
    MarcSubfield subfield = createMarcSubfield("0-8044-2957-X");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void test0851310419() {
    MarcSubfield subfield = createMarcSubfield("0851310419");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void test0943396042() {
    MarcSubfield subfield = createMarcSubfield("0943396042");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void testWithSpaces() {
    MarcSubfield subfield = createMarcSubfield("0 405 05352 5");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void testMultiple() {
    List<String> isbns = Arrays.asList("0-9752298-0-X", "0-9752298-0-X (fűzött)");

    for (String ISBN : isbns) {
      MarcSubfield subfield = createMarcSubfield(ISBN);
      ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
      assertTrue(ISBN, response.isValid());
      assertEquals(0, response.getValidationErrors().size());
    }
  }

  @Test
  public void testSuffixes() {
    MarcSubfield subfield = createMarcSubfield("9782070769148 (broché) :");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  private MarcSubfield createMarcSubfield(String ISBN) {
    BibliographicRecord marcRecord = new Marc21BibliographicRecord("test");
    DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", ISBN);
    field.setMarcRecord(marcRecord);

    return field.getSubfield("a").get(0);
  }

}
