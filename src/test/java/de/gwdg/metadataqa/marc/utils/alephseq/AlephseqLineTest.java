package de.gwdg.metadataqa.marc.utils.alephseq;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AlephseqLineTest {

  @Test
  public void testShortLine() {
    String line = " ";
    AlephseqLine alephseqLine = new AlephseqLine(line);
    assertFalse(alephseqLine.isValid());
    assertNull(alephseqLine.getRecordID());
    assertNull(alephseqLine.getTag());
    assertNull(alephseqLine.getInd1());
    assertNull(alephseqLine.getInd2());
    assertNull(alephseqLine.getContent());

    assertFalse(alephseqLine.isLeader());
    assertFalse(alephseqLine.isControlField());
    assertFalse(alephseqLine.isNumericTag());
  }

  @Test
  public void getContent_with_L() {
    String line = "000000002 008   L 780804s1977^^^^enk||||||b||||001^0|eng||";
    AlephseqLine alephseqLine = new AlephseqLine(line, AlephseqLine.TYPE.WITH_L);
    assertEquals("780804s1977    enk||||||b||||001 0|eng||", alephseqLine.getContent());

    alephseqLine = new AlephseqLine(line, AlephseqLine.TYPE.WITHOUT_L);
    assertEquals("L 780804s1977    enk||||||b||||001 0|eng||", alephseqLine.getContent());
  }

  @Test
  public void getContent_without_L() {
    String line = "990017782740205171 041   $$aeng$$aara";
    AlephseqLine alephseqLine = new AlephseqLine(line, AlephseqLine.TYPE.WITHOUT_L);
    assertEquals("$aeng$aara", alephseqLine.getContent());
  }

  @Test
  public void getRawContent() {
    String line = "990017782740205171 041   $$aeng$$aara";
    AlephseqLine alephseqLine = new AlephseqLine(line, AlephseqLine.TYPE.WITHOUT_L);
    assertEquals("$$aeng$$aara", alephseqLine.getRawContent());
  }

  @Test
  public void getSubfields() {
    String line = "990017782740205171 041   $$aeng$$aara";
    AlephseqLine alephseqLine = new AlephseqLine(line, AlephseqLine.TYPE.WITHOUT_L);
    assertEquals(2, alephseqLine.getSubfields().size());
    assertEquals("a", alephseqLine.getSubfields().get(0)[0]);
    assertEquals("eng", alephseqLine.getSubfields().get(0)[1]);
    assertEquals("a", alephseqLine.getSubfields().get(1)[0]);
    assertEquals("ara", alephseqLine.getSubfields().get(1)[1]);
  }

  @Test
  public void serialization() {
    String line = "990017782740205171 041   $$aeng$$aara";
    AlephseqLine alephseqLine = new AlephseqLine(line, AlephseqLine.TYPE.WITHOUT_L);
    assertEquals("AlephseqLine{recordID='990017782740205171', tag='041', ind1=' ', ind2=' ', content='$aeng$aara'}", alephseqLine.toString());
  }

  @Test
  public void isValidTag() {
    AlephseqLine alephseqLine = new AlephseqLine("990017782740205171 041   $$aeng$$aara", AlephseqLine.TYPE.WITHOUT_L);
    assertTrue(alephseqLine.isValidTag());
  }

  @Test
  public void isValidTag_false() {
    AlephseqLine alephseqLine = new AlephseqLine("990017782740205171 FMT   $$aeng$$aara", AlephseqLine.TYPE.WITHOUT_L);
    assertFalse(alephseqLine.isValidTag());
  }
}
