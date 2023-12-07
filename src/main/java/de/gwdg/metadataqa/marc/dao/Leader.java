package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.Validatable;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.controlpositions.LeaderPositions;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.tags.control.LeaderDefinition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import de.gwdg.metadataqa.marc.utils.keygenerator.PositionalControlFieldKeyGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Leader extends MarcPositionalControlField implements Extractable, Validatable, Serializable {

  private static final Logger logger = Logger.getLogger(Leader.class.getCanonicalName());

  // private String tag = "Leader";
  // private String mqTag = "Leader";

  public enum Type {
    BOOKS("Books"),
    CONTINUING_RESOURCES("Continuing Resources"),
    MUSIC("Music"),
    MAPS("Maps"),
    VISUAL_MATERIALS("Visual Materials"),
    COMPUTER_FILES("Computer Files"),
    MIXED_MATERIALS("Mixed Materials");

    String value;
    Type(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  // private String content;
  // private Map<ControlSubfieldDefinition, String> valuesMap;
  // private List<ControlValue> valuesList;
  private Type type;
  private Type defaultType = null;

  private ControlValue recordLength;
  private ControlValue recordStatus;
  private ControlValue typeOfRecord;
  private ControlValue bibliographicLevel;
  private ControlValue typeOfControl;
  private ControlValue characterCodingScheme;
  private ControlValue indicatorCount;
  private ControlValue subfieldCodeCount;
  private ControlValue baseAddressOfData;
  private ControlValue encodingLevel;
  private ControlValue descriptiveCatalogingForm;
  private ControlValue multipartResourceRecordLevel;
  private ControlValue lengthOfTheLengthOfFieldPortion;
  private ControlValue lengthOfTheStartingCharacterPositionPortion;
  private ControlValue lengthOfTheImplementationDefinedPortion;

  public Leader(String content) {
    super(LeaderDefinition.getInstance(), content);
    initialize();
  }

  public Leader(String content, Type defaultType) {
    super(LeaderDefinition.getInstance(), content);
    this.defaultType = defaultType;
    initialize();
  }

  private void initialize() {
    initializationErrors = new ArrayList<>();
    processContent();
    try {
      setType();
    } catch (IllegalArgumentException e) {
      initializationErrors.add(
        new ValidationError(
          null,
          definition.getTag(),
          ValidationErrorType.RECORD_UNDETECTABLE_TYPE,
          e.getMessage(),
          LeaderPositions.getInstance().getPositionList().get(0).getDescriptionUrl()
        )
      );
    }
  }

  protected void processContent() {
    for (ControlfieldPositionDefinition subfield : LeaderPositions.getInstance().getPositionList()) {
      int end = Math.min(content.length(), subfield.getPositionEnd());
      try {
        String value = content.substring(subfield.getPositionStart(), end);
        ControlValue controlValue = new ControlValue(subfield, value);
        valuesList.add(controlValue);

        switch (subfield.getId()) {
          case "leader00": recordLength = controlValue; break;
          case "leader05": recordStatus = controlValue; break;
          case "leader06": typeOfRecord = controlValue; break;
          case "leader07": bibliographicLevel = controlValue; break;
          case "leader08": typeOfControl = controlValue; break;
          case "leader09": characterCodingScheme = controlValue; break;
          case "leader10": indicatorCount = controlValue; break;
          case "leader11": subfieldCodeCount = controlValue; break;
          case "leader12": baseAddressOfData = controlValue; break;
          case "leader17": encodingLevel = controlValue; break;
          case "leader18": descriptiveCatalogingForm = controlValue; break;
          case "leader19": multipartResourceRecordLevel = controlValue; break;
          case "leader20": lengthOfTheLengthOfFieldPortion = controlValue; break;
          case "leader21": lengthOfTheStartingCharacterPositionPortion = controlValue; break;
          case "leader22": lengthOfTheImplementationDefinedPortion = controlValue; break;
          default:
            break;
        }
        valuesMap.put(subfield, value);
      } catch (StringIndexOutOfBoundsException e) {
        logger.log(Level.SEVERE, "Problem with processing Leader (\"{0}\"). The content length is only {1} while reading position @{2}-{3} (for {4})",
          new Object[]{content, content.length(), subfield.getPositionStart(), subfield.getPositionEnd(), subfield.getLabel()});
      }
    }
  }

  private void setType() {
    if (typeOfRecord.getValue().equals("a")
        && bibliographicLevel.getValue().matches("^(a|c|d|m)$")) {
      type = Type.BOOKS;
    } else if (typeOfRecord.getValue().equals("a")
        && bibliographicLevel.getValue().matches("^(b|i|s)$")) {
      type = Type.CONTINUING_RESOURCES;
    } else if (typeOfRecord.getValue().equals("t")) {
      type = Type.BOOKS;
    } else if (typeOfRecord.getValue().matches("^[cdij]$")) {
      type = Type.MUSIC;
    } else if (typeOfRecord.getValue().matches("^[ef]$")) {
      type = Type.MAPS;
    } else if (typeOfRecord.getValue().matches("^[gkor]$")) {
      type = Type.VISUAL_MATERIALS;
    } else if (typeOfRecord.getValue().equals("m")) {
      type = Type.COMPUTER_FILES;
    } else if (typeOfRecord.getValue().equals("p")) {
      type = Type.MIXED_MATERIALS;
    } else {
      if (defaultType != null)
        type = defaultType;
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

  @Override
  public ControlfieldPositionDefinition getSubfieldByPosition(Integer charStart) {
    return null;
  }

  public String get(ControlfieldPositionDefinition key) {
    return valuesMap.get(key);
  }

  public String getByLabel(String key) {
    return get(LeaderPositions.getByLabel(key));
  }

  public String getById(String key) {
    return get(LeaderPositions.getInstance().getById(key));
  }

  /**
   * Return Tpye
   * @return
   */
  public Type getType() {
    return type;
  }

  public String getLeaderString() {
    return content;
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

  public ControlValue getCharacterCodingScheme() {
    return characterCodingScheme;
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
   * Leader17
   * @return
   */
  public ControlValue getEncodingLevel() {
    return encodingLevel;
  }

  public ControlValue getDescriptiveCatalogingForm() {
    return descriptiveCatalogingForm;
  }

  public ControlValue getMultipartResourceRecordLevel() {
    return multipartResourceRecordLevel;
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

  @Override
  public void setMarcRecord(BibliographicRecord marcRecord) {
    this.marcRecord = marcRecord;
    for (ControlValue value : valuesList)
      value.setMarcRecord(marcRecord);
  }

  public String toString() {
    StringBuffer output = new StringBuffer(String.format("type: %s%n", type.getValue()));
    for (ControlfieldPositionDefinition key : LeaderPositions.getInstance().getPositionList()) {
      output.append(String.format("%s: %s%n", key.getLabel(), resolve(key)));
    }
    return output.toString();
  }

  @Override
  public Map<String, List<String>> getKeyValuePairs() {
    return getKeyValuePairs(SolrFieldType.MARC);
  }

  @Override
  public Map<String, List<String>> getKeyValuePairs(SolrFieldType type) {
    Map<String, List<String>> map = new LinkedHashMap<>();
    PositionalControlFieldKeyGenerator keyGenerator = new PositionalControlFieldKeyGenerator(
      definition.getTag(), definition.getMqTag(), type);
    map.put(keyGenerator.forTag(), Arrays.asList(content));
    for (Map.Entry<ControlfieldPositionDefinition, String> entry : valuesMap.entrySet()) {
      ControlfieldPositionDefinition position = entry.getKey();
      String value = position.resolve(entry.getValue());
      map.put(keyGenerator.forSubfield(position), Arrays.asList(value));
    }
    return map;
  }

  @Override
  public List<ValidationError> getInitializationErrors() {
    return initializationErrors;
  }
}
