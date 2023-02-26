package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.tags76x.Tag787;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MarcToSolrTest {

  @Test
  public void testVersionSpecificSubfield() {
    BibliographicRecord marcRecord = new Marc21Record("010000011");
    marcRecord.setLeader(new Leader("00860cam a22002774a 45 0"));
    marcRecord.addDataField(new DataField(Tag787.getInstance(), " ", " ","@", "japan"));
    Map<String, List<String>> solr = marcRecord.getKeyValuePairs(SolrFieldType.MIXED, false, MarcVersion.KBR);
    assertTrue(solr.containsKey("787x40_RelatedTo_language_KBR"));
    assertEquals(Arrays.asList("japan"), solr.get("787x40_RelatedTo_language_KBR"));
  }

  @Test
  public void pica() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/k10plus.json"));
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_PLAIN, CliTestUtils.getTestResource("pica/k10plus-sample.pica"), null);
    reader.hasNext();
    Record record = reader.next();
    BibliographicRecord marcRecord = MarcFactory.createPicaFromMarc4j(record, schema);
    Map<String, List<String>> map = marcRecord.getKeyValuePairs(SolrFieldType.HUMAN, true, MarcVersion.MARC21);
    // System.err.println(map.keySet());
    assertTrue(marcRecord.asJson().contains("036E/01"));
    assertTrue(map.containsKey("036E_01_a"));
  }

  @Test
  public void name() {
    assertEquals(-1, "a".compareTo("b"));
    assertEquals(0, "a".compareTo("a"));
    assertEquals(1, "b".compareTo("a"));
    assertEquals(-1, "b".compareTo("c"));
  }
}