package de.gwdg.metadataqa.marc.definition.controlpositions;

import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all00;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all06;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all07;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all11;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all15;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all35;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all38;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all39;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book18;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book22;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book23;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book24;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book28;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book29;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book30;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book31;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book33;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book34;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008computer22;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008computer23;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008computer26;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008computer28;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008continuing18;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008continuing19;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008continuing21;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008continuing22;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008continuing23;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008continuing24;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008continuing25;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008continuing28;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008continuing29;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008continuing33;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008continuing34;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008map18;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008map22;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008map25;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008map28;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008map29;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008map31;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008map33;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008mixed23;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008music18;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008music20;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008music21;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008music22;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008music23;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008music24;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008music30;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008music33;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008visual18;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008visual22;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008visual28;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008visual29;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008visual33;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008visual34;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;

import java.util.Arrays;
import java.util.List;


public class Control008Positions extends ControlfieldPositionList {

  private static Control008Positions uniqueInstance;

  private Control008Positions() {
    initialize();
    index();
  }

  public static Control008Positions getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Control008Positions();
    return uniqueInstance;
  }

  private void initialize() {

    positions.put(
      Control008Type.ALL_MATERIALS.getValue(),
      Arrays.asList(
        Tag008all00.getInstance(),
        Tag008all06.getInstance(),
        Tag008all07.getInstance(),
        Tag008all11.getInstance(),
        Tag008all15.getInstance(),
        Tag008all35.getInstance(),
        Tag008all38.getInstance(),
        Tag008all39.getInstance()
      )
    );
    positions.put(
      Control008Type.BOOKS.getValue(),
      Arrays.asList(
        Tag008book18.getInstance(),
        Tag008book22.getInstance(),
        Tag008book23.getInstance(),
        Tag008book24.getInstance(),
        Tag008book28.getInstance(),
        Tag008book29.getInstance(),
        Tag008book30.getInstance(),
        Tag008book31.getInstance(),
        // new ControlSubfield("undefined", 32, 33),
        Tag008book33.getInstance(),
        Tag008book34.getInstance()
      )
    );
    positions.put(
      Control008Type.COMPUTER_FILES.getValue(),
      Arrays.asList(
        // new ControlSubfield("undefined", 18, 21),
        Tag008computer22.getInstance(),
        Tag008computer23.getInstance(),
        // new ControlSubfield("undefined", 24, 25),
        Tag008computer26.getInstance(),
        // new ControlSubfield("undefined", 27, 28),
        Tag008computer28.getInstance()
        // new ControlSubfield("undefined", 29, 35)
      )
    );
    positions.put(Control008Type.MAPS.getValue(),
      Arrays.asList(
        Tag008map18.getInstance(),
        Tag008map22.getInstance(),
        // new ControlSubfield("undefined", 24, 25),
        Tag008map25.getInstance(),
        // new ControlSubfield("undefined", 26, 28),
        Tag008map28.getInstance(),
        Tag008map29.getInstance(),
        // new ControlSubfield("undefined", 30, 31),
        Tag008map31.getInstance(),
        // new ControlSubfield("undefined", 32, 33)
        Tag008map33.getInstance()
      )
    );
    positions.put(
      Control008Type.MUSIC.getValue(),
      Arrays.asList(
        Tag008music18.getInstance(),
        Tag008music20.getInstance(),
        Tag008music21.getInstance(),
        Tag008music22.getInstance(),
        Tag008music23.getInstance(),
        Tag008music24.getInstance(),
        Tag008music30.getInstance(),
        // new ControlSubfield("Undefined", 32, 33),
        Tag008music33.getInstance()
        // new ControlSubfield("Undefined", 34, 35)
      )
    );
    positions.put(
      Control008Type.CONTINUING_RESOURCES.getValue(),
      Arrays.asList(
        Tag008continuing18.getInstance(),
        Tag008continuing19.getInstance(),
        // new ControlSubfield("Undefined", 20, 21),
        Tag008continuing21.getInstance(),
        Tag008continuing22.getInstance(),
        Tag008continuing23.getInstance(),
        Tag008continuing24.getInstance(),
        Tag008continuing25.getInstance(),
        Tag008continuing28.getInstance(),
        Tag008continuing29.getInstance(),
        // new ControlSubfield("Undefined", 30, 33),
        Tag008continuing33.getInstance(),
        Tag008continuing34.getInstance()
      )
    );
    positions.put(
      Control008Type.VISUAL_MATERIALS.getValue(),
      Arrays.asList(
        Tag008visual18.getInstance(),
        // new ControlSubfield("Undefined", 21, 22),
        Tag008visual22.getInstance(),
        // new ControlSubfield("Undefined", 23, 28),
        Tag008visual28.getInstance(),
        Tag008visual29.getInstance(),
        // new ControlSubfield("Undefined", 30, 33),
        Tag008visual33.getInstance(),
        Tag008visual34.getInstance()
      ));
    positions.put(
      Control008Type.MIXED_MATERIALS.getValue(),
      Arrays.asList(
        // new ControlSubfield("Undefined", 18, 23),
        Tag008mixed23.getInstance()
        // new ControlSubfield("Undefined", 24, 35),
      )
    );
  }

  public List<ControlfieldPositionDefinition> get(Control008Type category) {
    return positions.get(category.getValue());
  }
}
