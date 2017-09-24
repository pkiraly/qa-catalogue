package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.NationalBibliographyNumberSourceCodes;

/**
 * National Bibliography Number
 * http://www.loc.gov/marc/bibliographic/bd015.html
 */
public class Tag015 extends DataFieldDefinition {

	private static Tag015 uniqueInstance;

	private Tag015() {
		initialize();
	}

	public static Tag015 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag015();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "015";
		label = "National Bibliography Number";
		cardinality = Cardinality.Repeatable;
		bibframeTag = "identifiedBy/Nbn";
		ind1 = new Indicator();
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "National bibliography number", "R",
			"q", "Qualifying information", "R",
			"z", "Canceled/invalid national bibliography number", "R",
			"2", "Source", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(NationalBibliographyNumberSourceCodes.getInstance());
		getSubfield("a").setBibframeTag("rdf:value");
		getSubfield("q").setBibframeTag("qualifier");
		getSubfield("z").setMqTag("canceled");
		getSubfield("2").setBibframeTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
