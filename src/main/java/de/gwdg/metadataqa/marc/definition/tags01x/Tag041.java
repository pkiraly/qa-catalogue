package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodeAndTermSourceCodes;

/**
 * Language Code
 * http://www.loc.gov/marc/bibliographic/bd041.html
 */
public class Tag041 extends DataFieldDefinition {

	private static Tag041 uniqueInstance;

	private Tag041(){
		initialize();
	}

	public static Tag041 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag041();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "041";
		label = "Language Code";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Translation indication").setCodes(
				" ", "No information provided",
				"0", "Item not a translation/does not include a translation",
				"1", "Item is or includes a translation"
		);
		ind2 = new Indicator("Source of code").setCodes(
				" ", "MARC language code",
				"7", "Source specified in subfield $2"
		);
		setSubfieldsWithCardinality(
				"a", "Language code of text/sound track or separate title", "R",
				"b", "Language code of summary or abstract", "R",
				"d", "Language code of sung or spoken text", "R",
				"e", "Language code of librettos", "R",
				"f", "Language code of table of contents", "R",
				"g", "Language code of accompanying material other than librettos", "R",
				"h", "Language code of original", "R",
				"j", "Language code of subtitles or captions", "R",
				"k", "Language code of intermediate translations", "R",
				"m", "Language code of original accompanying materials other than librettos", "R",
				"n", "Language code of original libretto", "R",
				"2", "Source of code", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		getSubfield("a").setCodeList(LanguageCodes.getInstance());
		getSubfield("b").setCodeList(LanguageCodes.getInstance());
		getSubfield("d").setCodeList(LanguageCodes.getInstance());
		getSubfield("e").setCodeList(LanguageCodes.getInstance());
		getSubfield("f").setCodeList(LanguageCodes.getInstance());
		getSubfield("g").setCodeList(LanguageCodes.getInstance());
		getSubfield("h").setCodeList(LanguageCodes.getInstance());
		getSubfield("j").setCodeList(LanguageCodes.getInstance());
		getSubfield("k").setCodeList(LanguageCodes.getInstance());
		getSubfield("m").setCodeList(LanguageCodes.getInstance());
		getSubfield("n").setCodeList(LanguageCodes.getInstance());
		getSubfield("2").setCodeList(LanguageCodeAndTermSourceCodes.getInstance());
	}
}
