package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordIgnoratorMarc21;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecordIgnoratorMarc21Test {

  RecordIgnoratorMarc21 conditions;

  @Test
  public void testParse() {
    conditions = new RecordIgnoratorMarc21("STA$s=SUPPRESSED");
    assertEquals(1, conditions.getConditions().size());
  }

  @Test
  public void isIgnorable() {
    conditions = new RecordIgnoratorMarc21("STA$s=SUPPRESSED");

    MarcRecord marcRecord = new MarcRecord("test");
    DataField field = new DataField("STA", "  $sSUPPRESSED");
    field.setMarcRecord(marcRecord);
    marcRecord.addDataField(field);

    assertTrue(conditions.isIgnorable(marcRecord));
  }
}