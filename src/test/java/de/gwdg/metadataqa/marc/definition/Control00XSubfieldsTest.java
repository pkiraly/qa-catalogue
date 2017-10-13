package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Code;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public abstract class Control00XSubfieldsTest {

	public void testControlSubfields(List<ControlSubfield> subfields) {
		int lastEnd = -1;
		for (ControlSubfield subfield : subfields) {
			assertTrue(subfield.getPositionStart() < subfield.getPositionEnd());

			if (lastEnd != -1)
				assertTrue(lastEnd <= subfield.getPositionStart());
			lastEnd = subfield.getPositionStart();
			assertTrue(subfield.getId().endsWith(String.valueOf(subfield.getPositionStart())));

			int length = subfield.getPositionEnd() - subfield.getPositionStart();

			if (subfield.isRepeatableContent()) {
				assertNotEquals(-1, subfield.getUnitLength());
				int unitLength = subfield.getUnitLength();
				assertNotEquals(length, unitLength);
				assertEquals(0, length % unitLength);
				length = unitLength;
			}

			if (subfield.getUnitLength() > -1)
				assertTrue(subfield.isRepeatableContent());

			if (subfield.getCodes() != null)
				for (Code code : subfield.getCodes())
					if (!isException(subfield, code))
						assertEquals(
							String.format(
								"field: %s, code: '%s' (length should be %d)\n",
								subfield.getId(), code.getCode(), length
							),
							code.getCode().length(), length
						);
		}
	}

	abstract boolean isException(ControlSubfield subfield, Code code);
}
