package de.gwdg.metadataqa.marc.definition.tags.dnbtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Bestandsinformationen
 */
public class Tag924 extends DataFieldDefinition {

	private static Tag924 uniqueInstance;

	private Tag924() {
		initialize();
		postCreation();
	}

	public static Tag924 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag924();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "924";
		label = "Bestandsinformationen";
		mqTag = "Bestandsinformationen";
		cardinality = Cardinality.Repeatable;
		// descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

		ind1 = new Indicator("Art der Ressource")
			.setCodes(
				"0", "Nicht-elektronisch",
				"1", "Elektronisch"
			)
			.setMqTag("artDerRessource");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Lokale IDN des Bestandsdatensatzes", "NR",
			"b", "ISIL als Kennzeichen der besitzenden Institution", "NR",
			"c", "Leihverkehrsregion", "NR",
			"d", "Fernleihindikator", "NR",
			"e", "(Vertragsrechtliche) Einschränkungen bei der Fernleihe", "R",
			"f", "Kommentar zum Fernleihindikator", "R",
			"g", "Signatur", "R",
			"h", "Sonderstandort / Abteilung", "R",
			"i", "Sonderstandort-Signatur", "R",
			"j", "Kommentar(e) zur Signatur", "R",
			"k", "Elektronische Adresse für eine Computerdatei im Fernzugriff, Uniform Resource Identifier", "R",
			"l", "Elektronische Adresse für eine Computerdatei im Fernzugriff, Interne und Allg. Bemerkungen (zum URI)", "R",
			"m", "Normierte Bestandsangaben, Bandzählung (Beginn)", "R",
			"n", "Normierte Bestandsangaben, Heftzählung (Beginn)", "R",
			"o", "Normierte Bestandsangaben, Tageszählung (Beginn)", "R",
			"p", "Normierte Bestandsangaben, Monatszählung (Beginn)", "R",
			"q", "Normierte Bestandsangaben, Jahr (Beginn)", "R",
			"r", "Normierte Bestandsangaben, Bandzählung (Ende)", "R",
			"s", "Normierte Bestandsangaben, Heftzählung (Ende)", "R",
			"t", "Normierte Bestandsangaben, Tageszählung (Ende)", "R",
			"u", "Normierte Bestandsangaben, Monatszählung (Ende)", "R",
			"v", "Normierte Bestandsangaben, Jahr (Ende)", "R",
			"w", "Normierte Bestandsangaben, Kettung", "R",
			"x", "Normierte Bestandsangaben, Kennzeichnung \"laufender Bestand\"", "R",
			"y", "Aufbewahrungs- und Verfügbarkeitszeitraum, Moving Wall", "R",
			"z", "Zusammenfassende Bestandsangaben", "R",
			"9", "Sigel als Kennzeichen der besitzenden Institution", "R"
		);

		getSubfield("c").setCodes(
			"ANL", "Nationallizenzen für Deutschland",
			"BAW", "Leihverkehrsregion Baden-Württemberg und Saarland",
			"BAY", "Leihverkehrsregion Bayern",
			"BER", "Leihverkehrsregion Berlin und Brandenburg",
			"HAM", "Leihverkehrsregion GBV (Hamburg, Bremen, Schleswig-Holstein und Mecklenburg-Vorpommern)",
			"HES", "Leihverkehrsregion Hessen und Teile von Rheinland-Pfalz",
			"NIE", "Leihverkehrsregion GBV (Niedersachsen)",
			"NRW", "Leihverkehrsregion Nordrhein-Westfalen und Teile von Rheinland-Pfalz",
			"SAA", "Leihverkehrsregion GBV (Sachsen-Anhalt)",
			"SAX", "Leihverkehrsregion Sachsen",
			"THU", "Leihverkehrsregion GBV (Thüringen)",
			"WEU", "Bibliotheken im Ausland",
			"WWW", "Nur im Internet"
		);

		getSubfield("e").setCodes(
			"a", "Fernleihe (nur Ausleihe)",
			"e", "Fernleihe (Kopie, elektronischer Versand an Endnutzer möglich)",
			"k", "Fernleihe (Nur Kopie)",
			"l", "Fernleihe (Kopie und Ausleihe)",
			"n", "Keine Fernleihe"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
