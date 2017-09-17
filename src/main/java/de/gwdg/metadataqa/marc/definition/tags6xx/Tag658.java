package de.gwdg.metadataqa.marc.definition.tags6xx;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Index Term - Curriculum Objective
 * http://www.loc.gov/marc/bibliographic/bd658.html,
 */
public class Tag658 extends DataFieldDefinition {

	private static Tag658 uniqueInstance;

	private Tag658() {
		initialize();
	}

	public static Tag658 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag658();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "658";
		label = "Index Term - Curriculum Objective";
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Main curriculum objective", "NR",
			"b", "Subordinate curriculum objective", "R",
			"c", "Curriculum code", "NR",
			"d", "Correlation factor", "NR",
			"2", "Source of term or code", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		// TODO: $2 Curriculum Objective Term and Code Source Codes.
		// http://www.loc.gov/standards/sourcelist/curriculum-objective.html
		// getSubfield("2").setCodeList(GenreFormCodeAndTermSourceCodes.getInstance());
	}
}
