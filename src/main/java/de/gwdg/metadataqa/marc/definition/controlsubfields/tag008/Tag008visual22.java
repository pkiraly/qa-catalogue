package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Target audience
 * https://www.loc.gov/marc/bibliographic/bd008v.html
 */
public class Tag008visual22 extends ControlSubfield {
	private static Tag008visual22 uniqueInstance;

	private Tag008visual22() {
		initialize();
		extractValidCodes();
	}

	public static Tag008visual22 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008visual22();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Target audience";
		id = "tag008visual22";
		mqTag = "targetAudience";
		positionStart = 22;
		positionEnd = 23;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008v.html";
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