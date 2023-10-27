package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator implements SubfieldValidator, Serializable {

  private final Pattern pattern;

  public RegexValidator(String rawPattern) {
    this.pattern = Pattern.compile(rawPattern);
  }

  @Override
  public ValidatorResponse isValid(MarcSubfield subfield) {

    ValidatorResponse response = new ValidatorResponse();
    SubfieldDefinition definition = subfield.getDefinition();
    String descriptionUrl = definition.getParent().getDescriptionUrl();
    String value = subfield.getValue();

    if (StringUtils.isNotBlank(value)) {
      Matcher matcher = pattern.matcher(value);
      if (!matcher.matches()) {
        response.addValidationError(
          new ValidationError(
            subfield.getMarcRecord().getId(),
            subfield.getDefinition().getPath(),
            ValidationErrorType.SUBFIELD_PATTERN_MISMATCH,
            String.format("mismatched '%s' against '%s'", value, pattern.pattern()),
            descriptionUrl
          )
        );
      }
    }
    return response;
  }

  public String getPattern() {
    return pattern.toString();
  }
}
