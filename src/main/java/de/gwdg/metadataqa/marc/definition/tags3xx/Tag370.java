package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

/**
 * Associated Place
 * http://www.loc.gov/marc/bibliographic/bd370.html
 */
public class Tag370 extends DataFieldDefinition {
	private static Tag370 uniqueInstance;

	private Tag370() {
		initialize();
	}

	public static Tag370 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag370();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "370";
		label = "Associated Place";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator();
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"c", "Associated country", "R",
			"f", "Other associated place", "R",
			"g", "Place of origin of work or expression", "R",
			"i", "Relationship information", "R",
			"s", "Start period", "NR",
			"t", "End period", "NR",
			"u", "Uniform Resource Identifier", "R",
			"v", "Source of information", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source of term", "NR",
			"3", "Materials specified", "NR",
			"4", "Relationship", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());
	}
}
