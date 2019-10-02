package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag086;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SubjectIndexerWithSchemaFromInd1OrIfEmptyFromSubfield2Test extends SubjectIndexerTest {

  @Test
  public void test086asssertIndexer() {
    DataField field = new DataField(
      Tag086.getInstance(), " ", "2",
      "a", "value",
      "2", "dnb"
    );
    assertEquals(
      SubjectIndexerWithSchemaFromInd1OrIfEmptyFromSubfield2.class,
      field.getDefinition().getFieldIndexer().getClass()
    );
  }

  @Test
  public void test086_ind2() {
    DataField field = new DataField(
      Tag086.getInstance(), " ", "0",
      "a", "value",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "086a_GovernmentDocumentClassification_dnb";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }

  @Test
  public void test086_ind2multivalue() {
    DataField field = new DataField(
      Tag086.getInstance(), " ", "0",
      "a", "value1",
      "a", "value2",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "086a_GovernmentDocumentClassification_dnb";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals(2, indexEntries.get(solrField).size());
    assertEquals("value1", indexEntries.get(solrField).get(0));
    assertEquals("value2", indexEntries.get(solrField).get(1));
  }

  @Test
  public void test086_ind2Equals7() {
    DataField field = new DataField(
      Tag086.getInstance(), " ", "7",
      "a", "value",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "086a_GovernmentDocumentClassification_dnb";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }

  @Test
  public void test086_ind2Equals7_multivalue() {
    DataField field = new DataField(
      Tag086.getInstance(), " ", "7",
      "a", "value1",
      "a", "value2",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "086a_GovernmentDocumentClassification_dnb";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals(2, indexEntries.get(solrField).size());
    assertEquals("value1", indexEntries.get(solrField).get(0));
    assertEquals("value2", indexEntries.get(solrField).get(1));
  }
}
