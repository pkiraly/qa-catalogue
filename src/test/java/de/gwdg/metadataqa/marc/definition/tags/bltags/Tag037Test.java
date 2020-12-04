package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag037;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class Tag037Test {

  @Test
  public void testDisallowedSubfield_c() {
    MarcRecord record = new MarcRecord("test");
    DataField field = new DataField(Tag037.getInstance(), " ", " ", "c", "$175.00");
    field.setRecord(record);

    assertTrue("037$c should be valid in normal case", field.validate(MarcVersion.MARC21));
    assertFalse("037$c should be invalid in BL", field.validate(MarcVersion.BL));
  }

  @Test
  public void testDisallowedSubfield_f() {
    MarcRecord record = new MarcRecord("test");
    DataField field = new DataField(Tag037.getInstance(), " ", " ", "f", "paperbound");
    field.setRecord(record);

    assertTrue("037$f should be valid in normal case", field.validate(MarcVersion.MARC21));
    assertFalse("037$f should be invalid in BL", field.validate(MarcVersion.BL));
  }

  @Test
  public void testDisallowedSubfield_g() {
    MarcRecord record = new MarcRecord("test");
    DataField field = new DataField(Tag037.getInstance(), " ", " ", "g", "paperbound");
    field.setRecord(record);

    assertTrue("037$g should be valid in normal case", field.validate(MarcVersion.MARC21));
    assertFalse("037$g should be invalid in BL", field.validate(MarcVersion.BL));
  }

  @Test
  public void testDisallowedSubfield_6() {
    MarcRecord record = new MarcRecord("test");
    DataField field = new DataField(Tag037.getInstance(), " ", " ", "6", "100-01/(N");
    field.setRecord(record);

    assertTrue("037$6 should be valid in normal case", field.validate(MarcVersion.MARC21));
    assertFalse("037$6 should be invalid in BL", field.validate(MarcVersion.BL));
  }

  @Test
  public void testDisallowedSubfield_8() {
    MarcRecord record = new MarcRecord("test");
    DataField field = new DataField(Tag037.getInstance(), " ", " ", "8", "1.5\\a");
    field.setRecord(record);

    assertTrue("037$8 should be valid in normal case", field.validate(MarcVersion.MARC21));
    assertFalse("037$8 should be invalid in BL", field.validate(MarcVersion.BL));
  }
}
