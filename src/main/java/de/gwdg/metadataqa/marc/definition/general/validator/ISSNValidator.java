package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.regex.Pattern;

/**
 * Validation of ISSN (International_Standard_Serial_Number)
 */
public class ISSNValidator implements SubfieldValidator {

	public static final String URL = "https://en.wikipedia.org/wiki/International_Standard_Serial_Number";
	public static final Pattern ISSN = Pattern.compile("^\\d{4}-\\d{3}[\\dxX]$");

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
		boolean isValid;
		if (ISSN.matcher(value).matches()) {
			value = value.replace("-", "").toUpperCase();
			int sum = 0;
			for (int i = 0; i<7; i++)
				sum += Integer.parseInt(value.substring(i, i+1)) * (8-i);

			int remainder = sum % 11;
			if (remainder > 0) {
				remainder = 11 - remainder;
			}
			String checkDigit = (remainder == 10) ? "X" : String.valueOf(remainder);
			if (value.endsWith(checkDigit)) {
				isValid = true;
			} else {
				isValid = false;
				message = String.format("'%s' is not a valid ISSN value, it failed in integrity check", subfield.getValue());
			}
		} else {
			isValid = false;
			message = String.format("'%s' is not a valid ISSN value, it does not fit the pattern \\d{4}-\\d{3}[\\dX].", subfield.getValue());
		}

		response.setValid(isValid);
		if (!isValid) {
			response.addValidationError(new ValidationError(
				subfield.getField().getRecord().getId(),
				subfield.getDefinition().getPath(),
				ValidationErrorType.ISSN,
				message,
				URL
			));
		}

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
