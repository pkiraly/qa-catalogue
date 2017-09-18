package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.json.JsonBranch;
import de.gwdg.metadataqa.api.model.EdmFieldInstance;
import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.api.util.Converter;
import de.gwdg.metadataqa.api.util.FileUtils;
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
		cache = new JsonPathCache(FileUtils.readFirstLine("general/marc.json"));
	}

	@Test
	public void testMarcSchemaIteration() {
		List<String> skippable = Arrays.asList("leader", "001", "003", "005", "006", "007", "008");
		// fixedValues
		for (JsonBranch branch : schema.getPaths()) {
			if (skippable.contains(branch.getLabel()) || branch.getParent() != null)
				continue;

			// List<Map<String, List<String>>> expectedList = fixedValues.get(branch.getLabel());
			JSONArray fieldInstances = (JSONArray) cache.getFragment(branch.getJsonPath());
			for (int fieldInsanceNr = 0; fieldInsanceNr < fieldInstances.size(); fieldInsanceNr++) {
				// Map<String, List<String>> expectedInstances = expectedList.get(fieldInsanceNr);
				Map fieldInstance = (Map)fieldInstances.get(fieldInsanceNr);
				DataField field = MapToDatafield.parse(fieldInstance);
				System.err.println(field);

				// new DataField(fieldInstance.get("tag"), )

				// System.err.println(fieldInstance.get("subfield"));
				// List<Object> subfields = Converter.jsonObjectToList(fieldInstance.get("subfield"));
				// System.err.println(subfields.get(0).getClass());
				/*
				for(JsonBranch subfieldDef : branch.getChildren()) {
					List<EdmFieldInstance> childInstances = (List<EdmFieldInstance>)
						cache.get(subfieldDef.getAbsoluteJsonPath(fieldInsanceNr),
							subfieldDef.getJsonPath(), fieldInstance);
				}
				*/
			}
		}
	}

}
