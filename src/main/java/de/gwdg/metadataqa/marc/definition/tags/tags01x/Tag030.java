package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * CODEN Designation
 * http://www.loc.gov/marc/bibliographic/bd030.html
 * <p>
 * CODEN is a unique identifier for scientific and technical periodical titles; it consists of
 * six characters. The CODEN is assigned by the International CODEN Section of Chemical Abstracts Service.
 */
public class Tag030 extends DataFieldDefinition {

	private static Tag030 uniqueInstance;

	private Tag030() {
		initialize();
		postCreation();
	}

	public static Tag030 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag030();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "030";
		label = "CODEN Designation";
		bibframeTag = "Coden";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd030.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Publisher or distributor number", "NR",
			"z", "Canceled/invalid CODEN", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("rdf:value");
		getSubfield("z").setBibframeTag("canceled");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
