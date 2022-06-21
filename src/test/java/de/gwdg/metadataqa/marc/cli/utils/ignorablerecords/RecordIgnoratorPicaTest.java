package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RecordIgnoratorPicaTest {

  @Test
  public void parse_ex1() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 !~ '^L'");
    assertFalse(ignorator.isEmpty());
    List<CriteriumPica> criteria = ((RecordIgnoratorPica)ignorator).getCriteria();
    assertEquals(1, criteria.size());
    assertEquals("002@.0", criteria.get(0).getPath().getPath());
    assertEquals(Operator.DOESNT_MATCH, criteria.get(0).getOperator());
    assertEquals("^L", criteria.get(0).getValue());
  }

  @Test
  public void parse_ex2() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 !~ '^..[iktN]'");
    assertFalse(ignorator.isEmpty());
    List<CriteriumPica> criteria = ((RecordIgnoratorPica)ignorator).getCriteria();
    assertEquals(1, criteria.size());
    assertEquals("002@.0", criteria.get(0).getPath().getPath());
    assertEquals(Operator.DOESNT_MATCH, criteria.get(0).getOperator());
    assertEquals("^..[iktN]", criteria.get(0).getValue());
  }

  @Test
  public void parse_ex3() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 !~ '^.v'");
    assertFalse(ignorator.isEmpty());
    List<CriteriumPica> criteria = ((RecordIgnoratorPica)ignorator).getCriteria();
    assertEquals(1, criteria.size());
    assertEquals("002@.0", criteria.get(0).getPath().getPath());
    assertEquals(Operator.DOESNT_MATCH, criteria.get(0).getOperator());
    assertEquals("^.v", criteria.get(0).getValue());
  }

  @Test
  public void parse_ex4() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("021A.a?");
    assertFalse(ignorator.isEmpty());
    List<CriteriumPica> criteria = ((RecordIgnoratorPica)ignorator).getCriteria();
    assertEquals(1, criteria.size());
    assertEquals("021A.a", criteria.get(0).getPath().getPath());
    assertEquals(Operator.EXISTS, criteria.get(0).getOperator());
    assertEquals(null, criteria.get(0).getValue());
  }

  @Test
  public void parse_ex5() {
    RecordIgnorator ignorator = new RecordIgnoratorPica("002@.0 !~ '^L',002@.0 !~ '^..[iktN]',002@.0 !~ '^.v',021A.a?");
    assertFalse(ignorator.isEmpty());
    List<CriteriumPica> criteria = ((RecordIgnoratorPica)ignorator).getCriteria();
    assertEquals(4, criteria.size());

    assertEquals("002@.0", criteria.get(0).getPath().getPath());
    assertEquals(Operator.DOESNT_MATCH, criteria.get(0).getOperator());
    assertEquals("^L", criteria.get(0).getValue());

    assertEquals("021A.a", criteria.get(3).getPath().getPath());
    assertEquals(Operator.EXISTS, criteria.get(3).getOperator());
    assertEquals(null, criteria.get(3).getValue());
  }

  @Test
  public void isEmpty() {
    assertEquals(Operator.DOESNT_EQUAL, Operator.byCode("!="));
  }

  @Test
  public void isIgnorable() {
  }
}