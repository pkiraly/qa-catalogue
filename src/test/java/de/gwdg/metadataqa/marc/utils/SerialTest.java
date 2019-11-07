package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerialTest {

  @Test
  public void test() {
    MarcRecord record = new MarcRecord("ocn655855524");
    record.setLeader(new Leader("02157cas a2200517Ka 4500"));
    record.setControl008(new Control008("100812c19359999ne.qx.p.o.....0...a0eng.d", record.getLeader().getType()));
    record.setControl006(new Control006("m.....o..d........", record.getLeader().getType()));
    record.addDataField(new DataField("245",
      "00$aActa biotheoretica :$bediderunt Directores Fundationis cui nomen est .Prof. Dr. Jan van der Hoeven-Stichting"
      + " voor Theoretische Biologie van Dier en Mench verbonden aan de Rijksuniversiteit te Leiden.."
    ));
    record.addDataField(new DataField("260", "  $aLeiden:$bBrill.$c1935-"));
    record.addDataField(new DataField("310", "  $a4 no. a year"));
    record.addDataField(new DataField("336", "  $atext$btxt$2rdacontent"));
    record.addDataField(new DataField("362", "0 $aVol. 1 (Dec. 1935)-"));
    record.addDataField(new DataField("588", "0 $aPrint version record."));
    record.addDataField(new DataField("022", "  $a0001-5342$l0001-5342$2z"));
    record.addDataField(new DataField("650", " 0$aBiology$vPeriodicals."));
    record.addDataField(new DataField("650", " 2$aBiology."));
    record.addDataField(new DataField("650", " 2$aZoology."));
    record.addDataField(new DataField("650", " 6$aBiologie$vPériodiques."));
    record.addDataField(new DataField("650", " 7$aBiology.$2fast$0(OCoLC)fst00832383"));
    record.addDataField(new DataField("650", "17$aBiologie.$2gtt"));
    record.addDataField(new DataField("650", "17$aTheorievorming.$2gtt"));
    assertEquals("text", record.getDatafield("336").get(0).getSubfield("a").get(0).getValue());

    assertEquals(Leader.Type.CONTINUING_RESOURCES, record.getType());

    Serial serial = new Serial(record);
    int score = serial.determineRecordQualityScore();
    assertEquals(14, score);
    assertEquals("[(enc-2,1), (006,1), (260,1), (310,1), (336,1), (332,1), (588,1), (subject,7)]", serial.getScores().toString());

  }
}
