package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.definition.controlsubfields.Control007Subfields;

/**
 * Control Number Identifier
 * https://www.loc.gov/marc/bibliographic/bd007.html
 */
public class Control007 extends ControlFieldDefinition {

	private static Control007 uniqueInstance;

	private Control007() {
		initialize();
		postCreation();
	}

	public static Control007 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Control007();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "007";
		label = "Physical Description";
		mqTag = "PhysicalDescription";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007.html";
		subfields = Control007Subfields.getSubfields();
	}
}
