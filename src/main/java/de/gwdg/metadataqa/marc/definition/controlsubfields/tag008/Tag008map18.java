package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Relief
 * https://www.loc.gov/marc/bibliographic/bd008p.html
 */
public class Tag008map18 extends ControlSubfield {
	private static Tag008map18 uniqueInstance;

	private Tag008map18() {
		initialize();
		extractValidCodes();
	}

	public static Tag008map18 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008map18();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Relief";
		id = "tag008map18";
		mqTag = "relief";
		positionStart = 18;
		positionEnd = 22;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008p.html";
		codes = Utils.generateCodes(
			" ", "No relief shown",
			"a", "Contours",
			"b", "Shading",
			"c", "Gradient and bathymetric tints",
			"d", "Hachures",
			"e", "Bathymetry/soundings",
			"f", "Form lines",
			"g", "Spot heights",
			"i", "Pictorially",
			"j", "Land forms",
			"k", "Bathymetry/isolines",
			"m", "Rock drawings",
			"z", "Other",
			"|", "No attempt to code"
		);

		repeatableContent = true;
		unitLength = 1;
	}
}