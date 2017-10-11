package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.Range;

import java.util.*;

public class Indicator {
	private String label = null;
	private String bibframeTag = null;
	private String mqTag = null;
	private String indexTag = null;
	private List<Code> codes;
	private Map<String, Code> codeIndex = new LinkedHashMap<>();
	private Map<Range, Code> ranges;

	public Indicator() {}

	public Indicator(String label) {
		this.label = label;
	}

	public Indicator(String label, List<Code> codes) {
		this.label = label;
		this.codes = codes;
		index();
	}

	public String getIndexTag(String defaultTag) {
		if (indexTag == null) {
			if (mqTag != null)
				indexTag = mqTag;
			else if (bibframeTag != null)
				indexTag = bibframeTag.replace("/", "");
			else
				indexTag = defaultTag;
		}
		return indexTag;
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

	public Indicator setMqTag(String mqTag) {
		this.mqTag = mqTag;
		return this;
	}

	public boolean exists() {
		return label != null && !label.equals("");
	}

	public List<Code> getCodes() {
		return codes;
	}

	public Code getCode(String codeString) {
		if (codeIndex.containsKey(codeString))
			return codeIndex.get(codeString);

		for (Range range : getRanges().keySet()) {
			if (range.isValid(codeString))
				return ranges.get(range);
		}

		return null;
	}

	public boolean hasCode(String code) {
		if (codeIndex.containsKey(code))
			return true;
		for (Range range : getRanges().keySet()) {
			if (range.isValid(code))
				return true;
		}

		return false;
	}

	private Map<Range, Code> getRanges() {
		if (ranges == null) {
			ranges = new HashMap<>();
			for (Code code : codes) {
				if (code.isRange()) {
					ranges.put(code.getRange(), code);
				}
			}
		}
		return ranges;
	}

	private void index() {
		codeIndex = new LinkedHashMap<>();
		for (Code code : codes) {
			codeIndex.put(code.getCode(), code);
		}
	}
}
