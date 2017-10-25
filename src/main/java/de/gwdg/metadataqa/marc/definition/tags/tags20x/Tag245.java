package de.gwdg.metadataqa.marc.definition.tags.tags20x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Title Statement
 * http://www.loc.gov/marc/bibliographic/bd245.html
 */
public class Tag245 extends DataFieldDefinition {
	private static Tag245 uniqueInstance;

	private Tag245() {
		initialize();
		postCreation();
	}

	public static Tag245 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag245();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "245";
		label = "Title Statement";
		bibframeTag = "Title";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd245.html";

		ind1 = new Indicator("Title added entry")
			.setCodes(
				"0", "No added entry",
				"1", "Added entry"
			)
			.setMqTag("titleAddedEntry");
		ind2 = new Indicator("Nonfiling characters")
			.setCodes(
				"0", "No nonfiling characters",
				"1-9", "Number of nonfiling characters"
			)
			.setMqTag("nonfilingCharacters");
		ind2.getCode("1-9").setRange(true);

		setSubfieldsWithCardinality(
			"a", "Title", "NR",
			"b", "Remainder of title", "NR",
			"c", "Statement of responsibility, etc.", "NR",
			"f", "Inclusive dates", "NR",
			"g", "Bulk dates", "NR",
			"h", "Medium", "NR",
			"k", "Form", "R",
			"n", "Number of part/section of a work", "R",
			"p", "Name of part/section of a work", "R",
			"s", "Version", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("mainTitle");
		getSubfield("b").setBibframeTag("subtitle");
		getSubfield("c").setBibframeTag("responsibilityStatement");
		getSubfield("f").setBibframeTag("originDate").setMqTag("inclusiveDates");
		getSubfield("g").setBibframeTag("originDate").setMqTag("bulkDates");
		getSubfield("h").setBibframeTag("genreForm").setMqTag("medium");
		getSubfield("k").setMqTag("form");
		getSubfield("n").setBibframeTag("partNumber");
		getSubfield("p").setBibframeTag("partName");
		getSubfield("s").setMqTag("version");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

		setHistoricalSubfields(
			"d", "Designation of section/part/series (SE) [OBSOLETE, 1979]",
			"e", "Name of part/section/series (SE) [OBSOLETE, 1979]"
		);
	}
}
