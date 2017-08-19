package de.gwdg.metadataqa.marc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcSubfield {

	private String code;
	private String type;
	private String label;
	private List<String> allowedValues;

	/**
	 * Create a MarcSubfield object
	 *
	 * @param code The subfield code
	 * @param label The description of the code
	 */
	public MarcSubfield(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public MarcSubfield(String code, String type, String label) {
		this.code = code;
		this.type = type;
		this.label = label;
		if (code.startsWith("ind")) {
			processIndicatorType(type);
		}
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public String getLabel() {
		return label;
	}

	public List<String> getAllowedValues() {
		return allowedValues;
	}

	private void processIndicatorType(String types) {
		allowedValues = new ArrayList<>();
		if (types.equals("blank")) {
			allowedValues.add(" ");
		} else {
			for (int i = 0, len = types.length(); i < len; i++) {
				String type = String.valueOf(types.charAt(i));
				if (type.equals("b")) {
					type = " ";
				}
				allowedValues.add(type);
			}
		}
	}
}
