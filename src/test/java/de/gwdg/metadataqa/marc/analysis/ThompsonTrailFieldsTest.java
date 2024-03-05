package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.analysis.thompsontraill.ThompsonTraillFields;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ThompsonTrailFieldsTest {

  @Test
  public void fields() {
    List<ThompsonTraillFields> classifications = Arrays.asList(
      ThompsonTraillFields.LC_NLM, ThompsonTraillFields.LOC,
      ThompsonTraillFields.MESH, ThompsonTraillFields.FAST,
      ThompsonTraillFields.GND, ThompsonTraillFields.OTHER
    );

    var fields = ThompsonTraillFields.values();
    assertEquals(22, fields.length);

    for (var field : fields) {
      if (classifications.contains(field))
        assertTrue(field.toString(), field.isClassification());
      else
        assertFalse(field.toString(), field.isClassification());
    }
  }
}
