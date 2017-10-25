package de.gwdg.metadataqa.marc.definition.tags.dnbtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Bemerkungen zur Titelaufnahme
 * http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010
 */
public class Tag591 extends DataFieldDefinition {

	private static Tag591 uniqueInstance;

	private Tag591() {
		initialize();
		postCreation();
	}

	public static Tag591 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag591();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "591";
		label = "Bemerkungen zur Titelaufnahme";
		mqTag = "BemerkungenZurTitelaufnahme";
		cardinality = Cardinality.Nonrepeatable;
		// descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Fussnote", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
