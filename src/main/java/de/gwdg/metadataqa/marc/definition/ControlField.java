package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.model.SolrFieldType;

public class ControlField {

	protected String content;

	public String getContent() {
		return content;
	}

	public String getSolrKey(SolrFieldType type, String tag, String mqTag) {
		String key = tag;
		switch (type) {
			case HUMAN: key = mqTag; break;
			case MIXED: key = String.format("%s_%s", tag, mqTag); break;
			case MARC:
			  default:  key = tag; break;
		}
		return key;
	}

}
