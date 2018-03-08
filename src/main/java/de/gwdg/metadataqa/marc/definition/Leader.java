package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.definition.controlsubfields.LeaderSubfields;

/**
 * Leader
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader extends ControlFieldDefinition {

	private static Leader uniqueInstance;

	private Leader() {
		initialize();
		postCreation();
	}

	public static Leader getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Leader();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "Leader";
		label = "Leader";
		mqTag = "Leader";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
		subfields = LeaderSubfields.getSubfields();
	}

}
