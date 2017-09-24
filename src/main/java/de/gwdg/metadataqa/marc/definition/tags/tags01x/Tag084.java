package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Other Classificaton Number
 * http://www.loc.gov/marc/bibliographic/bd084.html
 */
public class Tag084 extends DataFieldDefinition {

	private static Tag084 uniqueInstance;

	private Tag084() {
		initialize();
	}

	public static Tag084 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag084();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "084";
		label = "Other Classificaton Number";
		bibframeTag = "Classification";
		// mqTag = "OtherClassificatonNumber";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator();
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Classification number", "R",
			"b", "Item number", "NR",
			"q", "Assigning agency", "NR",
			"2", "Number source", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("q").setCodeList(OrganizationCodes.getInstance());
		getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());
		getSubfield("a").setBibframeTag("classificationPortion");
		getSubfield("b").setBibframeTag("itemPortion");
		getSubfield("q").setBibframeTag("assigner");
		getSubfield("2").setBibframeTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
