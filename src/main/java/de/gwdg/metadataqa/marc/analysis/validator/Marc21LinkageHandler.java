package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import de.gwdg.metadataqa.marc.definition.general.Linkage;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.model.validation.ErrorsCollector;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.ArrayList;
import java.util.List;

import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.FIELD_MISSING_REFERENCE_SUBFIELD;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.RECORD_AMBIGUOUS_LINKAGE;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.RECORD_INVALID_LINKAGE;

/**
 * A handler for linkage validation. Currently, handles only the MARC21 field 880, subfield 6. It is not clear
 * whether similar fields exist in UNIMARC and PICA.
 */
public class Marc21LinkageHandler {
  private boolean isAmbiguousLinkage = false;
  private final ErrorsCollector errorsCollector;
  private final ValidatorConfiguration configuration;
  public Marc21LinkageHandler(ValidatorConfiguration configuration, ErrorsCollector errorsCollector) {
    this.configuration = configuration;
    this.errorsCollector = errorsCollector;
  }

  /**
   * Validates the linkage of the field 880. Returns the associated field information if the linkage is valid.
   * Otherwise, returns null.
   * In addition:
   * - adds errors to the errorsCollector
   * - sets the ambiguousLinkage flag to true if the linkage is ambiguous
   * <p>
   * <b>Note:</b> Linkage means that the field 880 is linked to another field specified in the subfield $6. In that
   * case, the subfields of the field 880 are the same as the subfields of the linked (i.e. <b>associated</b>) field.
   * </p>
   * @param field The field for which the linkage is to be validated and handled
   * @param definition The definition of the field
   * @return The associated field if the linkage is valid, otherwise null
   */
  public DataField handleLinkage(DataField field,
                                 DataFieldDefinition definition) {
    if (!field.getTag().equals("880")) {
      return null;
    }
    String recordId = field.getBibliographicRecord() == null ? null : field.getBibliographicRecord().getId();

    List<MarcSubfield> subfield6s = field.getSubfield("6");
    if (subfield6s == null || subfield6s.isEmpty()) {
      addError(recordId,
          definition.getExtendedTag(),
          FIELD_MISSING_REFERENCE_SUBFIELD,
          "$6",
          definition.getDescriptionUrl());
      return null;
    }

    if (subfield6s.size() > 1) {
      addError(recordId,
          definition.getTag() + "$6",
          RECORD_AMBIGUOUS_LINKAGE,
          "There are multiple $6",
          definition.getDescriptionUrl());
      this.isAmbiguousLinkage = true;
      return null;
    }

    MarcSubfield subfield6 = subfield6s.get(0);

    Linkage linkage;
    try {
      linkage = LinkageParser.getInstance().create(subfield6.getValue());
    } catch (ParserException e) {
      addError(recordId, definition.getTag() + "$6", RECORD_INVALID_LINKAGE, e.getMessage(), definition.getDescriptionUrl());
      return null;
    }

    if (linkage == null || linkage.getLinkingTag() == null) {
      String message = String.format("Unparseable reference: '%s'", subfield6.getValue());
      addError(recordId, definition.getExtendedTag(), RECORD_INVALID_LINKAGE, message, definition.getDescriptionUrl());
      return null;
    }

    DataFieldDefinition referencedDefinition = TagDefinitionLoader
        .load(linkage.getLinkingTag(), configuration.getMarcVersion());

    if (referencedDefinition == null) {
      String message = String.format("refers to field %s, which is not defined", linkage.getLinkingTag());
      addError(recordId, definition.getTag() + "$6", RECORD_INVALID_LINKAGE, message, definition.getDescriptionUrl());
      return null;
    }

    List<MarcSubfield> associatedSubfields = new ArrayList<>();
    for (MarcSubfield subfield : field.getSubfields()) {
      MarcSubfield associatedSubfield = new MarcSubfield(
          definition.getSubfield(subfield.getCode()),
          subfield.getCode(),
          subfield.getValue()
      );
      associatedSubfield.setField(field);
      associatedSubfield.setMarcRecord(field.getBibliographicRecord());
      associatedSubfield.setLinkage(linkage);
      associatedSubfield.setReferencePath(definition.getTag());
      associatedSubfields.add(associatedSubfield);
    }

    DataField associatedField = new DataField(referencedDefinition, field.getInd1(), field.getInd2());

    associatedField.setSubfields(associatedSubfields);
    return associatedField;
  }

  public List<ValidationError> getErrors() {
    return errorsCollector.getErrors();
  }

  public boolean isAmbiguousLinkage() {
    return isAmbiguousLinkage;
  }

  private void addError(String recordId, String path, ValidationErrorType type, String message, String url) {
    if (!isIgnorableType(type)) {
      errorsCollector.add(recordId, path, type, message, url);
    }
  }

  private boolean isIgnorableType(ValidationErrorType type) {
    return (
        configuration.getIgnorableIssueTypes() != null
            && !configuration.getIgnorableIssueTypes().isEmpty()
            && configuration.getIgnorableIssueTypes().contains(type)
    );
  }
}
