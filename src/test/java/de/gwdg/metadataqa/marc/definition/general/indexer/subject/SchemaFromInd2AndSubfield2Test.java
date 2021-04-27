package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag072;
import de.gwdg.metadataqa.marc.definition.tags.tags6xx.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * // 055: Classification Numbers Assigned in Canada (http://www.loc.gov/marc/bibliographic/bd055.html
 * 072: Subject Category Code (http://www.loc.gov/marc/bibliographic/bd072.html)
 * 600: Subject Added Entry - Personal Name (http://www.loc.gov/marc/bibliographic/bd600.html)
 * 610: Subject Added Entry - Corporate Name (http://www.loc.gov/marc/bibliographic/bd610.html)
 * 611: Subject Added Entry - Meeting Name (http://www.loc.gov/marc/bibliographic/bd611.html)
 * 630: Subject Added Entry - Uniform Title (http://www.loc.gov/marc/bibliographic/bd630.html)
 * 647: Subject Added Entry - Named Event (http://www.loc.gov/marc/bibliographic/bd647.html)
 * 648: Subject Added Entry - Chronological Term (http://www.loc.gov/marc/bibliographic/bd648.html)
 * 650: Subject Added Entry - Topical Term (http://www.loc.gov/marc/bibliographic/bd650.html)
 * 651: Subject Added Entry - Geographic Name (http://www.loc.gov/marc/bibliographic/bd651.html)
 * 655: Index Term - Genre/Form (http://www.loc.gov/marc/bibliographic/bd655.html)
 */
public class SchemaFromInd2AndSubfield2Test extends SubjectIndexerTest {

  @Test
  public void test072asssertIndexer() {
    DataField field = new DataField(Tag072.getInstance(), " ", "2", "a", "value", "2", "dnb");
    assertEquals(
      SchemaFromInd2AndSubfield2.class,
      field.getFieldIndexer().getClass()
    );
  }

  @Test
  public void test600asssertIndexer() {
    DataField field = new DataField(Tag600.getInstance(), " ", "2", "a", "value", "2", "dnb");
    assertEquals(
      SchemaFromInd2AndSubfield2.class,
      field.getFieldIndexer().getClass()
    );
  }

  @Test
  public void test610asssertIndexer() {
    DataField field = new DataField(Tag610.getInstance(), " ", "2", "a", "value", "2", "dnb");
    assertEquals(
      SchemaFromInd2AndSubfield2.class,
      field.getFieldIndexer().getClass()
    );
  }

  @Test
  public void test611asssertIndexer() {
    DataField field = new DataField(Tag611.getInstance(), " ", "2", "a", "value", "2", "dnb");
    assertEquals(
      SchemaFromInd2AndSubfield2.class,
      field.getFieldIndexer().getClass()
    );
  }

  @Test
  public void test630asssertIndexer() {
    DataField field = new DataField(Tag630.getInstance(), " ", "2", "a", "value", "2", "dnb");
    assertEquals(
      SchemaFromInd2AndSubfield2.class,
      field.getFieldIndexer().getClass()
    );
  }

  @Test
  public void test647asssertIndexer() {
    DataField field = new DataField(Tag647.getInstance(), " ", "2", "a", "value", "2", "dnb");
    assertEquals(
      SchemaFromInd2AndSubfield2.class,
      field.getFieldIndexer().getClass()
    );
  }

  @Test
  public void test648asssertIndexer() {
    DataField field = new DataField(Tag648.getInstance(), " ", "2", "a", "value", "2", "dnb");
    assertEquals(
      SchemaFromInd2AndSubfield2.class,
      field.getFieldIndexer().getClass()
    );
  }

  @Test
  public void test650asssertIndexer() {
    DataField field = new DataField(Tag650.getInstance(), " ", "2", "a", "value", "2", "dnb");
    assertEquals(
      SchemaFromInd2AndSubfield2.class,
      field.getFieldIndexer().getClass()
    );
  }

  @Test
  public void test651asssertIndexer() {
    DataField field = new DataField(Tag651.getInstance(), " ", "2", "a", "value", "2", "dnb");
    assertEquals(
      SchemaFromInd2AndSubfield2.class,
      field.getFieldIndexer().getClass()
    );
  }

  @Test
  public void test655asssertIndexer() {
    DataField field = new DataField(Tag655.getInstance(), " ", "2", "a", "value", "2", "dnb");
    assertEquals(
      SchemaFromInd2AndSubfield2.class,
      field.getFieldIndexer().getClass()
    );
  }

  @Test
  public void test072_ind2() {
    DataField field = new DataField(
      Tag072.getInstance(), " ", "0",
      "a", "value",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "072a_SubjectCategoryCode_nal";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }

  @Test
  public void test072_ind2multivalue() {
    DataField field = new DataField(
      Tag072.getInstance(), " ", "0",
      "a", "value1",
      "a", "value2",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "072a_SubjectCategoryCode_nal";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals(2, indexEntries.get(solrField).size());
    assertEquals("value1", indexEntries.get(solrField).get(0));
    assertEquals("value2", indexEntries.get(solrField).get(1));
  }

  @Test
  public void test072_ind2Equals7() {
    DataField field = new DataField(
      Tag072.getInstance(), " ", "7",
      "a", "value",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "072a_SubjectCategoryCode_dnb";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }

  @Test
  public void test072_ind2Equals7_multivalue() {
    DataField field = new DataField(
      Tag072.getInstance(), " ", "7",
      "a", "value1",
      "a", "value2",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "072a_SubjectCategoryCode_dnb";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals(2, indexEntries.get(solrField).size());
    assertEquals("value1", indexEntries.get(solrField).get(0));
    assertEquals("value2", indexEntries.get(solrField).get(1));
  }

  @Test
  public void test072_ind2Equals7without2() {
    DataField field = new DataField(
      Tag072.getInstance(), " ", "7",
      "a", "value1",
      "a", "value2",
      "3", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    String solrField = "072a_SubjectCategoryCode_dnb";
    assertEquals(0, indexEntries.size());
  }

}
