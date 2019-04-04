package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.controlsubfields.Control007Subfields;
import de.gwdg.metadataqa.marc.definition.controltype.Control007Category;
import de.gwdg.metadataqa.marc.definition.controltype.ControlType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Control007SubfieldsTest extends Control00XSubfieldsTest {
  @Test
  public void test() {
    for (String type : Control007Subfields.getInstance().getSubfields().keySet()) {
      List<ControlSubfieldDefinition> subfields = Control007Subfields.getInstance().get(type);
      testControlSubfields(subfields);
      testGetControlField(subfields);
    }
  }

  private void testGetControlField(List<ControlSubfieldDefinition> subfields) {
    for (ControlSubfieldDefinition subfield : subfields) {
      assertTrue(
        String.format("%s: %s  should contain 007", subfield.getId(), subfield.getDescriptionUrl()),
        subfield.getDescriptionUrl().contains("007")
      );
      assertEquals("tag007", subfield.getId().substring(0, 6));
      assertEquals(subfield.getId(), "007", subfield.getControlField());
    }
  }

  protected boolean isException(ControlSubfieldDefinition subfield, Code code) {
    return (
         (subfield.getId().equals("tag007tactile03") && code.getCode().equals("||"))
      || (subfield.getId().equals("tag007electro06") && code.getCode().equals("001-999"))
    );
  }
}
