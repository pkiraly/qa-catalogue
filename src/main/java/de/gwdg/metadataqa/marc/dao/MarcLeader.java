package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.Validatable;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.controlpositions.LeaderPositions;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.io.Serializable;
import java.util.Map;

/**
 * This represents the Leader field of a MARC record. Its definition is in the LeaderDefinition class for the MARC21,
 * and for UNIMARC, there's an avram-style definition which gets read directly into the ControlFieldDefinition class.
 */
public abstract class MarcLeader extends MarcPositionalControlField implements Extractable, Validatable, Serializable {
  public enum Type {
    // This could be separated into a separate enum class because it is essentially a record type.
    BOOKS("Books"),
    CONTINUING_RESOURCES("Continuing Resources"),
    MUSIC("Music"),
    MAPS("Maps"),
    VISUAL_MATERIALS("Visual Materials"),
    COMPUTER_FILES("Computer Files"),
    MIXED_MATERIALS("Mixed Materials");

    final String value;
    Type(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  protected MarcLeader.Type type;
  protected MarcLeader.Type defaultType = null;

  protected ControlValue recordLength;
  protected ControlValue recordStatus;
  protected ControlValue typeOfRecord;
  protected ControlValue bibliographicLevel;
  protected ControlValue typeOfControl;
  protected ControlValue indicatorCount;
  protected ControlValue subfieldCodeCount;
  protected ControlValue baseAddressOfData;
  protected ControlValue encodingLevel;
  protected ControlValue descriptiveCatalogingForm;
  protected ControlValue lengthOfTheLengthOfFieldPortion;
  protected ControlValue lengthOfTheStartingCharacterPositionPortion;
  protected ControlValue lengthOfTheImplementationDefinedPortion;

  protected MarcLeader(ControlFieldDefinition definition, String content) {
    super(definition, content);
  }

  protected MarcLeader(ControlFieldDefinition definition, String content, MarcLeader.Type defaultType) {
    super(definition, content);
    this.defaultType = defaultType;
  }

  public String resolve(ControlfieldPositionDefinition key) {
    String value = valuesMap.get(key);
    return key.resolve(value);
  }

  public String resolve(String key) {
    return resolve(LeaderPositions.getByLabel(key));
  }

  @Override
  public Map<ControlfieldPositionDefinition, String> getMap() {
    return valuesMap;
  }

  public String get(ControlfieldPositionDefinition key) {
    return valuesMap.get(key);
  }

  public ControlValue getRecordLength() {
    return recordLength;
  }

  public ControlValue getRecordStatus() {
    return recordStatus;
  }

  public ControlValue getTypeOfRecord() {
    return typeOfRecord;
  }

  public ControlValue getBibliographicLevel() {
    return bibliographicLevel;
  }

  public ControlValue getTypeOfControl() {
    return typeOfControl;
  }

  public ControlValue getIndicatorCount() {
    return indicatorCount;
  }

  public ControlValue getSubfieldCodeCount() {
    return subfieldCodeCount;
  }

  public ControlValue getBaseAddressOfData() {
    return baseAddressOfData;
  }

  /**
   * @return The encoding level of the record from the position 17 of the Leader field.
   */
  public ControlValue getEncodingLevel() {
    return encodingLevel;
  }

  public ControlValue getDescriptiveCatalogingForm() {
    return descriptiveCatalogingForm;
  }

  public ControlValue getLengthOfTheLengthOfFieldPortion() {
    return lengthOfTheLengthOfFieldPortion;
  }

  public ControlValue getLengthOfTheStartingCharacterPositionPortion() {
    return lengthOfTheStartingCharacterPositionPortion;
  }

  public ControlValue getLengthOfTheImplementationDefinedPortion() {
    return lengthOfTheImplementationDefinedPortion;
  }

  public MarcLeader.Type getType() {
    return type;
  }

  public String getLeaderString() {
    return content;
  }
  @Override
  public ControlfieldPositionDefinition getSubfieldByPosition(Integer charStart) {
    return null;
  }


}
