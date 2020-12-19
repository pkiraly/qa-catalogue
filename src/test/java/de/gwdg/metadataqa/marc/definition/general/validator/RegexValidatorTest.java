package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag020;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegexValidatorTest {

  @Test
  public void yearRange() {
    RegexValidator validator = new RegexValidator("^(1[6-9]|20)\\d\\d(/(1[6-9]|20)\\d\\d)?$");
    assertTrue(validator.isValid(createMarcSubfield("1629")).isValid());
    assertTrue(validator.isValid(createMarcSubfield("1629/1703")).isValid());
    assertFalse(validator.isValid(createMarcSubfield("13")).isValid());
    assertFalse(validator.isValid(createMarcSubfield("1413")).isValid());
  }

  private MarcSubfield createMarcSubfield(String value) {
    MarcRecord record = new MarcRecord("test");
    DataField field = new DataField(Tag020.getInstance(), " ", " ", "a", value);
    field.setRecord(record);

    return field.getSubfield("a").get(0);
  }
}