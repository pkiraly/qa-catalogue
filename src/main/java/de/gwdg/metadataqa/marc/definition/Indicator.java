package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Indicator {
	private String label = null;
	private String bibframeTag = null;
	private String mqTag = null;
	private List<Code> codes;
	private Map<String, Code> codeIndex = new LinkedHashMap<>();

	public Indicator() {}

	public Indicator(String label) {
		this.label = label;
	}

	public Indicator(String label, List<Code> codes) {
		this.label = label;
		this.codes = codes;
		index();
	}

	public Indicator setLabel(String label) {
		this.label = label;
		return this;
	}

	public Indicator setCodes(List<Code> codes) {
		this.codes = codes;
		return this;
	}

	public Indicator setCodes(String... input) {
		codes = new ArrayList<>();
		for (int i = 0; i<input.length; i+=2) {
			codes.add(new Code(input[i], input[i+1]));
		}
		index();
		return this;
	}

	public String getLabel() {
		return label;
	}

	public String getBibframeTag() {
		return bibframeTag;
	}

	public void setBibframeTag(String bibframeTag) {
		this.bibframeTag = bibframeTag;
	}

	public String getMqTag() {
		return mqTag;
	}

	public void setMqTag(String mqTag) {
		this.mqTag = mqTag;
	}

	public boolean exists() {
		return label != null && !label.equals("");
	}

	public List<Code> getCodes() {
		return codes;
	}

	public Code getCode(String codeString) {
		for (Code code : codes) {
			if (code.getCode().equals(codeString))
				return code;
		}
		return null;
	}

	public boolean hasCode(String code) {
		return codeIndex.containsKey(code);
	}

	private void index() {
		codeIndex = new LinkedHashMap<>();
		for (Code code : codes) {
			codeIndex.put(code.getCode(), code);
		}
	}

}
