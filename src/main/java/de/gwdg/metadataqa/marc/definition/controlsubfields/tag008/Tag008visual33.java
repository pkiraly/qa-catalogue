package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Type of visual material
 * https://www.loc.gov/marc/bibliographic/bd008v.html
 */
public class Tag008visual33 extends ControlSubfield {
	private static Tag008visual33 uniqueInstance;

	private Tag008visual33() {
		initialize();
		extractValidCodes();
	}

	public static Tag008visual33 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008visual33();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Type of visual material";
		id = "tag008visual33";
		mqTag = "typeOfVisualMaterial";
		positionStart = 33;
		positionEnd = 34;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008v.html";
		codes = Utils.generateCodes(
			"a", "Art original",
			"b", "Kit",
			"c", "Art reproduction",
			"d", "Diorama",
			"f", "Filmstrip",
			"g", "Game",
			"i", "Picture",
			"k", "Graphic",
			"l", "Technical drawing",
			"m", "Motion picture",
			"n", "Chart",
			"o", "Flash card",
			"p", "Microscope slide",
			"q", "Model",
			"r", "Realia",
			"s", "Slide",
			"t", "Transparency",
			"v", "Videorecording",
			"w", "Toy",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}