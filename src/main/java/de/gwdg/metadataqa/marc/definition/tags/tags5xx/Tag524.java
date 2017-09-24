package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CitationSchemeSourceCodes;

/**
 * Preferred Citation of Described Materials Note
 * http://www.loc.gov/marc/bibliographic/bd524.html
 */
public class Tag524 extends DataFieldDefinition {

	private static Tag524 uniqueInstance;

	private Tag524() {
		initialize();
	}

	public static Tag524 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag524();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "524";
		label = "Preferred Citation of Described Materials Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Display constant controller").setCodes(
			" ", "Cite as",
			"8", "No display constant generated"
		).setMqTag("displayConstant");
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Preferred citation of described materials note", "NR",
			"2", "Source of schema used", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(CitationSchemeSourceCodes.getInstance());
	}
}
