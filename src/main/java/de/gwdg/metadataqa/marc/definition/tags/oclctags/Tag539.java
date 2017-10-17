package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.oclc.FormOfItem;
import de.gwdg.metadataqa.marc.definition.general.codelist.oclc.Frequency;
import de.gwdg.metadataqa.marc.definition.general.codelist.oclc.Regularity;
import de.gwdg.metadataqa.marc.definition.general.codelist.oclc.TypeOfDateOrPublicationStatus;

/**
 * Fixed-Length Data Elements of Reproduction Note
 * http://www.oclc.org/bibformats/en/5xx/539.html
 */
public class Tag539 extends DataFieldDefinition {

	private static Tag539 uniqueInstance;

	private Tag539() {
		initialize();
		postCreation();
	}

	public static Tag539 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag539();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "539";
		label = "Fixed-Length Data Elements of Reproduction Note";
		mqTag = "Reproduction";
		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator("The type of system control number").setCodes(
			"0", "Primary control number",
			"1", "Secondary control number"
		).setMqTag("controlNumberType");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Type of date/Publication status", "NR",
			"b", "Date 1/Beginning date of publication", "NR",
			"c", "Date 2/Ending date of publication", "NR",
			"d", "Place of publication, production, or execution", "NR",
			"e", "Frequency", "NR",
			"f", "Regularity", "NR",
			"g", "Form of item", "NR"
		);
		// TODO:
		// $b - yyyy
		// $c - yyyy
		// $d - code from Ctry, http://www.oclc.org/bibformats/en/fixedfield/ctry.html
		getSubfield("a").setCodeList(TypeOfDateOrPublicationStatus.getInstance());
		getSubfield("e").setCodeList(Frequency.getInstance());
		getSubfield("f").setCodeList(Regularity.getInstance());
		getSubfield("g").setCodeList(FormOfItem.getInstance());

		getSubfield("a").setMqTag("type");
		getSubfield("b").setMqTag("date1");
		getSubfield("c").setMqTag("date2");
		getSubfield("d").setMqTag("place");
		getSubfield("e").setMqTag("frequency");
		getSubfield("f").setMqTag("regularity");
		getSubfield("g").setMqTag("form");
	}
}
