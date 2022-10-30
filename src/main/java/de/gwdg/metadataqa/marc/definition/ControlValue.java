package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.Validatable;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentParser;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ControlValue implements Validatable, Serializable {

  private ControlfieldPositionDefinition definition;
  private String value;
  private BibliographicRecord marcRecord;
  private List<ValidationError> validationErrors;

  public ControlValue(ControlfieldPositionDefinition definition, String value) {
    this.definition = definition;
    this.value = value;
  }

  public void setMarcRecord(BibliographicRecord marcRecord) {
    this.marcRecord = marcRecord;
  }

  public String getLabel() {
    return definition.getLabel();
  }

  public String getId() {
    return definition.getId();
  }

  public String resolve() {
    return definition.resolve(value);
  }

  public ControlfieldPositionDefinition getDefinition() {
    return definition;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean validate(MarcVersion marcVersion) {
    var isValid = true;
    validationErrors = new ArrayList<>();

    if (!definition.getValidCodes().isEmpty()
      && (!definition.getValidCodes().contains(value)
          && definition.getCode(value) == null)) {
      if (definition.isHistoricalCode(value)) {
        validationErrors.add(new ValidationError(marcRecord.getId(), definition.getPath(), ValidationErrorType.CONTROL_POSITION_OBSOLETE_CODE,
          value, definition.getDescriptionUrl()));
        isValid = false;

      } else {
        if (definition.isRepeatableContent()) {
          int unitLength = definition.getUnitLength();
          for (int i = 0; i < value.length(); i += unitLength) {
            String unit = value.substring(i, i + unitLength);
            if (!definition.getValidCodes().contains(unit)) {
              validationErrors.add(
                new ValidationError(
                  marcRecord.getId(),
                  definition.getPath(),
                  ValidationErrorType.CONTROL_POSITION_INVALID_CODE,
                  String.format("'%s' in '%s'", unit, value),
                  definition.getDescriptionUrl()));
              isValid = false;
            }
          }
        } else {
          validationErrors.add(
            new ValidationError(
              ((marcRecord == null) ? null : marcRecord.getId()),
              definition.getPath(), ValidationErrorType.CONTROL_POSITION_INVALID_VALUE,
            value, definition.getDescriptionUrl()));
          isValid = false;
        }
      }
    }

    if (definition.hasParser()) {
      try {
        SubfieldContentParser parser = definition.getParser();
        parser.parse(value);
      } catch (ParserException e) {
        validationErrors.add(
          new ValidationError(
            ((marcRecord == null) ? null : marcRecord.getId()),
            definition.getPath(), ValidationErrorType.CONTROL_POSITION_INVALID_VALUE,
            e.getMessage(), definition.getDescriptionUrl()));
        // logger.log(Level.SEVERE, "validate", e);
        isValid = false;
      }
    }

    return isValid;
  }

  @Override
  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }
}
