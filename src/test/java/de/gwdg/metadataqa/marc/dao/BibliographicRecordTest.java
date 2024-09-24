package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.definition.controltype.Control007Category;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BibliographicRecordTest {

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
    BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(records.get(0));

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
    assertEquals(expected, marcRecord.asJson());
  }

  @Test
  public void testSelect() throws Exception {
    Path path = FileUtils.getPath("general/0001-01.mrc");
    List<Record> records = ReadMarc.read(path.toString());
    BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(records.get(0));
    MarcSpec spec = new MarcSpec("008~0-5");
    List<String> results = marcRecord.select(spec);
    assertEquals(1, results.size());
    assertEquals("800108", results.get(0));

    spec = new MarcSpec("008~7-10");
    results = marcRecord.select(spec);
    assertEquals(1, results.size());
    assertEquals("1899", results.get(0));

    spec = new MarcSpec("008~0-1");
    results = marcRecord.select(spec);
    assertEquals(1, results.size());
    assertEquals("80", results.get(0));
  }

  @Test
  public void testMultiple007() throws Exception {
    Marc21Record marcRecord = new Marc21BibliographicRecord("010000011");
    marcRecord.setLeader(new Marc21Leader("00860cam a22002774a 45 0"));
    marcRecord.setControl003(new Control003("DE-627"));
    marcRecord.setControl005(new Control005("20180502143346.0"));
    marcRecord.setControl008(new Control008("861106s1985    xx |||||      10| ||ger c", marcRecord.getType()));
    marcRecord.setControl007(new Control007("tu"));
    marcRecord.setControl007(new Control007("at"));

    assertTrue(marcRecord.getControl007() instanceof List);
    assertEquals(2, marcRecord.getControl007().size());
    assertEquals("tu", marcRecord.getControl007().get(0).getContent());
    assertEquals(Control007Category.TEXT, marcRecord.getControl007().get(0).getCategory());
    assertEquals("at", marcRecord.getControl007().get(1).getContent());
    assertEquals(Control007Category.MAP, marcRecord.getControl007().get(1).getCategory());
  }

  @Test
  public void asJson() throws IOException, URISyntaxException {
    Path path = FileUtils.getPath("general/0001-01.mrc");
    List<Record> records = null;
    try {
      records = ReadMarc.read(path.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(records.get(0));
    assertNotNull(marcRecord);
    // System.err.println(record.asJson());
    assertTrue(marcRecord.asJson().contains("\"245\":[{\"ind1\":\"1\",\"ind2\":\"0\",\"subfields\":{\"a\":\"Botanical materia medica and pharmacology;\""));
  }

  @Test
  public void testFromMek() throws Exception {
    Path path = FileUtils.getPath("marc/22561.mrc");
    List<Record> records = ReadMarc.read(path.toString(), "MARC8");
    Marc21Record marcRecord = (Marc21Record) MarcFactory.createFromMarc4j(records.get(0));
    assertEquals(' ', records.get(0).getLeader().getCharCodingScheme());
    assertEquals(" ", ((Marc21Leader) marcRecord.getLeader()).getCharacterCodingScheme().getValue());
    assertEquals("Az ítélet :", marcRecord.getDatafieldsByTag("245").get(0).getSubfield("a").get(0).getValue());
  }

  @Test
  public void testFileReaderFromMek() throws Exception {
    Path path = FileUtils.getPath("marc/22561.mrc");
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.ISO, path.toString(), new CommonParameters(new String[]{"--defaultEncoding", "MARC8"}));
    Record record = reader.next();
    assertEquals(' ', record.getLeader().getCharCodingScheme());

    Marc21Record marcRecord = (Marc21Record) MarcFactory.createFromMarc4j(record);
    assertEquals(" ", ((Marc21Leader) marcRecord.getLeader()).getCharacterCodingScheme().getValue());
    assertEquals("Az ítélet :", marcRecord.getDatafieldsByTag("245").get(0).getSubfield("a").get(0).getValue());
    assertEquals("[Följegyzések és dokumentumok néhány magyarországi református egyházi döntésről 1948 és 1998 között] :", marcRecord.getDatafieldsByTag("245").get(0).getSubfield("b").get(0).getValue());
  }

}
