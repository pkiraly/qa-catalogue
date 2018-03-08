package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.definition.controlsubfields.Control008Subfields;

/**
 * Control Number Identifier
 * https://www.loc.gov/marc/bibliographic/bd008.html
 */
public class Control008 extends ControlFieldDefinition {

	private static Control008 uniqueInstance;

	private Control008() {
		initialize();
		postCreation();
	}

	public static Control008 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Control008();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "008";
		label = "General Information";
		mqTag = "GeneralInformation";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008.html";
		subfields = Control008Subfields.getSubfields();
	}
}
