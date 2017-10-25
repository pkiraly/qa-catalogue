package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;
import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class SubfieldDefinition {

	private String code;
	private String bibframeTag;
	private String mqTag;
	private String cardinalityCode;
	private String label;
	private DataFieldDefinition parent;
	private Validator validator;
	private SubfieldContentParser contentParser;
	protected CodeList codeList;
	private List<Code> codes;
	private List<String> allowedCodes;
	private Map<String, String> allowedValues = new HashMap<>();
	private String codeForIndex = null;
	private List<ControlSubfield> positions;

	public String getCodeForIndex() {
		if (codeForIndex == null) {
			if (mqTag != null) {
				if (mqTag.equals("rdf:value"))
					codeForIndex = "";
				else
					codeForIndex = "_" + mqTag;
			} else if (bibframeTag != null) {
				switch (bibframeTag) {
					case "rdf:value": codeForIndex = ""; break;
					case "rdfs:label": codeForIndex = "label"; break;
					default: codeForIndex = "_" + bibframeTag; break;
				}
			} else {
				codeForIndex = "_" + code;
			}
		}
		return codeForIndex;
	}

	/**
	 * Create a MarcSubfield object
	 *
	 * @param code The subfield code
	 * @param label The description of the code
	 */
	public SubfieldDefinition(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public SubfieldDefinition(String code, String label, String cardinalityCode) {
		this.code = code;
		this.label = label;
		this.cardinalityCode = cardinalityCode;
		if (code.startsWith("ind")) {
			processIndicatorType(cardinalityCode);
		}
	}

	public String getCode() {
		return code;
	}

	public SubfieldDefinition setCodes(List<Code> codes) {
		this.codes = codes;
		return this;
	}

	public SubfieldDefinition setCodes(String... input) {
		codes = new ArrayList<>();
		for (int i = 0; i<input.length; i+=2) {
			codes.add(new Code(input[i], input[i+1]));
		}
		return this;
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

	public List<Code> getCodes() {
		return codes;
	}

	public String getCardinalityCode() {
		return cardinalityCode;
	}

	public Cardinality getCardinality() {
		return Cardinality.byCode(cardinalityCode);
	}

	public String getLabel() {
		return label;
	}

	public List<String> getAllowedCodes() {
		return allowedCodes;
	}

	public SubfieldContentParser getContentParser() {
		return contentParser;
	}

	public boolean hasContentParser() {
		return contentParser != null;
	}

	public void setContentParser(SubfieldContentParser contentParser) {
		this.contentParser = contentParser;
	}

	private void processIndicatorType(String types) {
		allowedCodes = new ArrayList<>();
		if (types.equals("blank")) {
			allowedCodes.add(" ");
		} else {
			for (int i = 0, len = types.length(); i < len; i++) {
				String type = String.valueOf(types.charAt(i));
				if (type.equals("b")) {
					type = " ";
				}
				allowedCodes.add(type);
			}
		}
	}

	public SubfieldDefinition setValidator(Validator validator) {
		this.validator = validator;
		return this;
	}

	public Validator getValidator() {
		return validator;
	}

	public SubfieldDefinition setCodeList(CodeList codeList) {
		this.codeList = codeList;
		return this;
	}

	public CodeList getCodeList() {
		return codeList;
	}

	public String resolve(String value) {
		if (codeList != null && codeList.isValid(value))
			return codeList.getCode(value).getLabel();

		return allowedValues.getOrDefault(value, value);
	}

	public String getBibframeTag() {
		return bibframeTag;
	}

	public SubfieldDefinition setBibframeTag(String bibframeTag) {
		this.bibframeTag = bibframeTag;
		return this;
	}

	public String getMqTag() {
		return mqTag;
	}

	public DataFieldDefinition getParent() {
		return parent;
	}

	public void setParent(DataFieldDefinition parent) {
		this.parent = parent;
	}

	public SubfieldDefinition setMqTag(String mqTag) {
		this.mqTag = mqTag;
		return this;
	}

	public void setPositions(List<ControlSubfield> positions) {

	}

	@Override
	public String toString() {
		return "MarcSubfield{" +
				"code='" + code + '\'' +
				", typeCode='" + cardinalityCode + '\'' +
				", label='" + label + '\'' +
				", allowedValues=" + allowedValues +
				'}';
	}
}
