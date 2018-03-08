package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Target audience
 * https://www.loc.gov/marc/bibliographic/bd008c.html
 */
public class Tag008computer22 extends ControlSubfieldDefinition {
	private static Tag008computer22 uniqueInstance;

	private Tag008computer22() {
		initialize();
		extractValidCodes();
	}

	public static Tag008computer22 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008computer22();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Target audience";
		id = "tag008computer22";
		mqTag = "targetAudience";
		positionStart = 22;
		positionEnd = 23;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008c.html";
		codes = Utils.generateCodes(
			" ", "Unknown or not specified",
			"a", "Preschool",
			"b", "Primary",
			"c", "Pre-adolescent",
			"d", "Adolescent",
			"e", "Adult",
			"f", "Specialized",
			"g", "General",
			"j", "Juvenile",
			"|", "No attempt to code"
		);
	}
}