package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Digital File Characteristics
 * http://www.loc.gov/marc/bibliographic/bd347.html
 */
public class Tag347 extends DataFieldDefinition {
	private static Tag347 uniqueInstance;

	private Tag347() {
		initialize();
	}

	public static Tag347 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag347();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "347";
		label = "Digital File Characteristics";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "File type", "R",
			"b", "Encoding format", "R",
			"c", "File size", "R",
			"d", "Resolution", "R",
			"e", "Regional encoding", "R",
			"f", "Encoded bitrate", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
