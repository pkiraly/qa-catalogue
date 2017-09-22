package de.gwdg.metadataqa.marc.definition.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Projected Publication Date
 * http://www.loc.gov/marc/bibliographic/bd263.html
 */
public class Tag263 extends DataFieldDefinition {

	private static Tag263 uniqueInstance;

	private Tag263() {
		initialize();
	}

	public static Tag263 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag263();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "263";
		label = "Projected Publication Date";
		mqTag = "ProjectedPublicationDate";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator();
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Projected publication date", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		// TODO $a - regex: yyyymm and '-' for the unknown portion of the date.
		getSubfield("a").setBibframeTag("projectedProvisionDate").setMqTag("rdf:value");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
