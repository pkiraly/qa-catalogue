package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validation of ISSN (International_Standard_Serial_Number)
 */
public class ISSNValidator implements SubfieldValidator, Serializable {

	public static final String URL = "https://en.wikipedia.org/wiki/International_Standard_Serial_Number";
	public static final Pattern ISSN = Pattern.compile("\\d{4}-\\d{3}[\\dxX]");

	private static ISSNValidator uniqueInstance;

	private ISSNValidator() {}

	public static ISSNValidator getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ISSNValidator();
		return uniqueInstance;
	}

	@Override
	public ValidatorResponse isValid(MarcSubfield subfield) {
		String value = normalize(subfield.getValue());
		ValidatorResponse response = new ValidatorResponse();
		String message = null;
		boolean found = false;
		Matcher matcher = ISSN.matcher(value);
		while (matcher.find()) {
			found = true;
			String match = matcher.group();
			value = match.replace("-", "").toUpperCase();
			int sum = 0;
			for (int i = 0; i < 7; i++)
				sum += Integer.parseInt(value.substring(i, i + 1)) * (8 - i);

			int remainder = sum % 11;
			if (remainder > 0) {
				remainder = 11 - remainder;
			}
			String checkDigit = (remainder == 10) ? "X" : String.valueOf(remainder);
			if (!value.endsWith(checkDigit)) {
				message = String.format(
					"'%s' is not a valid ISSN value, it failed in integrity check",
					match
				);
				if (!match.equals(subfield.getValue()))
					message += String.format(" (in \"%s\")", subfield.getValue());

				response.addValidationError(new ValidationError(
					subfield.getField().getRecord().getId(),
					subfield.getDefinition().getPath(),
					ValidationErrorType.ISSN,
					message,
					URL
				));
			}
		}
		if (!found) {
			message = String.format(
				"'%s' does not a have ISSN value, it does not fit the pattern \\d{4}-\\d{3}[\\dX].",
				subfield.getValue()
			);
			response.addValidationError(new ValidationError(
				subfield.getField().getRecord().getId(),
				subfield.getDefinition().getPath(),
				ValidationErrorType.ISSN,
				message,
				URL
			));
		}

		response.setValid(response.getValidationErrors().isEmpty());

		return response;
	}

	private static String normalize(String value) {
		value = value
			// .replaceAll(" ?\\(.*?\\)$", "")
			// .replaceAll(" ", "")
			// .replaceAll("[ :-]", "")
			.replaceAll(" \\(ISSN\\)$", "")
      			.replaceAll("[\\s;:-]+$", "")
			.trim();
		return value;
	}
}
