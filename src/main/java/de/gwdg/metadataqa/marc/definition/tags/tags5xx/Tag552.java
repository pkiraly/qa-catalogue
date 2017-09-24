package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Entity and Attribute Information Note
 * http://www.loc.gov/marc/bibliographic/bd552.html
 */
public class Tag552 extends DataFieldDefinition {

	private static Tag552 uniqueInstance;

	private Tag552() {
		initialize();
	}

	public static Tag552 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag552();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "552";
		label = "Entity and Attribute Information Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator();
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Entity type label", "NR",
			"b", "Entity type definition and source", "NR",
			"c", "Attribute label", "NR",
			"d", "Attribute definition and source", "NR",
			"e", "Enumerated domain value", "R",
			"f", "Enumerated domain value definition and source", "R",
			"g", "Range domain minimum and maximum", "NR",
			"h", "Codeset name and source", "NR",
			"i", "Unrepresentable domain", "NR",
			"j", "Attribute units of measurement and resolution", "NR",
			"k", "Beginning and ending date of attribute values", "NR",
			"l", "Attribute value accuracy", "NR",
			"m", "Attribute value accuracy explanation", "NR",
			"n", "Attribute measurement frequency", "NR",
			"o", "Entity and attribute overview", "R",
			"p", "Entity and attribute detail citation", "R",
			"u", "Uniform Resource Identifier", "R",
			"z", "Display note", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
