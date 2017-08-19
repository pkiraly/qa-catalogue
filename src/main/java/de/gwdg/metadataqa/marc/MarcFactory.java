package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.json.JsonBranch;
import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.api.schema.Schema;

import java.util.ArrayList;
import java.util.List;

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
			switch (branch.getLabel()) {
				case "leader": record.setLeader(new Leader(extractFirst(cache, branch))); break;
				default:
					break;
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
