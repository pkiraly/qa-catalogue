package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.tags76x.Tag787;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MarcToSolrTest {

  @Test
  public void testVersionSpecificSubfield() {
    MarcRecord marcRecord = new MarcRecord("010000011");
    marcRecord.setLeader(new Leader("00860cam a22002774a 45 0"));
    marcRecord.addDataField(new DataField(Tag787.getInstance(), " ", " ","@", "japan"));
    Map<String, List<String>> solr = marcRecord.getKeyValuePairs(SolrFieldType.MIXED, false, MarcVersion.KBR);
    assertTrue(solr.containsKey("787x40_RelatedTo_language_KBR"));
    assertEquals(Arrays.asList("japan"), solr.get("787x40_RelatedTo_language_KBR"));
  }
}