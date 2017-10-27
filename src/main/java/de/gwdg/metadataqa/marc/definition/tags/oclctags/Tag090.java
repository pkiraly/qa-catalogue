package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.*;

import java.util.Arrays;

/**
 * Locally Assigned LC-type Call Number
 * http://www.oclc.org/bibformats/en/0xx/090.html
 */
public class Tag090 extends DataFieldDefinition {

	private static Tag090 uniqueInstance;

	private Tag090() {
		initialize();
		postCreation();
	}

	public static Tag090 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag090();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "090";
		label = "Locally Assigned LC-type Call Number";
		mqTag = "LocallyAssignedCallNumber";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://www.oclc.org/bibformats/en/0xx/090.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Classification number", "R",
			"b", "Local Cutter number", "NR",
			"e", "Feature heading", "NR",
			"f", "Filing suffix", "NR"
		);

		getSubfield("a").setMqTag("classificationNumber");
		getSubfield("b").setMqTag("cutterNumber");
		getSubfield("e").setMqTag("featureHeading");
		getSubfield("f").setMqTag("filingSuffix");

		putVersionSpecificSubfields(MarcVersion.DNB, Arrays.asList(
			new SubfieldDefinition("i", "Angaben der Freiwilligen Selbstkontrollen der Filmwirtschaft", "NR"),
			new SubfieldDefinition("n", "Veröffentlichungsart und Inhalt", "R")
				.setCodes(
					"ao", "Zeitungen für die allgemeine Öffentlichkeit",
					"az", "Anzeigenblatt",
					"eo", "Zeitungen für eine eingeschränkte Öffentlichkeit",
					"fb", "Fortschrittsbericht",
					"ft", "Fachzeitung",
					"fz", "Firmenzeitschrift/-zeitung",
					"ha", "Haushaltsplan",
					"il", "Illustrierte",
					"lp", "Lokale Zeitungen",
					"me", "Messeblatt",
					"mg", "Magazin",
					"re", "Report-Serie",
					"rp", "Regionale Zeitungen",
					"sc", "Schulschrift",
					"ub", "Übersetzungszeitschrift",
					"up", "Überregionale Zeitungen",
					"ad", "DFG-geförderte Allianzlizenz",
					"ag", "Aggregatordatenbank",
					"al", "Allianzlizenz",
					"dm", "Digitalisierungsmaster",
					"fn", "zeitungsähnliche Periodika oder früher Zeitung (wird ab Nov. 2016 nicht mehr erfasst)",
					"fp", "früher Zeitschrift oder zeitschriftenartige Reihe",
					"fr", "früher Schriftenreihe",
					"la", "Langzeitarchivierung elektronischer Ressourcen",
					"ld", "layoutgetreue Digitalisierung",
					"mw", "mehrbändiges Werk",
					"nk", "Nationalkonsortium",
					"nl", "Nationallizenz",
					"pa", "Parlamentaria",
					"pt", "Paket-Titelaufnahme",
					"pu", "Pay-per-use-Datenbank",
					"rs", "Restitutionsbestand vorhanden",
					"sm", "sekundäre Mikroform",
					"sw", "Software",
					"wk", "Webclient-Katalogisat (m)",
					"wl", "Weblog"
				)
			)
		);
	}
}
