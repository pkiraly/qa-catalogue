package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.analysis.serial.Marc21Serial;
import de.gwdg.metadataqa.marc.analysis.serial.SerialFields;
import de.gwdg.metadataqa.marc.cli.SerialScore;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static de.gwdg.metadataqa.marc.cli.CliTestUtils.getTestResource;
import static org.junit.Assert.assertEquals;

public class SerialTest {

  private String outputDir;

  @Before
  public void setUp() throws Exception {
    outputDir = TestUtils.getPath("output");
  }

  @Test
  public void test() {
    Marc21BibliographicRecord marcRecord = new Marc21BibliographicRecord("ocn655855524");
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
    assertEquals("text", marcRecord.getDatafieldsByTag("336").get(0).getSubfield("a").get(0).getValue());

    assertEquals(MarcLeader.Type.CONTINUING_RESOURCES, marcRecord.getType());

    Marc21Serial serial = new Marc21Serial(marcRecord);
    List<Integer> scores = serial.determineRecordQualityScore();
    assertEquals(20, scores.size());
    assertEquals(20, serial.getScores().getScores().size());

    assertEquals("0,0,0,0,0,1,1,1,0,1,1,1,1,0,7,0,0,0,0,14", StringUtils.join(scores, ','));
    assertEquals(0, serial.getScores().get(SerialFields.ENCODING_LEVEL_FULL));
    assertEquals(1, serial.getScores().get(SerialFields.ENCODING_LEVEL_MINIMAL));
    assertEquals(1, serial.getScores().get(SerialFields.HAS_006));
    assertEquals(1, serial.getScores().get(SerialFields.HAS_PUBLISHER_260));
    assertEquals(1, serial.getScores().get(SerialFields.HAS_PUBLICATION_FREQUENCY_310));
    assertEquals(1, serial.getScores().get(SerialFields.HAS_CONTENT_TYPE_336));
    assertEquals(1, serial.getScores().get(SerialFields.HAS_DATES_OF_PUBLICATION_362));
    assertEquals(1, serial.getScores().get(SerialFields.HAS_SOURCE_OF_DESCRIPTION_588));
    assertEquals(7, serial.getScores().get(SerialFields.HAS_SUBJECT));
    assertEquals(14, serial.getScores().get(SerialFields.TOTAL));
    // assertEquals("[(enc-2,1), (006,1), (260,1), (310,1), (336,1), (332,1), (588,1), (subject,7)]", serial.getScores().toString());

  }

  @Test
  public void marcSerial_whenAlephSeq_RunsSuccessfully() throws IOException {
    String[] args = {
      "--schemaType", "MARC21",
      "--marcFormat", "ALEPHSEQ",
      "--marcVersion", "GENT",
      "--outputDir", outputDir,
      TestUtils.getPath("alephseq/alephseq-example2-continuing-resources.txt")
    };

    SerialScore.main(args);

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("alephseq/expected-results/serial-score/serial-score.csv"));
    File actualFile = new File(outputDir, "serial-score.csv");
    String expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void serial_whenUnimarc_RunsSuccessfully() throws IOException {
    String[] args = {
      "--schemaType", "UNIMARC",
      "--outputDir", outputDir,
      TestUtils.getPath("unimarc/serial.bnr.1993.mrc")
    };

    SerialScore.main(args);

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("unimarc/expected-results/serial-score/serial-score.csv"));
    File actualFile = new File(outputDir, "serial-score.csv");
    String expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }
}
