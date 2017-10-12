package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.Control008Subfields;
import de.gwdg.metadataqa.marc.definition.Control008Type;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ControlValueTest {

	@Test
	public void testValidate() {
		List<ControlSubfield> subfields = Control008Subfields.get(Control008Type.BOOKS);
		ControlSubfield subfield = null;
		for (ControlSubfield _subfield : subfields) {
			if (_subfield.getId().equals("tag008book18")) {
				subfield = _subfield;
				break;
			}
		}
		ControlValue value = new ControlValue(subfield, "af  ");
		assertTrue(value.validate());
	}
}
