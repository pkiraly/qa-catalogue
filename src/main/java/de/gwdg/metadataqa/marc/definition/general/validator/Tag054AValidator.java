package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tag054AValidator implements SubfieldValidator {

	private static final Pattern BC = Pattern.compile("^([a-d])(-)$");
	private static final Pattern CE = Pattern.compile("^([e-y])(\\d|-)$");

	public Tag054AValidator() {
	}

	@Override
	public ValidatorResponse isValid(MarcSubfield subfield) {

		ValidatorResponse response = new ValidatorResponse();
		SubfieldDefinition definition = subfield.getDefinition();
		String descriptionUrl = definition.getParent().getDescriptionUrl();
		String value = subfield.getValue();

		if (value.length() != 4) {
			response.addValidationError(new ValidationError(
				subfield.getRecord().getId(),
				definition.getPath(),
				ValidationErrorType.InvalidLength,
				String.format("'%s': length is not 4 char", value),
				descriptionUrl));
			response.addError(String.format("%s error in '%s': length is not 4 char (%s)",
				definition.getPath(), value, descriptionUrl));
			response.setValid(false);
		} else {
			List<String> parts = Arrays.asList(
				value.substring(0, 2),
				value.substring(2, 4)
			);

			Matcher matcher;
			for (String part : parts) {
				if (definition.getCode(part) == null) {
					matcher = BC.matcher(part);
					if (!matcher.find()) {
						matcher = CE.matcher(part);
						if (!matcher.find()) {
							response.addValidationError(
								new ValidationError(
									subfield.getRecord().getId(),
									subfield.getDefinition().getPath(),
									ValidationErrorType.PatternMismatch,
									String.format("mismatched part '%s' in '%s'", part, value),
									descriptionUrl
								)
							);
							response.addError(String.format("%s error in '%s': '%s' does not match any patterns (%s)\n",
								definition.getPath(), value, part, descriptionUrl));
						}
					}
				}
			}
		}
		return response;
	}
}
