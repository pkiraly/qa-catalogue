package de.gwdg.metadataqa.marc.definition.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Work identifier
 * no documentation only practice ;-)
 */
public class Tag912 extends DataFieldDefinition {

	private static Tag912 uniqueInstance;

	private Tag912() {
		initialize();
	}

	public static Tag912 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag912();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "912";
		label = "Work identifier";
		mqTag = "WorkIdentifier";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"9", "OCLC work identifier", "NR"
		);
		getSubfield("9").setMqTag("rdf:value");
	}
}
