package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * CONSER Fixed Length Field
 * http://www.oclc.org/bibformats/en/0xx/012.html
 * Library of Congress (LC) and CONSER use field 012 for control elements that are not
 * accommodated in the fixed field. All other users can delete field 012 in their locally
 * edited copy of the record. Field 012 is not part of the standard MARC 21 Bibliographic
 * format. For more information about field 012, see the CONSER Editing Guide.
 */
public class Tag012 extends DataFieldDefinition {

	private static Tag012 uniqueInstance;

	private Tag012() {
		initialize();
	}

	public static Tag012 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag012();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "012";
		label = "CONSER Fixed Length Field";
		mqTag = "CONSER";
		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Priority byte", "NR",
			"b", "Non-permanent distribution code", "NR",
			"c", "Major/minor change byte", "NR",
			"d", "Permanent distribution code", "NR",
			"e", "Special LC projects", "NR",
			"f", "OCAT certification", "NR",
			"g", "Type of cataloging code", "NR",
			"h", "Non-established name indicator", "NR",
			"i", "NST publication date code", "NR",
			"j", "ISSN distribution", "NR",
			"k", "ISSN on publication", "NR",
			"l", "Communication with publisher", "NR",
			"m", "Communication with USPS", "NR",
			"z", "Record status overrider", "NR"
		);

		getSubfield("a").setMqTag("priorityByte");
		getSubfield("b").setMqTag("nonPermanentDistribution");
		getSubfield("c").setMqTag("change");
		getSubfield("d").setMqTag("permanentDistribution");
		getSubfield("e").setMqTag("lcProjects");
		getSubfield("f").setMqTag("ocatCertification");
		getSubfield("g").setMqTag("typeOfCatalogingCode");
		getSubfield("h").setMqTag("nonEstablishedNameIndicator");
		getSubfield("i").setMqTag("nstPublicationDateCode");
		getSubfield("j").setMqTag("issnDistribution");
		getSubfield("k").setMqTag("issnOnPublication");
		getSubfield("l").setMqTag("communicationWithPublisher");
		getSubfield("m").setMqTag("communicationWithUSPS");
		getSubfield("z").setMqTag("recordStatusOverrider");
	}
}
