package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.tags01x.Tag040;
import de.gwdg.metadataqa.marc.definition.tags20x.Tag245;
import de.gwdg.metadataqa.marc.utils.SubfieldParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataFieldTest {

	@Test
	public void testMultiDefinition() {
		DataFieldDefinition tag040 = Tag040.getInstance();
		DataFieldDefinition tag245 = Tag245.getInstance();
		assertEquals("040", tag040.getTag());
		assertEquals("245", tag245.getTag());
	}

	@Test
	public void testMultiFields() {
		DataField tag245 = SubfieldParser.parseField(Tag245.getInstance(), "10$aAdvanced calculus.$pStudent handbook.");
		DataField tag040 = SubfieldParser.parseField(Tag040.getInstance(), "  $aMt$cMt");
		assertEquals("1", tag245.getInd1());
		assertEquals(" ", tag040.getInd1());
		assertEquals("Advanced calculus.", tag245.getSubfield("a").get(0).getValue());
		assertEquals("Mt", tag040.getSubfield("a").get(0).getValue());
	}
}
