package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SerialTest {

  @Test
  public void test() {
    MarcRecord marcRecord = new MarcRecord("ocn655855524");
    marcRecord.setLeader("02157cas a2200517Ka 4500");
    marcRecord.setField("008", "100812c19359999ne.qx.p.o.....0...a0eng.d");
    marcRecord.setField("006", "m.....o..d........");
    marcRecord.setField("245",
      "00$aActa biotheoretica :$bediderunt Directores Fundationis cui nomen est .Prof. Dr. Jan van der Hoeven-Stichting"
      + " voor Theoretische Biologie van Dier en Mench verbonden aan de Rijksuniversiteit te Leiden.."
    );
    marcRecord.setField("260", "  $aLeiden:$bBrill.$c1935-");
    marcRecord.setField("310", "  $a4 no. a year");
    marcRecord.setField("336", "  $atext$btxt$2rdacontent");
    marcRecord.setField("362", "0 $aVol. 1 (Dec. 1935)-");
    marcRecord.setField("588", "0 $aPrint version record.");
    marcRecord.setField("022", "  $a0001-5342$l0001-5342$2z");
    marcRecord.setField("650", " 0$aBiology$vPeriodicals.");
    marcRecord.setField("650", " 2$aBiology.");
    marcRecord.setField("650", " 2$aZoology.");
    marcRecord.setField("650", " 6$aBiologie$vPériodiques.");
    marcRecord.setField("650", " 7$aBiology.$2fast$0(OCoLC)fst00832383");
    marcRecord.setField("650", "17$aBiologie.$2gtt");
    marcRecord.setField("650", "17$aTheorievorming.$2gtt");
    assertEquals("text", marcRecord.getDatafield("336").get(0).getSubfield("a").get(0).getValue());

    assertEquals(Leader.Type.CONTINUING_RESOURCES, marcRecord.getType());

    Serial serial = new Serial(marcRecord);
    List<Integer> scores = serial.determineRecordQualityScore();
    assertEquals(19, scores.size());
    assertEquals(19, serial.getScores().getScores().size());

    assertEquals("0,0,0,0,0,1,1,1,0,1,1,1,1,0,7,0,0,0,14", StringUtils.join(scores, ','));
    assertEquals(0, serial.getScores().get(SerialFields.EncodingLevelFull));
    assertEquals(1, serial.getScores().get(SerialFields.EncodingLevelMinimal));
    assertEquals(1, serial.getScores().get(SerialFields.Has006));
    assertEquals(1, serial.getScores().get(SerialFields.HasPublisher260));
    assertEquals(1, serial.getScores().get(SerialFields.HasPublicationFrequency310));
    assertEquals(1, serial.getScores().get(SerialFields.HasContentType336));
    assertEquals(1, serial.getScores().get(SerialFields.HasDatesOfPublication362));
    assertEquals(1, serial.getScores().get(SerialFields.HasSourceOfDescription588));
    assertEquals(7, serial.getScores().get(SerialFields.HasSubject));
    assertEquals(14, serial.getScores().get(SerialFields.TOTAL));
    // assertEquals("[(enc-2,1), (006,1), (260,1), (310,1), (336,1), (332,1), (588,1), (subject,7)]", serial.getScores().toString());

  }
}
