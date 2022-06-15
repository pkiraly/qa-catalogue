package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.definition.DataSource;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.utils.alephseq.AlephseqLine;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.utils.alephseq.AlephseqLine.TYPE.WITH_L;
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
  public void alephseqLineType_with_l() {
    String[] arguments = new String[]{"--alephseqLineType", "WITH_L"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertEquals(AlephseqLine.TYPE.WITH_L, parameters.getAlephseqLineType());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error ", e);
    }
  }

  @Test
  public void alephseqLineType_without_l() {
    String[] arguments = new String[]{"--alephseqLineType", "WITHOUT_L"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertEquals(AlephseqLine.TYPE.WITHOUT_L, parameters.getAlephseqLineType());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error ", e);
    }
  }

  @Test
  public void alephseqLineType_invalid1() {
    String[] arguments = new String[]{"--alephseqLineType", "WITHOUT"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertEquals(null, parameters.getAlephseqLineType());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error ", e);
    }
  }

  @Test
  public void alephseqLineType_invalid2() {
    String[] arguments = new String[]{"--alephseqLineType", "abc"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
    } catch (ParseException e) {
      assertEquals("Unrecognized alephseqLineType parameter value: 'abc'", e.getMessage());
    }
  }

  @Test
  public void picaIdField() {
    String[] arguments = new String[]{"--picaIdField", "003"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertEquals("003", parameters.getPicaIdField());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error ", e);
    }
  }

  @Test
  public void picaIdCode() {
    String[] arguments = new String[]{"--picaIdCode", "0"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertEquals("0", parameters.getPicaIdCode());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error ", e);
    }
  }

  @Test
  public void picaSubfieldSeparator() {
    String[] arguments = new String[]{"--picaSubfieldSeparator", "$"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertEquals("$", parameters.getPicaSubfieldSeparator());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error ", e);
    }
  }

  @Test
  public void getMarcFormat_pica_plain() {
    String[] arguments = new String[]{"--marcFormat", "PICA_PLAIN"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertEquals(MarcFormat.PICA_PLAIN, parameters.getMarcFormat());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error ", e);
    }
  }

  @Test
  public void getDataSource_file() {
    String[] arguments = new String[]{"--dataSource", "FILE"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
      assertEquals(DataSource.FILE, parameters.getDataSource());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error ", e);
    }
  }

  @Test
  public void getDataSource_file2() {
    String[] arguments = new String[]{"--dataSource", "file"};
    try {
      CommonParameters parameters = new CommonParameters(arguments);
    } catch (ParseException e) {
      assertEquals("Unrecognized marcFormat parameter value: 'file'", e.getMessage());
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
        "defaultEncoding: null\n" +
        "alephseqLineType: null\n" +
        "picaIdField: null\n" +
        "picaIdCode: null\n" +
        "picaSubfieldSeparator: null\n";
      assertEquals(expected, parameters.formatParameters());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in formatParameters()", e);
    }
  }
}
