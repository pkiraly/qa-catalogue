package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static junit.framework.TestCase.*;

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
      assertEquals("validation-report.txt", parameters.getDetailsFileName());
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
    String[] arguments = new String[]{"--fileName", "stdout", "a-marc-file.mrc"};
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
  public void testInValidFormat() {
    String[] arguments = new String[]{"--format", "iso", "a-marc-file.mrc"};
    try {
      ValidatorParameters parameters = new ValidatorParameters(arguments);
      assertEquals(ValidationErrorFormat.TEXT, parameters.getFormat());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
