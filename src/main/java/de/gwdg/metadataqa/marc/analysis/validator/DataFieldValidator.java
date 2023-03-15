package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.general.Linkage;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.model.validation.ErrorsCollector;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.FIELD_MISSING_REFERENCE_SUBFIELD;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.FIELD_UNDEFINED;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.INDICATOR_INVALID_VALUE;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.INDICATOR_NON_EMPTY;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.INDICATOR_OBSOLETE;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.RECORD_AMBIGUOUS_LINKAGE;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.RECORD_INVALID_LINKAGE;
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
    validationErrors = new ArrayList<>();
    errors = new ErrorsCollector();
    List<MarcSubfield> subfields = field.getSubfields();
    SubfieldValidator subfieldValidator = new SubfieldValidator(configuration);

    DataFieldDefinition referencerDefinition = null;
    List<MarcSubfield> linkedSubfields = null;
    boolean ambiguousLinkage = false;

    definition = field.getDefinition();
    if (configuration.getSchemaType().equals(SchemaType.MARC21)
        && TagDefinitionLoader.load(definition.getTag(), configuration.getMarcVersion()) == null) {
      addError(FIELD_UNDEFINED, "");
      return false;
    }

    if (field.getTag().equals("880")) {
      List<MarcSubfield> subfield6s = field.getSubfield("6");
      if (subfield6s == null) {
        addError(FIELD_MISSING_REFERENCE_SUBFIELD, "$6");
      } else {
        if (!subfield6s.isEmpty()) {
          if (subfield6s.size() != 1) {
            addError(definition.getTag() + "$6", RECORD_AMBIGUOUS_LINKAGE, "There are multiple $6");
            ambiguousLinkage = true;
          } else {
            MarcSubfield subfield6 = subfield6s.get(0);
            Linkage linkage = null;
            try {
              linkage = LinkageParser.getInstance().create(subfield6.getValue());
              if (linkage == null || linkage.getLinkingTag() == null) {
                String message = String.format("Unparseable reference: '%s'", subfield6.getValue());
                addError(RECORD_INVALID_LINKAGE, message);
              } else {
                referencerDefinition = definition;
                definition = TagDefinitionLoader.load(linkage.getLinkingTag(), configuration.getMarcVersion());

                if (definition == null) {
                  definition = referencerDefinition;
                  String message = String.format("refers to field %s, which is not defined", linkage.getLinkingTag());
                  addError(definition.getTag() + "$6", RECORD_INVALID_LINKAGE, message);
                } else {
                  linkedSubfields = field.getSubfields();
                  List<MarcSubfield> alternativeSubfields = new ArrayList<>();
                  for (MarcSubfield subfield : field.getSubfields()) {
                    MarcSubfield alternativeSubfield = new MarcSubfield(
                      definition.getSubfield(subfield.getCode()),
                      subfield.getCode(),
                      subfield.getValue()
                    );
                    alternativeSubfield.setField(field);
                    alternativeSubfield.setMarcRecord(field.getMarcRecord());
                    alternativeSubfield.setLinkage(linkage);
                    alternativeSubfield.setReferencePath(referencerDefinition.getTag());
                    alternativeSubfields.add(alternativeSubfield);
                  }
                  subfields = alternativeSubfields;
                }
              }
            } catch (ParserException e) {
              addError(definition.getTag() + "$6", RECORD_INVALID_LINKAGE, e.getMessage());
            }
          }
        }
      }
    }

    if (field.getUnhandledSubfields() != null)
      addError(SUBFIELD_UNDEFINED, StringUtils.join(field.getUnhandledSubfields(), ", "));

    if (configuration.getSchemaType().equals(SchemaType.MARC21)) {
      if (field.getInd1() != null)
        validateIndicator(definition.getInd1(), field.getInd1(), configuration.getMarcVersion(), referencerDefinition);

      if (field.getInd2() != null)
        validateIndicator(definition.getInd2(), field.getInd2(), configuration.getMarcVersion(), referencerDefinition);
    }

    if (!ambiguousLinkage) {
      Map<SubfieldDefinition, Integer> counter = new HashMap<>();
      for (MarcSubfield subfield : subfields) {
        if (subfield.getDefinition() == null) {
          if (definition.isVersionSpecificSubfields(configuration.getMarcVersion(), subfield.getCode())) {
            subfield.setDefinition(
              definition.getVersionSpecificSubfield(
                configuration.getMarcVersion(), subfield.getCode()));
          } else {
            addError(SUBFIELD_UNDEFINED, subfield.getCode());
            continue;
          }
        }
        Utils.count(subfield.getDefinition(), counter);

        if (!subfieldValidator.validate(subfield))
          errors.addAll(subfieldValidator.getValidationErrors());
      }

      for (Map.Entry<SubfieldDefinition, Integer> entry : counter.entrySet()) {
        SubfieldDefinition subfieldDefinition = entry.getKey();
        Integer count = entry.getValue();
        if (count > 1 && subfieldDefinition.getCardinality().equals(Cardinality.Nonrepeatable)) {
          addError(subfieldDefinition, SUBFIELD_NONREPEATABLE, String.format("there are %d instances", count));
        }
      }
    }

    if (referencerDefinition != null)
      definition = referencerDefinition;
    if (linkedSubfields != null)
      subfields = linkedSubfields;

    validationErrors.addAll(errors.getErrors());
    return errors.isEmpty();
  }

  private boolean validateIndicator(Indicator indicatorDefinition,
                                    String value,
                                    MarcVersion marcVersion,
                                    DataFieldDefinition referencerDefinition) {
    String path = indicatorDefinition.getPath();
    if (referencerDefinition != null)
      path = String.format("%s->%s", referencerDefinition.getTag(), path);

    if (indicatorDefinition.exists()) {
      if (!indicatorDefinition.hasCode(value)
          && !indicatorDefinition.isVersionSpecificCode(marcVersion, value)) {
        if (indicatorDefinition.isHistoricalCode(value)) {
          addError(path, INDICATOR_OBSOLETE, value);
        } else {
          addError(path, INDICATOR_INVALID_VALUE, value);
        }
      }
    } else {
      if (!value.equals(" ")
          && !indicatorDefinition.isVersionSpecificCode(marcVersion, value))
         addError(path, INDICATOR_NON_EMPTY, value);
    }
    return errors.isEmpty();
  }

  private void addError(ValidationErrorType type, String message) {
    addError(definition.getExtendedTag(), type, message);
  }

  private void addError(SubfieldDefinition subfieldDefinition, ValidationErrorType type, String message) {
    addError(subfieldDefinition.getPath(), type, message);
  }

  private void addError(String path, ValidationErrorType type, String message) {
    if (!isIgnorableType(type)) {
      String id = field.getMarcRecord() == null ? null : field.getMarcRecord().getId();
      String url = definition.getDescriptionUrl();
      errors.add(id, path, type, message, url);
    }
  }
}
