package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractValidator {
  final ValidatorConfiguration configuration;
  List<ValidationError> validationErrors = null;

  protected AbstractValidator(ValidatorConfiguration configuration) {
    this.configuration = configuration;
  }

  /**
   * Remove ignorable errors from the list of errors
   *
   * @param errors The list of error objects
   * @return
   */
  protected List<ValidationError> filterErrors(List<ValidationError> errors) {
    if (configuration.getIgnorableIssueTypes() == null || configuration.getIgnorableIssueTypes().isEmpty())
      return errors;
    return errors
      .stream()
      .filter(error -> !configuration.getIgnorableIssueTypes().contains(error.getType()))
      .collect(Collectors.toList());
  }

  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }

  protected boolean isIgnorableType(ValidationErrorType type) {
    return (
          configuration.getIgnorableIssueTypes() != null
       && !configuration.getIgnorableIssueTypes().isEmpty()
       && configuration.getIgnorableIssueTypes().contains(type)
    );
  }
}
