package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.Validatable;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import de.gwdg.metadataqa.marc.utils.unimarc.UnimarcLeaderDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnimarcLeader extends MarcLeader implements Extractable, Validatable, Serializable {

  private static final Logger logger = Logger.getLogger(UnimarcLeader.class.getCanonicalName());

  private ControlValue hierarchicalLevelCode;

  // This should probably get a factory
  public UnimarcLeader(UnimarcLeaderDefinition leaderDefinition, String content) {
    super(leaderDefinition, content);
  }

  public UnimarcLeader(UnimarcLeaderDefinition leaderDefinition, String content, Type defaultType) {
    super(leaderDefinition, content, defaultType);
  }

  /**
   * Initializes the field by processing the content of the leader. It also
   * sets the type of the record.
   * Processing the content means that the given content gets divided into the predefined
   * fields within the leader, as well as the map of values, so that the both can be utilized.
   */
  public void initialize() {
    initializationErrors = new ArrayList<>();
    processContent();

    try {
      setType();
    } catch (IllegalArgumentException e) {
      initializationErrors.add(new ValidationError(
          null,
          definition.getTag(),
          ValidationErrorType.RECORD_UNDETECTABLE_TYPE,
          e.getMessage(),
          ""));
    }
  }

  /**
   * Given the content of the leader (essentially values of the field with the LEADER tag), e.g. 02794cam0 2200709   450,
   * it processes the content and divides it into the predefined fields within the leader, as well as the map of values.
   * From the example, the first five characters are the record length, and they get assigned to the <code>recordLength</code>
   * field, and so on.
   */
  protected void processContent() {
    ControlFieldDefinition fieldDefinition = (ControlFieldDefinition) definition;
    List<ControlfieldPositionDefinition> positionDefinitions =
        fieldDefinition.getControlfieldPosition("ALL_MATERIALS");

    for (ControlfieldPositionDefinition positionDefinition : positionDefinitions) {
      int end = Math.min(content.length(), positionDefinition.getPositionEnd());
      try {
        String value = content.substring(positionDefinition.getPositionStart(), end);
        ControlValue controlValue = new ControlValue(positionDefinition, value);
        valuesList.add(controlValue);

        // One portion of this could technically be moved to the MarcLeader class
        switch (positionDefinition.getPositionStart()) {
          case 0: recordLength = controlValue; break;
          case 5: recordStatus = controlValue; break;
          case 6: typeOfRecord = controlValue; break;
          case 7: bibliographicLevel = controlValue; break;
          case 8: hierarchicalLevelCode = controlValue; break;
          case 9: typeOfControl = controlValue; break;
          case 10: indicatorCount = controlValue; break;
          case 11: subfieldCodeCount = controlValue; break;
          case 12: baseAddressOfData = controlValue; break;
          case 17: encodingLevel = controlValue; break;
          case 18: descriptiveCatalogingForm = controlValue; break;
          case 20: lengthOfTheLengthOfFieldPortion = controlValue; break;
          case 21: lengthOfTheStartingCharacterPositionPortion = controlValue; break;
          case 22: lengthOfTheImplementationDefinedPortion = controlValue; break;
          default:
            break;
        }
        valuesMap.put(positionDefinition, value);
      } catch (StringIndexOutOfBoundsException e) {
        logger.log(Level.SEVERE, "Problem with processing Leader (\"{0}\"). The content length is only {1} while reading position @{2}-{3} (for {4})",
          new Object[]{content, content.length(), positionDefinition.getPositionStart(), positionDefinition.getPositionEnd(), positionDefinition.getLabel()});
      }
    }
  }

  private void setType() {
    // As this is quite similar to Marc21 except for some position values, types will be determined for UNIMARC as well.
    if (typeOfRecord.getValue().equals("a")
        && bibliographicLevel.getValue().matches("^[acm]$")) {
      type = Type.BOOKS;
    } else if (typeOfRecord.getValue().equals("a")
        && bibliographicLevel.getValue().matches("^[is]$")) {
      type = Type.CONTINUING_RESOURCES;
    } else if (typeOfRecord.getValue().equals("b")) {
      type = Type.BOOKS;
    } else if (typeOfRecord.getValue().matches("^[cdij]$")) {
      type = Type.MUSIC;
    } else if (typeOfRecord.getValue().matches("^[ef]$")) {
      type = Type.MAPS;
    } else if (typeOfRecord.getValue().matches("^[gkmr]$")) {
      type = Type.VISUAL_MATERIALS;
    } else if (typeOfRecord.getValue().equals("l")) {
      type = Type.COMPUTER_FILES;
    } else {
      if (defaultType != null) {
        type = defaultType;
      }
      throw new IllegalArgumentException(
        String.format(
          "Leader/%s (%s): '%s', Leader/%s (%s): '%s'",
          typeOfRecord.getDefinition().formatPositon(),
          typeOfRecord.getDefinition().getMqTag(),
          typeOfRecord.getValue(),
          bibliographicLevel.getDefinition().formatPositon(),
          bibliographicLevel.getDefinition().getMqTag(),
          bibliographicLevel.getValue()));
    }
  }

  @Override
  public void setMarcRecord(BibliographicRecord marcRecord) {
    this.marcRecord = marcRecord;
    for (ControlValue value : valuesList) {
      value.setMarcRecord(marcRecord);
    }
  }

  public String toString() {
    ControlFieldDefinition fieldDefinition = (ControlFieldDefinition) definition;
    List<ControlfieldPositionDefinition> positionDefinitions =
        fieldDefinition.getControlfieldPosition("ALL_MATERIALS");

    StringBuilder output = new StringBuilder(String.format("type: %s%n", type.getValue()));
    for (ControlfieldPositionDefinition key : positionDefinitions) {
      output.append(String.format("%s: %s%n", key.getLabel(), resolve(key)));
    }
    return output.toString();
  }

  public ControlValue getHierarchicalLevelCode() {
    return hierarchicalLevelCode;
  }

  public void setHierarchicalLevelCode(ControlValue hierarchicalLevelCode) {
    this.hierarchicalLevelCode = hierarchicalLevelCode;
  }

}
