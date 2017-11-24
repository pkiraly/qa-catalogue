package de.gwdg.metadataqa.marc.definition.general.parser;

import de.gwdg.metadataqa.marc.definition.general.Linkage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;
import java.util.regex.Matcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LinkageParserTest {

	LinkageParser parser = LinkageParser.getInstance();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void test88001() throws ParserException {
		Linkage linkage = parser.create("880-01");
		assertEquals("880", linkage.getLinkingTag());
		assertEquals("01", linkage.getOccurrenceNumber());
	}

	@Test
	public void test10001N() throws ParserException {
		Linkage linkage = parser.create("100-01/(N");
		assertEquals("100", linkage.getLinkingTag());
		assertEquals("01", linkage.getOccurrenceNumber());
		assertEquals("(N", linkage.getScriptIdentificationCode());
		assertEquals("Cyrillic", linkage.resolveScriptIdentificationCode());
	}

	@Test
	public void test245031() throws ParserException {
		Linkage linkage = parser.create("245-03/$1");
		assertEquals("245", linkage.getLinkingTag());
		assertEquals("03", linkage.getOccurrenceNumber());
		assertEquals("$1", linkage.getScriptIdentificationCode());
		assertEquals("Chinese, Japanese, Korean", linkage.resolveScriptIdentificationCode());
	}

	@Test
	public void test10001B() throws ParserException {
		Linkage linkage = parser.create("100-01/(B");
		assertEquals("100", linkage.getLinkingTag());
		assertEquals("01", linkage.getOccurrenceNumber());
		assertEquals("(B", linkage.getScriptIdentificationCode());
		assertEquals("Latin", linkage.resolveScriptIdentificationCode());
	}

	@Test
	public void test530002r() throws ParserException {
		Linkage linkage = parser.create("530-00/(2/r");
		assertEquals("530", linkage.getLinkingTag());
		assertEquals("00", linkage.getOccurrenceNumber());
		assertEquals("(2", linkage.getScriptIdentificationCode());
		assertEquals("Hebrew", linkage.resolveScriptIdentificationCode());
		assertEquals("r", linkage.getFieldOrientationCode());
		assertEquals("right-to-left", linkage.resolveFieldOrientationCode());
	}


	@Test
	public void test24603() throws ParserException {
		Linkage linkage = parser.create("246-03");
		assertEquals("246", linkage.getLinkingTag());
		assertEquals("03", linkage.getOccurrenceNumber());
		assertEquals(null, linkage.getScriptIdentificationCode());
		assertEquals(null, linkage.resolveScriptIdentificationCode());
		assertEquals(null, linkage.getFieldOrientationCode());
		assertEquals(null, linkage.resolveFieldOrientationCode());
	}

	@Test
	public void test13001AndInvalidChar() throws ParserException {
		expectedEx.expect(ParserException.class);
		expectedEx.expectMessage("Invalid characters in '130-01\u200F': \\u200F");
		Map<String, String> linkage = parser.parse("130-01\u200F");
	}

	@Test
	public void test13001() throws ParserException {
		expectedEx.expect(ParserException.class);
		expectedEx.expectMessage("'13001' is not parsable");
		Map<String, String> linkage = parser.parse("13001");
	}

	@Test
	public void testCodeTransformation() throws ParserException {
		String codeWithWrongChar = "130-01\u200F";
		assertEquals(7, codeWithWrongChar.length());
		assertEquals('1', codeWithWrongChar.charAt(0));
		assertEquals("0031", String.format("%04X", Character.codePointAt(codeWithWrongChar, 0)));
		assertEquals('3', codeWithWrongChar.charAt(1));
		assertEquals("0033", String.format("%04X", Character.codePointAt(codeWithWrongChar, 1)));
		assertEquals('0', codeWithWrongChar.charAt(2));
		assertEquals("0030", String.format("%04X", Character.codePointAt(codeWithWrongChar, 2)));
		assertEquals('-', codeWithWrongChar.charAt(3));
		assertEquals("002D", String.format("%04X", Character.codePointAt(codeWithWrongChar, 3)));
		assertEquals('0', codeWithWrongChar.charAt(4));
		assertEquals("0030", String.format("%04X", Character.codePointAt(codeWithWrongChar, 4)));
		assertEquals('1', codeWithWrongChar.charAt(5));
		assertEquals("0031", String.format("%04X", Character.codePointAt(codeWithWrongChar, 5)));
		assertEquals('\u200F', codeWithWrongChar.charAt(6));
		assertEquals("200F", String.format("%04X", Character.codePointAt(codeWithWrongChar, 6)));
	}

	@Test
	public void testInvalidCharRegexp() throws ParserException {
		String codeWithWrongChar = "130-01\u200F";
		Matcher matcher = LinkageParser.INVALID_CHAR.matcher(codeWithWrongChar);
		assertTrue(matcher.find());
		assertEquals("200f", String.format("%04x", (int)matcher.group().charAt(0)));
	}

	@Test
	public void testInvalidCharException() throws ParserException {
		expectedEx.expect(ParserException.class);
		expectedEx.expectMessage("Invalid characters in '130-01\u200F': \\u200F");
		String codeWithWrongChar = "130-01\u200F";
		parser.create(codeWithWrongChar);
	}

}
