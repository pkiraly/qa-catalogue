package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.Control003;
import de.gwdg.metadataqa.marc.dao.Control005;
import de.gwdg.metadataqa.marc.dao.Control008;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Marc21Leader;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag010;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag080;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class Marc21FilterTest {

  @Test
  public void equals() {
    Marc21Filter filter = new Marc21Filter();
    filter.parseInput("STA$a=SUPPRESSED");
    List<Condition> conditions = filter.getConditions();
    assertEquals(1, conditions.size());
    Condition condition = conditions.get(0);
    assertEquals("STA", condition.getTag());
    assertEquals("a", condition.getSubfield());
    assertEquals("SUPPRESSED", condition.getValue());
    assertEquals(Operator.EQUAL1, condition.getOperator());
  }

  @Test
  public void equals_space() {
    Marc21Filter filter = new Marc21Filter();
    filter.parseInput("STA$a = 'SUPPRESSED'");
    List<Condition> conditions = filter.getConditions();
    assertEquals(1, conditions.size());
    Condition condition = conditions.get(0);
    assertEquals("STA", condition.getTag());
    assertEquals("a", condition.getSubfield());
    assertEquals("SUPPRESSED", condition.getValue());
    assertEquals(Operator.EQUAL1, condition.getOperator());
  }

  @Test
  public void pattern() {
    Marc21Filter filter = new Marc21Filter();
    filter.parseInput("080$a =~ '^8*'");
    List<Condition> conditions = filter.getConditions();
    assertEquals(1, conditions.size());
    Condition condition = conditions.get(0);
    assertEquals("080", condition.getTag());
    assertEquals("a", condition.getSubfield());
    assertEquals("^8*", condition.getValue());
    assertEquals(Operator.MATCH, condition.getOperator());
  }

  @Test
  public void pattern_met() {
    Marc21Filter filter = new Marc21Filter();
    filter.parseInput("080$a =~ '^8'");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));
    assertTrue(filter.met(marcRecord));
  }
}