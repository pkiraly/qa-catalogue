package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MarcSubfield implements Validatable {
	private SubfieldDefinition definition;
	private String code;
	private String value;
	private String codeForIndex = null;
	private List<String> errors = null;

	public MarcSubfield(SubfieldDefinition definition, String code, String value) {
		this.definition = definition;
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		String label = code;
		if (definition == null)
			System.err.printf("no definition for %s/%s\n", code, value);
		else if (definition.getLabel() == null)
			System.err.printf("no label for %s/%s\n", code, value);
		else
			label = definition.getLabel();
		return label;
	}

	public String resolve() {
		if (definition == null) {
			System.err.printf("no definition for %s/%s\n", code, value);
			return value;
		}
		return definition.resolve(value);
	}

	public SubfieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(SubfieldDefinition definition) {
		this.definition = definition;
	}

	public String getCodeForIndex() {
		if (codeForIndex == null) {
			codeForIndex = "_" + code;
			if (definition != null && definition.getCodeForIndex() != null) {
				codeForIndex = definition.getCodeForIndex();
			}
		}
		return codeForIndex;
	}

	public Map<String, String> parseContent() {
		if (definition.hasContentParser())
			return definition.getContentParser().parse(value);
		return null;
	}

	@Override
	public boolean validate(MarcVersion marcVersion) {
		boolean isValid = true;
		errors = new ArrayList<>();

		if (definition == null) {
			errors.add(String.format("no definition for %s", code));
			isValid = false;
		} else {
			if (code == null) {
				errors.add(String.format("code is null for %s (%s)", definition.getCode(),
					definition.getParent().getDescriptionUrl()));
				isValid = false;
			} else {
				if (definition.getValidator() != null) {
					Validator validator = definition.getValidator();
					if (!validator.isValid(value)) {
						errors.addAll(validator.getErrors());
						isValid = false;
					}
				} else if (definition.getCodes() != null && definition.getCode(value) == null) {
					errors.add(String.format("%s$%s has an invalid value: '%s' (%s)",
						definition.getParent().getTag(),
						definition.getCode(),
						value,
						definition.getParent().getDescriptionUrl()));
					isValid = false;
				}
			}
		}

		return isValid;
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}

}
