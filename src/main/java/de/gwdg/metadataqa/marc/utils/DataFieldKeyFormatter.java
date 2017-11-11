package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;

public class DataFieldKeyFormatter {
	private DataFieldDefinition definition;
	private boolean withMarcTag;
	private String tag;
	private String indexTag;

	public DataFieldKeyFormatter(DataFieldDefinition definition, boolean withMarcTag) {
		this.definition = definition;
		this.withMarcTag = withMarcTag;
		tag = definition.getTag();
		indexTag = definition.getIndexTag();
	}

	public String forInd1() {
		String key = withMarcTag ? tag + "ind1_" : "";
		key += String.format("%s_%s", indexTag, definition.getInd1().getIndexTag());
		return key;
	}

	public String forInd2() {
		String key = withMarcTag ? tag + "ind2_" : "";
		key += String.format("%s_%s", indexTag, definition.getInd2().getIndexTag());
		return key;
	}

	public String forSubfield(MarcSubfield subfield) {
		String code = subfield.getCodeForIndex();
		String key = withMarcTag ? tag + subfield.getCode() + "_" : "";
		key += String.format("%s%s", definition.getIndexTag(), code);
		return key;
	}

	public String forSubfield(MarcSubfield subfield, String extra) {
		return String.format("%s_%s", forSubfield(subfield), extra);
	}

}
