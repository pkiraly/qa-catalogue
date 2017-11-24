package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in Gent
 */
public class Tag699 extends DataFieldDefinition {

	private static Tag699 uniqueInstance;

	private Tag699() {
		initialize();
		postCreation();
	}

	public static Tag699 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag699();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "699";
		label = "Jelleg";
		mqTag = "Feature";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Jelleg", "R",
			"x", "Korábbi (törölt) tárgykörkód", "R"
		);

		getSubfield("a").setCodes(
			"A", "Adatbázis",
			"F", "Elektronikus folyóirat",
			"I", "Ingyenes internetes forrás",
			"K", "Könyv",
			"T", "Tananyag"
		);

		getSubfield("x").setCodes(
			"A", "Adatbázis",
			"F", "Elektronikus folyóirat",
			"I", "Ingyenes internetes forrás",
			"K", "Könyv",
			"T", "Tananyag"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
