package de.gwdg.metadataqa.marc.definition.tags.genttags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Unterreihenangaben in strukturierter Form - ZDB
 * http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010
 */
public class Tag920 extends DataFieldDefinition {

	private static Tag920 uniqueInstance;

	private Tag920() {
		initialize();
		postCreation();
	}

	public static Tag920 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag920();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "920";
		label = "Used in the union catalog of Belgium";
		mqTag = "DefinedForUnionCatalogOfBelgium";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Value", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
