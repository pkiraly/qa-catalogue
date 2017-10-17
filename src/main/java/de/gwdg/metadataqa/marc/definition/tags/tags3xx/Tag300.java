package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Physical Description
 * http://www.loc.gov/marc/bibliographic/bd300.html
 */
public class Tag300 extends DataFieldDefinition {
	private static Tag300 uniqueInstance;

	private Tag300() {
		initialize();
		postCreation();
	}

	public static Tag300 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag300();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "300";
		label = "Physical Description";
		mqTag = "PhysicalDescription";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Extent", "R",
			"b", "Other physical details", "NR",
			"c", "Dimensions", "R",
			"e", "Accompanying material", "NR",
			"f", "Type of unit", "R",
			"g", "Size of unit", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("extent");
		getSubfield("b").setBibframeTag("note").setMqTag("otherPhysicalDetails");
		getSubfield("c").setBibframeTag("dimensions");
		getSubfield("e").setBibframeTag("note").setMqTag("accompanyingMaterial");
		getSubfield("f").setMqTag("typeOfUnit");
		getSubfield("g").setMqTag("sizeOfUnit");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setMqTag("linkage");
	}
}
