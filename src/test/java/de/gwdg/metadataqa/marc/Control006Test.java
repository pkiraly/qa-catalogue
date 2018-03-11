package de.gwdg.metadataqa.marc;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Control006Test {

	@Test
	public void testSa() {
		Control006 field = new Control006("sa", Leader.Type.CONTINUING_RESOURCES);
		assertEquals(2, field.getMap().size());
		assertEquals("0, 1", StringUtils.join(field.getSubfieldPositions(), ", "));
	}
}
