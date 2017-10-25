package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Vendor-Specific Ordering Data
 * http://www.oclc.org/bibformats/en/9xx/938.html
 */
public class Tag938 extends DataFieldDefinition {

	private static Tag938 uniqueInstance;

	private Tag938() {
		initialize();
		postCreation();
	}

	public static Tag938 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag938();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "938";
		label = "Vendor-Specific Ordering Data";
		mqTag = "VendorSpecificOrderingData";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://www.oclc.org/bibformats/en/9xx/938.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Full name of vendor", "NR",
			"b", "OCLC-defined symbol for vendor", "NR",
			"c", "Terms of availability", "NR",
			"d", "Vendor net price", "NR",
			"i", "Inventory number", "NR",
			"n", "Vendor control number", "NR",
			"s", "Vendor status", "NR",
			"z", "Note", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("b").setMqTag("oclcSymbol");
		getSubfield("c").setMqTag("termsOfAvailability");
		getSubfield("d").setMqTag("vendorNetPrice");
		getSubfield("i").setMqTag("inventoryNumber");
		getSubfield("n").setMqTag("vendorControlNumber");
		getSubfield("s").setMqTag("vendorStatus");
		getSubfield("z").setMqTag("note");
	}
}
