package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class Tag039Test {

  @Test
  public void testRegex() {
    assertTrue(Pattern.compile("^\\d\\d(\\d{2}|0[1-3])$").matcher("1785").matches());
  }

  @Test
  public void testFieldDefinition() {
    validateField("0", "a", "1785");
    validateField("0", "a", "8901");
    validateField("1", "a", "8903");
    validateField("0", "a", "1754");
    validateField("0", "a", "9602");
    validateField(new DataField(Tag039.getInstance(), "0", " ", "p", "1627", "a", "1608"));
    validateField("0", "a", "8901");
    validateField("1", "a", "8903");
  }

  public void validateField(String ind1, String subfield, String value) {
    DataField field = new DataField(Tag039.getInstance(), ind1, " ", subfield, value);

    MarcRecord record = new MarcRecord("test");
    field.setRecord(record);

    assertFalse(
      String.format("039$%s=%s should be invalid in normal case", subfield, value),
      field.validate(MarcVersion.MARC21));
    assertTrue(
      String.format("039$%s=%s should be valid in normal case", subfield, value),
      field.validate(MarcVersion.BL));
  }

  public void validateField(DataField field) {
    MarcRecord record = new MarcRecord("test");
    field.setRecord(record);

    assertFalse("039 should be invalid in normal case", field.validate(MarcVersion.MARC21));
    assertTrue("039 should be valid in normal case", field.validate(MarcVersion.BL));
  }
}
