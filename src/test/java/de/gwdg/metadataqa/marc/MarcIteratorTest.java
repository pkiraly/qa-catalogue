package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.json.DataElement;
import de.gwdg.metadataqa.api.model.pathcache.JsonPathCache;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.utils.MapToDatafield;
import net.minidev.json.JSONArray;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class MarcIteratorTest {

  JsonPathCache cache;
  Schema schema = new MarcJsonSchema();
  Map<String, List<Map<String, List<String>>>> fixedValues;

  public MarcIteratorTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() throws URISyntaxException, IOException {
    cache = new JsonPathCache(FileUtils.readFirstLineFromResource("general/marc.json"));
  }

  @Test
  public void testMarcSchemaIteration() {
    List<String> skippable = Arrays.asList("leader", "001", "003", "005", "006", "007", "008");
    // fixedValues
    for (DataElement dataElement : schema.getPaths()) {
      if (skippable.contains(dataElement.getLabel()) || dataElement.getParent() != null)
        continue;

      // List<Map<String, List<String>>> expectedList = fixedValues.get(branch.getLabel());
      JSONArray fieldInstances = (JSONArray) cache.getFragment(dataElement.getPath());
      for (int fieldInsanceNr = 0; fieldInsanceNr < fieldInstances.size(); fieldInsanceNr++) {
        Map fieldInstance = (Map)fieldInstances.get(fieldInsanceNr);
        DataField field = MapToDatafield.parse(fieldInstance, MarcVersion.DNB);
        if (!fieldInstance.get("tag").equals("935")) {
          assertNotNull(fieldInstance.get("tag").toString() + " should not be null", field);
          assertEquals(fieldInstance.get("tag"), field.getTag());
        }
      }
    }
  }
}
