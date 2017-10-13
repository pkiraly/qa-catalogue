package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Code;
import org.junit.Test;

import java.util.List;

public class Control007SubfieldsTest extends Control00XSubfieldsTest {
	@Test
	public void test() {
		for (Control007Category type : Control007Subfields.getSubfields().keySet()) {
			List<ControlSubfield> subfields = Control007Subfields.get(type);
			testControlSubfields(subfields);
		}
	}

	public boolean isException(ControlSubfield subfield, Code code) {
		return (
			   (subfield.getId().equals("tag007tactile03") && code.getCode().equals("||"))
			|| (subfield.getId().equals("tag007electro06") && code.getCode().equals("001-999"))
		);
	}
}
