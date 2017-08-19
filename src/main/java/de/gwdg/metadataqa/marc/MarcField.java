package de.gwdg.metadataqa.marc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcField {
	private String code;
	private String type;
	private String label;
	private MarcSubfield indicator1;
	private MarcSubfield indicator2;
	private Map<String, MarcSubfield> subfields = new HashMap<>();
	private List<MarcSubfield> subfieldList = new ArrayList<>();
	private List<String> validSubfieldCodes = new ArrayList<>();

	public MarcField(String code, String type, String label) {
		this.code = code;
		this.type = type;
		this.label = label;
	}

	public void addSubfield(String code, String type, String label) {
		MarcSubfield subfield = new MarcSubfield(code, type, label);
		switch (code) {
			case "ind1": indicator1 = subfield; break;
			case "ind2": indicator2 = subfield; break;
			default:
				subfields.put(code, subfield);
				subfieldList.add(subfield);
				validSubfieldCodes.add(code);
				break;
		}
	}

	public String getCode() {
		return code;
	}

	public void setField(String field) {
		this.code = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public MarcSubfield getIndicator1() {
		return indicator1;
	}

	public void setIndicator1(MarcSubfield indicator1) {
		this.indicator1 = indicator1;
	}

	public MarcSubfield getIndicator2() {
		return indicator2;
	}

	public void setIndicator2(MarcSubfield indicator2) {
		this.indicator2 = indicator2;
	}

	public Map<String, MarcSubfield> getSubfields() {
		return subfields;
	}

	public List<MarcSubfield> getSubfieldList() {
		return subfieldList;
	}

	public void setSubfieldList(List<MarcSubfield> subfields) {
		this.subfieldList = subfields;
	}

	public List<String> getValidSubfieldCodes() {
		return validSubfieldCodes;
	}

	public void setValidSubfieldCodes(List<String> validSubfieldCodes) {
		this.validSubfieldCodes = validSubfieldCodes;
	}
}
