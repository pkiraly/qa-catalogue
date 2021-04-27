package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import org.junit.Test;

import static org.junit.Assert.*;

public class IgnorableRecordsTest {

  IgnorableRecords conditions = new IgnorableRecords();

  @Test
  public void testParse() {
    conditions.parseInput("STA$s=SUPPRESSED");
    assertEquals(1, conditions.getConditions().size());
  }

  @Test
  public void isIgnorable() {
    conditions.parseInput("STA$s=SUPPRESSED");

    MarcRecord record = new MarcRecord("test");
    DataField field = new DataField("STA", "  $sSUPPRESSED");
    field.setRecord(record);
    record.addDataField(field);

    assertTrue(conditions.isIgnorable(record));
  }
}