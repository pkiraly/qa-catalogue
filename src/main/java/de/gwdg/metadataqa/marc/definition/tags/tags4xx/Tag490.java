package de.gwdg.metadataqa.marc.definition.tags.tags4xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Series Statement
 * http://www.loc.gov/marc/bibliographic/bd490.html
 */
public class Tag490 extends DataFieldDefinition {

	private static Tag490 uniqueInstance;

	private Tag490() {
		initialize();
		postCreation();
	}

	public static Tag490 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag490();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "490";
		label = "Series Statement";
		mqTag = "SeriesStatement";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Series tracing policy").setCodes(
			"0", "Series not traced",
			"1", "Series traced"
		).setMqTag("seriesTracing");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Series statement", "R",
			"l", "Library of Congress call number", "NR",
			"v", "Volume/sequential designation", "R",
			"x", "International Standard Serial Number", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("l").setMqTag("lccn");
		getSubfield("v").setMqTag("volume");
		getSubfield("x").setMqTag("issn");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
