package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.definition.tags.tags76x.Tag775;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

public class ISBNValidator implements SubfieldValidator {

	public static final String URL = "https://en.wikipedia.org/wiki/International_Standard_Book_Number";

	private static ISBNValidator uniqueInstance;

	private ISBNValidator() {}

	public static ISBNValidator getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ISBNValidator();
		return uniqueInstance;
	}

	@Override
	public ValidatorResponse isValid(MarcSubfield subfield) {
		String value = normalize(subfield.getValue());
		ValidatorResponse response = new ValidatorResponse();
		String message = null;
		boolean isValid;
		if (value.length() == 10) {
			isValid = validateIsbn10(value);
			if (!isValid)
				message = String.format("'%s' is not a valid ISBN 10 value", subfield.getValue());
		} else if (value.length() == 13) {
			isValid = validateIsbn13(value);
			if (!isValid)
				message = String.format("'%s' is not a valid ISBN 13 value", subfield.getValue());
		} else {
			isValid = false;
			message = String.format(
				"'%s' is not a valid ISBN value: length should be either 10 or 13, but it is %d",
				subfield.getValue(), value.length());
		}

		response.setValid(isValid);
		if (!isValid) {
			response.addValidationError(new ValidationError(
				subfield.getField().getRecord().getId(),
				subfield.getDefinition().getPath(),
				ValidationErrorType.ISBN,
				message,
				URL
			));
		}

		return response;
	}

	private static String normalize(String value) {
		value = value
			.replaceAll(" ?\\(.*?\\)$", "")
			.replaceAll(" ", "")
			.replaceAll("[ :-]", "")
			.trim();
		return value;
	}

	public static boolean validateIsbn10(String isbn) {
		if (isbn == null) {
			return false;
		}

		//remove any hyphens
		isbn = isbn.replaceAll("-", "");

		//must be a 10 digit ISBN
		if (isbn.length() != 10) {
			return false;
		}

		try {
			int total = 0;
			for (int i = 0; i < 9; i++) {
				int digit = Integer.parseInt(isbn.substring(i, i + 1));
				total += ((10 - i) * digit);
			}

			String checksum = Integer.toString((11 - (total % 11)) % 11);
			if ("10".equals(checksum)) {
				checksum = "X";
			}

			return checksum.equals(isbn.substring(9));
		} catch (NumberFormatException nfe) {
			//to catch invalid ISBNs that have non-numeric characters in them
			return false;
		}
	}

	public static boolean validateIsbn13(String isbn) {
		if (isbn == null) {
			return false;
		}

		//remove any hyphens
		isbn = isbn.replaceAll("-", "");

		//must be a 13 digit ISBN
		if (isbn.length() != 13) {
			return false;
		}

		try {
			int total = 0;
			for (int i = 0; i < 12; i++) {
				int digit = Integer.parseInt(isbn.substring(i, i + 1));
				total += (i % 2 == 0) ? digit * 1 : digit * 3;
			}

			//checksum must be 0-9. If calculated as 10 then = 0
			int checksum = 10 - (total % 10);
			if (checksum == 10) {
				checksum = 0;
			}

			return checksum == Integer.parseInt(isbn.substring(12));
		} catch (NumberFormatException nfe) {
			//to catch invalid ISBNs that have non-numeric characters in them
			return false;
		}
	}
}
