package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Action Note
 * http://www.loc.gov/marc/bibliographic/bd583.html
 */
public class Tag583 extends DataFieldDefinition {

	private static Tag583 uniqueInstance;

	private Tag583() {
		initialize();
	}

	public static Tag583 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag583();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "583";
		label = "Action Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Privacy").setCodes(
			" ", "Publications",
			"8", "No display constant generated"
		);
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Action", "NR",
			"b", "Action identification", "R",
			"c", "Time/date of action", "R",
			"d", "Action interval", "R",
			"e", "Contingency for action", "R",
			"f", "Authorization", "R",
			"h", "Jurisdiction", "R",
			"i", "Method of action", "R",
			"j", "Site of action", "R",
			"k", "Action agent", "R",
			"l", "Status", "R",
			"n", "Extent", "R",
			"o", "Type of unit", "R",
			"u", "Uniform Resource Identifier", "R",
			"x", "Nonpublic note", "R",
			"z", "Public note", "R",
			"2", "Source of term", "NR",
			"3", "Materials specified", "NR",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		// TODO: $2 Resource Action Term Source Codes. https://www.loc.gov/standards/sourcelist/resource-action.html
	}
}
