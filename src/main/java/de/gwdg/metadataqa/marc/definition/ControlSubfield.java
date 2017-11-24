package de.gwdg.metadataqa.marc.definition;

import java.util.ArrayList;
import java.util.List;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentParser;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class ControlSubfield {
	protected String label;
	protected String bibframeTag;
	protected String mqTag;
	protected int positionStart;
	protected int positionEnd;
	protected String id;
	protected List<Code> codes;
	protected List<Code> historicalCodes;

	protected List<String> validCodes = new ArrayList<>();
	protected int unitLength = -1;
	protected boolean repeatableContent = false;
	protected String defaultCode;
	protected String descriptionUrl;
	protected SubfieldContentParser parser;

	public ControlSubfield() {}

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

	public ControlSubfield setCodes(List<Code> codes) {
		this.codes = codes;
		extractValidCodes();
		return this;
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

	public String getDescriptionUrl() {
		return descriptionUrl;
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

	protected void extractValidCodes() {
		if (codes == null)
			return;
		for (Code code : codes)
			validCodes.add(code.getCode());
	}

	public List<String> getValidCodes() {
		return validCodes;
	}

	public String formatPositon() {
		return (positionStart == positionEnd - 1)
			? String.format("%02d", positionStart)
			: String.format("%02d-%02d", positionStart, positionEnd-1);
	}

	public String getControlField() {
		String className = this.getClass().getSimpleName();
		if (className.startsWith("Leader"))
			return "Leader";
		return this.getClass().getSimpleName().substring(3, 6);
	}

	public Code getCode(String _code) {
		for (Code code : codes) {
			if (code.getCode().equals(_code)) {
				return code;
			} else if (code.isRange() && code.getRange().isValid(_code)) {
				return code;
			}
		}
		return null;
	}

	public boolean isHistoricalCode(String code) {
		return historicalCodes != null
		       && !historicalCodes.isEmpty()
		       && historicalCodes.contains(code);
	}

	public String getPath() {
		return String.format("%s/%s (%s)", getControlField(), formatPositon(), getId());
	}

	public boolean hasParser() {
		return parser != null;
	}

	public SubfieldContentParser getParser() {
		return parser;
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
				", mqTag=" + mqTag +
				", id=" + id +
				'}';
	}
}
