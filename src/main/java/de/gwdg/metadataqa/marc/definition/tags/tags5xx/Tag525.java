package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Supplement Note
 * http://www.loc.gov/marc/bibliographic/bd525.html
 */
public class Tag525 extends DataFieldDefinition {

	private static Tag525 uniqueInstance;

	private Tag525() {
		initialize();
		postCreation();
	}

	public static Tag525 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag525();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "525";
		label = "Supplement Note";
		bibframeTag = "SupplementaryContent";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd525.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Supplement note", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

		setHistoricalSubfields(
			"z", "Source of note information (SE) [OBSOLETE, 1990]"
		);
	}
}
