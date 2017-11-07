package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tag007nonprojected02Test {
	@Test
	public void test() {
		Tag007nonprojected02 tag = Tag007nonprojected02.getInstance();
		assertEquals("Color", tag.getLabel());
		assertEquals("tag007nonprojected03", tag.getId());
		assertEquals("color", tag.getMqTag());
		assertEquals(3, tag.getPositionStart());
		assertEquals(4, tag.getPositionEnd());
		assertEquals(8, tag.getCodes().size());
	}
}
