package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control006Positions;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control006Definition;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control006 extends MarcPositionalControlField implements Serializable {

  private static final Logger logger = Logger.getLogger(Control006.class.getCanonicalName());

  private ControlValue tag006all00;

  private ControlValue tag006book01;
  private ControlValue tag006book05;
  private ControlValue tag006book06;
  private ControlValue tag006book07;
  private ControlValue tag006book11;
  private ControlValue tag006book12;
  private ControlValue tag006book13;
  private ControlValue tag006book14;
  private ControlValue tag006book16;
  private ControlValue tag006book17;

  private ControlValue tag006computer05;
  private ControlValue tag006computer06;
  private ControlValue tag006computer09;
  private ControlValue tag006computer11;

  private ControlValue tag006map01;
  private ControlValue tag006map05;
  private ControlValue tag006map08;
  private ControlValue tag006map11;
  private ControlValue tag006map12;
  private ControlValue tag006map14;
  private ControlValue tag006map16;

  private ControlValue tag006music01;
  private ControlValue tag006music03;
  private ControlValue tag006music04;
  private ControlValue tag006music05;
  private ControlValue tag006music06;
  private ControlValue tag006music07;
  private ControlValue tag006music13;
  private ControlValue tag006music16;

  private ControlValue tag006continuing01;
  private ControlValue tag006continuing02;
  private ControlValue tag006continuing04;
  private ControlValue tag006continuing05;
  private ControlValue tag006continuing06;
  private ControlValue tag006continuing07;
  private ControlValue tag006continuing08;
  private ControlValue tag006continuing11;
  private ControlValue tag006continuing12;
  private ControlValue tag006continuing16;
  private ControlValue tag006continuing17;

  private ControlValue tag006visual01;
  private ControlValue tag006visual05;
  private ControlValue tag006visual11;
  private ControlValue tag006visual12;
  private ControlValue tag006visual16;
  private ControlValue tag006visual17;

  private ControlValue tag006mixed06;

  private Map<Integer, ControlfieldPositionDefinition> byPosition = new LinkedHashMap<>();

  public Control006(String content, Leader.Type recordType) {
    super(Control006Definition.getInstance(), content, recordType);
    if (content != null)
      processContent();
  }

  protected void processContent() {

    for (ControlfieldPositionDefinition subfield : Control006Positions.getInstance().get(Control008Type.ALL_MATERIALS)) {
      int end = Math.min(content.length(), subfield.getPositionEnd());
      if (end < 0)
        logger.severe(String.format("%d %d", content.length(), subfield.getPositionEnd()));

      try {
        String value = content.substring(subfield.getPositionStart(), end);
        ControlValue controlValue = new ControlValue(subfield, value);
        valuesList.add(controlValue);

        if (subfield.getId().equals("006all00"))
          tag006all00 = controlValue;
        else
          logger.severe(String.format("Unhandled 006 subfield: %s", subfield.getId()));

        valuesMap.put(subfield, value);
        byPosition.put(subfield.getPositionStart(), subfield);
      } catch (StringIndexOutOfBoundsException e) {
        logger.severe(content.length() + " " + subfield.getPositionStart() + "-" + subfield.getPositionEnd());
      }
    }

    Control008Type actual = Control008Type.byCode(recordType.getValue().toString());
    for (ControlfieldPositionDefinition subfield : Control006Positions.getInstance().get(actual)) {
      int end = Math.min(content.length(), subfield.getPositionEnd());

      String value = null;
      if (subfield.getPositionStart() <= content.length()
        && subfield.getPositionStart() < end) {
        try {
          value = content.substring(subfield.getPositionStart(), end);
        } catch (StringIndexOutOfBoundsException e) {
          logger.severe(String.format("Problem with processing 006 ('%s'). " +
              "The content length is only %d while reading position @%d-%d" +
              " (for %s '%s')",
            content,
            content.length(), subfield.getPositionStart(), subfield.getPositionEnd(),
            subfield.getId(), subfield.getLabel()));
        }
      } else {
        break;
      }

      ControlValue controlValue = new ControlValue(subfield, value);
      valuesList.add(controlValue);

      switch (actual) {
        case BOOKS: extractBooks(actual, subfield, controlValue); break;
        case COMPUTER_FILES: extractComputerFiles(actual, subfield, controlValue); break;
        case MAPS: extractMap(actual, subfield, controlValue); break;
        case MUSIC: extractMusic(actual, subfield, controlValue); break;
        case CONTINUING_RESOURCES:
          extractContinuingResources(actual, subfield, controlValue); break;
        case VISUAL_MATERIALS: extractVisualMaterials(actual, subfield, controlValue); break;
        case MIXED_MATERIALS: extractMixedMaterials(actual, subfield, controlValue); break;
        default:
            logger.severe(String.format("Unhandled 006 type: %s", actual.getValue()));
            break;
      }

      valuesMap.put(subfield, value);
      byPosition.put(subfield.getPositionStart(), subfield);
    }
  }

  private void extractBooks(Control008Type actual, ControlfieldPositionDefinition subfield, ControlValue controlValue) {
    switch (subfield.getId()) {
      case "006book01": tag006book01 = controlValue; break;
      case "006book05": tag006book05 = controlValue; break;
      case "006book06": tag006book06 = controlValue; break;
      case "006book07": tag006book07 = controlValue; break;
      case "006book11": tag006book11 = controlValue; break;
      case "006book12": tag006book12 = controlValue; break;
      case "006book13": tag006book13 = controlValue; break;
      case "006book14": tag006book14 = controlValue; break;
      case "006book16": tag006book16 = controlValue; break;
      case "006book17": tag006book17 = controlValue; break;
      default:
        logger.severe(String.format("Unhandled 006 subfield (for %s): %s", actual.getValue(), subfield.getId()));
        break;
    }
  }

  private void extractComputerFiles(Control008Type actual, ControlfieldPositionDefinition subfield, ControlValue controlValue) {
    switch (subfield.getId()) {
      case "006computer05": tag006computer05 = controlValue; break;
      case "006computer06": tag006computer06 = controlValue; break;
      case "006computer09": tag006computer09 = controlValue; break;
      case "006computer11": tag006computer11 = controlValue; break;
      default:
        logger.severe(String.format("Unhandled 006 subfield (for %s): %s", actual.getValue(), subfield.getId()));
        break;
    }
  }

  private void extractMap(Control008Type actual, ControlfieldPositionDefinition subfield, ControlValue controlValue) {
    switch (subfield.getId()) {
      case "006map01": tag006map01 = controlValue; break;
      case "006map05": tag006map05 = controlValue; break;
      case "006map08": tag006map08 = controlValue; break;
      case "006map11": tag006map11 = controlValue; break;
      case "006map12": tag006map12 = controlValue; break;
      case "006map14": tag006map14 = controlValue; break;
      case "006map16": tag006map16 = controlValue; break;
      default:
        logger.severe(String.format("Unhandled 006 subfield (for %s): %s", actual.getValue(), subfield.getId()));
        break;
    }
  }

  private void extractMusic(Control008Type actual, ControlfieldPositionDefinition subfield, ControlValue controlValue) {
    switch (subfield.getId()) {
      case "006music01": tag006music01 = controlValue; break;
      case "006music03": tag006music03 = controlValue; break;
      case "006music04": tag006music04 = controlValue; break;
      case "006music05": tag006music05 = controlValue; break;
      case "006music06": tag006music06 = controlValue; break;
      case "006music07": tag006music07 = controlValue; break;
      case "006music13": tag006music13 = controlValue; break;
      case "006music16": tag006music16 = controlValue; break;
      default:
        logger.severe(String.format("Unhandled 006 subfield (for %s): %s", actual.getValue(), subfield.getId()));
        break;
    }
  }

  private void extractVisualMaterials(Control008Type actual, ControlfieldPositionDefinition subfield, ControlValue controlValue) {
    switch (subfield.getId()) {
      case "006visual01": tag006visual01 = controlValue; break;
      case "006visual05": tag006visual05 = controlValue; break;
      case "006visual11": tag006visual11 = controlValue; break;
      case "006visual12": tag006visual12 = controlValue; break;
      case "006visual16": tag006visual16 = controlValue; break;
      case "006visual17": tag006visual17 = controlValue; break;
      default:
        logger.severe(String.format("Unhandled 006 subfield (for %s): %s", actual.getValue(), subfield.getId()));
        break;
    }
  }

  private void extractContinuingResources(Control008Type actual,
                                          ControlfieldPositionDefinition subfield,
                                          ControlValue controlValue) {
    switch (subfield.getId()) {
      case "006continuing01": tag006continuing01 = controlValue; break;
      case "006continuing02": tag006continuing02 = controlValue; break;
      case "006continuing04": tag006continuing04 = controlValue; break;
      case "006continuing05": tag006continuing05 = controlValue; break;
      case "006continuing06": tag006continuing06 = controlValue; break;
      case "006continuing07": tag006continuing07 = controlValue; break;
      case "006continuing08": tag006continuing08 = controlValue; break;
      case "006continuing11": tag006continuing11 = controlValue; break;
      case "006continuing12": tag006continuing12 = controlValue; break;
      case "006continuing16": tag006continuing16 = controlValue; break;
      case "006continuing17": tag006continuing17 = controlValue; break;
      default:
        logger.severe(String.format("Unhandled 006 subfield (for %s): %s",
          actual.getValue(), subfield.getId()));
        break;
    }
  }

  private void extractMixedMaterials(Control008Type actual, ControlfieldPositionDefinition subfield, ControlValue controlValue) {
    switch (subfield.getId()) {
      case "006mixed06": tag006mixed06 = controlValue; break;
      default:
        logger.severe(String.format("Unhandled 006 subfield (for %s): %s", actual.getValue(), subfield.getId()));
        break;
    }
  }

  public String resolve(ControlfieldPositionDefinition key) {
    String value = (String)valuesMap.get(key);
    String text = key.resolve(value);
    return text;
  }

  public Map<ControlfieldPositionDefinition, String> getValueMap() {
    return valuesMap;
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

  public Leader.Type getRecordType() {
    return recordType;
  }

  public ControlValue getTag006all00() {
    return tag006all00;
  }

  public ControlValue getTag006book01() {
    return tag006book01;
  }

  public ControlValue getTag006book05() {
    return tag006book05;
  }

  public ControlValue getTag006book06() {
    return tag006book06;
  }

  public ControlValue getTag006book07() {
    return tag006book07;
  }

  public ControlValue getTag006book11() {
    return tag006book11;
  }

  public ControlValue getTag006book12() {
    return tag006book12;
  }

  public ControlValue getTag006book13() {
    return tag006book13;
  }

  public ControlValue getTag006book14() {
    return tag006book14;
  }

  public ControlValue getTag006book16() {
    return tag006book16;
  }

  public ControlValue getTag006book17() {
    return tag006book17;
  }

  public ControlValue getTag006computer05() {
    return tag006computer05;
  }

  public ControlValue getTag006computer06() {
    return tag006computer06;
  }

  public ControlValue getTag006computer09() {
    return tag006computer09;
  }

  public ControlValue getTag006computer11() {
    return tag006computer11;
  }

  public ControlValue getTag006map01() {
    return tag006map01;
  }

  public ControlValue getTag006map05() {
    return tag006map05;
  }

  public ControlValue getTag006map08() {
    return tag006map08;
  }

  public ControlValue getTag006map11() {
    return tag006map11;
  }

  public ControlValue getTag006map12() {
    return tag006map12;
  }

  public ControlValue getTag006map14() {
    return tag006map14;
  }

  public ControlValue getTag006map16() {
    return tag006map16;
  }

  public ControlValue getTag006music01() {
    return tag006music01;
  }

  public ControlValue getTag006music03() {
    return tag006music03;
  }

  public ControlValue getTag006music04() {
    return tag006music04;
  }

  public ControlValue getTag006music05() {
    return tag006music05;
  }

  public ControlValue getTag006music06() {
    return tag006music06;
  }

  public ControlValue getTag006music07() {
    return tag006music07;
  }

  public ControlValue getTag006music13() {
    return tag006music13;
  }

  public ControlValue getTag006music16() {
    return tag006music16;
  }

  public ControlValue getTag006continuing01() {
    return tag006continuing01;
  }

  public ControlValue getTag006continuing02() {
    return tag006continuing02;
  }

  public ControlValue getTag006continuing04() {
    return tag006continuing04;
  }

  public ControlValue getTag006continuing05() {
    return tag006continuing05;
  }

  public ControlValue getTag006continuing06() {
    return tag006continuing06;
  }

  public ControlValue getTag006continuing07() {
    return tag006continuing07;
  }

  public ControlValue getTag006continuing08() {
    return tag006continuing08;
  }

  public ControlValue getTag006continuing11() {
    return tag006continuing11;
  }

  public ControlValue getTag006continuing12() {
    return tag006continuing12;
  }

  public ControlValue getTag006continuing16() {
    return tag006continuing16;
  }

  public ControlValue getTag006continuing17() {
    return tag006continuing17;
  }

  public ControlValue getTag006visual01() {
    return tag006visual01;
  }

  public ControlValue getTag006visual05() {
    return tag006visual05;
  }

  public ControlValue getTag006visual11() {
    return tag006visual11;
  }

  public ControlValue getTag006visual12() {
    return tag006visual12;
  }

  public ControlValue getTag006visual16() {
    return tag006visual16;
  }

  public ControlValue getTag006visual17() {
    return tag006visual17;
  }

  public ControlValue getTag006mixed06() {
    return tag006mixed06;
  }
}
