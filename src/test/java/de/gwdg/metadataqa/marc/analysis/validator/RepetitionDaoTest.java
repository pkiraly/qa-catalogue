package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.utils.pica.PicaDatafieldFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class RepetitionDaoTest {

  @Test
  public void getExtendedTag() {
    DataField df = PicaDatafieldFactory.create("041A/01", "9", "104589787");

    RepetitionDao dao = new RepetitionDao(df.getTagWithOccurrence(), df.getDefinition());

    assertEquals("041A/01", dao.getExtendedTag());
  }

  @Test
  public void getFieldDefinition() {
    DataField df = PicaDatafieldFactory.create("041A/01", "9", "104589787");

    RepetitionDao dao = new RepetitionDao(df.getTagWithOccurrence(), df.getDefinition());

    assertEquals("041A", dao.getFieldDefinition().getTag());
  }

  @Test
  public void equals_no() {
    DataField df1 = PicaDatafieldFactory.create("041A/01", "9", "104589787");
    RepetitionDao dao1 = new RepetitionDao(df1.getTagWithOccurrence(), df1.getDefinition());

    DataField df2 = PicaDatafieldFactory.create("041A/02", "9", "104589787");
    RepetitionDao dao2 = new RepetitionDao(df2.getTagWithOccurrence(), df2.getDefinition());

    assertNotEquals(dao1, dao2);
    assertFalse(dao1.equals(dao2));
  }

  @Test
  public void equals_yes() {
    DataField df1 = PicaDatafieldFactory.create("041A/01", "9", "104589787");
    RepetitionDao dao1 = new RepetitionDao(df1.getTagWithOccurrence(), df1.getDefinition());

    DataField df2 = PicaDatafieldFactory.create("041A/01", "8", "104589787");
    RepetitionDao dao2 = new RepetitionDao(df2.getTagWithOccurrence(), df2.getDefinition());

    assertEquals(dao1, dao2);
    assertTrue(dao1.equals(dao2));
  }
}