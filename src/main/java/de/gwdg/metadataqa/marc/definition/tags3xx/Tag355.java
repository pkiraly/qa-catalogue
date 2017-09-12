package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Security Classification Control
 * http://www.loc.gov/marc/bibliographic/bd355.html
 */
public class Tag355 extends DataFieldDefinition {
	private static Tag355 uniqueInstance;

	private Tag355(){
		initialize();
	}

	public static Tag355 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag355();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "355";
		label = "Security Classification Control";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Controlled element").setCodes(
				"0", "Document",
				"1", "Title",
				"2", "Abstract",
				"3", "Contents note",
				"4", "Author",
				"5", "Record",
				"8", "None of the above"
		);
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Security classification", "NR",
				"b", "Handling instructions", "R",
				"c", "External dissemination information", "R",
				"d", "Downgrading or declassification event", "NR",
				"e", "Classification system", "NR",
				"f", "Country of origin code", "NR",
				"g", "Downgrading date", "NR",
				"h", "Declassification date", "NR",
				"j", "Authorization", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		getSubfield("f").setCodeList(CountryCodes.getInstance());
		getSubfield("j").setCodeList(OrganizationCodes.getInstance());
	}
}
