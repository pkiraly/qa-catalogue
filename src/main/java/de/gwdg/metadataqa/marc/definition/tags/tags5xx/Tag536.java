package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Funding Information Note
 * http://www.loc.gov/marc/bibliographic/bd536.html
 */
public class Tag536 extends DataFieldDefinition {

	private static Tag536 uniqueInstance;

	private Tag536() {
		initialize();
	}

	public static Tag536 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag536();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "536";
		label = "Funding Information Note";
		bibframeTag = "FundingInformation";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Text of note", "NR",
			"b", "Contract number", "R",
			"c", "Grant number", "R",
			"d", "Undifferentiated number", "R",
			"e", "Program element number", "R",
			"f", "Project number", "R",
			"g", "Task number", "R",
			"h", "Work unit number", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("b").setMqTag("contract");
		getSubfield("c").setMqTag("grant");
		getSubfield("d").setMqTag("undifferentiatedNumber");
		getSubfield("e").setMqTag("program");
		getSubfield("f").setMqTag("project");
		getSubfield("g").setMqTag("tast");
		getSubfield("h").setMqTag("workUnit");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
