package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

/**
 * Musical Incipits Information
 * http://www.loc.gov/marc/bibliographic/bd031.html
 */
public class Tag033 extends DataFieldDefinition {

	private static Tag033 uniqueInstance;

	private Tag033() {
		initialize();
	}

	public static Tag033 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag033();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "033";
		label = "Date/Time and Place of an Event";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Type of date in subfield $a").setCodes(
			" ", "No date information",
			"0", "Single date",
			"1", "Multiple single dates",
			"2", "Range of dates"
		);
		ind2 = new Indicator("Type of event").setCodes(
			" ", "No information provided",
			"0", "Capture",
			"1", "Broadcast",
			"2", "Finding"
		);
		setSubfieldsWithCardinality(
			"a", "Formatted date/time", "R",
			"b", "Geographic classification area code", "R",
			"c", "Geographic classification subarea code", "R",
			"p", "Place of event", "R",
			"0", "Authority record control number", "R",
			"2", "Source of term", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());
	}
}
