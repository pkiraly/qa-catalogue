package de.gwdg.metadataqa.marc.model.validation;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidationErrorFormatter {

	public static String format(List<ValidationError> errors, ValidationErrorFormat format) {
		String message = "";
		switch (format) {
			case TAB_SEPARATED:
			case COMMA_SEPARATED:
				for (ValidationError error : errors)
					message += format(error, format) + "\n";
				break;
			case TEXT:
				message += String.format("%s in '%s':\n",
					(errors.size() == 1 ? "Error" : "Errors"),
					errors.get(0).getRecordId());
				for (ValidationError error : errors) {
					message += "\t" + formatTextWithoutId(error) + "\n";
				}
			default:
				break;
		}

		return message;
	}

	public static String format(ValidationError error, ValidationErrorFormat format) {
		String message = "";
		if (format.equals(ValidationErrorFormat.TAB_SEPARATED)) {
			message = StringUtils.join(asList(error), "\t");
		} else if (format.equals(ValidationErrorFormat.COMMA_SEPARATED)) {
			message = StringUtils.join(asList(error), ",");
		}
		return message;
	}

	public static List<String> formatForSummary(List<ValidationError> validationErrors,
															  ValidationErrorFormat format) {
		List<String> messages = new ArrayList<>();

		for (ValidationError error : validationErrors)
			messages.add(formatForSummary(error, format));

		return messages;
	}

	public static String formatForSummary(ValidationError error, ValidationErrorFormat format) {
		String message = "";
		switch (format) {
			case TAB_SEPARATED:
				message = StringUtils.join(asListWithoutId(error), "\t");
				break;
			case COMMA_SEPARATED:
				message = StringUtils.join(asListWithoutId(error), ",");
				break;
			case TEXT:
				message = formatTextWithoutId(error);
			default:
				break;
		}
		return message;
	}


	private static String formatTextWithoutId(ValidationError error) {
		return String.format("%s: %s '%s' (%s)",
			error.getMarcPath(),
			error.getType().getMessage(),
			error.getMessage(),
			error.getUrl()
		);
	}

	private static List<String> asListWithoutId(ValidationError error) {
		return Arrays.asList(
			error.getMarcPath(),
			error.getType().getMessage(),
			error.getMessage(),
			error.getUrl()
		);
	}

	private static List<String> asList(ValidationError error) {
		return Arrays.asList(
			error.getRecordId(),
			error.getMarcPath(),
			error.getType().getMessage(),
			error.getMessage(),
			error.getUrl()
		);
	}
}
