package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Machine-generated Metadata Provenance
 * http://www.loc.gov/marc/bibliographic/bd883.html
 */
public class Tag883 extends DataFieldDefinition {

	private static Tag883 uniqueInstance;

	private Tag883() {
		initialize();
	}

	public static Tag883 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag883();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "883";
		label = "Machine-generated Metadata Provenance";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Method of machine assignment").setCodes(
			" ", "No information provided/not applicable",
			"0", "Fully machine-generated",
			"1", "Partially machine-generated"
		).setMqTag("methodOfMachineAssignment");
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Generation process", "NR",
			"c", "Confidence value", "NR",
			"d", "Generation date", "NR",
			"q", "Generation agency", "NR",
			"x", "Validity end date", "NR",
			"u", "Uniform Resource Identifier", "NR",
			"w", "Bibliographic record control number", "R",
			"0", "Authority record control number or standard number", "R",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("q").setCodeList(OrganizationCodes.getInstance());
	}
}
