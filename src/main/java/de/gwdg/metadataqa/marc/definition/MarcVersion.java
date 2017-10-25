package de.gwdg.metadataqa.marc.definition;

public enum MarcVersion {
	DNB("DNB", "Deutsche Nationalbibliothek"),
	OCLC("OCLC", "OCLC");

	String code;
	String label;

	MarcVersion(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public static MarcVersion byCode(String code) {
		for(MarcVersion version : values())
			if (version.code.equals(code))
				return version;
		return null;
	}
}
