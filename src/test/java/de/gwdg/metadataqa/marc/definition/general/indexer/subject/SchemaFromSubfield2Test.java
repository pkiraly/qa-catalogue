package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag084;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * 084: Other Classificaton Number (https://www.loc.gov/marc/bibliographic/bd084.html)
 */
public class SchemaFromSubfield2Test extends SubjectIndexerTest {

  @Test
  public void test084asssertIndexer() {
    DataField field = new DataField(
      Tag084.getInstance(), " ", " ",
      "a", "value",
      "2", "dnb"
    );
    assertEquals(
      SchemaFromSubfield2.class,
      field.getFieldIndexers().get(0).getClass()
    );
  }

  @Test
  public void test084() {
    DataField field = new DataField(
      Tag084.getInstance(), " ", " ",
      "a", "value",
      "2", "dnb"
    );
    Map<String, List<String>> indexEntries = getIndexEntries(field);

    assertEquals(1, indexEntries.size());
    assertEquals("084a_Classification_classificationPortion_dnb", indexEntries.keySet().toArray()[0]);
    assertEquals("value", indexEntries.get("084a_Classification_classificationPortion_dnb").get(0));
  }

  @Test
  public void test084_multivalue() {
    DataField field = new DataField(
      Tag084.getInstance(), " ", " ",
      "a", "value1",
      "a", "value2",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    assertEquals(1, indexEntries.size());
    assertEquals("084a_Classification_classificationPortion_dnb", indexEntries.keySet().toArray()[0]);
    assertEquals(2, indexEntries.get("084a_Classification_classificationPortion_dnb").size());
    assertEquals("value1", indexEntries.get("084a_Classification_classificationPortion_dnb").get(0));
    assertEquals("value2", indexEntries.get("084a_Classification_classificationPortion_dnb").get(1));
  }

  @Test
  public void test084_repeatingMultivalue() {
    DataField field = new DataField(
      Tag084.getInstance(), " ", " ",
      "a", "value",
      "a", "value",
      "2", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    assertEquals(1, indexEntries.size());
    assertEquals("084a_Classification_classificationPortion_dnb", indexEntries.keySet().toArray()[0]);
    assertEquals(1, indexEntries.get("084a_Classification_classificationPortion_dnb").size());
    assertEquals("value", indexEntries.get("084a_Classification_classificationPortion_dnb").get(0));
  }

  @Test
  public void test084_withoutSubfield2() {
    DataField field = new DataField(
      Tag084.getInstance(), " ", " ",
      "a", "value",
      "3", "dnb"
    );

    Map<String, List<String>> indexEntries = getIndexEntries(field);

    assertEquals(0, indexEntries.size());
  }

}
