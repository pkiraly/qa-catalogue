package de.gwdg.metadataqa.marc.definition.tags.tags4xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;

/**
 * Series Statement/Added Entry-Personal Name
 * http://www.loc.gov/marc/bibliographic/bd400.html
 */
public class Tag400 extends DataFieldDefinition {

	private static Tag400 uniqueInstance;

	private Tag400() {
		initialize();
	}

	public static Tag400 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag400();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "400";
		label = "Series Statement/Added Entry-Personal Name";
		mqTag = "SeriesStatementAddedEntryPersonalName";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Type of personal name entry element").setCodes(
			"0", "Forename",
			"1", "Surname",
			"3", "Family name"
		).setMqTag("type");
		ind2 = new Indicator("Pronoun represents main entry").setCodes(
			"0", "Main entry not represented by pronoun",
			"9", "Main entry represented by pronoun"
		).setMqTag("pronounRepresentsMainEntry");

		setSubfieldsWithCardinality(
			"a", "Personal name", "NR",
			"b", "Numeration", "NR",
			"c", "Titles and other words associated with a name", "R",
			"d", "Dates associated with a name", "NR",
			"e", "Relator term", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "NR",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"n", "Number of part/section of a work", "R",
			"p", "Name of part/section of a work", "R",
			"t", "Title of a work", "NR",
			"u", "Affiliation", "NR",
			"v", "Volume/sequential designation", "NR",
			"x", "International Standard Serial Number", "NR",
			"4", "Relator code", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("4").setCodeList(RelatorCodes.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("b").setMqTag("numeration");
		getSubfield("c").setMqTag("titles");
		getSubfield("d").setMqTag("dates");
		getSubfield("e").setMqTag("relator");
		getSubfield("f").setMqTag("dateOfAWork");
		getSubfield("g").setMqTag("miscellaneous");
		getSubfield("k").setMqTag("formSubheading");
		getSubfield("l").setMqTag("languageOfAWork");
		getSubfield("n").setMqTag("numberOfPart");
		getSubfield("p").setMqTag("nameOfPart");
		getSubfield("t").setMqTag("title");
		getSubfield("u").setMqTag("affiliation");
		getSubfield("v").setMqTag("volume");
		getSubfield("x").setMqTag("issn");
		getSubfield("4").setMqTag("relationship");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
