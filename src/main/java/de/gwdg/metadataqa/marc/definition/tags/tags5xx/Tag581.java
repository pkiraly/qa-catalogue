package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.ISBNValidator;

/**
 * Publications About Described Materials Note
 * http://www.loc.gov/marc/bibliographic/bd581.html
 */
public class Tag581 extends DataFieldDefinition {

	private static Tag581 uniqueInstance;

	private Tag581() {
		initialize();
		postCreation();
	}

	public static Tag581 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag581();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "581";
		label = "Publications About Described Materials Note";
		mqTag = "PublicationsAboutDescribedMaterials";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd581.html";

		ind1 = new Indicator("Display constant controller")
			.setCodes(
				" ", "Publications",
				"8", "No display constant generated"
			)
			.setMqTag("displayConstant");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Publications about described materials note", "NR",
			"z", "International Standard Book Number", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("z").setValidator(ISBNValidator.getInstance());

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("z").setMqTag("isbn");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
