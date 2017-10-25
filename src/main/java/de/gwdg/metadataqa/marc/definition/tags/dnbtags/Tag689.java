package de.gwdg.metadataqa.marc.definition.tags.dnbtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * RSWK-Kette
 * http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010
 */
public class Tag689 extends DataFieldDefinition {

	private static Tag689 uniqueInstance;

	private Tag689() {
		initialize();
		postCreation();
	}

	public static Tag689 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag689();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "689";
		label = "RSWK-Kette";
		mqTag = "RSWKKette";
		cardinality = Cardinality.Repeatable;
		// descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

		ind1 = new Indicator("Nummer der RSWK-Kette")
			.setCodes(
				"0-9", "Nummer der RSWK-Kette"
			)
			.setMqTag("nummerDerRSWKKette");
		ind1.getCode("0-9").setRange(true);
		ind2 = new Indicator("Nummer des Kettengliedes")
			.setCodes(
				"0-9", "Nummer des Kettengliedes"
			)
			.setMqTag("nummerDesKettengliedes");
		ind2.getCode("0-9").setRange(true);

		setSubfieldsWithCardinality(
			"A", "Indikator des Kettengliedes", "R",
			"B", "Permutationsmuster", "R",
			"C", "Bemerkungen", "R",
			"D", "Repräsentation der MARC-Feldnummer", "R",
			"0", "Authority record control number(IDN des Normdatensatzes)", "R",
			"5", "Herkunft", "R"
		);

		getSubfield("A").setCodes(
			"f", "Formschlagwort",
			"g", "geographischer Unterbegriff",
			"z", "Zeitschlagwort"
		);
		getSubfield("D").setCodes(
			"b", "Körperschaft",
			"f", "Kongress",
			"g", "Geografikum",
			"n", "Person (nicht individualisiert)",
			"p", "Person (individualisiert)",
			"s", "Sachbegriff",
			"u", "Werk (nur bei Werken ohne geistige Schöpfer)"
		);

		getSubfield("A").setMqTag("rdf:value");
	}
}
