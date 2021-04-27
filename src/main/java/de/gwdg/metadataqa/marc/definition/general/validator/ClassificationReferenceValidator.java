package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ClassificationReferenceValidator implements RecordValidator, FieldValidator {

  private static final Logger logger = Logger.getLogger(ClassificationReferenceValidator.class.getCanonicalName());

  public static final List<String> fields = Arrays.asList(
    "016", "024", "041", "047", "048", "052", "072",
    "600", "610", "611", "630", "647", "648", "650", "651", "655", "656", "657", "852"
  );

  public static ValidatorResponse validate(MarcRecord marcRecord) {
    ValidatorResponse response = new ValidatorResponse();
    for (String tag : fields) {
      if (marcRecord.exists(tag)) {
        List<DataField> fields = marcRecord.getDatafield(tag);
        for (DataField field : fields) {
          if (field.getInd2().equals("7")) {
            List<MarcSubfield> subfield2 = field.getSubfield("2");
            if (subfield2 == null) {
              response.addValidationError(
                new ValidationError(
                  field.getRecord().getId(),
                  field.getTag() + "$ind2",
                  ValidationErrorType.SUBFIELD_INVALID_CLASSIFICATION_REFERENCE,
                  "ind2 is '7' which means that the value should be found in subfield $2, but it is missing",
                  field.getDefinition().getDescriptionUrl()
                )
              );
            }
          }
        }
      }
    }
    return response;
  }

  public static ValidatorResponse validate(DataField field) {
    ValidatorResponse response = new ValidatorResponse();
    if (fields.contains(field.getTag()))
      if (field.getInd2().equals("7")) {
        List<MarcSubfield> subfield2 = field.getSubfield("2");
        if (subfield2 == null) {
          response.setValid(false);
          response.addValidationError(
            new ValidationError(
              field.getRecord().getId(),
              field.getTag() + "$ind2",
              ValidationErrorType.SUBFIELD_INVALID_CLASSIFICATION_REFERENCE,
              "ind2 is '7' which means that the value should be found in subfield $2, but it is missing",
              field.getDefinition().getDescriptionUrl()
            )
          );
        }
      }
    return response;
  }

}
