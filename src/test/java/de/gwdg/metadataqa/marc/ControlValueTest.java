package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.*;
import org.apache.commons.lang3.StringUtils;
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
		assertTrue(StringUtils.join(value.getErrors(), "; "), value.validate());
	}

	@Test
	public void generateCode() {
		// List<ControlSubfield> subfields = Control006Subfields.get(Control008Type.MIXED_MATERIALS);
		List<ControlSubfield> subfields = LeaderSubfields.getSubfields();
		for (ControlSubfield subfield : subfields) {
			System.err.printf("===== [%s%s] ====\n", subfield.getId().substring(0, 1).toUpperCase(), subfield.getId().substring(1));
			System.err.printf("label = \"%s\";\n", subfield.getLabel());
			System.err.printf("id = \"%s\";\n", subfield.getId());
			System.err.printf("mqTag = \"%s\";\n", subfield.getMqTag());
			System.err.printf("positionStart = %d;\n", subfield.getPositionStart());
			System.err.printf("positionEnd = %d;\n", subfield.getPositionEnd());
			System.err.printf("descriptionUrl = \"https://www.loc.gov/marc/bibliographic/bdleader.html\";\n", subfield.getMqTag());
			if (subfield.getCodes() != null) {
				System.err.printf("codes = Utils.generateCodes(\n");
				int i = 0;
				for (Code code : subfield.getCodes()) {
					i++;
					if (i == subfield.getCodes().size())
						System.err.printf("\"%s\", \"%s\"\n", code.getCode(), code.getLabel());
					else
						System.err.printf("\"%s\", \"%s\",\n", code.getCode(), code.getLabel());
				}
				System.err.printf(");\n");
			}
			if (subfield.isRepeatableContent()) {
				System.err.printf("repeatableContent = true;\n", subfield.getPositionEnd());
				System.err.printf("unitLength = %d;\n", subfield.getUnitLength());
			}
			if (subfield.getDefaultCode() != null)
				System.err.printf("defaultCode = \"%s\";\n", subfield.getDefaultCode());
		}
	}
}
