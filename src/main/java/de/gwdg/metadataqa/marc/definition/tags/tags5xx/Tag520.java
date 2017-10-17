package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ContentAdviceClassificationSourceCodes;

/**
 * Summary, etc.
 * http://www.loc.gov/marc/bibliographic/bd520.html
 */
public class Tag520 extends DataFieldDefinition {

	private static Tag520 uniqueInstance;

	private Tag520() {
		initialize();
		postCreation();
	}

	public static Tag520 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag520();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "520";
		label = "Summary, etc.";
		bibframeTag = "Summary";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Display constant controller").setCodes(
			" ", "Summary",
			"0", "Subject",
			"1", "Review",
			"2", "Scope and content",
			"3", "Abstract",
			"4", "Content advice",
			"8", "No display constant generated"
		).setMqTag("displayConstant");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Summary, etc.", "NR",
			"b", "Expansion of summary note", "NR",
			"c", "Assigning source", "NR",
			"u", "Uniform Resource Identifier", "R",
			"2", "Source", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("2").setCodeList(ContentAdviceClassificationSourceCodes.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("b").setMqTag("expansion");
		getSubfield("c").setMqTag("assigningSource");
		getSubfield("u").setMqTag("uri");
		getSubfield("2").setMqTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
