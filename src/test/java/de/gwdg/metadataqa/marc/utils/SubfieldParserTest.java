package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag245;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubfieldParserTest {

  @Test
  public void parseTester() {
    DataField field = SubfieldParser.parseField(Tag245.getInstance(), "00$kRecords,$f1939-1973$g1965-1972.");
    assertEquals("0", field.getInd1());
    assertEquals("0", field.getInd2());
    assertEquals("245", field.getTag());
    assertEquals("Records,", field.getSubfield("k").get(0).getValue());
    assertEquals("1939-1973", field.getSubfield("f").get(0).getValue());
    assertEquals("1965-1972.", field.getSubfield("g").get(0).getValue());
  }

  @Test
  public void validatorTest() {
    DataField field = SubfieldParser.parseField(Tag245.getInstance(), "14$aThe white dove /$cby William J. Locke.");
    assertEquals("1", field.getInd1());
    assertEquals("4", field.getInd2());
    assertEquals("245", field.getTag());
    assertEquals("The white dove /", field.getSubfield("a").get(0).getValue());
    assertEquals("by William J. Locke.", field.getSubfield("c").get(0).getValue());
    field.validate(null);
  }
}
