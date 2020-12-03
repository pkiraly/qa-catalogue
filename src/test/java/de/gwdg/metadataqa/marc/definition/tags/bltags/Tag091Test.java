package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.oclctags.Tag029;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class Tag091Test {

  @Test
  public void testFieldDefinition() {
    MarcRecord record = new MarcRecord("test");
    DataField field = new DataField(Tag091.getInstance(), " ", " ", "a", "c7507160a");
    field.setRecord(record);

    assertFalse("091$a should be invalid in normal case", field.validate(MarcVersion.MARC21));
    assertTrue("091$a should be valid in BL", field.validate(MarcVersion.BL));
  }

  @Test
  public void testInvalidData() {
    MarcRecord record = new MarcRecord("test");
    DataField field = new DataField(Tag091.getInstance(), " ", " ", "a", "c7507160b");
    field.setRecord(record);

    assertFalse("091$a should be invalid in BL", field.validate(MarcVersion.BL));
  }
}
