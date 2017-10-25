package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Formatted Contents Note
 * http://www.loc.gov/marc/bibliographic/bd505.html
 */
public class Tag505 extends DataFieldDefinition {

	private static Tag505 uniqueInstance;

	private Tag505() {
		initialize();
		postCreation();
	}

	public static Tag505 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag505();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "505";
		label = "Formatted Contents Note";
		bibframeTag = "TableOfContents";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Display constant controller")
			.setCodes(
				"0", "Contents",
				"1", "Incomplete contents",
				"2", "Partial contents",
				"8", "No display constant generated"
			)
			.setMqTag("displayConstant");
		ind2 = new Indicator("Level of content designation")
			.setCodes(
				" ", "Basic",
				"0", "Enhanced"
			)
			.setMqTag("level");

		setSubfieldsWithCardinality(
			"a", "Formatted contents note", "NR",
			"g", "Miscellaneous information", "R",
			"r", "Statement of responsibility", "R",
			"t", "Title", "R",
			"u", "Uniform Resource Identifier", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("g").setMqTag("miscellaneous");
		getSubfield("r").setMqTag("responsibility");
		getSubfield("t").setMqTag("title");
		getSubfield("u").setMqTag("uri");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
