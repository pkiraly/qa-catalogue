package de.gwdg.metadataqa.marc;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Control001Test {

	@Test
	public void testConstructor() {
		Control001 field = new Control001("010000178");
		assertEquals(0, field.getMap().size());
		assertEquals("", StringUtils.join(field.getSubfieldPositions(), ", "));

		assertEquals(1, field.getKeyValuePairs().size());
		assertEquals(1, field.getKeyValuePairs().get("001").size());
		assertEquals("010000178", field.getKeyValuePairs().get("001").get(0));
	}
}
