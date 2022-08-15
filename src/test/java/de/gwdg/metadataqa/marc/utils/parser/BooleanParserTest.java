package de.gwdg.metadataqa.marc.utils.parser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BooleanParserTest {

  @Test
  public void parse_ex1() {
    String input = "002@.0 !~ '^L' && 002@.0 !~ '^..[iktN]' && (002@.0 !~ '^.v' || 021A.a?)";
    BooleanContainer<String> root = BooleanParser.parse(input);
    assertNotNull(root);
    assertEquals(BooleanContainer.Op.AND, root.getOp());
    assertEquals(null, root.getValue());
    assertEquals(3, root.getChildren().size());
    assertEquals(null, root.getChildren().get(0).getOp());
    assertEquals("002@.0 !~ '^L'", root.getChildren().get(0).getValue());
    assertEquals(null, root.getChildren().get(1).getOp());
    assertEquals("002@.0 !~ '^..[iktN]'", root.getChildren().get(1).getValue());
    assertEquals(BooleanContainer.Op.OR, root.getChildren().get(2).getOp());
    assertEquals(2, root.getChildren().get(2).getChildren().size());
    assertEquals(null, root.getChildren().get(2).getChildren().get(0).getOp());
    assertEquals("002@.0 !~ '^.v'", root.getChildren().get(2).getChildren().get(0).getValue());
    assertEquals(null, root.getChildren().get(2).getChildren().get(1).getOp());
    assertEquals("021A.a?", root.getChildren().get(2).getChildren().get(1).getValue());
    assertEquals("BooleanContainer{op=AND, children=[BooleanContainer{value='002@.0 !~ '^L''}, BooleanContainer{value='002@.0 !~ '^..[iktN]''}, BooleanContainer{op=OR, children=[BooleanContainer{value='002@.0 !~ '^.v''}, BooleanContainer{value='021A.a?'}]}]}",
      root.toString());
    assertEquals(4, root.size());
  }

  @Test
  public void parse_ex2() {
    String input = "002@.0 !~ '^L'";
    BooleanContainer<String> root = BooleanParser.parse(input);
    assertNotNull(root);
    assertEquals("BooleanContainer{value='002@.0 !~ '^L''}",
      root.toString());
    assertEquals(1, root.size());
  }

  @Test
  public void parse_ex3() {
    String input = "002@.0 !~ \"^L\"";
    BooleanContainer<String> root = BooleanParser.parse(input);
    assertNotNull(root);
    assertEquals("BooleanContainer{value='002@.0 !~ \"^L\"'}",
      root.toString());
    assertEquals(1, root.size());
  }
}
