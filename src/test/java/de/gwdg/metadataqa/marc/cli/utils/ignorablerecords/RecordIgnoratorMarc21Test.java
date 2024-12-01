package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    BibliographicRecord marcRecord = new Marc21BibliographicRecord("test");
    DataField field = new DataField("STA", "  $sSUPPRESSED");
    field.setBibliographicRecord(marcRecord);
    marcRecord.addDataField(field);

    assertTrue(conditions.isIgnorable(marcRecord));
  }
}