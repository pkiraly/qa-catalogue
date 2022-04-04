package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class RangeValidator implements SubfieldValidator, Serializable {

  private final int min;
  private final int max;

  public RangeValidator(int min, int max) {
    this.min = min;
    this.max = max;
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
      int intValue = Integer.parseInt(value);
      if (intValue < min || intValue > max)
        response.addValidationError(
          new ValidationError(
            subfield.getMarcRecord().getId(),
            subfield.getDefinition().getPath(),
            ValidationErrorType.SUBFIELD_PATTERN_MISMATCH,
            String.format("'%s' is out of range '%d-%d'", value, min, max),
            descriptionUrl
          )
        );
      }
    return response;
  }
}
