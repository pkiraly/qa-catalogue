package de.gwdg.metadataqa.marc.definition.tags.tags80x;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Series Added Entry - Personal Name
 * http://www.loc.gov/marc/bibliographic/bd800.html
 */
public class Tag800 extends DataFieldDefinition {

	private static Tag800 uniqueInstance;

	private Tag800() {
		initialize();
		postCreation();
	}

	public static Tag800 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag800();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "800";
		label = "Series Added Entry - Personal Name";
		mqTag = "SeriesAddedPersonalName";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd800.html";

		ind1 = new Indicator("Type of personal name entry element")
			.setCodes(
				"0", "Forename",
				"1", "Surname",
				"3", "Family name"
			)
			.setMqTag("type");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Personal name", "NR",
			"b", "Numeration", "NR",
			"c", "Titles and other words associated with a name", "R",
			"d", "Dates associated with a name", "NR",
			"e", "Relator term", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"h", "Medium", "NR",
			"j", "Attribution qualifier", "R",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"m", "Medium of performance for music", "R",
			"n", "Number of part/section of a work", "R",
			"o", "Arranged statement for music", "NR",
			"p", "Name of part/section of a work", "R",
			"q", "Fuller form of name", "NR",
			"r", "Key for music", "NR",
			"s", "Version", "NR",
			"t", "Title of a work", "NR",
			"u", "Affiliation", "NR",
			"v", "Volume/sequential designation", "NR",
			"w", "Bibliographic record control number", "R",
			"x", "International Standard Serial Number", "NR",
			"0", "Authority record control number or standard number", "R",
			"3", "Materials specified", "NR",
			"4", "Relationship", "R",
			"5", "Institution to which field applies", "R",
			"6", "Linkage", "NR",
			"7", "Control subfield", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("4").setCodeList(RelatorCodes.getInstance());
		getSubfield("7").setPositions(Arrays.asList(
			new ControlSubfield("Type of record", 0, 1)
				.setCodes(Utils.generateCodes(
					"a", "Language material",
					"c", "Notated music",
					"d", "Manuscript notated music",
					"e", "Cartographic material",
					"f", "Manuscript cartographic material",
					"g", "Projected medium",
					"i", "Nonmusical sound recording",
					"j", "Musical sound recording",
					"k", "Two-dimensional nonprojectable graphic",
					"m", "Computer file",
					"o", "Kit",
					"p", "Mixed material",
					"r", "Three-dimensional artifact or naturally occurring object",
					"t", "Manuscript language material"
				)),
			new ControlSubfield("Bibliographic level", 1, 2)
				.setCodes(Utils.generateCodes(
					"a", "Monographic component part",
					"b", "Serial component part",
					"c", "Collection",
					"d", "Subunit",
					"i", "Integrating resource",
					"m", "Monograph/item",
					"s", "Serial"
				))
		));

		getSubfield("a").setMqTag("personalName");
		getSubfield("b").setMqTag("numeration");
		getSubfield("c").setMqTag("titlesAndWords");
		getSubfield("d").setMqTag("dates");
		getSubfield("e").setMqTag("relatorTerm");
		getSubfield("f").setMqTag("dateOfAWork");
		getSubfield("g").setMqTag("miscellaneous");
		getSubfield("h").setMqTag("medium");
		getSubfield("j").setMqTag("attributionQualifier");
		getSubfield("k").setMqTag("formSubheading");
		getSubfield("l").setMqTag("language");
		getSubfield("m").setMqTag("mediumOfPerformance");
		getSubfield("n").setMqTag("numberOfPart");
		getSubfield("o").setMqTag("arrangedStatement");
		getSubfield("p").setMqTag("nameOfPart");
		getSubfield("q").setMqTag("fullerForm");
		getSubfield("r").setMqTag("keyForMusic");
		getSubfield("s").setMqTag("version");
		getSubfield("t").setMqTag("titleOfAWork");
		getSubfield("u").setMqTag("affiliation");
		getSubfield("v").setMqTag("volumeDesignation");
		getSubfield("w").setMqTag("bibliographicRecordControlNumber");
		getSubfield("x").setMqTag("issn");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("4").setMqTag("relatorCode");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setMqTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

		putAdditionalSubfields(MarcVersion.DNB, Arrays.asList(
			new SubfieldDefinition("9", "Sortierzählung", "R")
		));
	}
}
