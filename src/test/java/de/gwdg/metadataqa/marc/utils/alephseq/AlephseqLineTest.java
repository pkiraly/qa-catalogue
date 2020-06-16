package de.gwdg.metadataqa.marc.utils.alephseq;

import de.gwdg.metadataqa.marc.utils.alephseq.AlephseqLine;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

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
}
