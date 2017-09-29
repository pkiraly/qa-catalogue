package de.gwdg.metadataqa.marc.definition.tags.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;

/**
 * Added Entry - Uncontrolled Name
 * http://www.loc.gov/marc/bibliographic/bd720.html
 */
public class Tag720 extends DataFieldDefinition {

	private static Tag720 uniqueInstance;

	private Tag720() {
		initialize();
	}

	public static Tag720 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag720();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "720";
		label = "Added Entry - Uncontrolled Name";
		mqTag = "UncontrolledName";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Type of name").setCodes(
			" ", "Not specified",
			"1", "Personal",
			"2", "Other"
		).setMqTag("type");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Name", "NR",
			"e", "Relator term", "R",
			"4", "Relationship", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("4").setCodeList(RelatorCodes.getInstance());

		getSubfield("4").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("4").setMqTag("relator");
		getSubfield("4").setMqTag("relationship");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");	}
}
