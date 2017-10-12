package de.gwdg.metadataqa.marc.definition;

import java.util.ArrayList;
import java.util.List;

import de.gwdg.metadataqa.marc.Code;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class ControlSubfield {
	private String label;
	private String bibframeTag;
	private String mqTag;
	private int positionStart;
	private int positionEnd;
	private String id;
	private List<Code> codes;
	private List<String> validCodes;
	private int unitLength = -1;
	private boolean repeatableContent = false;
	private String defaultCode;

	public ControlSubfield(String label, int positionStart, int positionEnd) {
		this.label = label;
		this.positionStart = positionStart;
		this.positionEnd = positionEnd;
		validCodes = new ArrayList<>();
	}

	public ControlSubfield(String label, int positionStart, int positionEnd,
			List<Code> codes) {
		this(label, positionStart, positionEnd);
		this.codes = codes;
		extractValidCodes();
	}

	public void setCodes(List<Code> codes) {
		this.codes = codes;
		extractValidCodes();
	}

	public String getLabel() {
		return label;
	}

	public String getBibframeTag() {
		return bibframeTag;
	}

	public ControlSubfield setBibframeTag(String bibframeTag) {
		this.bibframeTag = bibframeTag;
		return this;
	}

	public String getMqTag() {
		return mqTag;
	}

	public ControlSubfield setMqTag(String mqTag) {
		this.mqTag = mqTag;
		return this;
	}

	public int getPositionStart() {
		return positionStart;
	}

	public int getPositionEnd() {
		return positionEnd;
	}

	public List<Code> getCodes() {
		return codes;
	}

	public int getUnitLength() {
		return unitLength;
	}

	public String getId() {
		return id;
	}

	public ControlSubfield setId(String id) {
		this.id = id;
		return this;
	}

	public ControlSubfield setUnitLength(int unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	public boolean isRepeatableContent() {
		return repeatableContent;
	}

	public ControlSubfield setRepeatableContent(boolean repeatableContent) {
		this.repeatableContent = repeatableContent;
		return this;
	}

	public String getDefaultCode() {
		return defaultCode;
	}

	public ControlSubfield setDefaultCode(String defaultCode) {
		this.defaultCode = defaultCode;
		return this;
	}

	public boolean validate(String code) {
		if (isRepeatableContent()) {
			return validateRepeatable(code);
		} else {
			return validCodes.contains(code);
		}
	}

	private boolean validateRepeatable(String code) {
		for (int i=0; i < code.length(); i += unitLength) {
			String unit = code.substring(i, i+unitLength);
			if (!validCodes.contains(unit))
				return false;
		}
		return true;
	}

	public String resolve(String inputCode) {
		if (codes != null) {
			if (repeatableContent) {
				inputCode = resolveRepeatable(inputCode);
			} else {
				inputCode = resolveSingleCode(inputCode);
			}
		}
		return inputCode;
	}

	private String resolveRepeatable(String inputCode) {
		List<String> units = new ArrayList<>();
		for (int i=0; i < inputCode.length(); i += unitLength) {
			String unit = inputCode.substring(i, i+unitLength);
			if (!units.contains(unit))
				units.add(unit);
		}
		List<String> resolved = new ArrayList<>();
		for (String unit : units) {
			resolved.add(resolveSingleCode(unit));
		}
		inputCode = StringUtils.join(resolved, ", ");
		return inputCode;
	}

	private String resolveSingleCode(String inputCode) {
		for (Code code : codes)
			if (code.getCode().equals(inputCode))
				return code.getLabel();
		return inputCode;
	}

	private void extractValidCodes() {
		for (Code code : codes)
			validCodes.add(code.getCode());
	}

	public List<String> getValidCodes() {
		return validCodes;
	}

	public String formatPositon() {
		return (positionStart == positionEnd - 1)
			? String.valueOf(positionStart)
			: positionStart + "-" + positionEnd;
	}


	@Override
	public String toString() {
		return "ControlSubField{" +
				"label='" + label + '\'' +
				", positionStart=" + positionStart +
				", positionEnd=" + positionEnd +
				", codes=" + codes +
				", validCodes=" + validCodes +
				", unitLength=" + unitLength +
				", repeatableContent=" + repeatableContent +
				'}';
	}
}
