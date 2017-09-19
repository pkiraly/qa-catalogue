package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;

import java.util.Map;

public class MarcSubfield {
	private SubfieldDefinition definition;
	private String code;
	private String value;
	private String codeForIndex = null;

	public MarcSubfield(SubfieldDefinition definition, String code, String value) {
		this.definition = definition;
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		String label = code;
		if (definition == null)
			System.err.printf("no definition for %s/%s\n", code, value);
		else if (definition.getLabel() == null)
			System.err.printf("no label for %s/%s\n", code, value);
		else
			label = definition.getLabel();
		return label;
	}

	public String resolve() {
		if (definition == null) {
			System.err.printf("no definition for %s/%s\n", code, value);
			return value;
		}
		return definition.resolve(value);
	}

	public SubfieldDefinition getDefinition() {
		return definition;
	}

	public String getCodeForIndex() {
		if (codeForIndex == null) {
			codeForIndex = "_" + code;
			if (definition != null) {
				if (definition.getMqTag() != null) {
					codeForIndex = "_" + definition.getMqTag();
				} else {
					String bibframeTag = definition.getBibframeTag();
					if (bibframeTag != null)
						switch (bibframeTag) {
							case "rdf:value":
								codeForIndex = "";
								break;
							case "rdfs:label":
								codeForIndex = "label";
								break;
							default:
								codeForIndex = "_" + bibframeTag;
								break;
						}
				}
			}
		}
		return codeForIndex;
	}

	public Map<String, String> parseContent() {
		if (definition.hasContentParser())
			return definition.getContentParser().parse(value);
		return null;
	}

}
