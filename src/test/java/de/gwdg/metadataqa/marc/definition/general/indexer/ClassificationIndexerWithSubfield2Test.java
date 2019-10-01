package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag084;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ClassificationIndexerWithSubfield2Test {

  @Test
  public void test084() throws ParserException {
    FieldIndexer indexer = ClassificationIndexerWithSubfield2.getInstance();

    DataField field = new DataField(
      Tag084.getInstance(), " ", " ",
      "a", "value",
      "2", "dnb"
    );
    DataFieldKeyGenerator keyGenerator = new DataFieldKeyGenerator(
      field.getDefinition(), SolrFieldType.MIXED
    );

    Map<String, List<String>> i = indexer.index(field, keyGenerator);
    System.err.println(i);
    assertEquals(1, i.size());
    assertEquals("084a_Classification_classificationPortion_dnb", i.keySet().toArray()[0]);
    assertEquals("value", i.get("084a_Classification_classificationPortion_dnb").get(0));
  }

  @Test
  public void test084_multivalue() throws ParserException {
    FieldIndexer indexer = ClassificationIndexerWithSubfield2.getInstance();

    DataField field = new DataField(
      Tag084.getInstance(), " ", " ",
      "a", "value1",
      "a", "value2",
      "2", "dnb"
    );
    DataFieldKeyGenerator keyGenerator = new DataFieldKeyGenerator(
      field.getDefinition(), SolrFieldType.MIXED
    );

    Map<String, List<String>> i = indexer.index(field, keyGenerator);
    System.err.println(i);
    assertEquals(1, i.size());
    assertEquals("084a_Classification_classificationPortion_dnb", i.keySet().toArray()[0]);
    assertEquals(2, i.get("084a_Classification_classificationPortion_dnb").size());
    assertEquals("value1", i.get("084a_Classification_classificationPortion_dnb").get(0));
    assertEquals("value2", i.get("084a_Classification_classificationPortion_dnb").get(1));
  }

}
