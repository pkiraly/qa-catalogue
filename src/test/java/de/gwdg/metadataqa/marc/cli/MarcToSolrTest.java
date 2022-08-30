package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.tags76x.Tag787;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaFieldDefinition;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

  @Test
  public void pica() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getPath("pica/k10plus.json"));
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_PLAIN, getFile("pica/k10plus-sample.pica"), null);
    reader.hasNext();
    Record record = reader.next();
    MarcRecord marcRecord = MarcFactory.createPicaFromMarc4j(record, schema);
    Map<String, List<String>> map = marcRecord.getKeyValuePairs(SolrFieldType.HUMAN, true, MarcVersion.MARC21);
    // System.err.println(map.keySet());
    System.err.println(marcRecord.asJson());
  }

  @Test
  public void name() {
    assertEquals(-1, "a".compareTo("b"));
    assertEquals(0, "a".compareTo("a"));
    assertEquals(1, "b".compareTo("a"));
    assertEquals(-1, "b".compareTo("c"));
  }

  private String getFile(String relativePath) {
    try {
      return FileUtils.getPath(relativePath).toFile().getAbsolutePath();
    } catch (IOException e) {
      // throw new RuntimeException(e);
    } catch (URISyntaxException e) {
      // throw new RuntimeException(e);
    }
    return "";
  }

  private String getPath(String fileName) {
    return Paths.get("src/test/resources/" + fileName).toAbsolutePath().toString();
  }
}