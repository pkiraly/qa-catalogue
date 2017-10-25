package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.MusicalInstrumentationAndVoiceSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.MusicalInstrumentsOrVoicesCodes;

/**
 * Number of Musical Instruments or Voices Codes
 * http://www.loc.gov/marc/bibliographic/bd048.html
 */
public class Tag048 extends DataFieldDefinition {

	private static Tag048 uniqueInstance;

	private Tag048() {
		initialize();
		postCreation();
	}

	public static Tag048 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag048();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "048";
		label = "Number of Musical Instruments or Voices Codes";
		mqTag = "NumberOfMusicalInstrumentsOrVoices";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator()
			.setHistoricalCodes(
				"0", "One performer to a part",
				"1", "More than one performer to some parts or all parts",
				"2", "Soloist with type of ensemble referred to in 1 above"
			);
		ind2 = new Indicator("Source of code")
			.setCodes(
				" ", "MARC code",
				"7", "Source specified in subfield $2"
			)
			.setMqTag("sourceOfCode");

		setSubfieldsWithCardinality(
			"a", "Performer or ensemble", "R",
			"b", "Soloist", "R",
			"2", "Source of code", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setCodeList(MusicalInstrumentsOrVoicesCodes.getInstance());
		getSubfield("b").setCodeList(MusicalInstrumentsOrVoicesCodes.getInstance());
		getSubfield("2").setCodeList(MusicalInstrumentationAndVoiceSourceCodes.getInstance());

		getSubfield("a").setMqTag("performerOrEnsemble");
		getSubfield("b").setMqTag("soloist");
		getSubfield("2").setMqTag("source");
		getSubfield("8").setMqTag("fieldLink");
	}
}
