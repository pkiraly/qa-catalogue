package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Manifestation identifier
 * no documentation only practice ;-)
 */
public class Tag911 extends DataFieldDefinition {

	private static Tag911 uniqueInstance;

	private Tag911() {
		initialize();
		postCreation();
	}

	public static Tag911 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag911();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "911";
		label = "Manifestation identifier";
		mqTag = "ManifestationIdentifier";
		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"9", "OCLC manifestation identifier", "NR"
		);

		getSubfield("9").setMqTag("rdf:value");
	}
}
