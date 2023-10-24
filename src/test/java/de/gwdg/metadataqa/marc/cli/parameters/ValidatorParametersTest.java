package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.INDICATOR_NON_EMPTY;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.SUBFIELD_UNDEFINED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ValidatorParametersTest {

  @Test
  public void testDefaults() {
    String[] arguments = new String[]{"a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);

      assertNotNull(parameters.getArgs());
      assertEquals(1, parameters.getArgs().length);
      assertEquals("a-marc-file.mrc", parameters.getArgs()[0]);

      assertFalse(parameters.doHelp());

      assertNotNull(parameters.getDetailsFileName());
      assertEquals("issue-details.csv", parameters.getDetailsFileName());
      assertFalse(parameters.useStandardOutput());

      assertEquals(-1, parameters.getLimit());
      assertEquals(-1, parameters.getOffset());

      assertFalse(parameters.doSummary());

      assertEquals(ValidationErrorFormat.TEXT, parameters.getFormat());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testHelp() {
    String[] arguments = new String[]{"--help", "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);
      assertTrue(parameters.doHelp());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testStdOut() {
    String[] arguments = new String[]{"--detailsFileName", "stdout", "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);

      assertNotNull(parameters.getDetailsFileName());
      assertEquals("stdout", parameters.getDetailsFileName());
      assertTrue(parameters.useStandardOutput());

    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testLimit() {
    String[] arguments = new String[]{"--limit", "3", "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);
      assertEquals(3, parameters.getLimit());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testOffset() {
    String[] arguments = new String[]{"--offset", "3", "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);
      assertEquals(3, parameters.getOffset());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSummary() {
    String[] arguments = new String[]{"--summary", "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);
      assertTrue(parameters.doSummary());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testValidFormat() {
    String[] arguments = new String[]{"--format", "tab-separated", "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);
      assertEquals(ValidationErrorFormat.TAB_SEPARATED, parameters.getFormat());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testInvalidFormat() {
    String msg = null;
    ValidatorParameters parameters = null;
    String[] arguments = new String[]{"--format", "iso", "a-marc-file.mrc"};
    try {
      parameters = new ValidatorParameters(arguments);
    } catch (ParseException e) {
      msg = e.getMessage();
    }
    assertNull(parameters);
    assertNotNull(msg);
    assertEquals("Unrecognized ValidationErrorFormat parameter value: 'iso'", msg);
  }

  @Test
  public void getIgnorableIssueTypes_single() {
    String[] arguments = new String[]{"--ignorableIssueTypes", "undefinedSubfield", "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);
      assertNotNull(parameters.getIgnorableIssueTypes());
      assertEquals(1, parameters.getIgnorableIssueTypes().size());
      assertEquals(SUBFIELD_UNDEFINED, parameters.getIgnorableIssueTypes().get(0));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void getIgnorableIssueTypes_multiple() {
    String[] arguments = new String[]{"--ignorableIssueTypes", "undefinedSubfield,nonEmptyIndicator", "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);
      assertNotNull(parameters.getIgnorableIssueTypes());
      assertEquals(2, parameters.getIgnorableIssueTypes().size());
      assertEquals(SUBFIELD_UNDEFINED, parameters.getIgnorableIssueTypes().get(0));
      assertEquals(INDICATOR_NON_EMPTY, parameters.getIgnorableIssueTypes().get(1));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void getIgnorableIssueTypes_invalid() {
    String[] arguments = new String[]{"--ignorableIssueTypes", "undefinedSubfield2", "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);
      assertNotNull(parameters.getIgnorableIssueTypes());
      assertTrue(parameters.getIgnorableIssueTypes().isEmpty());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void empty_constructor() {
    ValidatorParameters parameters = new ValidatorParameters();
    assertNull(parameters.getIgnorableIssueTypes());
    assertFalse(parameters.doEmptyLargeCollectors());
  }

  @Test
  public void emptyLargeCollectors() {
    String[] arguments = new String[]{"--emptyLargeCollectors", "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);
      assertTrue(parameters.doEmptyLargeCollectors());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void complexConditions() {
    String msg = null;
    String[] arguments = new String[]{"--details", "--summary",  "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);
      assertFalse(parameters.doEmptyLargeCollectors());
    } catch (ParseException e) {
      msg = e.getMessage();
    }
    assertNull(msg);
  }

}
