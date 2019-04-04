package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.controlsubfields.Control006Subfields;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;
import de.gwdg.metadataqa.marc.definition.controltype.ControlType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Control006SubfieldsTest extends Control00XSubfieldsTest {

  @Test
  public void testSubfields() {
    for (String type : Control006Subfields.getInstance().getSubfields().keySet()) {
      List<ControlSubfieldDefinition> subfields = Control006Subfields.getInstance().get(type);
      testControlSubfields(subfields);
      testGetControlField(subfields);
    }
  }

  private void testGetControlField(List<ControlSubfieldDefinition> subfields) {
    for (ControlSubfieldDefinition subfield : subfields) {
      assertTrue(
        String.format("%s: %s  should contain 006", subfield.getId(), subfield.getDescriptionUrl()),
        subfield.getDescriptionUrl().contains("006")
      );
      assertEquals("tag006", subfield.getId().substring(0, 6));
      assertEquals(subfield.getId(), "006", subfield.getControlField());
    }
  }

  protected boolean isException(ControlSubfieldDefinition subfield, Code code) {
    return (
         (subfield.getId().equals("tag006map16") && code.getCode().equals("||"))
      || (subfield.getId().equals("tag006visual01") && code.getCode().equals("001-999"))
    );
  }

}
