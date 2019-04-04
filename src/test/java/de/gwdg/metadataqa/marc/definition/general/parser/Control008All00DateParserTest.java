package de.gwdg.metadataqa.marc.definition.general.parser;

import org.junit.Test;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Control008All00DateParserTest {

  @Test
  public void test730812() throws ParserException {
    SubfieldContentParser parser = new Control008All00DateParser();

    Map<String, String> extra = parser.parse("730812");
    assertEquals("1973-08-12", extra.get("normalized"));
  }

  @Test(expected = ParserException.class)
  public void test88001() throws ParserException {
    SubfieldContentParser parser = new Control008All00DateParser();
    Map<String, String> extra = parser.parse("730842");
    assertEquals("1973-08-12", extra.get("normalized"));
  }

}
