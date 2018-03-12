package de.gwdg.metadataqa.marc;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class CodeTest {

	@Test
	public void testConstructor() {
		Code code = new Code("2", "Multiple surname");
		assertEquals("2", code.getCode());
		assertEquals("Multiple surname", code.getLabel());
	}

	@Test
	public void testBibframeTag() {
		Code code = new Code("0", "Issue number");
		code.setBibframeTag("AudioIssueNumber");
		assertEquals("AudioIssueNumber", code.getBibframeTag());
	}

	@Test
	public void testToString() {
		Code code = new Code("0", "Issue number");
		assertEquals("Code{code='0', label='Issue number'}", code.toString());
	}

	@Test
	public void testRange() {
		Code code = new Code("001-999", "Running time");
		assertFalse(code.isRange());
		code.setRange(true);
		assertTrue(code.isRange());
		Range range = code.getRange();
		assertNotNull(range);
		assertEquals("001-999", range.getRange());
		assertFalse(range.isValid("0"));
		assertFalse(range.isValid("1000"));
		assertTrue(range.isValid("1"));
		assertTrue(range.isValid("100"));
		assertTrue(range.isValid("999"));
		assertTrue(range.isValid("00999"));
	}

	@Test
	public void testRegex() {
		Code code = new Code("^\\d+$", "Running time");
		assertFalse(code.isRegex());
		code.setRegex(true);
		assertTrue(code.isRegex());
	}
}
