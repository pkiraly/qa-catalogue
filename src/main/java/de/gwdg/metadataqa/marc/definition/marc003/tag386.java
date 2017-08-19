package de.gwdg.metadataqa.marc.definition.marc003;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;

import java.util.ArrayList;
import java.util.List;

public class tag386 extends DataFieldDefinition {
	static List<MarcSubfield> subfields = new ArrayList<MarcSubfield>();

	static {
		subfields.add(new MarcSubfield("a", "Creator/contributor term"));
		subfields.add(new MarcSubfield("b", "Creator/contributor code"));
		subfields.add(new MarcSubfield("i", "Relationship information"));
		subfields.add(new MarcSubfield("m", "Demographic group term"));
		subfields.add(new MarcSubfield("n", "Demographic group code"));
		subfields.add(new MarcSubfield("0", "Authority record control number or standard number"));
		subfields.add(new MarcSubfield("2", "Source"));
		subfields.add(new MarcSubfield("3", "Materials specified"));
		subfields.add(new MarcSubfield("4", "Relationship"));
		subfields.add(new MarcSubfield("6", "Linkage"));
		subfields.add(new MarcSubfield("8", "Field link and sequence number"));
	}
}
