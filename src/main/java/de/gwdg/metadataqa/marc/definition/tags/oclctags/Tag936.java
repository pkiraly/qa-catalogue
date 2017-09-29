package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * CONSER/OCLC Miscellaneous Data
 * http://www.oclc.org/bibformats/en/9xx/936.html
 */
public class Tag936 extends DataFieldDefinition {

	private static Tag936 uniqueInstance;

	private Tag936() {
		initialize();
	}

	public static Tag936 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag936();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "936";
		label = "CONSER/OCLC Miscellaneous Data";
		mqTag = "ConserOrOclcMiscellaneousData";
		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "CONSER/OCLC miscellaneous data", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
