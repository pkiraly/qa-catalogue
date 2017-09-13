package de.gwdg.metadataqa.marc.definition.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;

/**
 * Address
 * http://www.loc.gov/marc/bibliographic/bd270.html
 */
public class Tag270 extends DataFieldDefinition {

	private static Tag270 uniqueInstance;

	private Tag270() {
		initialize();
	}

	public static Tag270 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag270();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "270";
		label = "Address";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Level").setCodes(
				" ", "No level specified",
				"1", "Primary",
				"2", "Secondary"
		);
		ind2 = new Indicator("Type of address").setCodes(
				" ", "No type specified",
				"0", "Mailing",
				"7", "Type specified in subfield $i"
		);
		setSubfieldsWithCardinality(
				"a", "Address", "R",
				"b", "City", "NR",
				"c", "State or province", "NR",
				"d", "Country", "NR",
				"e", "Postal code", "NR",
				"f", "Terms preceding attention name", "NR",
				"g", "Attention name", "NR",
				"h", "Attention position", "NR",
				"i", "Type of address", "NR",
				"j", "Specialized telephone number", "R",
				"k", "Telephone number", "R",
				"l", "Fax number", "R",
				"m", "Electronic mail address", "R",
				"n", "TDD or TTY number", "R",
				"p", "Contact person", "R",
				"q", "Title of contact person", "R",
				"r", "Hours", "R",
				"z", "Public note", "R",
				"4", "Relationship", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		getSubfield("4").setCodeList(RelatorCodes.getInstance());
	}
}
