package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.MarcRecord;
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
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static de.gwdg.metadataqa.marc.Utils.count;

public class Validator extends AbstractValidator {
  private BibliographicRecord bibliographicRecord;
  private List<ValidationError> parsingErrors;

  private static final Logger logger = Logger.getLogger(Validator.class.getCanonicalName());

  public Validator() {
    super(new ValidatorConfiguration());
  }

  public Validator(ValidatorConfiguration configuration) {
    super(configuration);
  }

  public Validator(ValidatorConfiguration configuration, List<ValidationError> parsingErrors) {
    super(configuration);
    this.parsingErrors = parsingErrors;
  }

  public boolean validate(BibliographicRecord bibliographicRecord) {
    this.bibliographicRecord = bibliographicRecord;

    validationErrors = new ArrayList<>();
    if (parsingErrors != null && !parsingErrors.isEmpty()) {
      validationErrors.addAll(parsingErrors);
    }

    if (bibliographicRecord == null) {
      return validationErrors.isEmpty();
    }

    if (!bibliographicRecord.getSchemaType().equals(SchemaType.PICA)) {
      validateLeader();
    }
    validateUnhandledTags();
    validateControlfields();
    validateDatafields();

    // TODO: use reflection to get all validator class

    return validationErrors.isEmpty();
  }

  private boolean validateLeader() {

    // If it isn't a MARC record with a leader, it's valid
    if (!(bibliographicRecord instanceof MarcRecord)) {
      return true;
    }

    MarcRecord marcRecord = (MarcRecord) bibliographicRecord;

    LeaderValidator leaderValidator = new LeaderValidator(configuration);
    boolean isValidComponent = leaderValidator.validate(marcRecord.getLeader());
    if (isValidComponent) {
      return true;
    }
    List<ValidationError> leaderErrors = leaderValidator.getValidationErrors();

    for (ValidationError leaderError : leaderErrors) {
      if (leaderError.getRecordId() == null) {
        leaderError.setRecordId(marcRecord.getId());
      }
    }


    validationErrors.addAll(filterErrors(leaderErrors));
    return false;
  }

  private boolean validateUnhandledTags() {
    List<String> unhandledTags = bibliographicRecord.getUnhandledTags();
    if (unhandledTags.isEmpty()) {
      // No unhandled tags, so the record is valid
      return true;
    }

    List<ValidationError> unhandledTagErrors;
    if (configuration.doSummary()) {
      unhandledTagErrors = getUnhandledTagErrorsSummary(unhandledTags);
    } else {
      unhandledTagErrors = getUnhandledTagErrorsDetailed(unhandledTags);
    }

    // These errors weren't being filtered originally, so they aren't going to be filtered now either
    validationErrors.addAll(unhandledTagErrors);

    // As there were unhandled tags, the record is invalid
    return false;
  }

  private boolean validateControlfields() {

    if (!(bibliographicRecord instanceof MarcRecord)) {
      return true;
    }

    MarcRecord marcRecord = (MarcRecord) bibliographicRecord;

    boolean isValidComponent = true;

    ControlFieldValidator controlFieldValidator = new ControlFieldValidator(configuration);
    for (MarcControlField controlField : marcRecord.getControlfields()) {
      if (controlField == null) {
        continue;
      }

      // If the control field is not valid, add the errors to the list of validation errors,
      // and set isValidComponent to false. Setting and returning isValidComponent wouldn't work as it would
      // only return the result of the last control field validation.
      boolean isValidControlField = controlFieldValidator.validate(controlField);
      if (!isValidControlField) {
        validationErrors.addAll(filterErrors(controlFieldValidator.getValidationErrors()));
        isValidComponent = false;
      }
    }
    return isValidComponent;
  }

  private void validateDatafields() {
    DataFieldValidator validator = new DataFieldValidator(configuration);
    Map<RepetitionDao, Integer> repetitionCounter = new HashMap<>();

    for (DataField field : bibliographicRecord.getDatafields()) {
      validateDatafield(validator, repetitionCounter, field);
    }

    validateRepeatability(repetitionCounter);
  }

  /**
   * Returns a list of validation errors for each unhandled tag.
   * @param unhandledTags A list of unhandled tags
   * @return A list of error messages
   */
  private List<ValidationError> getUnhandledTagErrorsSummary(List<String> unhandledTags) {
    List<ValidationError> errors = new ArrayList<>();
    for (String tag : unhandledTags) {

      // If the tag is ignorable or the error type is ignorable, skip it
      boolean shouldBeValidated = !bibliographicRecord.isIgnorableField(tag, configuration.getIgnorableFields())
          && (!isIgnorableType(ValidationErrorType.FIELD_UNDEFINED));

      if (shouldBeValidated) {
        errors.add(new ValidationError(bibliographicRecord.getId(), tag, ValidationErrorType.FIELD_UNDEFINED, tag, null));
      }
    }
    return errors;
  }

  /**
   * Gets a list of validation errors for each set of identical unhandled tags as well as the count of such tags,
   * e.g. 198 (2*)
   * @param unhandledTags A list of unhandled tags
   * @return A list of error messages
   */
  private List<ValidationError> getUnhandledTagErrorsDetailed(List<String> unhandledTags) {
    List<ValidationError> errors = new ArrayList<>();

    // For all unhandled tags, count the occurrences
    Map<String, Long> tagCounts = unhandledTags.stream()
        .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));

    for (Map.Entry<String, Long> entry : tagCounts.entrySet()) {
      String tag = entry.getKey();
      Long count = entry.getValue();
      boolean shouldBeValidated = !bibliographicRecord.isIgnorableField(tag, configuration.getIgnorableFields())
          && !isIgnorableType(ValidationErrorType.FIELD_UNDEFINED);

      if (shouldBeValidated) {
        String errorMessage = (count == 1) ? tag : String.format("%s (%d*)", tag, count);
        errors.add(new ValidationError(bibliographicRecord.getId(), tag, ValidationErrorType.FIELD_UNDEFINED, errorMessage, null));
      }
    }

    return errors;
  }

  /**
   * Validates a datafield by the given data field validator as well as classification reference validator.
   */
  private void validateDatafield(DataFieldValidator validator,
                                 Map<RepetitionDao, Integer> repetitionCounter,
                                 DataField field) {

    boolean shouldSkipValidation = field.getDefinition() == null
        || bibliographicRecord.isIgnorableField(field.getTag(), configuration.getIgnorableFields());

    if (shouldSkipValidation) {
      return;
    }

    RepetitionDao dao = new RepetitionDao(field.getTagWithOccurrence(), field.getDefinition());
    count(dao, repetitionCounter);

    validateField(validator, field);
    validateClassificationReference(field);
  }

  /**
   * The first portion of datafield validation. This method validates the datafield by the given data field validator.
   * @param validator The data field validator
   * @param field The data field to validate
   */
  private void validateField(DataFieldValidator validator, DataField field) {
    boolean isValidField = validator.validate(field);
    if (!isValidField) {
      validationErrors.addAll(filterErrors(validator.getValidationErrors()));
    }
  }

  private void validateClassificationReference(DataField field) {
    ValidatorResponse validatorResponse = ClassificationReferenceValidator.validate(field);
    if (!validatorResponse.isValid()) {
      validationErrors.addAll(filterErrors(validatorResponse.getValidationErrors()));
    }
  }

  /**
   * Given the repetition counter, this method validates the repeatability of the datafields. In case a field is
   * non-repeatable and it is repeated, an error is added to the list of validation errors.
   * @param repetitionCounter A map of repetition data access objects and their counts, i.e. how many times a given
   *                          datafield has occurred in the record
   */
  private void validateRepeatability(Map<RepetitionDao, Integer> repetitionCounter) {
    if (isIgnorableType(ValidationErrorType.FIELD_NONREPEATABLE)) {
      return;
    }

    // Get errors for all non-repeatable fields which were repeated
    List<ValidationError> nonRepeatableFieldErrors = repetitionCounter.entrySet().stream()
        .filter(entry -> isNonRepeatableRepeated(entry.getKey(), entry.getValue()))
        .map(entry -> createNonRepeatableFieldError(entry.getKey(), entry.getValue()))
        .collect(Collectors.toList());

    validationErrors.addAll(filterErrors(nonRepeatableFieldErrors));
  }

  private static boolean isNonRepeatableRepeated(RepetitionDao dao, Integer count) {
    DataFieldDefinition fieldDefinition = dao.getFieldDefinition();
    return count > 1 && fieldDefinition.getCardinality().equals(Cardinality.Nonrepeatable);
  }

  private ValidationError createNonRepeatableFieldError(RepetitionDao dao, Integer count) {
    DataFieldDefinition fieldDefinition = dao.getFieldDefinition();
    return new ValidationError(bibliographicRecord.getId(), fieldDefinition.getExtendedTag(),
        ValidationErrorType.FIELD_NONREPEATABLE,
        String.format("there are %d instances", count),
        fieldDefinition.getDescriptionUrl()
    );
  }
}
