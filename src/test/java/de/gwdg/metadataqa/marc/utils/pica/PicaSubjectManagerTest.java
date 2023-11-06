package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.analysis.FieldWithScheme;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PicaSubjectManagerTest {

  @Test
  public void readFieldsWithScheme() {
    List<FieldWithScheme> fields = PicaSubjectManager.readFieldsWithScheme();
    assertEquals(40, fields.size());
    assertEquals(FieldWithScheme.class, fields.get(0).getClass());
    assertEquals("041A/00-99", fields.get(0).getTag());
    assertEquals("Schlagwortfolgen (DNB und Verbünde)", fields.get(0).getSchemaName());
  }
}