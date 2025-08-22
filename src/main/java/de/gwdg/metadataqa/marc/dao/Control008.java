package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control008Positions;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control008Definition;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control008 extends MarcPositionalControlField implements Serializable {

  private static final Logger logger = Logger.getLogger(Control008.class.getCanonicalName());

  private ControlValue tag008all00;
  private ControlValue tag008all06;
  private ControlValue tag008all07;
  private ControlValue tag008all11;
  private ControlValue tag008all15;
  private ControlValue tag008all35;
  private ControlValue tag008all38;
  private ControlValue tag008all39;

  private ControlValue tag008book18;
  private ControlValue tag008book22;
  private ControlValue tag008book23;
  private ControlValue tag008book24;
  private ControlValue tag008book28;
  private ControlValue tag008book29;
  private ControlValue tag008book30;
  private ControlValue tag008book31;
  private ControlValue tag008book33;
  private ControlValue tag008book34;

  private ControlValue tag008computer22;
  private ControlValue tag008computer23;
  private ControlValue tag008computer26;
  private ControlValue tag008computer28;

  private ControlValue tag008map18;
  private ControlValue tag008map22;
  private ControlValue tag008map25;
  private ControlValue tag008map28;
  private ControlValue tag008map29;
  private ControlValue tag008map31;
  private ControlValue tag008map33;

  private ControlValue tag008music18;
  private ControlValue tag008music20;
  private ControlValue tag008music21;
  private ControlValue tag008music22;
  private ControlValue tag008music23;
  private ControlValue tag008music24;
  private ControlValue tag008music30;
  private ControlValue tag008music33;

  private ControlValue tag008continuing18;
  private ControlValue tag008continuing19;
  private ControlValue tag008continuing21;
  private ControlValue tag008continuing22;
  private ControlValue tag008continuing23;
  private ControlValue tag008continuing24;
  private ControlValue tag008continuing25;
  private ControlValue tag008continuing28;
  private ControlValue tag008continuing29;
  private ControlValue tag008continuing33;
  private ControlValue tag008continuing34;

  private ControlValue tag008visual18;
  private ControlValue tag008visual22;
  private ControlValue tag008visual28;
  private ControlValue tag008visual29;
  private ControlValue tag008visual33;
  private ControlValue tag008visual34;

  private ControlValue tag008mixed23;

  private final Map<Control008Type, List<ControlValue>> fieldGroups = new EnumMap<>(Control008Type.class);

  private final Map<Integer, ControlfieldPositionDefinition> byPosition = new LinkedHashMap<>();
  private Control008Type actual008Type;

  public Control008(String content, MarcLeader.Type recordType) {
    super(Control008Definition.getInstance(), content, recordType);
    initialize();
  }

  public Control008(String content, Marc21Record marcRecord) {
    super(Control008Definition.getInstance(), content, marcRecord.getType());
    this.marcRecord = marcRecord;
    initialize();
  }

  public void initialize() {
    if (recordType == null) {
      throw new InvalidParameterException(String.format("Record type is null. 008 content: '%s'", content));
    }
    actual008Type = Control008Type.byCode(recordType.getValue());
    processContent();
  }

  protected void processContent() {
    for (ControlfieldPositionDefinition subfield : Control008Positions.getInstance().get(Control008Type.ALL_MATERIALS)) {

      int end = Math.min(content.length(), subfield.getPositionEnd());
      if (end < 0) {
        logger.severe(content.length() + " " + subfield.getPositionEnd());
      }
      String value = null;
      if (subfield.getPositionStart() <= content.length()
        && subfield.getPositionStart() < end) {
        try {
          value = content.substring(subfield.getPositionStart(), end);
        } catch (StringIndexOutOfBoundsException e) {
          logger.log(Level.SEVERE,
            "Problem with processing 008 (\"{0}\"). The content length is only {1} while reading position @{2}-{3} (for {4} \"{5}\")",
            new Object[]{content, content.length(), subfield.getPositionStart(), subfield.getPositionEnd(), subfield.getId(), subfield.getLabel()});
        }
      } else {
        break;
      }

      ControlValue controlValue = new ControlValue(subfield, value);
      registerControlValue(controlValue);

      switch (subfield.getId()) {
        case "008all00": tag008all00 = controlValue; break;
        case "008all06": tag008all06 = controlValue; break;
        case "008all07": tag008all07 = controlValue; break;
        case "008all11": tag008all11 = controlValue; break;
        case "008all15": tag008all15 = controlValue; break;
        case "008all35": tag008all35 = controlValue; break;
        case "008all38": tag008all38 = controlValue; break;
        case "008all39": tag008all39 = controlValue; break;
        default:
          logger.log(Level.SEVERE, "Unhandled 008 subfield: " + subfield.getId());
          break;
      }

      valuesMap.put(subfield, value);
      byPosition.put(subfield.getPositionStart(), subfield);
    }

    for (ControlfieldPositionDefinition subfield : Control008Positions.getInstance().get(actual008Type)) {
      int end = Math.min(content.length(), subfield.getPositionEnd());

      String value = null;
      if (subfield.getPositionStart() <= content.length()) {
        try {
          value = content.substring(subfield.getPositionStart(), end);
        } catch (StringIndexOutOfBoundsException e) {
          logger.log(Level.SEVERE,
            "Problem with processing 008 (\"{0}\"). The content length is only {1} while reading position @{2}-{3} (for {4} \"{5}\")",
            new Object[]{content, content.length(), subfield.getPositionStart(), subfield.getPositionEnd(), subfield.getId(), subfield.getLabel()});
        }
      } else {
        break;
      }

      ControlValue controlValue = new ControlValue(subfield, value);
      registerControlValue(controlValue);

      switch (actual008Type) {
        case BOOKS:
          switch (subfield.getId()) {
            case "008book18": tag008book18 = controlValue; break;
            case "008book22": tag008book22 = controlValue; break;
            case "008book23": tag008book23 = controlValue; break;
            case "008book24": tag008book24 = controlValue; break;
            case "008book28": tag008book28 = controlValue; break;
            case "008book29": tag008book29 = controlValue; break;
            case "008book30": tag008book30 = controlValue; break;
            case "008book31": tag008book31 = controlValue; break;
            case "008book33": tag008book33 = controlValue; break;
            case "008book34": tag008book34 = controlValue; break;
            default:
              logger.log(Level.SEVERE, "Unhandled 008 subfield: " + subfield.getId());
              break;
          }
          break;
        case COMPUTER_FILES:
          switch (subfield.getId()) {
            case "008computer22": tag008computer22 = controlValue; break;
            case "008computer23": tag008computer23 = controlValue; break;
            case "008computer26": tag008computer26 = controlValue; break;
            case "008computer28": tag008computer28 = controlValue; break;
            default:
              logger.log(Level.SEVERE, "Unhandled 008 subfield: " + subfield.getId());
              break;
          }
          break;
        case MAPS:
          switch (subfield.getId()) {
            case "008map18": tag008map18 = controlValue; break;
            case "008map22": tag008map22 = controlValue; break;
            case "008map25": tag008map25 = controlValue; break;
            case "008map28": tag008map28 = controlValue; break;
            case "008map29": tag008map29 = controlValue; break;
            case "008map31": tag008map31 = controlValue; break;
            case "008map33": tag008map33 = controlValue; break;
            default:
              logger.log(Level.SEVERE, "Unhandled 008 subfield: " + subfield.getId());
              break;
          }
          break;
        case MUSIC:
          switch (subfield.getId()) {
            case "008music18": tag008music18 = controlValue; break;
            case "008music20": tag008music20 = controlValue; break;
            case "008music21": tag008music21 = controlValue; break;
            case "008music22": tag008music22 = controlValue; break;
            case "008music23": tag008music23 = controlValue; break;
            case "008music24": tag008music24 = controlValue; break;
            case "008music30": tag008music30 = controlValue; break;
            case "008music33": tag008music33 = controlValue; break;
            default:
              logger.log(Level.SEVERE, "Unhandled 008 subfield: " + subfield.getId());
              break;
          }
          break;
        case CONTINUING_RESOURCES:
          switch (subfield.getId()) {
            case "008continuing18": tag008continuing18 = controlValue; break;
            case "008continuing19": tag008continuing19 = controlValue; break;
            case "008continuing21": tag008continuing21 = controlValue; break;
            case "008continuing22": tag008continuing22 = controlValue; break;
            case "008continuing23": tag008continuing23 = controlValue; break;
            case "008continuing24": tag008continuing24 = controlValue; break;
            case "008continuing25": tag008continuing25 = controlValue; break;
            case "008continuing28": tag008continuing28 = controlValue; break;
            case "008continuing29": tag008continuing29 = controlValue; break;
            case "008continuing33": tag008continuing33 = controlValue; break;
            case "008continuing34": tag008continuing34 = controlValue; break;
            default:
              logger.log(Level.SEVERE, "Unhandled 008 subfield: " + subfield.getId());
              break;
          }
          break;
        case VISUAL_MATERIALS:
          switch (subfield.getId()) {
            case "008visual18": tag008visual18 = controlValue; break;
            case "008visual22": tag008visual22 = controlValue; break;
            case "008visual28": tag008visual28 = controlValue; break;
            case "008visual29": tag008visual29 = controlValue; break;
            case "008visual33": tag008visual33 = controlValue; break;
            case "008visual34": tag008visual34 = controlValue; break;
            default:
              logger.log(Level.SEVERE, "Unhandled 008 subfield: " + subfield.getId());
              break;
          }
          break;
        case MIXED_MATERIALS:
          if ("008mixed23".equals(subfield.getId()))
            tag008mixed23 = controlValue;
          else
            logger.log(Level.SEVERE, "Unhandled 008 subfield: " + subfield.getId());
          break;
      }

      valuesMap.put(subfield, value);
      byPosition.put(subfield.getPositionStart(), subfield);
    }
  }

  @Override
  public ControlfieldPositionDefinition getSubfieldByPosition(Integer charStart) {
    return null;
  }

  public String resolve(ControlfieldPositionDefinition key) {
    String value = valuesMap.get(key);
    return key.resolve(value);
  }

  public String getValueByPosition(int position) {
    return valuesMap.get(getSubfieldByPosition(position));
  }

  public ControlfieldPositionDefinition getSubfieldByPosition(int position) {
    return byPosition.get(position);
  }

  public Set<Integer> getSubfieldPositions() {
    return byPosition.keySet();
  }

  public Map<ControlfieldPositionDefinition, String> getValueMap() {
    return valuesMap;
  }

  public MarcLeader.Type getRecordType() {
    return recordType;
  }

  public ControlValue getTag008all00() {
    return tag008all00;
  }

  public ControlValue getTag008all06() {
    return tag008all06;
  }

  public ControlValue getTag008all07() {
    return tag008all07;
  }

  public ControlValue getTag008all11() {
    return tag008all11;
  }

  public ControlValue getTag008all15() {
    return tag008all15;
  }

  public ControlValue getTag008all35() {
    return tag008all35;
  }

  public ControlValue getTag008all38() {
    return tag008all38;
  }

  public ControlValue getTag008all39() {
    return tag008all39;
  }

  public ControlValue getTag008book18() {
    return tag008book18;
  }

  public ControlValue getTag008book22() {
    return tag008book22;
  }

  public ControlValue getTag008book23() {
    return tag008book23;
  }

  public ControlValue getTag008book24() {
    return tag008book24;
  }

  public ControlValue getTag008book28() {
    return tag008book28;
  }

  public ControlValue getTag008book29() {
    return tag008book29;
  }

  public ControlValue getTag008book30() {
    return tag008book30;
  }

  public ControlValue getTag008book31() {
    return tag008book31;
  }

  public ControlValue getTag008book33() {
    return tag008book33;
  }

  public ControlValue getTag008book34() {
    return tag008book34;
  }

  public ControlValue getTag008computer22() {
    return tag008computer22;
  }

  public ControlValue getTag008computer23() {
    return tag008computer23;
  }

  public ControlValue getTag008computer26() {
    return tag008computer26;
  }

  public ControlValue getTag008computer28() {
    return tag008computer28;
  }

  public ControlValue getTag008map18() {
    return tag008map18;
  }

  public ControlValue getTag008map22() {
    return tag008map22;
  }

  public ControlValue getTag008map25() {
    return tag008map25;
  }

  public ControlValue getTag008map28() {
    return tag008map28;
  }

  public ControlValue getTag008map29() {
    return tag008map29;
  }

  public ControlValue getTag008map31() {
    return tag008map31;
  }

  public ControlValue getTag008map33() {
    return tag008map33;
  }

  public ControlValue getTag008music18() {
    return tag008music18;
  }

  public ControlValue getTag008music20() {
    return tag008music20;
  }

  public ControlValue getTag008music21() {
    return tag008music21;
  }

  public ControlValue getTag008music22() {
    return tag008music22;
  }

  public ControlValue getTag008music23() {
    return tag008music23;
  }

  public ControlValue getTag008music24() {
    return tag008music24;
  }

  public ControlValue getTag008music30() {
    return tag008music30;
  }

  public ControlValue getTag008music33() {
    return tag008music33;
  }

  public ControlValue getTag008continuing18() {
    return tag008continuing18;
  }

  public ControlValue getTag008continuing19() {
    return tag008continuing19;
  }

  public ControlValue getTag008continuing21() {
    return tag008continuing21;
  }

  public ControlValue getTag008continuing22() {
    return tag008continuing22;
  }

  public ControlValue getTag008continuing23() {
    return tag008continuing23;
  }

  public ControlValue getTag008continuing24() {
    return tag008continuing24;
  }

  public ControlValue getTag008continuing25() {
    return tag008continuing25;
  }

  public ControlValue getTag008continuing28() {
    return tag008continuing28;
  }

  public ControlValue getTag008continuing29() {
    return tag008continuing29;
  }

  public ControlValue getTag008continuing33() {
    return tag008continuing33;
  }

  public ControlValue getTag008continuing34() {
    return tag008continuing34;
  }

  public ControlValue getTag008visual18() {
    return tag008visual18;
  }

  public ControlValue getTag008visual22() {
    return tag008visual22;
  }

  public ControlValue getTag008visual28() {
    return tag008visual28;
  }

  public ControlValue getTag008visual29() {
    return tag008visual29;
  }

  public ControlValue getTag008visual33() {
    return tag008visual33;
  }

  public ControlValue getTag008visual34() {
    return tag008visual34;
  }

  public ControlValue getTag008mixed23() {
    return tag008mixed23;
  }

}
