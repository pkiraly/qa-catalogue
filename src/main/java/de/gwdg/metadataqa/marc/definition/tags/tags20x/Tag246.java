package de.gwdg.metadataqa.marc.definition.tags.tags20x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Varying Form of Title
 * http://www.loc.gov/marc/bibliographic/bd246.html
 */
public class Tag246 extends DataFieldDefinition {
	private static Tag246 uniqueInstance;

	private Tag246() {
		initialize();
		postCreation();
	}

	public static Tag246 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag246();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "246";
		label = "Varying Form of Title";
		bibframeTag = "ParallelTitle";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Note/added entry controller").setCodes(
			"0", "Note, no added entry",
			"1", "Note, added entry",
			"2", "No note, no added entry",
			"3", "No note, added entry"
		).setMqTag("noteAndAddedEntry");
		ind2 = new Indicator("Type of title").setCodes(
			" ", "No type specified",
			"0", "Portion of title",
			"1", "Parallel title",
			"2", "Distinctive title",
			"3", "Other title",
			"4", "Cover title",
			"5", "Added title page title",
			"6", "Caption title",
			"7", "Running title",
			"8", "Spine title"
		).setMqTag("type");
		setSubfieldsWithCardinality(
			"a", "Title proper/short title", "NR",
			"b", "Remainder of title", "NR",
			"f", "Date or sequential designation", "NR",
			"g", "Miscellaneous information", "R",
			"h", "Medium", "NR",
			"i", "Display text", "NR",
			"n", "Number of part/section of a work", "R",
			"p", "Name of part/section of a work", "R",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("5").setCodeList(OrganizationCodes.getInstance());
		getSubfield("a").setBibframeTag("mainTitle");
		getSubfield("b").setBibframeTag("subtitle");
		getSubfield("f").setBibframeTag("date");
		getSubfield("g").setBibframeTag("miscellaneous");
		getSubfield("h").setMqTag("medium");
		getSubfield("i").setMqTag("displayText");
		getSubfield("n").setBibframeTag("partNumber");
		getSubfield("p").setBibframeTag("partName");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
