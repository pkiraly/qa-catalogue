package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static junit.framework.TestCase.*;

public class CommonParametersTest {

  private static final Logger logger = Logger.getLogger(CommonParametersTest.class.getCanonicalName());

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
      logger.log(Level.WARNING, "error in testDefaults()", e);
    }
  }

  @Test
  public void testHelp() {
    String[] arguments = new String[]{"--help", "a-marc-file.mrc"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertTrue(parameters.doHelp());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in testHelp()", e);
    }
  }

  @Test
  public void testNoLog() {
    String[] arguments = new String[]{"--nolog", "a-marc-file.mrc"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertFalse(parameters.doLog());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in testNoLog()", e);
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
      logger.log(Level.WARNING, "error in testTrimId()", e);
    }
  }

  @Test
  public void testDefaultEncoding() {
    String[] arguments = new String[]{"--defaultEncoding", "MARC8"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertEquals("MARC8", parameters.getDefaultEncoding());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in testTrimId()", e);
    }
  }

  @Test
  public void formatParameters() {
    String[] arguments = new String[]{"--trimId"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      String expected = "marcVersion: MARC21, MARC21\n" +
        "marcFormat: ISO, Binary (ISO 2709)\n" +
        "dataSource: FILE, from file\n" +
        "limit: -1\n" +
        "offset: -1\n" +
        "MARC files: \n" +
        "id: null\n" +
        "defaultRecordType: null\n" +
        "fixAlephseq: false\n" +
        "fixAlma: false\n" +
        "alephseq: false\n" +
        "marcxml: false\n" +
        "lineSeparated: false\n" +
        "outputDir: .\n" +
        "trimId: true\n" +
        "ignorableFields: \n" +
        "ignorableRecords: \n" +
        "defaultEncoding: null\n";
      assertEquals(expected, parameters.formatParameters());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in formatParameters()", e);
    }
  }
}
