package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.tags.tags6xx.Tag653;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SchemaFromInd2Test extends SubjectIndexerTest {

  @Test
  public void checkClass() {
    DataField field = new DataField(Tag653.getInstance(), " ", "0", "a", "value");
    assertEquals(SchemaFromInd2.class, field.getFieldIndexer().getClass());
  }

  @Test
  public void index0() {
    DataField field = new DataField(Tag653.getInstance(), " ", "0", "a", "value");

    Map<String, List<String>> indexEntries = field.getFieldIndexer().index(
      field, field.getKeyGenerator(SolrFieldType.MIXED));

    String solrField = "653a_UncontrolledIndexTerm_topical";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }

  @Test
  public void indexZero() {
    DataField field = new DataField(Tag653.getInstance(), " ", " ", "a", "value");

    Map<String, List<String>> indexEntries = field.getFieldIndexer().index(
      field, field.getKeyGenerator(SolrFieldType.MIXED));

    String solrField = "653a_UncontrolledIndexTerm_unspec";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }
}