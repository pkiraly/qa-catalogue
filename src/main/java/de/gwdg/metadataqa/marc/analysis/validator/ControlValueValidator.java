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

    boolean isCodeValid = definition.validate(value);

    if (!isCodeValid) {
      if (definition.isHistoricalCode(value)) {
        addError(controlValue, ValidationErrorType.CONTROL_POSITION_OBSOLETE_CODE, value);
      } else {
        // It's unnecessary to check if the code is repeatable in here, as there's already the validate method in the
        // definition that does this - it's a matter of question where the validation should be done but currently,
        // it's in the definition.

        // This checking here is just to conform to the test cases which expect that error is added whenever the code
        // isn't valid in case it's repeatable.
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
