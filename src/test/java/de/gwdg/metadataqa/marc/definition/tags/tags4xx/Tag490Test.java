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
    MarcRecord marcRecord = new MarcRecord("000714573");
    marcRecord.setLeader(new Leader("01168cam a2200325 a 4500"));
    DataField field = new DataField(Tag490.getInstance(), "0", " ", "6", "880-03", "a", "ifriyat ha-Entsiḳlopedyah ha-Miḳraʼit ;", "v", "9");
    field.setMarcRecord(marcRecord);
    marcRecord.addDataField(field);
    boolean isValid = marcRecord.validate(MarcVersion.GENT);
    assertTrue(isValid);
    assertTrue(marcRecord.getValidationErrors().isEmpty());
  }


  @Test
  public void test880() {
    MarcRecord marcRecord = new MarcRecord("000714573");
    marcRecord.setLeader(new Leader("01168cam a2200325 a 4500"));
    DataField field = new DataField(Tag880.getInstance(), " ", "0", "6", "490-03/(2/r", "a", "ספריית האנציקלופדיה המקראית ;", "v", "9");
    field.setMarcRecord(marcRecord);
    marcRecord.addDataField(field);

    boolean isValid = marcRecord.validate(MarcVersion.GENT);
    assertFalse(isValid);
    assertFalse(marcRecord.getValidationErrors().isEmpty());
    assertEquals(2, marcRecord.getValidationErrors().size());
    assertEquals("880->490$ind1", marcRecord.getValidationErrors().get(0).getMarcPath());
    assertEquals("880->490$ind2", marcRecord.getValidationErrors().get(1).getMarcPath());
  }

}
