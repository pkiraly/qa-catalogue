package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag245;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubfieldParserTest {

	@Test
	public void parseTester() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "00$kRecords,$f1939-1973$g1965-1972.");
		assertEquals("0", field.getInd1());
		assertEquals("0", field.getInd2());
		assertEquals("245", field.getTag());
		assertEquals("Records,", field.getSubfield("k").get(0).getValue());
		assertEquals("1939-1973", field.getSubfield("f").get(0).getValue());
		assertEquals("1965-1972.", field.getSubfield("g").get(0).getValue());
	}
}
