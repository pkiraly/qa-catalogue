package de.gwdg.metadataqa.marc.utils.keygenerator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;

public class DataFieldKeyGenerator {
	private DataFieldDefinition definition;
	private SolrFieldType type;
	private String tag;
	private String indexTag;

	public DataFieldKeyGenerator(DataFieldDefinition definition, SolrFieldType type) {
		this.definition = definition;
		this.type = type;
		tag = definition.getTag();
		indexTag = definition.getIndexTag();
	}

	public String forInd1() {
		String key = "";
		switch (type) {
			case HUMAN:
				key = String.format(
					"%s_%s",
					indexTag, definition.getInd1().getIndexTag());
				break;
			case MIXED:
				key = String.format(
					"%sind1_%s_%s",
					tag, indexTag, definition.getInd1().getIndexTag());
				break;
			case MARC:
			default:
				key = String.format("%sind1", tag);
				break;
		}
		return key;
	}

	public String forInd2() {
		String key = "";
		switch (type) {
			case HUMAN:
				key = String.format(
					"%s_%s",
					indexTag, definition.getInd2().getIndexTag()); break;
			case MIXED:
				key = String.format(
					"%sind2_%s_%s",
					tag, indexTag, definition.getInd2().getIndexTag());
				break;
			case MARC:
			default:
				key = String.format("%sind2", tag);
				break;
		}
		return key;
	}

	public String forSubfield(MarcSubfield subfield) {
		String code = subfield.getCode();
		String label = subfield.getCodeForIndex();
		String key = "";
		switch (type) {
			case HUMAN:
				key = String.format("%s%s", indexTag, label); break;
			case MIXED:
				key = String.format("%s%s_%s%s", tag, code, indexTag, label);
				break;
			case MARC:
			default:
				key = String.format("%s%s", tag, code);
				break;
		}

		return key;
	}

	public String forSubfield(MarcSubfield subfield, String extra) {
		return String.format("%s_%s", forSubfield(subfield), extra);
	}

}
