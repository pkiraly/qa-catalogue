package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISBNValidator;

import java.util.Arrays;

/**
 * International Standard Book Number
 * http://www.loc.gov/marc/bibliographic/bd020.html
 */
public class Tag020 extends DataFieldDefinition {

	private static Tag020 uniqueInstance;

	private Tag020() {
		initialize();
		postCreation();
	}

	public static Tag020 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag020();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "020";
		label = "International Standard Book Number";
		bibframeTag = "Isbn";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd020.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "International Standard Book Number", "NR",
			"c", "Terms of availability", "NR",
			"q", "Qualifying information", "R",
			"z", "Canceled/invalid ISBN", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		// TODO validation ISO 2108

		getSubfield("a").setValidator(ISBNValidator.getInstance());
		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setBibframeTag("rdf:value");
		getSubfield("c").setBibframeTag("acquisitionTerms");
		getSubfield("q").setBibframeTag("qualifier");
		getSubfield("z").setMqTag("canceledOrInvalidISBN");
		getSubfield("6").setMqTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

		setHistoricalSubfields(
			"b", "Binding information (BK, MP, MU) [OBSOLETE]"
		);

		putVersionSpecificSubfields(MarcVersion.DNB, Arrays.asList(
			new SubfieldDefinition("9", "ISBN mit Bindestrichen", "R")
		));
	}
}
