package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
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
    List<String> lines = FileUtils.readLines("general/010000011.mrctxt");
    MarcRecord record = MarcFactory.createFromFormattedText(lines);
    Map<String, List<String>> index = record.getKeyValuePairs(SolrFieldType.MIXED);
    assertEquals(148, index.size());
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
}
