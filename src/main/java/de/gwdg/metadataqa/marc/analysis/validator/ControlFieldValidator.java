package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.dao.Control005;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.MarcPositionalControlField;
import de.gwdg.metadataqa.marc.dao.SimpleControlField;
import de.gwdg.metadataqa.marc.definition.ControlValue;

import java.util.ArrayList;

public class ControlFieldValidator extends AbstractValidator {

  public ControlFieldValidator() {
    super(new ValidatorConfiguration());
  }

  public ControlFieldValidator(ValidatorConfiguration configuration) {
    super(configuration);
  }

  public boolean validate(MarcControlField controlField) {
    validationErrors = new ArrayList<>();
    if (controlField instanceof SimpleControlField) {
      return validateSimpleControlField((SimpleControlField) controlField);
    } else if (controlField instanceof MarcPositionalControlField) {
      return validateMarcPositionalControlField((MarcPositionalControlField) controlField);
    }
    return validationErrors.isEmpty();
  }

  private boolean validateMarcPositionalControlField(MarcPositionalControlField controlField) {
    if (!controlField.getInitializationErrors().isEmpty()) {
      validationErrors.addAll(filterErrors(controlField.getInitializationErrors()));
    }
    ControlValueValidator validator = new ControlValueValidator(configuration);
    for (ControlValue controlValue : controlField.getValuesList()) {
      if (!validator.validate(controlValue)) {
        validationErrors.addAll(validator.getValidationErrors());
      }
    }
    return validationErrors.isEmpty();
  }

  private boolean validateSimpleControlField(SimpleControlField controlField) {
    if (controlField instanceof Control005) {
      Control005 control = (Control005) controlField;
      control.validate(configuration.getMarcVersion());
      validationErrors.addAll(filterErrors(control.getValidationErrors()));
    }
    return validationErrors.isEmpty();
  }
}
