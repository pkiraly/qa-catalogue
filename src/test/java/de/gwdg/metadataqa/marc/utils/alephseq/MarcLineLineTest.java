package de.gwdg.metadataqa.marc.utils.alephseq;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MarcLineLineTest {

  @Test
  public void leaderV1() {
    String input = "LEADER 02191cam a2200541   4500";
    MarclineLine line = new MarclineLine(input);
    assertTrue(line.isValid());
    assertTrue(line.isLeader());
    assertEquals("02191cam a2200541   4500", line.getContent());
  }

  @Test
  public void leaderV2() {
    String input = "LDR 02191cam a2200541   4500";
    MarclineLine line = new MarclineLine(input);
    assertTrue(line.isValid());
    assertTrue(line.isLeader());
    assertEquals("02191cam a2200541   4500", line.getContent());
  }

  @Test
  public void leaderV3() {
    String input = "02191cam a2200541   4500";
    MarclineLine line = new MarclineLine(input);
    assertTrue(line.isValid());
    assertTrue(line.isLeader());
    assertEquals("02191cam a2200541   4500", line.getContent());
  }

  @Test
  public void controlFields() {
    List<String> inputs = List.of("001 010000011", "003 DE-627", "005 20180502143346.0", "007 tu", "008 861106s1985    xx |||||      10| ||ger c");
    for (String input : inputs) {
      MarclineLine line = new MarclineLine(input);
      assertTrue(line.isValid());
      assertTrue(!line.isLeader());
      assertTrue(line.isControlField());
      if (line.getTag().equals("001"))
        assertEquals("010000011", line.getContent());
      if (line.getTag().equals("003"))
        assertEquals("DE-627", line.getContent());
      if (line.getTag().equals("005"))
        assertEquals("20180502143346.0", line.getContent());
      if (line.getTag().equals("007"))
        assertEquals("tu", line.getContent());
      if (line.getTag().equals("008"))
        assertEquals("861106s1985    xx |||||      10| ||ger c", line.getContent());
    }
  }

  @Test
  public void dataFields() {
    List<String> inputs = List.of("035   $a(DE-627)010000011", "490 1 $aIWL-Forum$v1985,3");
    for (String input : inputs) {
      MarclineLine line = new MarclineLine(input);
      assertTrue(line.isValid());
      assertTrue(!line.isLeader());
      assertTrue(!line.isControlField());
      assertTrue(line.isNumericTag());
      if (line.getTag().equals("035")) {
        assertEquals(" ", line.getInd1());
        assertEquals(" ", line.getInd2());
        assertEquals("$a(DE-627)010000011", line.getContent());

        List<String[]> subfields = line.parseSubfields();
        assertEquals(1, subfields.size());
        assertEquals(2, subfields.get(0).length);
        assertEquals("a", subfields.get(0)[0]);
        assertEquals("(DE-627)010000011", subfields.get(0)[1]);
      }
    }
  }
}
