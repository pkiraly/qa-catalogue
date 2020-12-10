package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class Tag985Test extends BLTagTest {

  public Tag985Test() {
    super(Tag985.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "ELECTRONIC ITEM. DO NOT MATCH/MERGE");
    validField("a", "CONFERENCE ANALYTIC RECORDS EXIST");
    validField("a", "Early Music Online project");
    validField("a", "Awaiting NETCAT record - item sent to shelf");
    validField("a", "100 not NACO");
    validField("a", "Corrected by MS generated file for WR# 14.056");
    validField("a", "Record merged by DQ team");
    validField("a", "LDLSCP record – no ISBN match on import to BLL01");
    validField("a", "LDLSCP record – potential duplicate on BLL01");
    validField("a", "957: ATBL");
    validField("a", "260 Parenti");
    validField("a", "245 & 260$a, $b: by analogy with editions in the catalogue." +
      " 260$c: from the title pages of the individual parts");
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("5", "B");
    invalidField("b", "Card,");
  }
}
