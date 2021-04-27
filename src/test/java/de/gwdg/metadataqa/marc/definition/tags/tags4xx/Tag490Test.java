package de.gwdg.metadataqa.marc.definition.tags.tags4xx;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.tags84x.Tag880;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class Tag490Test {

  @Test
  public void test() {
    MarcRecord record = new MarcRecord("000714573");
    record.setLeader(new Leader("01168cam a2200325 a 4500"));
    DataField field = new DataField(Tag490.getInstance(), "0", " ", "6", "880-03", "a", "ifriyat ha-Entsiḳlopedyah ha-Miḳraʼit ;", "v", "9");
    field.setRecord(record);
    record.addDataField(field);
    boolean isValid = record.validate(MarcVersion.GENT);
    assertTrue(isValid);
    assertTrue(record.getValidationErrors().isEmpty());
  }


  @Test
  public void test880() {
    MarcRecord record = new MarcRecord("000714573");
    record.setLeader(new Leader("01168cam a2200325 a 4500"));
    DataField field = new DataField(Tag880.getInstance(), " ", "0", "6", "490-03/(2/r", "a", "ספריית האנציקלופדיה המקראית ;", "v", "9");
    field.setRecord(record);
    record.addDataField(field);

    boolean isValid = record.validate(MarcVersion.GENT);
    assertFalse(isValid);
    assertFalse(record.getValidationErrors().isEmpty());
    assertEquals(2, record.getValidationErrors().size());
    assertEquals("880->490$ind1", record.getValidationErrors().get(0).getMarcPath());
    assertEquals("880->490$ind2", record.getValidationErrors().get(1).getMarcPath());
  }

}
