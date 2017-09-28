package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Production, Publication, Distribution, Manufacture, and Copyright Notice
 * http://www.loc.gov/marc/bibliographic/bd264.html
 */
public class Tag264 extends DataFieldDefinition {

	private static Tag264 uniqueInstance;

	private Tag264() {
		initialize();
	}

	public static Tag264 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag264();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "264";
		label = "Production, Publication, Distribution, Manufacture, and Copyright Notice";
		mqTag = "ProvisionActivity";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Sequence of statements").setCodes(
			" ", "Not applicable/No information provided/Earliest",
			"2", "Intervening",
			"3", "Current/Latest"
		).setMqTag("sequenceOfStatements");
		ind2 = new Indicator("Function of entity").setCodes(
			"0", "Production",
			"1", "Publication",
			"2", "Distribution",
			"3", "Manufacture",
			"4", "Copyright notice date"
		).setMqTag("function");

		setSubfieldsWithCardinality(
			"a", "Place of production, publication, distribution, manufacture", "R",
			"b", "Name of producer, publisher, distributor, manufacturer", "R",
			"c", "Date of production, publication, distribution, manufacture, or copyright notice", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("place");
		getSubfield("b").setBibframeTag("agent");
		getSubfield("c").setBibframeTag("date");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setMqTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
