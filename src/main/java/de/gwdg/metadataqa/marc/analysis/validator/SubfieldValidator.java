package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentParser;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
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
  private MarcSubfield subfield;

  public SubfieldValidator() {
    super(new ValidatorConfiguration());
  }

  public SubfieldValidator(ValidatorConfiguration configuration) {
    super(configuration);
  }

  public boolean validate(MarcSubfield subfield) {
    boolean debug = subfield.getCode().equals("7");
    errors = new ErrorsCollector();
    validationErrors = new ArrayList<>();
    this.subfield = subfield;

    definition = subfield.getDefinition();
    if (definition == null) {
      addError(subfield.getField().getDefinition().getTag(), SUBFIELD_UNDEFINED, subfield.getCode());
      return false;
    } else {
      if (subfield.getCode() == null) {
        addError(subfield.getField().getDefinition().getTag(), SUBFIELD_NULL_CODE, subfield.getCode());
      } else {
        if (definition.isDisallowedIn(configuration.getMarcVersion())) {
          addError(subfield.getField().getDefinition().getTag(), SUBFIELD_UNDEFINED, subfield.getCode());
        } else {
          if (definition.hasValidator()) {
            validateWithValidator();
          } else if (definition.hasContentParser()) {
            validateWithParser();
          } else if (definition.getCodes() != null
                     && definition.getCode(subfield.getValue()) == null) {
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
            for (ControlfieldPositionDefinition positionDefinition : definition.getPositions()) {
              String positionValue = subfield.getValue().substring(positionDefinition.getPositionStart(), positionDefinition.getPositionEnd());
              if (!positionDefinition.validate(positionValue)) {
                String path = definition.getPath() + "/" + positionDefinition.formatPositon();
                addError(path, ValidationErrorType.SUBFIELD_INVALID_VALUE,
                  String.format("invalid code for '%s': '%s' at position %s in '%s'",
                    positionDefinition.getLabel(), positionValue, positionDefinition.formatPositon(), subfield.getValue()));
              }
            }
          }
        }
      }
    }

    validationErrors.addAll(errors.getErrors());
    return errors.isEmpty();
  }

  private boolean validateWithValidator() {
    var isValid = true;
    de.gwdg.metadataqa.marc.definition.general.validator.SubfieldValidator validator = definition.getValidator();
    ValidatorResponse response = validator.isValid(subfield);
    if (!response.isValid()) {
      errors.addAll(response.getValidationErrors());
      isValid = false;
    }
    return isValid;
  }

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
      String url = definition.getParent().getDescriptionUrl();
      errors.add(id, path, type, message, url);
    }
  }
}
