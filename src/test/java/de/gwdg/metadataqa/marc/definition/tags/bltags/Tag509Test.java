package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class Tag509Test {

  @Test
  public void testValidFields() {
    validField("a", "Verify presence and nature of initial leaf (sig. A1), not present in L copy");
    validField("a", "Verify presence of English title page and text. CLU-C's copy entirely in Latin");
    validField("a", "NLS copy dimensions: 16 cm.");
  }

  @Test
  public void testInvalidFields() {
    invalidField("b", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
  }

  public void validField(String subfield, String value) {
    DataField field = new DataField(Tag509.getInstance(), " ", " ", subfield, value);

    MarcRecord record = new MarcRecord("test");
    field.setRecord(record);

    assertFalse(
      String.format("039$%s=%s should be invalid in normal case", subfield, value),
      field.validate(MarcVersion.MARC21));
    assertTrue(
      String.format("039$%s=%s should be valid in normal case", subfield, value),
      field.validate(MarcVersion.BL));
  }

  public void invalidField(String subfield, String value) {
    invalidField(" ", subfield, value);
  }

  public void invalidField(String ind1, String subfield, String value) {
    DataField field = new DataField(Tag509.getInstance(), ind1, " ", subfield, value);

    MarcRecord record = new MarcRecord("test");
    field.setRecord(record);

    assertFalse(
      String.format("039$%s=%s should be invalid in normal case", subfield, value),
      field.validate(MarcVersion.MARC21));
    assertFalse(
      String.format("039$%s=%s should be invalid in BL", subfield, value),
      field.validate(MarcVersion.BL));
  }
}
