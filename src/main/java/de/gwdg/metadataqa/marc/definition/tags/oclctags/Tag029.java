package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

import java.util.Arrays;

/**
 * Other System Control Number
 * http://www.oclc.org/bibformats/en/0xx/029.html
 */
public class Tag029 extends DataFieldDefinition {

	private static Tag029 uniqueInstance;

	private Tag029() {
		initialize();
		postCreation();
	}

	public static Tag029 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag029();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "029";
		label = "Other System Control Number";
		bibframeTag = "IdentifiedBy/Lccn";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://www.oclc.org/bibformats/en/0xx/029.html";

		ind1 = new Indicator("The type of system control number")
			.setCodes(
				"0", "Primary control number",
				"1", "Secondary control number"
			)
			.putAdditionalSubfields("dnb", Arrays.asList(
				new Code("a", "ISSN formal richtig"),
				new Code("b", "ISSN formal falsch")
			))
			.setMqTag("type");
		ind2 = new Indicator()
			.putAdditionalSubfields("dnb", Arrays.asList(
				new Code(" ", "Nicht spezifiziert (bei fehlerhaften ISSN)"),
				new Code("a", "Autorisierte ISSN"),
				new Code("b", "ISSN der Ausgabe auf anderem Datenträger"),
				new Code("c", "ISSN der Internet-Ausgabe"),
				new Code("d", "ISSN der Druck-Ausgabe")
			));

		setSubfieldsWithCardinality(
			"a", "OCLC library identifier", "NR",
			"b", "System control number", "NR",
			"c", "OAI set name", "NR",
			"t", "Content type identifier", "NR"
		);

		getSubfield("a").setMqTag("oclcLibraryIdentifier");
		getSubfield("b").setMqTag("otherSystemControlNumber");
		getSubfield("c").setMqTag("oaiSet");
		getSubfield("t").setMqTag("contentTypeIdentifier");
	}
}
