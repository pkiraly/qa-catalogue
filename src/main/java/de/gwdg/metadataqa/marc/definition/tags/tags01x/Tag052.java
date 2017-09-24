package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;

/**
 * Geographic Classification
 * http://www.loc.gov/marc/bibliographic/bd052.html
 */
public class Tag052 extends DataFieldDefinition {

	private static Tag052 uniqueInstance;

	private Tag052() {
		initialize();
	}

	public static Tag052 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag052();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "052";
		label = "Geographic Classification";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Code source").setCodes(
			" ", "Library of Congress Classification",
			"1", "U.S. Dept. of Defense Classification",
			"7", "Source specified in subfield $2"
		);
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Geographic classification area code", "NR",
			"b", "Geographic classification subarea code", "R",
			"d", "Populated place name", "R",
			"2", "Code source", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());
	}
}
