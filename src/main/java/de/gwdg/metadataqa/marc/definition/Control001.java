package de.gwdg.metadataqa.marc.definition;

/**
 * Control Number
 * https://www.loc.gov/marc/bibliographic/bd001.html
 */
public class Control001 extends DataFieldDefinition {

	private static Control001 uniqueInstance;

	private Control001() {
		initialize();
		postCreation();
	}

	public static Control001 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Control001();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "001";
		label = "Control Number";
		mqTag = "ControlNumber";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd001.html";
	}
}
