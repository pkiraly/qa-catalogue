package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Location
 * http://www.loc.gov/marc/bibliographic/bd852.html
 */
public class Tag852 extends DataFieldDefinition {

	private static Tag852 uniqueInstance;

	private Tag852() {
		initialize();
	}

	public static Tag852 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag852();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "852";
		label = "Location";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Shelving scheme").setCodes(
			" ", "No information provided",
			"0", "Library of Congress classification",
			"1", "Dewey Decimal classification",
			"2", "National Library of Medicine classification",
			"3", "Superintendent of Documents classification",
			"4", "Shelving control number",
			"5", "Title",
			"6", "Shelved separately",
			"7", "Source specified in subfield $2",
			"8", "Other scheme"
		).setMqTag("shelvingScheme");
		ind2 = new Indicator("Shelving order").setCodes(
			" ", "No information provided",
			"0", "Not enumeration",
			"1", "Primary enumeration",
			"2", "Alternative enumeration"
		).setMqTag("shelvingOrder");
		setSubfieldsWithCardinality(
			"a", "Location", "NR",
			"b", "Sublocation or collection", "R",
			"c", "Shelving location", "R",
			"d", "Former shelving location", "R",
			"e", "Address", "R",
			"f", "Coded location qualifier", "R",
			"g", "Non-coded location qualifier", "R",
			"h", "Classification part", "NR",
			"i", "Item part", "R",
			"j", "Shelving control number", "NR",
			"k", "Call number prefix", "R",
			"l", "Shelving form of title", "NR",
			"m", "Call number suffix", "R",
			"n", "Country code", "NR",
			"p", "Piece designation", "NR",
			"q", "Piece physical condition", "NR",
			"s", "Copyright article-fee code", "R",
			"t", "Copy number", "NR",
			"u", "Uniform Resource Identifier", "R",
			"x", "Nonpublic note", "R",
			"z", "Public note", "R",
			"2", "Source of classification or shelving scheme", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("a").setCodeList(OrganizationCodes.getInstance());
		getSubfield("n").setCodeList(CountryCodes.getInstance());
		getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());
		getSubfield("f").setCodes(
			"l", "Latest",
			"p", "Previous",
			" ", "Number of units: No information provided",
			"1-9", "Number of units",
			"m", "Month(s) time",
			"w", "Week(s) time",
			"y", "Year(s) time",
			"e", "Edition(s) part",
			"i", "Issue(s) part",
			"s", "Supplement(s) part"
		).getCode("1-9").setRange(true);
	}
}
