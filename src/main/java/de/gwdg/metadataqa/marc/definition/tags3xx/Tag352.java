package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Digital Graphic Representation
 * http://www.loc.gov/marc/bibliographic/bd352.html
 */
public class Tag352 extends DataFieldDefinition {
	private static Tag352 uniqueInstance;

	private Tag352() {
		initialize();
	}

	public static Tag352 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag352();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "352";
		label = "Digital Graphic Representation";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Direct reference method", "NR",
				"b", "Object type", "R",
				"c", "Object count", "R",
				"d", "Row count", "NR",
				"e", "Column count", "NR",
				"f", "Vertical count", "NR",
				"g", "VPF topology level", "NR",
				"i", "Indirect reference description", "NR",
				"q", "Format of the digital image", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
