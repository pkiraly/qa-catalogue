package de.gwdg.metadataqa.marc.definition;

/**
 * Control Number Identifier
 * https://www.loc.gov/marc/bibliographic/bd003.html
 */
public class Control003 extends DataFieldDefinition {

	private static Control003 uniqueInstance;

	private Control003() {
		initialize();
		postCreation();
	}

	public static Control003 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Control003();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "003";
		label = "Control Number Identifier";
		mqTag = "ControlNumberIdentifier";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd003.html";
	}
}
