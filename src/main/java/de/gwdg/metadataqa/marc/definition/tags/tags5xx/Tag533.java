package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Reproduction Note
 * http://www.loc.gov/marc/bibliographic/bd533.html
 */
public class Tag533 extends DataFieldDefinition {

	private static Tag533 uniqueInstance;

	private Tag533() {
		initialize();
		postCreation();
	}

	public static Tag533 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag533();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "533";
		label = "Reproduction Note";
		mqTag = "Reproduction";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Type of reproduction", "NR",
			"b", "Place of reproduction", "R",
			"c", "Agency responsible for reproduction", "R",
			"d", "Date of reproduction", "NR",
			"e", "Physical description of reproduction", "NR",
			"f", "Series statement of reproduction", "R",
			"m", "Dates and/or sequential designation of issues reproduced", "R",
			"n", "Note about reproduction", "R",
			"3", "Materials specified", "NR",
			"5", "Institution to which field applies", "NR",
			"7", "Fixed-length data elements of reproduction", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		// TODO write parser for $7
		/*
		 /0 - Type of date/Publication status
       /1-4 - Date 1
       /5-8 - Date 2
       /9-11 - Place of publication, production, or execution
       /12 - Frequency
       /13 - Regularity
       /14 - Form of item
	   */

		getSubfield("a").setMqTag("type");
		getSubfield("b").setMqTag("place");
		getSubfield("c").setMqTag("agency");
		getSubfield("d").setMqTag("date");
		getSubfield("e").setMqTag("physicalDescription");
		getSubfield("f").setMqTag("series");
		getSubfield("m").setMqTag("datesAndOrSequentialDesignation");
		getSubfield("n").setMqTag("note");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("7").setMqTag("controlSubfield");
		getSubfield("8").setMqTag("fieldLink");
	}
}
