package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.TemporalTermSourceCodes;

/**
 * Time Period of Creation
 * http://www.loc.gov/marc/bibliographic/bd388.html
 */
public class Tag388 extends DataFieldDefinition {

	private static Tag388 uniqueInstance;

	private Tag388() {
		initialize();
		postCreation();
	}

	public static Tag388 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag388();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "388";
		label = "Time Period of Creation";
		mqTag = "TimePeriodOfCreation";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd388.html";

		ind1 = new Indicator("Type of time period")
			.setCodes(
				" ", "No information provided",
				"1", "Creation of work",
				"2", "Creation of aggregate work"
			)
			.setMqTag("type");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Time period of creation term", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(TemporalTermSourceCodes.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setMqTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
