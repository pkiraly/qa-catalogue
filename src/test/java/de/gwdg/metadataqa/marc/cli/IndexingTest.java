package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
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
    MarcRecord marcRecord = MarcFactory.createFromFormattedText(lines);
    Map<String, List<String>> index = marcRecord.getKeyValuePairs(SolrFieldType.MIXED);
    assertEquals(136, index.size());
    assertEquals("(DE-576)19168161X",
      index.get("7100_AddedCorporateName_authorityRecordControlNumber")
        .get(0));
    assertEquals("19168161X",
      index.get("7100_AddedCorporateName_authorityRecordControlNumber_recordNumber")
        .get(0));
    assertEquals("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)",
      index.get("7100_AddedCorporateName_authorityRecordControlNumber_organization")
        .get(0));
    assertEquals("DE-576",
      index.get("7100_AddedCorporateName_authorityRecordControlNumber_organizationCode")
        .get(0));
  }

  @Test
  public void testSubfieldCode() throws IOException, URISyntaxException {
    MarcRecord marcRecord = new MarcRecord();
    marcRecord.setLeader("01445cem a22004454a 4500");
    marcRecord.setField("034", "0 $aa");
    assertEquals(1, marcRecord.getDatafield("034").size());
    Map<String, List<String>> index = marcRecord.getKeyValuePairs(SolrFieldType.MIXED);
    assertEquals("Linear scale", index.get("034a_Scale_category").get(0));
  }

  @Test
  public void testPositions() throws IOException, URISyntaxException {
    MarcRecord marcRecord = new MarcRecord();
    marcRecord.setLeader("01445cem a22004454a 4500");
    marcRecord.setField("800", "0 $7aa");
    assertEquals(1, marcRecord.getDatafield("800").size());
    Map<String, List<String>> index = marcRecord.getKeyValuePairs(SolrFieldType.MIXED);
    assertEquals("aa", index.get("8007_SeriesAddedPersonalName").get(0));
    assertEquals("Monographic component part", index.get("8007_SeriesAddedPersonalName_bibliographicLevel").get(0));
    assertEquals("Language material", index.get("8007_SeriesAddedPersonalName_typeOfRecord").get(0));
  }
}
