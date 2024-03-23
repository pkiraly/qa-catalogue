package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class IndexingTest {

  @Test
  public void testIndexing710() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLinesFromResource("marctxt/010000011.mrctxt");
    BibliographicRecord marcRecord = MarcFactory.createFromFormattedText(lines);
    Map<String, List<String>> index = marcRecord.getKeyValuePairs(SolrFieldType.MIXED, MarcVersion.DNB);
    assertEquals(140, index.size());
    assertEquals(
      List.of("(DE-588)2013822-2", "(DE-627)101809441", "(DE-576)19168161X"),
      index.get("7100_AddedCorporateName_authorityRecordControlNumber"));
    assertEquals(
      List.of("2013822-2", "101809441", "19168161X"),
      index.get("7100_AddedCorporateName_authorityRecordControlNumber_recordNumber"));
    assertEquals(
      List.of("Gemeinsame Normdatei", "Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)"),
      index.get("7100_AddedCorporateName_authorityRecordControlNumber_organization"));
    assertEquals(
      List.of("DE-588", "DE-627", "DE-576"),
      index.get("7100_AddedCorporateName_authorityRecordControlNumber_organizationCode"));
  }

  @Test
  public void testSubfieldCode() throws IOException, URISyntaxException {
    Marc21Record marcRecord = new Marc21BibliographicRecord();
    marcRecord.setLeader("01445cem a22004454a 4500");
    marcRecord.setField("034", "0 $aa");
    assertEquals(1, marcRecord.getDatafieldsByTag("034").size());
    Map<String, List<String>> index = marcRecord.getKeyValuePairs(SolrFieldType.MIXED);
    assertEquals("Linear scale", index.get("034a_Scale_category").get(0));
  }

  @Test
  public void testPositions() throws IOException, URISyntaxException {
    Marc21Record marcRecord = new Marc21BibliographicRecord();
    marcRecord.setLeader("01445cem a22004454a 4500");
    marcRecord.setField("800", "0 $7aa");
    assertEquals(1, marcRecord.getDatafieldsByTag("800").size());
    Map<String, List<String>> index = marcRecord.getKeyValuePairs(SolrFieldType.MIXED);
    assertEquals("aa", index.get("8007_SeriesAddedPersonalName").get(0));
    assertEquals("Monographic component part", index.get("8007_SeriesAddedPersonalName_bibliographicLevel").get(0));
    assertEquals("Language material", index.get("8007_SeriesAddedPersonalName_typeOfRecord").get(0));
  }
}
