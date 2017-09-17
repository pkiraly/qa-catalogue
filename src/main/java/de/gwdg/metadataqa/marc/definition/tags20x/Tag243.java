package de.gwdg.metadataqa.marc.definition.tags20x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;

/**
 * Collective Uniform Title
 * http://www.loc.gov/marc/bibliographic/bd243.html
 */
public class Tag243 extends DataFieldDefinition {
	private static Tag243 uniqueInstance;

	private Tag243() {
		initialize();
	}

	public static Tag243 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag243();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "243";
		label = "Collective Uniform Title";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator("Uniform title printed or displayed").setCodes(
			"0", "Not printed or displayed",
			"1", "Printed or displayed"
		);
		ind2 = new Indicator("Nonfiling characters").setCodes(
			"0-9", "Number of nonfiling characters"
		);
		ind2.getCode("1-9").setRange(true);
		setSubfieldsWithCardinality(
			"a", "Uniform title", "NR",
			"d", "Date of treaty signing", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"h", "Medium", "NR",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"m", "Medium of performance for music", "R",
			"n", "Number of part/section of a work", "R",
			"o", "Arranged statement for music", "NR",
			"p", "Name of part/section of a work", "R",
			"r", "Key for music", "NR",
			"s", "Version", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("y").setCodeList(LanguageCodes.getInstance());
		getSubfield("s").setBibframeTag("version");
	}
}
