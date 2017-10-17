package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeList implements Validator {
	protected List<Code> codes;
	protected Map<String, Code> index = new HashMap<>();

	protected void indexCodes() {
		for (Code code : codes) {
			index.put(code.getCode(), code);
		}
	}

	public List<Code> getCodes() {
		return codes;
	}

	public Code getCode(String code) {
		return index.getOrDefault(code, null);
	}

	public boolean isValid(String code) {
		return index.containsKey(code);
	}

	@Override
	public List<String> getErrors() {
		return null;
	}
}
