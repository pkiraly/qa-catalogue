package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.parser.BooleanContainer;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RecordIgnoratorPicaTest {

  PicaSchemaManager schema = PicaSchemaReader.createSchema(getPathFromMain("pica/avram-k10plus.json"));

  @Test
  public void parse_ex1() {
    testParsing("002@.0 !~ '^L'", 1, "002@.0", Operator.NOT_MATCH, "^L");
  }

  @Test
  public void parse_ex2() {
    testParsing("002@.0 !~ '^..[iktN]'", 1, "002@.0", Operator.NOT_MATCH, "^..[iktN]");
  }

  @Test
  public void parse_ex3() {
    testParsing("002@.0 !~ '^.v'", 1, "002@.0", Operator.NOT_MATCH, "^.v");
  }

  @Test
  public void parse_ex4() {
    testParsing("021A.a?", 1, "021A.a", Operator.EXIST, null);
  }

  @Test
  public void parse_ex5() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 !~ '^L' && 002@.0 !~ '^..[iktN]' && (002@.0 !~ '^.v' || 021A.a?)");
    assertFalse(ignorator.isEmpty());
    BooleanContainer<CriteriumPica> container = ((RecordIgnoratorPica)ignorator).getBooleanCriteria();
    assertEquals(4, container.size());
    CriteriumPica criteria = container.getChildren().get(0).getValue();

    assertEquals("002@.0", criteria.getPath().getPath());
    assertEquals(Operator.NOT_MATCH, criteria.getOperator());
    assertEquals("^L", criteria.getValue());

    criteria = (CriteriumPica) container.getChildren().get(2).getChildren().get(1).getValue();
    assertEquals("021A.a", criteria.getPath().getPath());
    assertEquals(Operator.EXIST, criteria.getOperator());
    assertEquals(null, criteria.getValue());
  }

  @Test
  public void isIgnorable_ex1() {
    BibliographicRecord marcRecord = new Marc21Record("010000011");
    marcRecord.addDataField(new DataField(schema.lookup("002@"), " ", " ", "0", "L"));

    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 =~ '^L'");

    assertFalse(ignorator.isEmpty());
    assertTrue(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_ex2_1() {
    isIgnorable("abi", "002@.0 =~ '^..[iktN]'");
  }

  @Test
  public void isIgnorable_ex2_2() {
    isIgnorable("abk", "002@.0 =~ '^..[iktN]'");
  }

  @Test
  public void isIgnorable_ex2_3() {
    isIgnorable("abt", "002@.0 =~ '^..[iktN]'");
  }

  @Test
  public void isIgnorable_ex2_4() {
    isIgnorable("abN", "002@.0 =~ '^..[iktN]'");
  }

  @Test
  public void isIgnorable_ex2_5() {
    isIgnorableFailing("abM", "002@.0 =~ '^..[iktN]'");
  }

  @Test
  public void isIgnorable_ex1_reverse() {
    isIgnorableFailing("L", "002@.0 !~ '^L'");
  }

  @Test
  public void isIgnorable_match() {
    isIgnorable("L", "002@.0 == 'L'");
  }

  @Test
  public void isIgnorable_not_match() {
    isIgnorableFailing("L", "002@.0 != 'L'");
  }

  @Test
  public void isIgnorable_startsWith() {
    isIgnorable("pica", "002@.0 =^ 'pi'");
  }

  @Test
  public void isIgnorable_startsWith_reverse() {
    isIgnorableFailing("pica", "002@.0 =^ 'po'");
  }

  @Test
  public void isIgnorable_endsWith() {
    isIgnorable("pica", "002@.0 =$ 'ca'");
  }

  @Test
  public void isIgnorable_endsWith_not() {
    isIgnorableFailing("pica", "002@.0 =$ 'co'");
  }

  @Test
  public void isIgnorable_exists() {
    isIgnorable("pica", "002@.0?");
  }

  @Test
  public void isIgnorable_exists_not() {
    isIgnorableFailing("pica", "002@.b?");
  }

  @Test
  public void parse_boolean() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 !~ '^L' && 002@.0 !~ '^..[iktN]' && (002@.0 !~ '^v' || 021A.a?)");
    assertEquals(4, ((RecordIgnoratorPica)ignorator).getBooleanCriteria().size());
    assertEquals(BooleanContainer.Op.AND, ((RecordIgnoratorPica)ignorator).getBooleanCriteria().getOp());
    assertEquals("CriteriumPica{path=002@.0, operator=NOT_MATCH, value='^L'}", ((RecordIgnoratorPica)ignorator).getBooleanCriteria().getChildren().get(0).getValue().toString());
  }

  private String getPath(String fileName) {
    return Paths.get("src/test/resources/" + fileName).toAbsolutePath().toString();
  }

  private String getPathFromMain(String fileName) {
    return Paths.get("src/main/resources/" + fileName).toAbsolutePath().toString();
  }

  private void testParsing(String ignorableRecordsInput, int size, String path, Operator op, String value) {
    RecordIgnorator ignorator = new RecordIgnoratorPica(ignorableRecordsInput);
    assertFalse(ignorator.isEmpty());
    BooleanContainer criteria = ((RecordIgnoratorPica)ignorator).getBooleanCriteria();
    // List<CriteriumPica> criteria = ((RecordIgnoratorPica)ignorator).getCriteria();
    // assertEquals(size, criteria.size());
    assertEquals("CriteriumPica", criteria.getValue().getClass().getSimpleName());
    assertEquals(path, ((CriteriumPica)criteria.getValue()).getPath().getPath());
    assertEquals(op, ((CriteriumPica)criteria.getValue()).getOperator());
    assertEquals(value, ((CriteriumPica)criteria.getValue()).getValue());
  }

  private void isIgnorable(String abk, String ignorableRecordsInput) {
    BibliographicRecord marcRecord = new Marc21Record("010000011");
    marcRecord.addDataField(new DataField(schema.lookup("002@"), " ", " ", "0", abk));
    RecordIgnorator ignorator = new RecordIgnoratorPica(ignorableRecordsInput);
    assertTrue(ignorator.isIgnorable(marcRecord));
  }

  private void isIgnorableFailing(String abM, String ignorableRecordsInput) {
    BibliographicRecord marcRecord = new Marc21Record("010000011");
    marcRecord.addDataField(new DataField(schema.lookup("002@"), " ", " ", "0", abM));
    RecordIgnorator ignorator = new RecordIgnoratorPica(ignorableRecordsInput);
    assertFalse(ignorator.isIgnorable(marcRecord));
  }
}