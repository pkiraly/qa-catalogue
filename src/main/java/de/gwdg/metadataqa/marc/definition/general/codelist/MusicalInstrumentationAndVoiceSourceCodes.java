package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Musical Instrumentation and Voice Code Source Codes
 * http://www.loc.gov/standards/sourcelist/musical-instrumentation.html
 */
public class MusicalInstrumentationAndVoiceSourceCodes extends CodeList {

	private void initialize() {
		name = "Musical Instrumentation and Voice Code Source Codes";
		url = "http://www.loc.gov/standards/sourcelist/musical-instrumentation.html";
		codes = Utils.generateCodes(
			"emnmus", "Emneord for musikkdokument i EDB-kataloger (Norwegian Directorate for Public and School Libraries)",
			"gnd", "Gemeinsame Normdatei (Leipzig, Frankfurt: Deutsche Nationalbibliothek)",
			"iamlmp", "International Association of Music Libraries, Medium of performance codes",
			"lcmpt", "Library of Congress medium of performance thesaurus for music (LCMPT) (Washington, DC: Library of Congress)",
			"marcmusperf", "MARC Instruments and Voices Code List",
			"rvmmem", "Thésaurus des moyens d'exécution en musique de l'Université Laval (Québec, Québec: Bibliothèque de l'Université Laval)",
			"seko", "Suomalainen esityskokoonpanosanasto"
		);
		indexCodes();
	}

	private static MusicalInstrumentationAndVoiceSourceCodes uniqueInstance;

	private MusicalInstrumentationAndVoiceSourceCodes() {
		initialize();
	}

	public static MusicalInstrumentationAndVoiceSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new MusicalInstrumentationAndVoiceSourceCodes();
		return uniqueInstance;
	}
}
