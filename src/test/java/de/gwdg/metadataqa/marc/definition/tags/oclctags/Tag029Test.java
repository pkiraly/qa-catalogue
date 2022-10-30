package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class Tag029Test {

  @Test
  public void test() {
    BibliographicRecord marcRecord = new Marc21Record("test");
    DataField field = new DataField(Tag029.getInstance(), "a", "a", "a", "0170-1967");
    field.setMarcRecord(marcRecord);

    assertFalse("029$ind=a should be invalid in normal case", field.validate(MarcVersion.MARC21));
    assertTrue("029$ind=a should be valid in DNB", field.validate(MarcVersion.DNB));
  }
}
