package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.definition.tags.tags4xx.Tag411;
import org.junit.Test;

import static org.junit.Assert.*;

public class ISSNValidatorTest {

  @Test
  public void test03785955() {
    MarcSubfield subfield = createMarcSubfield("0378-5955");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void test03785954() {
    MarcSubfield subfield = createMarcSubfield("0378-5954");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertFalse(response.isValid());
    assertEquals(1, response.getValidationErrors().size());
    assertEquals("ISSN failed in integrity check",
      response.getValidationErrors().get(0).getMessage());
    /*
    assertEquals("'0378-5954' is not a valid ISSN value, it failed in integrity check",
      response.getValidationErrors().get(0).getMessage());
    */
  }

  @Test
  public void test00249319() {
    MarcSubfield subfield = createMarcSubfield("0024-9319");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

   @Test
  public void testNormalization() {
    MarcSubfield subfield = createMarcSubfield("0024-9319 ;");

    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());

    subfield = createMarcSubfield("1040-0400 (ISSN)");
    response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  @Test
  public void testPoint() {
    MarcSubfield subfield = createMarcSubfield("0024-9319.");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue("0024-9319. should be valid", response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }


  /**
   * See https://github.com/pkiraly/qa-catalogue/pull/43#issuecomment-351627027
   */
  @Test
  public void testExtraText() {
    MarcSubfield subfield = createMarcSubfield("0024-9319 [print]");
    ValidatorResponse response = subfield.getDefinition().getValidator().isValid(subfield);
    assertTrue(response.isValid());
    assertEquals(0, response.getValidationErrors().size());
  }

  private MarcSubfield createMarcSubfield(String s) {
    BibliographicRecord marcRecord = new Marc21Record("test");
    DataField field = new DataField(Tag411.getInstance(), " ", " ", "x", s);
    field.setMarcRecord(marcRecord);

    return field.getSubfield("x").get(0);
  }


  // TODO test it: "1572-9001 (ESSN), 1040-0400 (ISSN). -"
}
