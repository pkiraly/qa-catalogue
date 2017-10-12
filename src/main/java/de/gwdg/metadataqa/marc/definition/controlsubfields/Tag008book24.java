package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Nature of contents
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag008book24 extends ControlSubfield {
	private static Tag008book24 uniqueInstance;

	private Tag008book24() {
		initialize();
		extractValidCodes();
	}

	public static Tag008book24 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008book24();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Nature of contents";
		id = "tag008book24";
		mqTag = "natureOfContents";
		positionStart = 24;
		positionEnd = 28;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008b.html";
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