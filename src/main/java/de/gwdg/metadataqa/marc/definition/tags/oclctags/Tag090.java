package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally Assigned LC-type Call Number
 * http://www.oclc.org/bibformats/en/0xx/090.html
 */
public class Tag090 extends DataFieldDefinition {

	private static Tag090 uniqueInstance;

	private Tag090() {
		initialize();
	}

	public static Tag090 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag090();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "090";
		label = "Locally Assigned LC-type Call Number";
		mqTag = "LocallyAssignedCallNumber";
		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Classification number", "R",
			"b", "Local Cutter number", "NR",
			"e", "Feature heading", "NR",
			"f", "Filing suffix", "NR"
		);

		getSubfield("a").setMqTag("classificationNumber");
		getSubfield("b").setMqTag("cutterNumber");
		getSubfield("e").setMqTag("featureHeading");
		getSubfield("f").setMqTag("filingSuffix");
	}
}
