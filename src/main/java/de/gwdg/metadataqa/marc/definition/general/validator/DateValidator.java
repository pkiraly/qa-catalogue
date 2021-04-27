package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements SubfieldValidator, Serializable {

  private final String pattern;
  private final DateTimeFormatter formatter;

  public DateValidator(String rawPattern) {
    this.pattern = rawPattern;
    formatter = DateTimeFormatter.ofPattern(rawPattern);
  }

  @Override
  public ValidatorResponse isValid(MarcSubfield subfield) {

    ValidatorResponse response = new ValidatorResponse();
    SubfieldDefinition definition = subfield.getDefinition();
    String descriptionUrl = (definition != null)
                          ? definition.getParent().getDescriptionUrl()
                          : null;
    String value = subfield.getValue();

    if (StringUtils.isNotBlank(value)) {
      try {
        LocalDate date = LocalDate.parse(value, formatter);
      } catch(DateTimeParseException e) {
        response.addValidationError(
          new ValidationError(
            subfield.getMarcRecord().getId(),
            subfield.getDefinition().getPath(),
            ValidationErrorType.SUBFIELD_PATTERN_MISMATCH,
            String.format("mismatched '%s' against '%s': %s", value, pattern, e.getMessage()),
            descriptionUrl
          )
        );
      }
    } else {
      response.addValidationError(
        new ValidationError(
          subfield.getMarcRecord().getId(),
          subfield.getDefinition().getPath(),
          ValidationErrorType.SUBFIELD_PATTERN_MISMATCH,
          String.format("mismatched '%s' against '%s'", value, pattern),
          descriptionUrl
        )
      );
    }
    return response;
  }
}
