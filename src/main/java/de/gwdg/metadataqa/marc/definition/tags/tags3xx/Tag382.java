package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.MusicalInstrumentationAndVoiceSourceCodes;

/**
 * Medium of Performance
 * http://www.loc.gov/marc/bibliographic/bd382.html
 */
public class Tag382 extends DataFieldDefinition {
	private static Tag382 uniqueInstance;

	private Tag382() {
		initialize();
		postCreation();
	}

	public static Tag382 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag382();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "382";
		label = "Medium of Performance";
		bibframeTag = "MusicMedium";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd382.html";

		ind1 = new Indicator("Display constant controller")
			.setCodes(
				" ", "No information provided",
				"0", "Medium of performance",
				"1", "Partial medium of performance"
			)
			.setMqTag("displayConstant");
		ind2 = new Indicator("Access control")
			.setCodes(
				" ", "No information provided",
				"0", "Not intended for access",
				"1", "Intended for access"
			)
			.setMqTag("accessControl");

		setSubfieldsWithCardinality(
			"a", "Medium of performance", "R",
			"b", "Soloist", "R",
			"d", "Doubling instrument", "R",
			"e", "Number of ensembles of the same type", "R",
			"n", "Number of performers of the same medium", "R",
			"p", "Alternative medium of performance", "R",
			"r", "Total number of individuals performing alongside ensembles", "NR",
			"s", "Total number of performers", "NR",
			"t", "Total number of ensembles", "NR",
			"v", "Note", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source of term", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("2").setCodeList(MusicalInstrumentationAndVoiceSourceCodes.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("b").setMqTag("Soloist");
		getSubfield("d").setMqTag("doublingInstrument");
		getSubfield("e").setMqTag("numberOfEnsembles");
		getSubfield("n").setMqTag("numberOfPerformers");
		getSubfield("p").setMqTag("alternativeMedium");
		getSubfield("r").setMqTag("totalNumberOfIndividuals");
		getSubfield("s").setMqTag("totalNumberOfPerformers");
		getSubfield("t").setMqTag("totalNumberOfEnsembles");
		getSubfield("v").setMqTag("note");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setMqTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
