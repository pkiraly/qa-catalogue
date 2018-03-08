package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.controlsubfields.Control008Subfields;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;
import de.gwdg.metadataqa.marc.definition.controltype.ControlType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class Control008SubfieldsTest extends Control00XSubfieldsTest {

	@Test
	public void test() {
		for (ControlType type : Control008Subfields.getSubfields().keySet()) {
			List<ControlSubfieldDefinition> subfields = Control008Subfields.get(type);
			testControlSubfields(subfields);
			testGetControlField(subfields);
		}
	}

	private void testGetControlField(List<ControlSubfieldDefinition> subfields) {
		for (ControlSubfieldDefinition subfield : subfields) {
			assertTrue(
				String.format("%s: %s  should contain 008", subfield.getId(), subfield.getDescriptionUrl()),
				subfield.getDescriptionUrl().contains("008")
			);
			assertEquals("tag008", subfield.getId().substring(0, 6));
			assertEquals(subfield.getId(), "008", subfield.getControlField());
		}
	}

	protected boolean isException(ControlSubfieldDefinition subfield, Code code) {
		return (
			   (subfield.getId().equals("tag008map33") && code.getCode().equals("||"))
			|| (subfield.getId().equals("tag008visual18") && code.getCode().equals("001-999"))
		);
	}
}
