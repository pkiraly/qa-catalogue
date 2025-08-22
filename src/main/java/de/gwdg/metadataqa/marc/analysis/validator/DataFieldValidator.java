package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.model.validation.ErrorsCollector;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.INDICATOR_INVALID_VALUE;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.INDICATOR_NON_EMPTY;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.INDICATOR_OBSOLETE;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.SUBFIELD_NONREPEATABLE;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.SUBFIELD_UNDEFINED;

public class DataFieldValidator extends AbstractValidator {

  private ErrorsCollector errors;
  private DataField field;
  private DataFieldDefinition definition;

  public DataFieldValidator() {
    super(new ValidatorConfiguration());
  }

  public DataFieldValidator(ValidatorConfiguration configuration) {
    super(configuration);
  }

  public boolean validate(DataField field) {
    this.field = field;
    definition = field.getDefinition();
    validationErrors = new ArrayList<>();
    errors = new ErrorsCollector();

    // Check if the tag is defined only in case it is a MARC21 tag
    // I'm not sure how this is different to validating unhandled tags. However, it will be kept until further notice.
    boolean isMarc21TagDefined = isMarc21TagDefined();
    if (!isMarc21TagDefined) {
      addError(ValidationErrorType.FIELD_UNDEFINED, "");
      return false;
    }

    // From here on, we know the tag is either MARC21 and defined, or not MARC21
    // This LinkageHandler can later be further abstracted into an abstract LinkageHandler for UNIMARC in case that's needed
    Marc21LinkageHandler linkageHandler = new Marc21LinkageHandler(configuration, errors);
    DataField associatedField = linkageHandler.handleLinkage(field, definition);
    errors.addAll(linkageHandler.getErrors());

    // In case the associated field is not null, and due to compatibility with the rest of this class,
    // assign the referencer definition and the subfields of the associated field to the local variables
    // This assignment makes sure that the methods which use the attribute definition, use the definition
    // of the referencer field if needed
    DataFieldDefinition referencerDefinition = null;
    List<MarcSubfield> subfields = field.getSubfields();
    if (associatedField != null) {
      referencerDefinition = definition;
      definition = associatedField.getDefinition();
      subfields = associatedField.getSubfields();
    }

    validateIndicators(referencerDefinition);

    // This seems to never be executed because the addUnhandledSubfield method is never called
    if (field.getUnhandledSubfields() != null) {
      addError(SUBFIELD_UNDEFINED, StringUtils.join(field.getUnhandledSubfields(), ", "));
    }

    boolean isAmbiguousLinkage = linkageHandler.isAmbiguousLinkage();

    if (!isAmbiguousLinkage) {
      validateSubfields(subfields);
    }

    if (associatedField != null) {
      definition = referencerDefinition;
    }

    validationErrors.addAll(errors.getErrors());
    return errors.isEmpty();
  }

  private void validateIndicators(DataFieldDefinition referencerDefinition) {
    if (configuration.getSchemaType().equals(SchemaType.PICA)) {
      return;
    }

    if (field.getInd1() != null) {
      validateIndicator(definition.getInd1(), field.getInd1(), configuration.getMarcVersion(), referencerDefinition);
    }
    if (field.getInd2() != null) {
      validateIndicator(definition.getInd2(), field.getInd2(), configuration.getMarcVersion(), referencerDefinition);
    }
  }

  private boolean validateIndicator(Indicator indicatorDefinition,
                                    String value,
                                    MarcVersion marcVersion,
                                    DataFieldDefinition referencerDefinition) {
    String path = indicatorDefinition.getPath();

    if (referencerDefinition != null) {
      path = String.format("%s->%s", referencerDefinition.getTag(), path);
    }

    boolean isVersionSpecific = indicatorDefinition.isVersionSpecificCode(marcVersion, value);
    boolean indicatorExists = indicatorDefinition.exists();

    // The first invalid case is:
    // Indicator undefined but the value is not empty (and also it doesn't depend on the marc version used)
    if (!indicatorExists && !value.equals(" ") && !isVersionSpecific) {
        addError(path, INDICATOR_NON_EMPTY, value);
        return false;
    }

    // The second invalid case is when the indicator is defined, but the value isn't a valid code (and also it
    // doesn't depend on the marc version used).
    // In case the code used is some old (historical) code, then that's an obsolete code error, and if it is
    // just plainly a wrong code, then it is an invalid value error.
    if (indicatorExists && !indicatorDefinition.hasCode(value) && !isVersionSpecific) {
      // Maybe if the value is a space, it should be clarified in the error message a little differently
      if (indicatorDefinition.isHistoricalCode(value)) {
        addError(path, INDICATOR_OBSOLETE, value);
      } else {
        addError(path, INDICATOR_INVALID_VALUE, value);
      }
      return false;
    }

    // All other cases are valid: either the indicator is defined and the value is a valid code, or the indicator
    // is undefined and the value is empty (or it is a version specific code).
    return true;
  }

  private void validateSubfields(List<MarcSubfield> subfields) {
    SubfieldValidator subfieldValidator = new SubfieldValidator(configuration);

    for (MarcSubfield subfield : subfields) {
      addVersionSpecificDefinition(subfield);
      boolean isSubfieldValid = subfieldValidator.validate(subfield);

      if (!isSubfieldValid) {
        errors.addAll(subfieldValidator.getValidationErrors());
      }
    }

    // Count the number of subfield definitions
    Map<SubfieldDefinition, Long> counter = subfields.stream()
        .filter(subfield -> subfield.getDefinition() != null)
        .collect(Collectors.groupingBy(MarcSubfield::getDefinition, Collectors.counting()));

    // Add errors for all non-repeatable subfields which were repeated
    for (Map.Entry<SubfieldDefinition, Long> entry : counter.entrySet()) {
      SubfieldDefinition subfieldDefinition = entry.getKey();
      long count = entry.getValue();
      if (count > 1 && subfieldDefinition.getCardinality().equals(Cardinality.Nonrepeatable)) {
        // addError(subfieldDefinition, SUBFIELD_NONREPEATABLE, String.format("there are %d instances", count));
        addError(subfieldDefinition, SUBFIELD_NONREPEATABLE, String.format("there are multiple instances", count));
      }
    }

  }

  /**
   * Adds the definition of the currently specified marc version to the subfield if it exists.
   * @param subfield The subfield to which the definition should be added
   */
  private void addVersionSpecificDefinition(MarcSubfield subfield) {
    if (subfield.getDefinition() != null) {
      return;
    }

    if (definition.isVersionSpecificSubfields(configuration.getMarcVersion(), subfield.getCode())) {
      subfield.setDefinition(
          definition.getVersionSpecificDefinition(
              configuration.getMarcVersion(), subfield.getCode()));
    }
  }

  private void addError(ValidationErrorType type, String message) {
    addError(definition.getExtendedTag(), type, message);
  }

  private void addError(SubfieldDefinition subfieldDefinition, ValidationErrorType type, String message) {
    addError(subfieldDefinition.getPath(), type, message);
  }

  private void addError(String path, ValidationErrorType type, String message) {
    if (!isIgnorableType(type)) {
      String id = field.getBibliographicRecord() == null ? null : field.getBibliographicRecord().getId();
      String url = definition.getDescriptionUrl();
      errors.add(id, path, type, message, url);
    }
  }

  /**
   * @return True if the tag isn't MARC21 or if it's MARC21 and is defined. Otherwise, false.
   */
  private boolean isMarc21TagDefined() {
    boolean isMarc21 = configuration.getSchemaType().equals(SchemaType.MARC21);

    // If it is not MARC21, we don't need to check if the tag is defined
    if (!isMarc21) {
      return true;
    }

    // If tag definition could have been loaded, it is defined
    return TagDefinitionLoader.load(definition.getTag(), configuration.getMarcVersion()) != null;
  }
}
