package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Target audience
 * same as 008/22
 * https://www.loc.gov/marc/bibliographic/bd006.html
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag006book05 extends ControlSubfieldDefinition {
	private static Tag006book05 uniqueInstance;

	private Tag006book05() {
		initialize();
		extractValidCodes();
	}

	public static Tag006book05 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006book05();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Target audience";
		id = "tag006book05";
		mqTag = "targetAudience";
		positionStart = 5;
		positionEnd = 6;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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