package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Planar Coordinate Data
 * http://www.loc.gov/marc/bibliographic/bd343.html
 */
public class Tag343 extends DataFieldDefinition {
	private static Tag343 uniqueInstance;

	private Tag343() {
		initialize();
	}

	public static Tag343 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag343();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "343";
		label = "Planar Coordinate Data";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Planar coordinate encoding method", "NR",
				"b", "Planar distance units", "NR",
				"c", "Abscissa resolution", "NR",
				"d", "Ordinate resolution", "NR",
				"e", "Distance resolution", "NR",
				"f", "Bearing resolution", "NR",
				"g", "Bearing units", "NR",
				"h", "Bearing reference direction", "NR",
				"i", "Bearing reference meridian", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
