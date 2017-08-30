package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.definition.general.CodeList;

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
	private String cardinalityCode;
	private String label;
	private Validator validator;
	private CodeList codeList;
	private List<String> allowedCodes;
	private Map<String, String> allowedValues = new HashMap<>();

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

	public String getCardinalityCode() {
		return cardinalityCode;
	}

	public Cardinality getType() {
		return Cardinality.byCode(cardinalityCode);
	}

	public String getLabel() {
		return label;
	}

	public List<String> getAllowedCodes() {
		return allowedCodes;
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

	public SubfieldDefinition setCodeList(CodeList codeList) {
		this.codeList = codeList;
		return this;
	}

	public String resolve(String value) {
		if (codeList != null && codeList.isValid(value))
			return codeList.getCode(value).getLabel();
		return allowedValues.getOrDefault(value, value);
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
