package de.gwdg.metadataqa.marc.utils.alephseq;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MarcMakerLineTest {

  @Test
  public void leaderV1() {
    String input = "=000  00000nam\\\\2200000\\a\\4500\n";
    MarcMakerLine line = new MarcMakerLine(input);
    assertTrue(line.isValid());
    assertTrue(line.isLeader());
    assertEquals("00000nam  2200000 a 4500", line.getContent());
  }

  @Test
  public void leaderV2() {
    String input = "=LDR  00251nas a2200121 c 4500";
    MarcMakerLine line = new MarcMakerLine(input);
    assertTrue(line.isValid());
    assertTrue(line.isLeader());
    assertEquals("00251nas a2200121 c 4500", line.getContent());
  }

  @Test
  public void controlFields() {
    Map<String, List<String>> inputs = Map.of(
      "=001  987874829", List.of("001", "987874829"),
      "=007  cr||||||||||||", List.of("007", "cr||||||||||||"),
      "=001  0123456789", List.of("001", "0123456789"),
      "=003  NjP", List.of("003", "NjP"),
      "=005  19930422091534.7", List.of("005", "19930422091534.7"),
      "=008  920806s1991\\\\\\\\nju\\\\\\\\\\\\\\\\\\\\\\000\\0\\\\eng\\d", List.of("008", "920806s1991    nju           000 0  eng d"),
      "=001  rb1993000850", List.of("001", "rb1993000850"),
      "=003  VaArRKB", List.of("003", "VaArRKB"),
      "=005  19930903074500.0", List.of("005", "19930903074500.0"),
      "=008  930903s1987nyu|||\\\\\\|\\\\\\\\\\\\\\\\\\\\eng\\d", List.of("008", "930903s1987nyu|||   |          eng d")
    );
    for (Map.Entry<String, List<String>> entry : inputs.entrySet()) {
      String input = entry.getKey();
      String tag = entry.getValue().get(0);
      String value = entry.getValue().get(1);
      MarcMakerLine line = new MarcMakerLine(input);
      assertTrue(line.isValid());
      assertTrue(!line.isLeader());
      assertTrue(line.isControlField());
      assertEquals(tag, line.getTag());
      assertEquals(value, line.getContent());
    }
  }

  @Test
  public void dataFields() {
    Map<String, List<String>> inputs = Map.of(
      "=022  \\\\$a1940-5758", List.of("022", " ", " ", "$a1940-5758", "a", "1940-5758"),
      "=041  \\\\$aeng", List.of("041", " ", " ", "$aeng", "a", "eng"),
      "=245  00$aCode4Lib journal$bC4LJ", List.of("245", "0", "0", "$aCode4Lib journal$bC4LJ", "a", "Code4Lib journal", "b", "C4LJ")
    );
    for (Map.Entry<String, List<String>> entry : inputs.entrySet()) {
      String input = entry.getKey();
      String tag = entry.getValue().get(0);
      String ind1 = entry.getValue().get(1);
      String ind2 = entry.getValue().get(2);
      String value = entry.getValue().get(3);
      List<String> subfields = entry.getValue().subList(4, entry.getValue().size());
      MarcMakerLine line = new MarcMakerLine(input);
      assertTrue(line.isValid());
      assertTrue(!line.isLeader());
      assertTrue(!line.isControlField());
      assertTrue(line.isNumericTag());
      assertEquals(tag, line.getTag());
      assertEquals(ind1, line.getInd1());
      assertEquals(ind2, line.getInd2());
      assertEquals(value, line.getContent());

      List<String[]> parsedSubfields = line.parseSubfields();
      assertEquals(subfields.size() / 2, parsedSubfields.size());
      assertEquals(2, parsedSubfields.get(0).length);
      for (int i = 0; i < parsedSubfields.size(); i++) {
        assertEquals(subfields.get(i * 2), parsedSubfields.get(i)[0]);
        assertEquals(subfields.get((i * 2) + 1), parsedSubfields.get(i)[1]);
      }

    }
  }

  @Test
  public void textLine() {
    MarcMakerLine line = new MarcMakerLine(" ; with an introduction and commentary by Spencer Yarborough.");
    assertEquals(null, line.getTag());
    assertEquals("; with an introduction and commentary by Spencer Yarborough.", line.getContent());
  }
}
