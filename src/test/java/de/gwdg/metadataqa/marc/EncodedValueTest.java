package de.gwdg.metadataqa.marc;

import org.junit.Test;

import java.security.InvalidParameterException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class EncodedValueTest {

  @Test
  public void testConstructor() {
    EncodedValue code = new EncodedValue("2", "Multiple surname");
    assertEquals("2", code.getCode());
    assertEquals("Multiple surname", code.getLabel());
  }

  @Test
  public void testBibframeTag() {
    EncodedValue code = new EncodedValue("0", "Issue number");
    code.setBibframeTag("AudioIssueNumber");
    assertEquals("AudioIssueNumber", code.getBibframeTag());
  }

  @Test
  public void testToString() {
    EncodedValue code = new EncodedValue("0", "Issue number");
    assertEquals("Code{code='0', label='Issue number'}", code.toString());
  }

  @Test
  public void testRange() {
    EncodedValue code = new EncodedValue("001-999", "Running time");
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

  @Test(expected = InvalidParameterException.class)
  public void testInvalidRange() {
    EncodedValue code = new EncodedValue("001999", "Running time");
    code.setRange(true);
  }

  @Test
  public void testInvalidRangeValue() {
    EncodedValue code = new EncodedValue("001-999", "Running time");
    code.setRange(true);
    assertFalse(code.getRange().isValid("a"));
  }

  @Test
  public void testRegex() {
    EncodedValue code = new EncodedValue("^\\d+$", "Running time");
    assertFalse(code.isRegex());
    code.setRegex(true);
    assertTrue(code.isRegex());
  }
}
