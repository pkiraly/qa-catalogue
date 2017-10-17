package de.gwdg.metadataqa.marc.definition.general.parser;

import de.gwdg.metadataqa.marc.definition.general.Linkage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinkageParserTest {

	LinkageParser parser = LinkageParser.getInstance();

	@Test
	public void test88001() {
		Linkage linkage = parser.create("880-01");
		assertEquals("880", linkage.getLinkingTag());
		assertEquals("01", linkage.getOccurrenceNumber());
	}

	@Test
	public void test10001N() {
		Linkage linkage = parser.create("100-01/(N");
		assertEquals("100", linkage.getLinkingTag());
		assertEquals("01", linkage.getOccurrenceNumber());
		assertEquals("(N", linkage.getScriptIdentificationCode());
		assertEquals("Cyrillic", linkage.resolveScriptIdentificationCode());
	}

	@Test
	public void test245031() {
		Linkage linkage = parser.create("245-03/$1");
		assertEquals("245", linkage.getLinkingTag());
		assertEquals("03", linkage.getOccurrenceNumber());
		assertEquals("$1", linkage.getScriptIdentificationCode());
		assertEquals("Chinese, Japanese, Korean", linkage.resolveScriptIdentificationCode());
	}

	@Test
	public void test10001B() {
		Linkage linkage = parser.create("100-01/(B");
		assertEquals("100", linkage.getLinkingTag());
		assertEquals("01", linkage.getOccurrenceNumber());
		assertEquals("(B", linkage.getScriptIdentificationCode());
		assertEquals("Latin", linkage.resolveScriptIdentificationCode());
	}

	@Test
	public void test530002r() {
		Linkage linkage = parser.create("530-00/(2/r");
		assertEquals("530", linkage.getLinkingTag());
		assertEquals("00", linkage.getOccurrenceNumber());
		assertEquals("(2", linkage.getScriptIdentificationCode());
		assertEquals("Hebrew", linkage.resolveScriptIdentificationCode());
		assertEquals("r", linkage.getFieldOrientationCode());
		assertEquals("right-to-left", linkage.resolveFieldOrientationCode());
	}
}
