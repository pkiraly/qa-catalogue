package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.Validatable;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentParser;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.ArrayList;
import java.util.List;

public class ControlValue implements Validatable {

	private ControlSubfield definition;
	private String value;
	private MarcRecord record;
	private List<String> errors;
	private List<ValidationError> validationErrors;

	public ControlValue(ControlSubfield definition, String value) {
		this.definition = definition;
		this.value = value;
	}

	public void setRecord(MarcRecord record) {
		this.record = record;
	}

	public String getLabel() {
		return definition.getLabel();
	}

	public String getId() {
		return definition.getId();
	}

	public String resolve() {
		return definition.resolve(value);
	}

	public ControlSubfield getDefinition() {
		return definition;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean validate(MarcVersion marcVersion) {
		boolean isValid = true;
		errors = new ArrayList<>();
		validationErrors = new ArrayList<>();

		if (!definition.getValidCodes().isEmpty()
			&& (!definition.getValidCodes().contains(value)
			    && definition.getCode(value) == null)) {
			if (definition.isHistoricalCode(value)) {
				validationErrors.add(new ValidationError(record.getId(), definition.getPath(), ValidationErrorType.Obsolete,
					value, definition.getDescriptionUrl()));
				errors.add(
					String.format(
						"%s/%s (%s) has an obsolete value: '%s' (%s)",
						definition.getControlField(), definition.formatPositon(), definition.getId(),
						value, definition.getDescriptionUrl()
					)
				);
				isValid = false;

			} else {
				if (definition.isRepeatableContent()) {
					int unitLength = definition.getUnitLength();
					for (int i = 0; i < value.length(); i += unitLength) {
						String unit = value.substring(i, i + unitLength);
						if (!definition.getValidCodes().contains(unit)) {
							validationErrors.add(new ValidationError(record.getId(), definition.getPath(), ValidationErrorType.ContainsInvalidCode,
								String.format("'%s' in '%s'", unit, value), definition.getDescriptionUrl()));
							errors.add(
								String.format(
									"%s/%s (%s) contains an invalid code: '%s' in '%s' (%s)",
									definition.getControlField(), definition.formatPositon(), definition.getId(),
									unit, value, definition.getDescriptionUrl()
								)
							);
							isValid = false;
						}
					}
				} else {
					validationErrors.add(
						new ValidationError(
							((record == null) ? null : record.getId()),
							definition.getPath(), ValidationErrorType.HasInvalidValue,
						value, definition.getDescriptionUrl()));
					errors.add(
						String.format(
							"%s/%s (%s) has an invalid value: '%s' (%s)",
							definition.getControlField(), definition.formatPositon(), definition.getId(),
							value, definition.getDescriptionUrl()
						)
					);
					isValid = false;
				}
			}
		}

		if (definition.hasParser()) {
			try {
				SubfieldContentParser parser = definition.getParser();
				parser.parse(value);
			} catch (ParserException e) {
				validationErrors.add(
					new ValidationError(
						((record == null) ? null : record.getId()),
						definition.getPath(), ValidationErrorType.HasInvalidValue,
						e.getMessage(), definition.getDescriptionUrl()));
				// e.printStackTrace();
				isValid = false;
			}
		}

		return isValid;
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}
}
