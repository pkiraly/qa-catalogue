package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Musical Presentation Statement
 * http://www.loc.gov/marc/bibliographic/bd254.html
 */
public class Tag254 extends DataFieldDefinition {
	private static Tag254 uniqueInstance;

	private Tag254() {
		initialize();
	}

	public static Tag254 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag254();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "254";
		label = "Musical Presentation Statement";
		mqTag = "MusicalPresentation";
		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Musical presentation statement", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
