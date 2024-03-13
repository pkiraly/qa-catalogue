package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ClassificationReferenceValidator implements RecordValidator, FieldValidator {

  private static final Logger logger = Logger.getLogger(ClassificationReferenceValidator.class.getCanonicalName());

  /**
   * List of fields that should contain a classification reference
   */
  protected static final List<String> classificationReferenceFields = Arrays.asList(
    "016", "024", "041", "047", "048", "052", "072",
    "600", "610", "611", "630", "647", "648", "650", "651", "655", "656", "657", "852"
  );

  private ClassificationReferenceValidator() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Given a record, validate that all classification fields that have indicator2 equal to 7 also have to have subfield 2
   * @param marcRecord The record to validate
   * @return A ValidatorResponse object containing the validation errors and isValid flag
   */
  public static ValidatorResponse validate(BibliographicRecord marcRecord) {
    logger.info("Validating classification reference fields in record " + marcRecord.getId());

    ValidatorResponse response = new ValidatorResponse();
    for (String tag : classificationReferenceFields) {
      if (!marcRecord.exists(tag)) {
        continue;
      }

      List<DataField> fields = marcRecord.getDatafield(tag);
      for (DataField field : fields) {
        List<MarcSubfield> subfield2 = field.getSubfield("2");

        if (field.getInd2().equals("7") && subfield2 == null) {
          addError(response, field);
        }
      }
    }
    return response;
  }

  /**
   * Given a field, validate that if the field has indicator2 equal to 7, it also has to have subfield 2
   * @param field The field to validate
   * @return A ValidatorResponse object containing the validation errors and isValid flag
   */
  public static ValidatorResponse validate(DataField field) {
    ValidatorResponse response = new ValidatorResponse();

    // If it's not a MARC21 record, then skip this.
    if (!field.getBibliographicRecord().getSchemaType().equals(SchemaType.MARC21)) {
      return response;
    }
    
    if (!classificationReferenceFields.contains(field.getTag())) {
      return response;
    }

    List<MarcSubfield> subfield2 = field.getSubfield("2");

    if (field.getInd2().equals("7") && subfield2 == null) {
      addError(response, field);
    }

    return response;
  }

  private static void addError(ValidatorResponse response, DataField field) {
    response.addValidationError(
        new ValidationError(
            field.getBibliographicRecord().getId(),
            field.getTag() + "$ind2",
            ValidationErrorType.SUBFIELD_INVALID_CLASSIFICATION_REFERENCE,
            "ind2 is '7' which means that the value should be found in subfield $2, but it is missing",
            field.getDefinition().getDescriptionUrl()
        )
    );
  }
}
