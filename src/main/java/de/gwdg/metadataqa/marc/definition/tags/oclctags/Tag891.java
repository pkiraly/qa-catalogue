package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Publication Pattern Data
 * http://www.oclc.org/bibformats/en/8xx/891.html
 */
public class Tag891 extends DataFieldDefinition {

	private static Tag891 uniqueInstance;

	private Tag891() {
		initialize();
		postCreation();
	}

	public static Tag891 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag891();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "891";
		label = "Publication Pattern Data";
		mqTag = "PublicationPatternData";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://www.oclc.org/bibformats/en/8xx/891.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "First level of enumeration", "NR",
			"b", "Second level of enumeration", "NR",
			"c", "Third level of enumeration", "NR",
			"d", "Fourth level of enumeration", "NR",
			"e", "Fifth level of enumeration", "NR",
			"f", "Sixth level of enumeration", "NR",
			"g", "Alternative numbering scheme, first level of enumeration", "NR",
			"h", "Alternative numbering scheme, second level of enumeration", "NR",
			"i", "First level of chronology", "NR",
			"j", "Second level of chronology", "NR",
			"k", "Third level of chronology", "NR",
			"l", "Fourth level of chronology", "NR",
			"m", "Alternative numbering scheme, chronology", "NR",
			"n", "Pattern note", "NR",
			// "n", "Converted Gregorian year", "NR",
			"o", "Type of unit", "R",
			"p", "Number of pieces per issuance", "NR",
			// "p", "Piece designation", "NR",
			"u", "Bibliographic units per next higher level", "NR",
			"v", "Numbering continuity", "R",
			// "v", "Issuing date", "R",
			"w", "Frequency", "NR",
			// "w", "Break indicator", "NR",
			"x", "Calendar change", "R",
			// "x", "Nonpublic note", "R",
			"y", "Regularity pattern", "R",
			"z", "Numbering scheme", "R",
			// "z", "Public note", "R",
			"3", "Materials specified", "NR",
			"8", "Field link and sequence number", "NR",
			"9", "Tag of the MARC holdings field", "NR"
		);

		getSubfield("a").setMqTag("firstLevelOfEnumeration");
		getSubfield("b").setMqTag("secondLevelOfEnumeration");
		getSubfield("c").setMqTag("thirdLevelOfEnumeration");
		getSubfield("d").setMqTag("fourthLevelOfEnumeration");
		getSubfield("e").setMqTag("fifthLevelOfEnumeration");
		getSubfield("f").setMqTag("sixthLevelOfEnumeration");
		getSubfield("g").setMqTag("alternativeFirstLevelOfEnumeration");
		getSubfield("h").setMqTag("alternativeSecondLevelOfEnumeration");
		getSubfield("i").setMqTag("firstLevelOfChronology");
		getSubfield("j").setMqTag("secondLevelOfChronology");
		getSubfield("k").setMqTag("thirdLevelOfChronology");
		getSubfield("l").setMqTag("fourthLevelOfChronology");
		getSubfield("m").setMqTag("alternativeNumberingSchemeChronology");
		getSubfield("n").setMqTag("patternNote");
		// getSubfield("n").setMqTag("convertedGregorianYear");
		getSubfield("o").setMqTag("typeOfUnit");
		getSubfield("p").setMqTag("numberOfPiecesPerIssuance");
		// getSubfield("p").setMqTag("pieceDesignation");
		getSubfield("u").setMqTag("bibliographicUnitsPerNextHigherLevel");
		getSubfield("v").setMqTag("numberingContinuity");
		// getSubfield("v").setMqTag("issuingDate");
		getSubfield("w").setMqTag("frequency");
		// getSubfield("w").setMqTag("breakIndicator");
		getSubfield("x").setMqTag("calendarChange");
		// getSubfield("x").setMqTag("nonpublicNote");
		getSubfield("y").setMqTag("regularityPattern");
		getSubfield("z").setMqTag("numberingScheme");
		// getSubfield("z").setMqTag("publicNote");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("8").setMqTag("fieldLink");
		getSubfield("9").setMqTag("marcHoldingsTag");
	}
}
