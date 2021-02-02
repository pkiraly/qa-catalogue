package de.gwdg.metadataqa.marc.definition.controlpositions;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Control007PositionsTest extends Control00XPositionsTest {
  @Test
  public void test() {
    for (String type : Control007Positions.getInstance().getPositions().keySet()) {
      List<ControlfieldPositionDefinition> subfields = Control007Positions.getInstance().get(type);
      testControlPositions(subfields);
      testGetControlField(subfields);
    }
  }

  private void testGetControlField(List<ControlfieldPositionDefinition> subfields) {
    for (ControlfieldPositionDefinition subfield : subfields) {
      assertTrue(
        String.format("%s: %s  should contain 007", subfield.getId(), subfield.getDescriptionUrl()),
        subfield.getDescriptionUrl().contains("007")
      );
      assertEquals("007", subfield.getId().substring(0, 3));
      assertEquals(subfield.getId(), "007", subfield.getControlField());
    }
  }

  protected boolean isException(ControlfieldPositionDefinition subfield, Code code) {
    return (
         (subfield.getId().equals("007tactile03") && code.getCode().equals("||"))
      || (subfield.getId().equals("007electro06") && code.getCode().equals("001-999"))
    );
  }
}
