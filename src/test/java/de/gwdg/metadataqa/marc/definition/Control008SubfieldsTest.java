package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Code;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class Control008SubfieldsTest extends Control00XSubfieldsTest {

	@Test
	public void test() {
		for (Control008Type type : Control008Subfields.getSubfields().keySet()) {
			List<ControlSubfield> subfields = Control008Subfields.get(type);
			testControlSubfields(subfields);
		}
	}

	public boolean isException(ControlSubfield subfield, Code code) {
		return (
			   (subfield.getId().equals("tag008map33") && code.getCode().equals("||"))
			|| (subfield.getId().equals("tag008visual18") && code.getCode().equals("001-999"))
		);
	}
}
