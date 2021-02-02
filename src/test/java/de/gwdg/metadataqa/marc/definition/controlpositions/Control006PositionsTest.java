package de.gwdg.metadataqa.marc.definition.controlpositions;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Control006PositionsTest extends Control00XPositionsTest {

  @Test
  public void testSubfields() {
    for (String type : Control006Positions.getInstance().getPositions().keySet()) {
      List<ControlfieldPositionDefinition> subfields = Control006Positions.getInstance().get(type);
      testControlPositions(subfields);
      testGetControlField(subfields);
    }
  }

  private void testGetControlField(List<ControlfieldPositionDefinition> subfields) {
    for (ControlfieldPositionDefinition subfield : subfields) {
      assertTrue(
        String.format("%s: %s  should contain 006", subfield.getId(), subfield.getDescriptionUrl()),
        subfield.getDescriptionUrl().contains("006")
      );
      assertEquals("006", subfield.getId().substring(0, 3));
      assertEquals(subfield.getId(), "006", subfield.getControlField());
    }
  }

  protected boolean isException(ControlfieldPositionDefinition subfield, Code code) {
    return (
         (subfield.getId().equals("006map16") && code.getCode().equals("||"))
      || (subfield.getId().equals("006visual01") && code.getCode().equals("001-999"))
    );
  }

}
