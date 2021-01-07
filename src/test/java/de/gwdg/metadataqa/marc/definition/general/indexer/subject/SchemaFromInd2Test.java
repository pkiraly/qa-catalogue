package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.tags.tags6xx.Tag653;
import org.junit.Test;

import static org.junit.Assert.*;

public class SchemaFromInd2Test {

  @Test
  public void index() {
    DataField field = new DataField(Tag653.getInstance(), " ", "0","a", "value");
    assertEquals(SchemaFromInd2.class, field.getDefinition().getFieldIndexer().getClass());
  }
}