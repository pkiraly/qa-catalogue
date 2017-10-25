package de.gwdg.metadataqa.marc.definition.tags.dnbtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Unterreihenangaben in strukturierter Form - ZDB
 * http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010
 */
public class Tag930 extends DataFieldDefinition {

	private static Tag930 uniqueInstance;

	private Tag930() {
		initialize();
		postCreation();
	}

	public static Tag930 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag930();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "930";
		label = "Unterreihenangaben in strukturierter Form - ZDB";
		mqTag = "UnterreihenangabenInStrukturierterFormZDB";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Titel der Unterreihe", "NR",
			"d", "Zusatz zum Sachtitel/Parallelsachtitel der Unterreihe", "R",
			"e", "Reihenbezeichnung und /oder Zählung", "R",
			"f", "Parallelsachtitel zur UR(@)", "R",
			"h", "Verfasserangabe zur Unterreihe", "NR",
			"l", "Reihenbezeichnung und /oder Zählung", "NR",
			"n", "Reihenbezeichnung und /oder Zählung", "NR",
			"r", "Undifferenzierter Text", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
