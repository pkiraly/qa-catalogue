package de.gwdg.metadataqa.marc.definition.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag260 extends DataFieldDefinition {

	private static Tag260 uniqueInstance;

	private Tag260() {
		initialize();
	}

	public static Tag260 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag260();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "260";
		label = "Publication, Distribution, etc. (Imprint)";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Sequence of publishing statements").setCodes(
			" ", "Not applicable/No information provided/Earliest available publisher",
			"2", "Intervening publisher",
			"3", "Current/latest publisher"
		);
		ind2 = new Indicator("").setCodes(" ", "Undefined");
		setSubfieldsWithCardinality(
			"a", "Place of publication, distribution, etc.", "R",
			"b", "Name of publisher, distributor, etc.", "R",
			"c", "Date of publication, distribution, etc.", "R",
			"e", "Place of manufacture", "R",
			"f", "Manufacturer", "R",
			"g", "Date of manufacture", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
