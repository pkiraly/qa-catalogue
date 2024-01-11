package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentParser;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.ArrayList;

public class ControlValueValidator extends AbstractValidator {

  public ControlValueValidator() {
    super(new ValidatorConfiguration());
  }

  public ControlValueValidator(ValidatorConfiguration configuration) {
    super(configuration);
  }

  public boolean validate(ControlValue controlValue) {
    validationErrors = new ArrayList<>();

    var definition = controlValue.getDefinition();
    var value = controlValue.getValue();

    boolean isAmongValidCodes = isValueAmongValidCodes(definition, value);

    if (!isAmongValidCodes) {
      if (definition.isHistoricalCode(value)) {
        addError(controlValue, ValidationErrorType.CONTROL_POSITION_OBSOLETE_CODE, value);
      } else {
        fallbackValidateRepeatableCode(definition, controlValue, value);
      }
    }

    if (definition.hasParser()) {
      try {
        SubfieldContentParser parser = definition.getParser();
        parser.parse(value);
      } catch (ParserException e) {
        addError(controlValue, ValidationErrorType.CONTROL_POSITION_INVALID_CODE, e.getMessage());
      }
    }

    return validationErrors.isEmpty();
  }

  private boolean isValueAmongValidCodes(ControlfieldPositionDefinition definition, String value) {
    return definition.getValidCodes().isEmpty()
        || definition.getValidCodes().contains(value)
        || (definition.getCode(value) != null);
  }

  /**
   * In case the control value is repeatable, the value is split into units of the length of the unit and then each unit is validated.
   */
  private void fallbackValidateRepeatableCode(ControlfieldPositionDefinition definition,
                                              ControlValue controlValue,
                                              String value) {
    if (!definition.isRepeatableContent()) {
      addError(controlValue, ValidationErrorType.CONTROL_POSITION_INVALID_VALUE, value);
      return;
    }

    int unitLength = definition.getUnitLength();
    for (int i = 0; i < value.length(); i += unitLength) {
      String unit = value.substring(i, i + unitLength);
      if (!definition.getValidCodes().contains(unit)) {
        addError(controlValue, ValidationErrorType.CONTROL_POSITION_INVALID_CODE, String.format("'%s' in '%s'", unit, value));
      }
    }
  }

  private void addError(ControlValue controlValue, ValidationErrorType type, String message) {
    if (isIgnorableType(type)) {
      return;
    }

    var definition = controlValue.getDefinition();
    validationErrors.add(
      new ValidationError(
        ((controlValue.getMarcRecord() == null) ? null : controlValue.getMarcRecord().getId()),
        definition.getPath(),
        type,
        message,
        definition.getDescriptionUrl()
      )
    );
  }
}
