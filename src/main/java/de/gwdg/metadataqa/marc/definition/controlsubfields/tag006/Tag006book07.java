package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Nature of contents
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006book07 extends ControlSubfield {
	private static Tag006book07 uniqueInstance;

	private Tag006book07() {
		initialize();
		extractValidCodes();
	}

	public static Tag006book07 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006book07();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Nature of contents";
		id = "tag006book07";
		mqTag = "natureOfContents";
		positionStart = 7;
		positionEnd = 11;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
		codes = Utils.generateCodes(
			" ", "No specified nature of contents",
			"a", "Abstracts/summaries",
			"b", "Bibliographies",
			"c", "Catalogs",
			"d", "Dictionaries",
			"e", "Encyclopedias",
			"f", "Handbooks",
			"g", "Legal articles",
			"i", "Indexes",
			"j", "Patent document",
			"k", "Discographies",
			"l", "Legislation",
			"m", "Theses",
			"n", "Surveys of literature in a subject area",
			"o", "Reviews"
		);
		repeatableContent = true;
		unitLength = 1;
	}
}