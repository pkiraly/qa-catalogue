package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

/**
 * Date/Time and Place of an Event Note
 * http://www.loc.gov/marc/bibliographic/bd518.html
 */
public class Tag518 extends DataFieldDefinition {

	private static Tag518 uniqueInstance;

	private Tag518(){
		initialize();
	}

	public static Tag518 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag518();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "518";
		label = "Date/Time and Place of an Event Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Date/time and place of an event note", "NR",
				"d", "Date of event", "R",
				"o", "Other event information", "R",
				"p", "Place of event", "R",
				"0", "Record control number", "R",
				"2", "Source of term", "R",
				"3", "Materials specified", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());
	}
}
