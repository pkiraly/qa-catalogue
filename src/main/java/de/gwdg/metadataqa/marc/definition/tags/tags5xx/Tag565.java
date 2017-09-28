package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Case File Characteristics Note
 * http://www.loc.gov/marc/bibliographic/bd565.html
 */
public class Tag565 extends DataFieldDefinition {

	private static Tag565 uniqueInstance;

	private Tag565() {
		initialize();
	}

	public static Tag565 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag565();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "565";
		label = "Case File Characteristics Note";
		mqTag = "CaseFileCharacteristics";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Display constant controller").setCodes(
			" ", "File size",
			"0", "Case file characteristics",
			"8", "No display constant generated"
		).setMqTag("displayConstant");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Number of cases/variables", "NR",
			"b", "Name of variable", "R",
			"c", "Unit of analysis", "R",
			"d", "Universe of data", "R",
			"e", "Filing scheme or code", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setMqTag("numberOfCases");
		getSubfield("b").setMqTag("variableName");
		getSubfield("c").setMqTag("analysisUnit");
		getSubfield("d").setMqTag("universeOfData");
		getSubfield("e").setMqTag("filingScheme");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
