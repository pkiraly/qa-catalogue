package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag086;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * 086: Government Document Classification (http://www.loc.gov/marc/bibliographic/bd086.html)
 */
public class SchemaFromInd1OrIfEmptyFromSubfield2Test extends SubjectIndexerTest {

  @Test
  public void test086asssertIndexer() {
    DataField field = new DataField(
      Tag086.getInstance(), " ", " ",
      "a", "value",
      "2", "dnb"
    );
    assertEquals(
      SchemaFromInd1OrIfEmptyFromSubfield2.class,
      field.getFieldIndexers().get(0).getClass()
    );
  }

  @Test
  public void test086_ind2() {
    DataField field = new DataField(
      Tag086.getInstance(), " ", " ",
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
      Tag086.getInstance(), " ", " ",
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

  // space->$2, 0, 1
  @Test
  public void test086_ind1EqualsSpace() {
    DataField field = new DataField(
      Tag086.getInstance(), " ", " ",
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
  public void test086_ind1EqualsSpace_multivalue() {
    DataField field = new DataField(
      Tag086.getInstance(), " ", " ",
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
  public void test086_ind1EqualsSpace_noSubfield2() {
    DataField field = new DataField(
      Tag086.getInstance(), " ", " ",
      "a", "value",
      "3", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "086a_GovernmentDocumentClassification_dnb";
    assertEquals(0, indexEntries.size());
    assertTrue(indexEntries.keySet().isEmpty());
  }

  @Test
  public void test086_ind1Equals0() {
    DataField field = new DataField(
      Tag086.getInstance(), "0", " ",
      "a", "value",
      "3", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "086a_GovernmentDocumentClassification_sudocs";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }

  @Test
  public void test086_ind1Equals1() {
    DataField field = new DataField(
      Tag086.getInstance(), "1", " ",
      "a", "value",
      "3", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "086a_GovernmentDocumentClassification_gcp";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }

}
