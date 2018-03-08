package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Form of material
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006all00 extends ControlSubfieldDefinition {
	private static Tag006all00 uniqueInstance;

	private Tag006all00() {
		initialize();
		extractValidCodes();
	}

	public static Tag006all00 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006all00();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Form of material";
		id = "tag006all00";
		mqTag = "formOfMaterial";
		positionStart = 0;
		positionEnd = 1;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
		codes = Utils.generateCodes(
			"a", "Language material",
			"c", "Notated music",
			"d", "Manuscript notated music",
			"e", "Cartographic material",
			"f", "Manuscript cartographic material",
			"g", "Projected medium",
			"i", "Nonmusical sound recording",
			"j", "Musical sound recording",
			"k", "Two-dimensional nonprojectable graphic",
			"m", "Computer file",
			"o", "Kit",
			"p", "Mixed materials",
			"r", "Three-dimensional artifact or naturally occurring object",
			"s", "Serial/Integrating resource",
			"t", "Manuscript language material"
		);
	}
}