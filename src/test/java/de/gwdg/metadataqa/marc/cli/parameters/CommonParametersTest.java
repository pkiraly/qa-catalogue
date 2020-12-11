package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class CommonParametersTest {

  @Test
  public void testConstructor() {
    CommonParameters parameters = new CommonParameters();
    assertTrue(parameters.getIgnorableFields().isEmpty());
  }

  @Test
  public void testDefaults() {
    String[] arguments = new String[]{"a-marc-file.mrc"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertFalse(parameters.doHelp());

      assertTrue(parameters.doLog);

      assertNotNull(parameters.getArgs());
      assertEquals(1, parameters.getArgs().length);
      assertEquals("a-marc-file.mrc", parameters.getArgs()[0]);

    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testHelp() {
    String[] arguments = new String[]{"--help", "a-marc-file.mrc"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertTrue(parameters.doHelp());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testNoLog() {
    String[] arguments = new String[]{"--nolog", "a-marc-file.mrc"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertFalse(parameters.doLog());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test(expected = ParseException.class)
  public void testMarcVersionException() throws ParseException {
    String[] arguments = new String[]{"--marcVersion", "UNKNOWN"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertFalse(parameters.doLog());
    } catch (ParseException e) {
      assertEquals("Unrecognized marcVersion parameter value: 'UNKNOWN'", e.getMessage());
      throw e;
    }
  }

  @Test
  public void testIgnorableFields() throws ParseException {
    String[] arguments = new String[]{"--ignorableFields", "200,300"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertTrue(parameters.getIgnorableFields().contains("200"));
      assertTrue(parameters.getIgnorableFields().contains("300"));
    } catch (ParseException e) {
      throw e;
    }
  }

  @Test
  public void testIgnorableRecords() throws ParseException {
    String[] arguments = new String[]{"--ignorableRecords", "STA$s=SUPPRESSED"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertFalse(parameters.getIgnorableRecords().isEmpty());
    } catch (ParseException e) {
      throw e;
    }
  }

  @Test
  public void testTrimId() {
    String[] arguments = new String[]{"--trimId"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertTrue(parameters.getTrimId());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
