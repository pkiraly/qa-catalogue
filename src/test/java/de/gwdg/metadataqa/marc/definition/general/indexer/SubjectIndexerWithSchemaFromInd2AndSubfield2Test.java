package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag072;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SubjectIndexerWithSchemaFromInd2AndSubfield2Test {

  @Test
  public void test072asssertIndexer() throws ParserException {
    DataField field = new DataField(
      Tag072.getInstance(), " ", "2",
      "a", "value",
      "2", "dnb"
    );
    assertEquals(
      SubjectIndexerWithSchemaFromInd2AndSubfield2.class,
      field.getDefinition().getFieldIndexer().getClass()
    );
  }

  @Test
  public void test072_ind2() throws ParserException {
    DataField field = new DataField(
      Tag072.getInstance(), " ", "2",
      "a", "value",
      "2", "dnb"
    );
    DataFieldKeyGenerator keyGenerator = new DataFieldKeyGenerator(
      field.getDefinition(), SolrFieldType.MIXED
    );
    FieldIndexer indexer = field.getDefinition().getFieldIndexer();

    Map<String, List<String>> indexEntries = indexer.index(field, keyGenerator);

    String solrField = "072a_SubjectCategoryCode_2";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }

  @Test
  public void test072_ind2multivalue() throws ParserException {
    DataField field = new DataField(
      Tag072.getInstance(), " ", "2",
      "a", "value1",
      "a", "value2",
      "2", "dnb"
    );
    DataFieldKeyGenerator keyGenerator = new DataFieldKeyGenerator(
      field.getDefinition(), SolrFieldType.MIXED
    );
    FieldIndexer indexer = field.getDefinition().getFieldIndexer();

    Map<String, List<String>> indexEntries = indexer.index(field, keyGenerator);

    String solrField = "072a_SubjectCategoryCode_2";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals(2, indexEntries.get(solrField).size());
    assertEquals("value1", indexEntries.get(solrField).get(0));
    assertEquals("value2", indexEntries.get(solrField).get(1));
  }

  @Test
  public void test072_ind2Equals7() throws ParserException {
    DataField field = new DataField(
      Tag072.getInstance(), " ", "7",
      "a", "value",
      "2", "dnb"
    );
    DataFieldKeyGenerator keyGenerator = new DataFieldKeyGenerator(
      field.getDefinition(), SolrFieldType.MIXED
    );
    FieldIndexer indexer = field.getDefinition().getFieldIndexer();

    Map<String, List<String>> indexEntries = indexer.index(field, keyGenerator);

    String solrField = "072a_SubjectCategoryCode_dnb";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get(solrField).get(0));
  }

  @Test
  public void test072_ind2Equals7_multivalue() throws ParserException {
    DataField field = new DataField(
      Tag072.getInstance(), " ", "7",
      "a", "value1",
      "a", "value2",
      "2", "dnb"
    );
    DataFieldKeyGenerator keyGenerator = new DataFieldKeyGenerator(
      field.getDefinition(), SolrFieldType.MIXED
    );
    FieldIndexer indexer = field.getDefinition().getFieldIndexer();

    Map<String, List<String>> indexEntries = indexer.index(field, keyGenerator);

    String solrField = "072a_SubjectCategoryCode_dnb";
    assertEquals(1, indexEntries.size());
    assertEquals(solrField, indexEntries.keySet().toArray()[0]);
    assertEquals(2, indexEntries.get(solrField).size());
    assertEquals("value1", indexEntries.get(solrField).get(0));
    assertEquals("value2", indexEntries.get(solrField).get(1));
  }

}
