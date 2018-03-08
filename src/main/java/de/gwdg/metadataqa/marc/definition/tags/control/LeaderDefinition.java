package de.gwdg.metadataqa.marc.definition.tags.control;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.controlsubfields.LeaderSubfields;

/**
 * Leader
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class LeaderDefinition extends ControlFieldDefinition {

	private static LeaderDefinition uniqueInstance;

	private LeaderDefinition() {
		initialize();
		postCreation();
	}

	public static LeaderDefinition getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new LeaderDefinition();
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
