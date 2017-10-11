package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentParser;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Code {
	private String code;
	private String label;
	private boolean isRange = false;
	private boolean isRegex = false;
	private String bibframeTag = null;
	private Range range = null;

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

	public boolean isRange() {
		return isRange;
	}

	public void setRange(boolean isRange) {
		if (isRange) {
			this.range = new Range(code);
		}
		this.isRange = isRange;
	}

	public Range getRange() {
		return range;
	}

	public boolean isRegex() {
		return isRegex;
	}

	public void setRegex(boolean regex) {
		isRegex = regex;
	}

	public String getBibframeTag() {
		return bibframeTag;
	}

	public void setBibframeTag(String bibframeTag) {
		this.bibframeTag = bibframeTag;
	}

	@Override
	public String toString() {
		return "Code{" +
				"code='" + code + '\'' +
				", label='" + label + '\'' +
				'}';
	}
}
