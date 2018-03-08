package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.definition.controlsubfields.Control006Subfields;

/**
 * Control Number Identifier
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Control006 extends ControlFieldDefinition {

	private static Control006 uniqueInstance;

	private Control006() {
		initialize();
		postCreation();
	}

	public static Control006 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Control006();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "006";
		label = "Additional Material Characteristics";
		mqTag = "AdditionalMaterialCharacteristics";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
		subfields = Control006Subfields.getSubfields();
	}
}
