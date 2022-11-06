package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.general.validator.ClassificationReferenceValidator;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.count;

public class Validator extends AbstractValidator {
  private BibliographicRecord marcRecord;

  private static final Logger logger = Logger.getLogger(Validator.class.getCanonicalName());

  public Validator() {
    super(new ValidatorConfiguration());
  }

  public Validator(ValidatorConfiguration configuration) {
    super(configuration);
  }

  public boolean validate(BibliographicRecord marcRecord) {
    this.marcRecord = marcRecord;

    validationErrors = new ArrayList<>();
    boolean isValidRecord = true;
    if (!marcRecord.getSchemaType().equals(SchemaType.PICA))
      isValidRecord = validateLeader(isValidRecord);
    isValidRecord = validateUnhandledTags(isValidRecord);
    isValidRecord = validateControlfields(isValidRecord);
    isValidRecord = validateDatafields(isValidRecord);

    // TODO: use reflection to get all validator class
    // ValidatorResponse validatorResponse;

    return validationErrors.isEmpty();
  }

  private boolean validateLeader(boolean isValidRecord) {
    boolean isValidComponent;
    LeaderValidator leaderValidator = new LeaderValidator(configuration);
    isValidComponent = leaderValidator.validate(marcRecord.getLeader());
    // isValidComponent = marcRecord.getLeader().validate(configuration.getMarcVersion());
    if (!isValidComponent) {
      List<ValidationError> leaderErrors = leaderValidator.getValidationErrors();
      for (ValidationError leaderError : leaderErrors)
        if (leaderError.getRecordId() == null)
          leaderError.setRecordId(marcRecord.getId());
      validationErrors.addAll(filterErrors(leaderErrors));
      isValidRecord = isValidComponent;
    }
    return isValidRecord;
  }

  private boolean validateUnhandledTags(boolean isValidRecord) {
    if (!marcRecord.getUnhandledTags().isEmpty()) {
      if (configuration.doSummary()) {
        for (String tag : marcRecord.getUnhandledTags()) {
          if (!marcRecord.isIgnorableField(tag, configuration.getIgnorableFields())
              && (!isIgnorableType(ValidationErrorType.FIELD_UNDEFINED)))
            validationErrors.add(new ValidationError(marcRecord.getId(), tag, ValidationErrorType.FIELD_UNDEFINED, tag, null));
        }
      } else {
        Map<String, Integer> tags = new LinkedHashMap<>();
        for (String tag : marcRecord.getUnhandledTags())
          Utils.count(tag, tags);

        List<String> unhandledTagsList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : tags.entrySet()) {
          String tag = entry.getKey();
          if (entry.getValue() == 1)
            unhandledTagsList.add(tag);
          else
            unhandledTagsList.add(String.format("%s (%d*)", tag, entry.getValue()));
        }
        for (String tag : unhandledTagsList) {
          if (!marcRecord.isIgnorableField(tag, configuration.getIgnorableFields())
              && !isIgnorableType(ValidationErrorType.FIELD_UNDEFINED))
            validationErrors.add(new ValidationError(marcRecord.getId(), tag, ValidationErrorType.FIELD_UNDEFINED, tag, null));
        }
      }

      isValidRecord = false;
    }
    return isValidRecord;
  }

  private boolean validateControlfields(boolean isValidRecord) {
    boolean isValidComponent;
    ControlFieldValidator controlFieldValidator = new ControlFieldValidator(configuration);
    for (MarcControlField controlField : marcRecord.getControlfields()) {
      if (controlField != null) {
        isValidComponent = controlFieldValidator.validate(controlField);
        if (!isValidComponent) {
          validationErrors.addAll(filterErrors(controlFieldValidator.getValidationErrors()));
          isValidRecord = isValidComponent;
        }
      }
    }
    return isValidRecord;
  }

  private boolean validateDatafields(boolean isValidRecord) {
    DataFieldValidator validator = new DataFieldValidator(configuration);
    ValidatorResponse validatorResponse;
    Map<DataFieldDefinition, Integer> repetitionCounter = new HashMap<>();
    for (DataField field : marcRecord.getDatafields()) {
      if (field.getDefinition() != null && !marcRecord.isIgnorableField(field.getTag(), configuration.getIgnorableFields())) {
        count(field.getDefinition(), repetitionCounter);
        if (!validator.validate(field)) {
          isValidRecord = false;
          validationErrors.addAll(filterErrors(validator.getValidationErrors()));
        }

        validatorResponse = ClassificationReferenceValidator.validate(field);
        if (!validatorResponse.isValid()) {
          validationErrors.addAll(filterErrors(validatorResponse.getValidationErrors()));
          isValidRecord = false;
        }
      }
    }

    if (!isIgnorableType(ValidationErrorType.FIELD_NONREPEATABLE)) {
      for (Map.Entry<DataFieldDefinition, Integer> entry : repetitionCounter.entrySet()) {
        DataFieldDefinition fieldDefinition = entry.getKey();
        Integer count = entry.getValue();
        if (count > 1
            && fieldDefinition.getCardinality().equals(Cardinality.Nonrepeatable)) {
          validationErrors.add(new ValidationError(marcRecord.getId(), fieldDefinition.getTag(),
            ValidationErrorType.FIELD_NONREPEATABLE,
            String.format("there are %d instances", count),
            fieldDefinition.getDescriptionUrl()
          ));
          isValidRecord = false;
        }
      }
    }
    return isValidRecord;
  }
}
