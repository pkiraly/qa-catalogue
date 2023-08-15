package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag052;
import de.gwdg.metadataqa.marc.definition.tags.tags84x.Tag852;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * 052: Geographic Classification (http://www.loc.gov/marc/bibliographic/bd052.html)
 * 852: Location (http://www.loc.gov/marc/bibliographic/bd852.html)
 */
public class SchemaFromInd1OrIf7FromSubfield2Test extends SubjectIndexerTest {

  @Test
  public void test052asssertIndexer() {
    DataField field = new DataField(
      Tag052.getInstance(), " ", "2",
      "a", "value",
      "2", "dnb"
    );
    assertEquals(
      SchemaFromInd1OrIf7FromSubfield2.class,
      field.getFieldIndexers().get(0).getClass()
    );
  }

  @Test
  public void test852asssertIndexer() {
    DataField field = new DataField(
      Tag852.getInstance(), " ", "2",
      "a", "value",
      "2", "dnb"
    );
    assertEquals(
      SchemaFromInd1OrIf7FromSubfield2.class,
      field.getFieldIndexers().get(0).getClass()
    );
  }

  @Test
  public void test052_ind2() {
    DataField field = new DataField(
      Tag052.getInstance(), " ", "0",
      "a", "value",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "052a_GeographicClassification_lcc";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }

  @Test
  public void test052_ind2multivalue() {
    DataField field = new DataField(
      Tag052.getInstance(), " ", "0",
      "a", "value1",
      "a", "value2",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "052a_GeographicClassification_lcc";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals(2, indexEntries.get(solrField).size());
    assertEquals("value1", indexEntries.get(solrField).get(0));
    assertEquals("value2", indexEntries.get(solrField).get(1));
  }

  @Test
  public void test052_ind2Equals7() {
    DataField field = new DataField(
      Tag052.getInstance(), " ", "7",
      "a", "value",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "052a_GeographicClassification_lcc";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }

  @Test
  public void test052_ind2Equals7_multivalue() {
    DataField field = new DataField(
      Tag052.getInstance(), " ", "7",
      "a", "value1",
      "a", "value2",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "052a_GeographicClassification_lcc";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals(2, indexEntries.get(solrField).size());
    assertEquals("value1", indexEntries.get(solrField).get(0));
    assertEquals("value2", indexEntries.get(solrField).get(1));
  }

  @Test
  public void test052_withoutSubfieldA() {
    DataField field = new DataField(
      Tag052.getInstance(), " ", " ",
      "b", "value1",
      "c", "value2",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);
    assertEquals(0, indexEntries.size());
  }


    @Test
  public void test852_ind1_empty() {
    Map<String, List<String>> indexEntries = getIndexEntries(new DataField(
      Tag852.getInstance(), " ", " ",
      "a", "value",
      "2", "dnb"
    ));
    assertEquals("852a_Location_location_unspec", indexEntries.keySet().toArray()[0]);
  }

  @Test
  public void test852_ind1_1() {
    Map<String, List<String>> indexEntries = getIndexEntries(new DataField(
      Tag852.getInstance(), "1", " ",
      "a", "value",
      "2", "dnb"
    ));
    assertEquals("852a_Location_location_ddc", indexEntries.keySet().toArray()[0]);
  }

  @Test
  public void test852_ind1_2() {
    Map<String, List<String>> indexEntries = getIndexEntries(new DataField(
      Tag852.getInstance(), "2", " ",
      "a", "value",
      "2", "dnb"
    ));
    assertEquals("852a_Location_location_nlm", indexEntries.keySet().toArray()[0]);
  }

  @Test
  public void test852_ind1_3() {
    Map<String, List<String>> indexEntries = getIndexEntries(new DataField(
      Tag852.getInstance(), "3", " ",
      "a", "value",
      "2", "dnb"
    ));
    assertEquals("852a_Location_location_sudocs", indexEntries.keySet().toArray()[0]);
  }

  @Test
  public void test852_ind1_4() {
    Map<String, List<String>> indexEntries = getIndexEntries(new DataField(
      Tag852.getInstance(), "4", " ",
      "a", "value",
      "2", "dnb"
    ));
    assertEquals("852a_Location_location_shelfcn", indexEntries.keySet().toArray()[0]);
  }

  @Test
  public void test852_ind1_5() {
    Map<String, List<String>> indexEntries = getIndexEntries(new DataField(
      Tag852.getInstance(), "5", " ",
      "a", "value",
      "2", "dnb"
    ));
    assertEquals("852a_Location_location_title", indexEntries.keySet().toArray()[0]);
  }

  @Test
  public void test852_ind1_6() {
    Map<String, List<String>> indexEntries = getIndexEntries(new DataField(
      Tag852.getInstance(), "6", " ",
      "a", "value",
      "2", "dnb"
    ));
    assertEquals("852a_Location_location_shelfs", indexEntries.keySet().toArray()[0]);
  }

  @Test
  public void test852_ind1_7() {
    Map<String, List<String>> indexEntries = getIndexEntries(new DataField(
      Tag852.getInstance(), "7", " ",
      "a", "value",
      "2", "dnb"
    ));
    assertEquals("852a_Location_location_dnb", indexEntries.keySet().toArray()[0]);
  }

  @Test
  public void test852_ind1_7without2() {
    Map<String, List<String>> indexEntries = getIndexEntries(new DataField(
      Tag852.getInstance(), "7", " ",
      "a", "value",
      "3", "dnb"
    ));
    assertEquals(0, indexEntries.size());
  }

  @Test
  public void test852_ind1_8() {
    Map<String, List<String>> indexEntries = getIndexEntries(new DataField(
      Tag852.getInstance(), "8", " ",
      "a", "value",
      "2", "dnb"
    ));
    assertEquals("852a_Location_location_other", indexEntries.keySet().toArray()[0]);
  }

  @Test
  public void test852_withoutSubfieldA() {
    Map<String, List<String>> indexEntries = getIndexEntries(new DataField(
      Tag852.getInstance(), "7", " ",
      "b", "value",
      "2", "dnb"
    ));
    assertEquals(0, indexEntries.size());
  }
}
