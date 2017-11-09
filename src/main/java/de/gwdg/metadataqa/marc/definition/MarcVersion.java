package de.gwdg.metadataqa.marc.definition;

public enum MarcVersion {
	MARC21("MARC21", "MARC21"),
	DNB("DNB", "Deutsche Nationalbibliothek"),
	OCLC("OCLC", "OCLC"),
	GENT("GENT", "Universiteitsbibliotheek Gent")
	;

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

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}
}
