package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.utils.pica.PicaFieldDefinition;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RecordIgnoratorPicaTest {

  Map<String, PicaFieldDefinition> schema = PicaSchemaReader.create(getPath("pica/avram-k10plus.json"));


  @Test
  public void parse_ex1() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 !~ '^L'");
    assertFalse(ignorator.isEmpty());
    List<CriteriumPica> criteria = ((RecordIgnoratorPica)ignorator).getCriteria();
    assertEquals(1, criteria.size());
    assertEquals("002@.0", criteria.get(0).getPath().getPath());
    assertEquals(Operator.NOT_MATCH, criteria.get(0).getOperator());
    assertEquals("^L", criteria.get(0).getValue());
  }

  @Test
  public void parse_ex2() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 !~ '^..[iktN]'");
    assertFalse(ignorator.isEmpty());
    List<CriteriumPica> criteria = ((RecordIgnoratorPica)ignorator).getCriteria();
    assertEquals(1, criteria.size());
    assertEquals("002@.0", criteria.get(0).getPath().getPath());
    assertEquals(Operator.NOT_MATCH, criteria.get(0).getOperator());
    assertEquals("^..[iktN]", criteria.get(0).getValue());
  }

  @Test
  public void parse_ex3() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 !~ '^.v'");
    assertFalse(ignorator.isEmpty());
    List<CriteriumPica> criteria = ((RecordIgnoratorPica)ignorator).getCriteria();
    assertEquals(1, criteria.size());
    assertEquals("002@.0", criteria.get(0).getPath().getPath());
    assertEquals(Operator.NOT_MATCH, criteria.get(0).getOperator());
    assertEquals("^.v", criteria.get(0).getValue());
  }

  @Test
  public void parse_ex4() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("021A.a?");
    assertFalse(ignorator.isEmpty());
    List<CriteriumPica> criteria = ((RecordIgnoratorPica)ignorator).getCriteria();
    assertEquals(1, criteria.size());
    assertEquals("021A.a", criteria.get(0).getPath().getPath());
    assertEquals(Operator.EXIST, criteria.get(0).getOperator());
    assertEquals(null, criteria.get(0).getValue());
  }

  @Test
  public void parse_ex5() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 !~ '^L',002@.0 !~ '^..[iktN]',002@.0 !~ '^.v',021A.a?");
    assertFalse(ignorator.isEmpty());
    List<CriteriumPica> criteria = ((RecordIgnoratorPica)ignorator).getCriteria();
    assertEquals(4, criteria.size());

    assertEquals("002@.0", criteria.get(0).getPath().getPath());
    assertEquals(Operator.NOT_MATCH, criteria.get(0).getOperator());
    assertEquals("^L", criteria.get(0).getValue());

    assertEquals("021A.a", criteria.get(3).getPath().getPath());
    assertEquals(Operator.EXIST, criteria.get(3).getOperator());
    assertEquals(null, criteria.get(3).getValue());
  }

  @Test
  public void isIgnorable_ex1() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "L"));

    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 =~ '^L'");

    assertFalse(ignorator.isEmpty());
    assertTrue(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_ex2_1() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "abi"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 =~ '^..[iktN]'");
    assertTrue(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_ex2_2() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "abk"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 =~ '^..[iktN]'");
    assertTrue(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_ex2_3() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "abt"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 =~ '^..[iktN]'");
    assertTrue(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_ex2_4() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "abN"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 =~ '^..[iktN]'");
    assertTrue(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_ex2_5() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "abM"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 =~ '^..[iktN]'");
    assertFalse(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_ex1_reverse() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "L"));

    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 !~ '^L'");

    assertFalse(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_match() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "L"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 == 'L'");
    assertTrue(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_not_match() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "L"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 != 'L'");
    assertFalse(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_startsWith() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "pica"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 =^ 'pi'");
    assertTrue(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_startsWith_reverse() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "pica"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 =^ 'po'");
    assertFalse(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_endsWith() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "pica"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 =$ 'ca'");
    assertTrue(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_endsWith_not() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "pica"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 =$ 'co'");
    assertFalse(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_exists() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "pica"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0?");
    assertTrue(ignorator.isIgnorable(marcRecord));
  }

  @Test
  public void isIgnorable_exists_not() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.addDataField(new DataField(schema.get("002@"), " ", " ", "0", "pica"));
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.b?");
    assertFalse(ignorator.isIgnorable(marcRecord));
  }

  private String getPath(String fileName) {
    return Paths.get("src/test/resources/" + fileName).toAbsolutePath().toString();
  }
}