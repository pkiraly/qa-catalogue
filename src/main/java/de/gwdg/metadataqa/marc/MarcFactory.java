package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.json.JsonBranch;
import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.marc.utils.MapToDatafield;
import net.minidev.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Factory class to create MarcRecord from JsonPathCache
 */
public class MarcFactory {

	private static Schema schema = new MarcJsonSchema();

	public static MarcRecord create(JsonPathCache cache) {
		MarcRecord record = new MarcRecord();
		for (JsonBranch branch : schema.getPaths()) {
			if (branch.getParent() != null)
				continue;
			if (branch.getLabel().equals("leader")) {
				record.setLeader(new Leader(extractFirst(cache, branch)));
			} else if (branch.getLabel().startsWith("00")) {

			} else {
				JSONArray fieldInstances = (JSONArray) cache.getFragment(branch.getJsonPath());
				for (int fieldInsanceNr = 0; fieldInsanceNr < fieldInstances.size(); fieldInsanceNr++) {
					Map fieldInstance = (Map) fieldInstances.get(fieldInsanceNr);
					DataField field = MapToDatafield.parse(fieldInstance);
					if (field != null) {
						record.addDataField(field);
					} else {
						record.addUnhandledTags(branch.getLabel());
					}
				}
			}
		}
		return record;
	}

	private static List<String> extractList(JsonPathCache cache, JsonBranch branch) {
		List<XmlFieldInstance> instances = (List<XmlFieldInstance>) cache.get(branch.getJsonPath());
		List<String> values = new ArrayList<>();
		for (XmlFieldInstance instance : instances) {
			values.add(instance.getValue());
		}
		return values;
	}

	private static String extractFirst(JsonPathCache cache, JsonBranch branch) {
		List<String> list = extractList(cache, branch);
		return list.get(0);
	}
}
