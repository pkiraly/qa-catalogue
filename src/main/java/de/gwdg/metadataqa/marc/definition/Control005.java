package de.gwdg.metadataqa.marc.definition;

/**
 * Control Number Identifier
 * https://www.loc.gov/marc/bibliographic/bd005.html
 */
public class Control005 extends DataFieldDefinition {

	private static Control005 uniqueInstance;

	private Control005() {
		initialize();
		postCreation();
	}

	public static Control005 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Control005();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "005";
		label = "Date and Time of Latest Transaction";
		mqTag = "LatestTransactionTime";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd005.html";
	}
}
