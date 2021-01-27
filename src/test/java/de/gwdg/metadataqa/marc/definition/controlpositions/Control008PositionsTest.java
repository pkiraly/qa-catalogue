package de.gwdg.metadataqa.marc.definition.controlpositions;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class Control008PositionsTest extends Control00XPositionsTest {

  @Test
  public void test() {
    for (String type : Control008Positions.getInstance().getPositions().keySet()) {
      List<ControlfieldPositionDefinition> subfields = Control008Positions.getInstance().get(type);
      testControlPositions(subfields);
      testGetControlField(subfields);
    }
  }

  private void testGetControlField(List<ControlfieldPositionDefinition> subfields) {
    for (ControlfieldPositionDefinition subfield : subfields) {
      assertTrue(
        String.format("%s: %s  should contain 008", subfield.getId(), subfield.getDescriptionUrl()),
        subfield.getDescriptionUrl().contains("008")
      );
      assertEquals("008", subfield.getId().substring(0, 3));
      assertEquals(subfield.getId(), "008", subfield.getControlField());
    }
  }

  protected boolean isException(ControlfieldPositionDefinition subfield, Code code) {
    return (
         (subfield.getId().equals("008map33") && code.getCode().equals("||"))
      || (subfield.getId().equals("008visual18") && code.getCode().equals("001-999"))
    );
  }
}
