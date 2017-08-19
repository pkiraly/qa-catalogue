package de.gwdg.metadataqa.marc;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Code {
	private String code;
	private String label;

	public Code(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return "Code{" +
				"code='" + code + '\'' +
				", label='" + label + '\'' +
				'}';
	}
}
