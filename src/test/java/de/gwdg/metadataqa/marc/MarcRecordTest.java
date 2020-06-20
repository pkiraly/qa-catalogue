package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;
import org.junit.Test;
import org.marc4j.marc.Record;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class MarcRecordTest {

  private static final Pattern positionalPattern = Pattern.compile("^(Leader|00[678])/(.*)$");

  @Test
  public void test() {
    Matcher matcher = positionalPattern.matcher("006/0");
    assertTrue(matcher.matches());
  }

  @Test
  public void testFromFile() throws Exception {
    Path path = FileUtils.getPath("general/0001-01.mrc");
    List<Record> records = ReadMarc.read(path.toString());
    MarcRecord record = MarcFactory.createFromMarc4j(records.get(0));

    String expected = "{\"leader\":\"00720cam a22002051  4500\"," +
      "\"001\":\"   00000002 \"," +
      "\"003\":\"DLC\"," +
      "\"005\":\"20040505165105.0\"," +
      "\"008\":\"800108s1899    ilu           000 0 eng  \"," +
      "\"010\":[{\"ind1\":\" \",\"ind2\":\" \",\"subfields\":{" +
        "\"a\":\"   00000002 \"}}]," +
      "\"035\":[{\"ind1\":\" \",\"ind2\":\" \",\"subfields\":{" +
        "\"a\":\"(OCoLC)5853149\"}}]," +
      "\"040\":[{\"ind1\":\" \",\"ind2\":\" \",\"subfields\":{" +
        "\"a\":\"DLC\"," +
        "\"c\":\"DSI\"," +
        "\"d\":\"DLC\"}}]," +
      "\"050\":[{\"ind1\":\"0\",\"ind2\":\"0\",\"subfields\":{" +
        "\"a\":\"RX671\"," +
        "\"b\":\".A92\"}}]," +
      "\"100\":[{\"ind1\":\"1\",\"ind2\":\" \",\"subfields\":{" +
        "\"a\":\"Aurand, Samuel Herbert,\",\"d\":\"1854-\"}}]," +
      "\"245\":[{\"ind1\":\"1\",\"ind2\":\"0\",\"subfields\":{" +
        "\"a\":\"Botanical materia medica and pharmacology;\"," +
        "\"b\":\"drugs considered from a botanical, pharmaceutical, physiological, therapeutical and toxicological standpoint.\"," +
        "\"c\":\"By S. H. Aurand.\"}}]," +
      "\"260\":[{\"ind1\":\" \",\"ind2\":\" \",\"subfields\":{" +
        "\"a\":\"Chicago,\"," +
        "\"b\":\"P. H. Mallen Company,\"," +
        "\"c\":\"1899.\"}}]," +
      "\"300\":[{\"ind1\":\" \",\"ind2\":\" \",\"subfields\":{" +
        "\"a\":\"406 p.\"," +
        "\"c\":\"24 cm.\"}}]," +
      "\"500\":[{\"ind1\":\" \",\"ind2\":\" \",\"subfields\":{" +
        "\"a\":\"Homeopathic formulae.\"}}]," +
      "\"650\":[" +
        "{\"ind1\":\" \",\"ind2\":\"0\",\"subfields\":{" +
          "\"a\":\"Botany, Medical.\"}}," +
        "{\"ind1\":\" \",\"ind2\":\"0\",\"subfields\":{" +
          "\"a\":\"Homeopathy\"," +
          "\"x\":\"Materia medica and therapeutics.\"}}]}";
    assertEquals(expected, record.asJson());
  }

  @Test
  public void testSelect() throws Exception {
    Path path = FileUtils.getPath("general/0001-01.mrc");
    List<Record> records = ReadMarc.read(path.toString());
    MarcRecord record = MarcFactory.createFromMarc4j(records.get(0));
    MarcSpec spec = new MarcSpec("008~0-5");
    List<String> results = record.select(spec);
    assertEquals(1, results.size());
    assertEquals("800108", results.get(0));

    spec = new MarcSpec("008~7-10");
    results = record.select(spec);
    assertEquals(1, results.size());
    assertEquals("1899", results.get(0));

    spec = new MarcSpec("008~0-1");
    results = record.select(spec);
    assertEquals(1, results.size());
    assertEquals("80", results.get(0));
  }

}
