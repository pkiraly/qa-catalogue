package de.gwdg.metadataqa.marc.definition.controlpositions;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LeaderPositionsTest extends Control00XPositionsTest {

  @Test
  public void testPositions() {
    List<ControlfieldPositionDefinition> subfields = LeaderPositions.getInstance().getPositionList();
    testControlPositions(subfields);
    testGetControlField(subfields);
  }

  private void testGetControlField(List<ControlfieldPositionDefinition> subfields) {
    for (ControlfieldPositionDefinition subfield : subfields) {
      assertTrue(
        String.format("%s: %s  should contain leader", subfield.getId(), subfield.getDescriptionUrl()),
        subfield.getDescriptionUrl().contains("leader")
      );
      assertEquals("leader", subfield.getId().substring(0, 6));
      assertEquals(subfield.getId(), "Leader", subfield.getControlField());
    }
  }

  protected boolean isException(ControlfieldPositionDefinition subfield, EncodedValue code) {
    return (
         (subfield.getId().equals("006map16") && code.getCode().equals("||"))
      || (subfield.getId().equals("006visual01") && code.getCode().equals("001-999"))
    );
  }

}
