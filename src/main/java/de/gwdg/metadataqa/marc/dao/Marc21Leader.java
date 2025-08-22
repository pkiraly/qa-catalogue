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
public class Marc21Leader extends MarcLeader implements Extractable, Validatable, Serializable {

  private static final Logger logger = Logger.getLogger(Marc21Leader.class.getCanonicalName());

  private ControlValue multipartResourceRecordLevel;
  private ControlValue characterCodingScheme;


  public Marc21Leader(String content) {
    super(LeaderDefinition.getInstance(), content);
    initialize();
  }

  public Marc21Leader(String content, MarcLeader.Type defaultType) {
    super(LeaderDefinition.getInstance(), content, defaultType);
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
        && bibliographicLevel.getValue().matches("^[acdm]$")) {
      type = MarcLeader.Type.BOOKS;
    } else if (typeOfRecord.getValue().equals("a")
        && bibliographicLevel.getValue().matches("^[bis]$")) {
      type = MarcLeader.Type.CONTINUING_RESOURCES;
    } else if (typeOfRecord.getValue().equals("t")) {
      type = MarcLeader.Type.BOOKS;
    } else if (typeOfRecord.getValue().matches("^[cdij]$")) {
      type = MarcLeader.Type.MUSIC;
    } else if (typeOfRecord.getValue().matches("^[ef]$")) {
      type = MarcLeader.Type.MAPS;
    } else if (typeOfRecord.getValue().matches("^[gkor]$")) {
      type = MarcLeader.Type.VISUAL_MATERIALS;
    } else if (typeOfRecord.getValue().equals("m")) {
      type = MarcLeader.Type.COMPUTER_FILES;
    } else if (typeOfRecord.getValue().equals("p")) {
      type = MarcLeader.Type.MIXED_MATERIALS;
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

  @Override
  public String resolve(ControlfieldPositionDefinition key) {
    String value = valuesMap.get(key);
    return key.resolve(value);
  }

  @Override
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

  @Override
  public String get(ControlfieldPositionDefinition key) {
    return valuesMap.get(key);
  }

  public String getByLabel(String key) {
    return get(LeaderPositions.getByLabel(key));
  }

  public String getById(String key) {
    return get(LeaderPositions.getInstance().getById(key));
  }

  public ControlValue getCharacterCodingScheme() {
    return characterCodingScheme;
  }

  public ControlValue getMultipartResourceRecordLevel() {
    return multipartResourceRecordLevel;
  }

  @Override
  public void setMarcRecord(BibliographicRecord marcRecord) {
    this.marcRecord = marcRecord;
    for (ControlValue value : valuesList)
      value.setMarcRecord(marcRecord);
  }

  public String toString() {
    StringBuilder output = new StringBuilder(String.format("type: %s%n", type.getValue()));
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
