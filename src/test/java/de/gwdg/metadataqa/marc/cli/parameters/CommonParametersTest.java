package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordFilterMarc21;
import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordFilterPica;
import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordIgnoratorMarc21;
import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordIgnoratorPica;
import de.gwdg.metadataqa.marc.definition.DataSource;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.utils.alephseq.AlephseqLine;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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
      assertFalse(parameters.getRecordIgnorator().isEmpty());
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
    Exception exception = assertThrows(ParseException.class, () -> new CommonParameters(arguments));
    assertEquals("Unrecognized alephseqLineType parameter value: 'WITHOUT'", exception.getMessage());
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
      String expected =
        "schemaType: MARC21\n" +
        "marcVersion: MARC21, MARC21\n" +
        "marcFormat: ISO, Binary (ISO 2709)\n" +
        "dataSource: FILE, from file\n" +
        "limit: -1\n" +
        "offset: -1\n" +
        "MARC files: \n" +
        "id: null\n" +
        "defaultRecordType: BOOKS\n" +
        "fixAlephseq: false\n" +
        "fixAlma: false\n" +
        "alephseq: false\n" +
        "marcxml: false\n" +
        "lineSeparated: false\n" +
        "outputDir: .\n" +
        "trimId: true\n" +
        "ignorableFields: \n" +
        "allowableRecords: \n" +
        "ignorableRecords: \n" +
        "defaultEncoding: null\n" +
        "alephseqLineType: null\n" +
        "groupBy: null\n" +
        "groupListFile: null\n" +
        "solrForScoresUrl: null\n";
      expected = expected.replaceAll("\n", System.lineSeparator());
      assertEquals(expected, parameters.formatParameters());
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in formatParameters()", e);
    }
  }

  @Test
  public void getSchemaType_default() {
    String[] arguments = new String[]{};
    CommonParameters parameters = null;
    try {
      parameters = new CommonParameters(arguments);
    } catch (ParseException e) {
    }
    assertEquals(SchemaType.MARC21, parameters.getSchemaType());
  }

  @Test
  public void getSchemaType_marc() {
    String[] arguments = new String[]{"--schemaType", "MARC21"};
    CommonParameters parameters = null;
    try {
      parameters = new CommonParameters(arguments);
    } catch (ParseException e) {
    }
    assertEquals(SchemaType.MARC21, parameters.getSchemaType());
  }

  @Test
  public void getSchemaType_pica() {
    String[] arguments = new String[]{"--schemaType", "PICA"};
    CommonParameters parameters = null;
    try {
      parameters = new CommonParameters(arguments);
    } catch (ParseException e) {
    }
    assertEquals(SchemaType.PICA, parameters.getSchemaType());
  }

  @Test
  public void getSchemaType_pica_wrong() {
    String[] arguments = new String[]{"--schemaType", "pica"};
    Exception exception = assertThrows(ParseException.class, () ->
      new CommonParameters(arguments)
    );
    assertEquals("Unrecognized schemaType parameter value: 'pica'", exception.getMessage());
  }

  @Test
  public void getPicaRecordType_default() {
    String[] arguments = new String[]{"--schemaType", "PICA"};
    CommonParameters parameters = null;
    try {
      parameters = new CommonParameters(arguments);
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in schemaType()", e);
    }
    assertEquals("002@$0", parameters.getPicaRecordTypeField());
  }

  @Test
  public void getPicaRecordType_set() {
    String[] arguments = new String[]{"--picaRecordType", "003$d"};
    CommonParameters parameters = null;
    try {
      parameters = new CommonParameters(arguments);
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in schemaType()", e);
    }
    assertEquals("003$d", parameters.getPicaRecordTypeField());
  }

  @Test
  public void getRecordIgnorator_pica() {
    String[] arguments = new String[]{"--schemaType", "PICA", "--ignorableRecords", "002@.0 !~ '^L'"};
    CommonParameters parameters = null;
    try {
      parameters = new CommonParameters(arguments);
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in schemaType()", e);
    }
    assertEquals("RecordIgnoratorPica", parameters.getRecordIgnorator().getClass().getSimpleName());
    RecordIgnoratorPica recordIgnorator = (RecordIgnoratorPica)parameters.getRecordIgnorator();
    assertNotNull(recordIgnorator.getBooleanCriteria());
    assertEquals("BooleanContainer{value='CriteriumPica{path=002@.0, operator=NOT_MATCH, value='^L'}'}",
      recordIgnorator.getBooleanCriteria().toString());
  }

  @Test
  public void getRecordIgnorator_marc21() {
    String[] arguments = new String[]{"--schemaType", "MARC21", "--ignorableRecords", "STA$a=SUPPRESSED"};
    CommonParameters parameters = null;
    try {
      parameters = new CommonParameters(arguments);
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in schemaType()", e);
    }
    assertEquals("RecordIgnoratorMarc21", parameters.getRecordIgnorator().getClass().getSimpleName());
    assertEquals(
      "[DataField{STA, ind1=' ', ind2=' ', subfields=[MarcSubfield{code='a', value='SUPPRESSED'}]}]",
      ((RecordIgnoratorMarc21)parameters.getRecordIgnorator()).toString());
  }

  @Test
  public void getRecordFilter_pica() {
    String[] arguments = new String[]{"--schemaType", "PICA", "--allowableRecords", "002@.0 !~ '^L'"};
    CommonParameters parameters = null;
    try {
      parameters = new CommonParameters(arguments);
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in schemaType()", e);
    }
    assertEquals("RecordFilterPica", parameters.getRecordFilter().getClass().getSimpleName());
    RecordFilterPica recordFilter = (RecordFilterPica)parameters.getRecordFilter();
    assertNotNull(recordFilter.getBooleanCriteria());
    assertEquals("BooleanContainer{value='CriteriumPica{path=002@.0, operator=NOT_MATCH, value='^L'}'}",
      recordFilter.getBooleanCriteria().toString());
  }

  @Test
  public void getRecordFilter_marc21() {
    String[] arguments = new String[]{"--schemaType", "MARC21", "--allowableRecords", "STA$a=SUPPRESSED"};
    CommonParameters parameters = null;
    try {
      parameters = new CommonParameters(arguments);
    } catch (ParseException e) {
      logger.log(Level.WARNING, "error in schemaType()", e);
    }
    assertEquals("RecordFilterMarc21", parameters.getRecordFilter().getClass().getSimpleName());
    assertEquals(
      "[DataField{STA, ind1=' ', ind2=' ', subfields=[MarcSubfield{code='a', value='SUPPRESSED'}]}]",
      ((RecordFilterMarc21)parameters.getRecordFilter()).toString());
  }

}
