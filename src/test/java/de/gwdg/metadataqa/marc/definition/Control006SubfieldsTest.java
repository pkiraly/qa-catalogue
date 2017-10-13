package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Code;
import org.junit.Test;

import java.util.List;

public class Control006SubfieldsTest extends Control00XSubfieldsTest {

	@Test
	public void test() {
		for (Control008Type type : Control006Subfields.getSubfields().keySet()) {
			List<ControlSubfield> subfields = Control006Subfields.get(type);
			testControlSubfields(subfields);
		}
	}

	public boolean isException(ControlSubfield subfield, Code code) {
		return (
			   (subfield.getId().equals("tag006map16") && code.getCode().equals("||"))
			|| (subfield.getId().equals("tag006visual01") && code.getCode().equals("001-999"))
		);
	}

}
