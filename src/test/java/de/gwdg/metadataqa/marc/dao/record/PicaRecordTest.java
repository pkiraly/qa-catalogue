package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.cli.CliTestUtils;
import de.gwdg.metadataqa.marc.utils.pica.PicaDatafieldFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PicaRecordTest {

  private PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/schema/k10plus.json"));

  @Test
  public void addDataField() {
    PicaRecord record = new PicaRecord("u2407796");
    record.addDataField(PicaDatafieldFactory.create("001B", "0", "1999:02-05-18"));
    assertNotNull(record.getDatafieldsByTag("001B"));
    assertEquals(1, record.getDatafieldsByTag("001B").size());
    assertEquals(1, record.getDatafieldsByTag("001B").get(0).getSubfields().size());
    assertEquals(1, record.getDatafieldsByTag("001B").get(0).getSubfield("0").size());
    assertEquals("1999:02-05-18", record.getDatafieldsByTag("001B").get(0).getSubfield("0").get(0).getValue());
  }

  @Test
  public void addDataField_withOccurence() {
    PicaRecord record = new PicaRecord("u2407796");
    record.addDataField(PicaDatafieldFactory.create("041A/01", "0", "1999:02-05-18"));
    assertNotNull(record.getDatafieldsByTag("041A"));
    // assertNotNull(record.getDatafield("041A/01"));
    assertEquals(1, record.getDatafieldsByTag("041A").size());
    assertEquals("041A/01", record.getDatafieldsByTag("041A").get(0).getTagWithOccurrence());
    assertEquals("01", record.getDatafieldsByTag("041A").get(0).getOccurrence());
    assertEquals(1, record.getDatafieldsByTag("041A").get(0).getSubfields().size());
    assertEquals(1, record.getDatafieldsByTag("041A").get(0).getSubfield("0").size());
    assertEquals("1999:02-05-18", record.getDatafieldsByTag("041A").get(0).getSubfield("0").get(0).getValue());
  }
}