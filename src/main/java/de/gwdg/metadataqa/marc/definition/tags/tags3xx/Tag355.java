package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

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

	private Tag355() {
		initialize();
		postCreation();
	}

	public static Tag355 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag355();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "355";
		label = "Security Classification Control";
		mqTag = "SecurityClassificationControl";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd355.html";

		ind1 = new Indicator("Controlled element")
			.setCodes(
				"0", "Document",
				"1", "Title",
				"2", "Abstract",
				"3", "Contents note",
				"4", "Author",
				"5", "Record",
				"8", "None of the above"
			)
			.setMqTag("controlledElement");
		ind2 = new Indicator();

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

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("b").setMqTag("handlingInstructions");
		getSubfield("c").setMqTag("externalDissemination");
		getSubfield("d").setMqTag("downgradingEvent");
		getSubfield("e").setMqTag("classificationSystem");
		getSubfield("f").setMqTag("countryOfOrigin");
		getSubfield("g").setMqTag("downgradingDate");
		getSubfield("h").setMqTag("declassificationDate");
		getSubfield("j").setBibframeTag("authorization");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
