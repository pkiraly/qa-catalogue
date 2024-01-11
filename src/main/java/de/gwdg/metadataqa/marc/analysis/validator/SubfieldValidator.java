package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentParser;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.model.validation.ErrorsCollector;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.ArrayList;

import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.SUBFIELD_NULL_CODE;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.SUBFIELD_UNDEFINED;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.SUBFIELD_UNPARSABLE_CONTENT;

public class SubfieldValidator extends AbstractValidator {

  private ErrorsCollector errors;
  private SubfieldDefinition definition;
  private DataFieldDefinition fieldDefinition;
  private MarcSubfield subfield;

  public SubfieldValidator() {
    super(new ValidatorConfiguration());
  }

  public SubfieldValidator(ValidatorConfiguration configuration) {
    super(configuration);
  }

  public boolean validate(MarcSubfield subfield) {
    this.subfield = subfield;

    errors = new ErrorsCollector();
    validationErrors = new ArrayList<>();
    definition = subfield.getDefinition();
    fieldDefinition = subfield.getField().getDefinition();

    if (definition == null) {
      addError(fieldDefinition.getExtendedTag(), SUBFIELD_UNDEFINED, subfield.getCode());
      validationErrors.addAll(errors.getErrors());
      return false;
    }

    if (subfield.getCode() == null) {
      addError(subfield.getField().getDefinition().getTag(), SUBFIELD_NULL_CODE, subfield.getCode());
      validationErrors.addAll(errors.getErrors());
      return false;
    }

    if (definition.isDisallowedIn(configuration.getMarcVersion())) {
      addError(subfield.getField().getDefinition().getTag(), SUBFIELD_UNDEFINED, subfield.getCode());
      validationErrors.addAll(errors.getErrors());
      return false;
    }

    if (definition.hasValidator()) {
      validateWithValidator();
    } else if (definition.hasContentParser()) {
      validateWithParser();
    } else if (definition.getCodes() != null && definition.getCode(subfield.getValue()) == null) {
      // If a subfield has a list of codes defined, and the value is not in the codelist, then it is invalid
      String message = subfield.getValue();
      if (subfield.getReferencePath() != null) {
        message += String.format(" (the field is embedded in %s)", subfield.getReferencePath());
      }
      String path = subfield.getReferencePath() == null
        ? definition.getPath()
        : subfield.getReferencePath() + "->" + definition.getPath();
      addError(path, ValidationErrorType.SUBFIELD_INVALID_VALUE, message);
    /*
    } else if (definition.getCodeList() != null &&
               !definition.getCodeList().isValid(value)) {
      String message = value;
      if (referencePath != null) {
        message += String.format(" (the field is embedded in %s)", referencePath);
      }
      String path = (referencePath == null
        ? definition.getPath()
        : referencePath + "->" + definition.getPath());
      addError(path, ValidationErrorType.SUBFIELD_INVALID_VALUE, message);
      isValid = false;
    */
    } else if (definition.hasPositions()) {
      validatePositions();
    }

    validationErrors.addAll(errors.getErrors());
    return errors.isEmpty();
  }

  private void validatePositions() {
    for (ControlfieldPositionDefinition positionDefinition : definition.getPositions()) {
      validatePosition(positionDefinition);
    }
  }

  private void validatePosition(ControlfieldPositionDefinition positionDefinition) {
    String subfieldValue = subfield.getValue();
    // If the subfield value is shorter than the position definition, then it is invalid
    if (subfieldValue.length() < positionDefinition.getPositionEnd()) {
      String path = String.format("%s/%s", definition.getPath(), positionDefinition.formatPositon());
      String errorMessage = String.format("invalid code for '%s': '%s' position %s out of range",
          positionDefinition.getLabel(), subfieldValue, positionDefinition.formatPositon());

      addError(path, ValidationErrorType.SUBFIELD_INVALID_VALUE, errorMessage);
      return;
    }
    String positionValue = subfield.getValue().substring(positionDefinition.getPositionStart(), positionDefinition.getPositionEnd());
    boolean isPositionDefinitionValid = positionDefinition.validate(positionValue);
    if (isPositionDefinitionValid) {
      return;
    }

    String path = String.format("%s/%s", definition.getPath(), positionDefinition.formatPositon());
    String errorMessage = String.format("invalid code for '%s': '%s' at position %s in '%s'",
        positionDefinition.getLabel(), positionValue, positionDefinition.formatPositon(), subfield.getValue());

    addError(path, ValidationErrorType.SUBFIELD_INVALID_VALUE, errorMessage);
  }

  /**
   * Validates a subfield by the given subfield validator which is assigned to the subfield definition in code.
   * @return True if the subfield is valid, false otherwise.
   */
  private boolean validateWithValidator() {
    de.gwdg.metadataqa.marc.definition.general.validator.SubfieldValidator validator = definition.getValidator();
    ValidatorResponse response = validator.isValid(subfield);
    if (!response.isValid()) {
      errors.addAll(response.getValidationErrors());
    }
    return response.isValid();
  }

  /**
   * Uses a similar approach as the subfield validator in the subfield definition trying to parse the subfield content.
   * @return True if the subfield is valid, false otherwise.
   */
  private boolean validateWithParser() {
    var isValid = true;
    SubfieldContentParser parser = definition.getContentParser();
    try {
      parser.parse(subfield.getValue());
    } catch (ParserException e) {
      addError(SUBFIELD_UNPARSABLE_CONTENT, e.getMessage());
      isValid = false;
    }
    return isValid;
  }

  private void addError(ValidationErrorType type, String message) {
    addError(definition.getPath(), type, message);
  }

  private void addError(String path, ValidationErrorType type, String message) {
    if (!isIgnorableType(type)) {
      String id = subfield.getMarcRecord() == null ? null : subfield.getMarcRecord().getId();
      String url = fieldDefinition.getDescriptionUrl();
      errors.add(id, path, type, message, url);
    }
  }
}
