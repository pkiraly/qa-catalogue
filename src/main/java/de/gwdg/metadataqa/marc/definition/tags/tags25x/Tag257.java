package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

/**
 * Country of Producing Entity
 * http://www.loc.gov/marc/bibliographic/bd257.html
 */
public class Tag257 extends DataFieldDefinition {
	private static Tag257 uniqueInstance;

	private Tag257() {
		initialize();
		postCreation();
	}

	public static Tag257 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag257();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "257";
		label = "Country of Producing Entity";
		mqTag = "Country";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Country of producing entity", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

		getSubfield("a").setBibframeTag("Place").setMqTag("rdf:value");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setMqTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
