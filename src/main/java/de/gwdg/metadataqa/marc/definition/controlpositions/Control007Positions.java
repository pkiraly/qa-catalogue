package de.gwdg.metadataqa.marc.definition.controlpositions;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag007.*;
import de.gwdg.metadataqa.marc.definition.controltype.Control007Category;

import java.util.*;

public class Control007Positions extends ControlfieldPositionList {

  private static Control007Positions uniqueInstance;

  private Control007Positions() {
    initialize();
    index();
  }

  public static Control007Positions getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Control007Positions();
    return uniqueInstance;
  }

  private void initialize() {

    positions.put(
      Control007Category.COMMON.getValue(),
      Arrays.asList(
        Tag007common00.getInstance()
      )
    );

    positions.put(
      Control007Category.MAP.getValue(),
      Arrays.asList(
        // Tag007map00.getInstance(),
        Tag007map01.getInstance(),
        // new ControlSubField("Undefined", 2, 3),
        Tag007map03.getInstance(),
        Tag007map04.getInstance(),
        Tag007map05.getInstance(),
        Tag007map06.getInstance(),
        Tag007map07.getInstance()
      )
    );

    positions.put(
      Control007Category.ELECTRONIC_RESOURCE.getValue(),
      Arrays.asList(
        // Tag007electro00.getInstance(),
        Tag007electro01.getInstance(),
        // new ControlSubField("Undefined", 2, 3),
        Tag007electro03.getInstance(),
        Tag007electro04.getInstance(),
        Tag007electro05.getInstance(),
        Tag007electro06.getInstance(),
        Tag007electro09.getInstance(),
        Tag007electro10.getInstance(),
        Tag007electro11.getInstance(),
        Tag007electro12.getInstance(),
        Tag007electro13.getInstance()
      )
    );

    positions.put(
      Control007Category.GLOBE.getValue(),
      Arrays.asList(
        // Tag007globe00.getInstance(),
        Tag007globe01.getInstance(),
        // new ControlSubField("Undefined", 2, 3),
        Tag007globe03.getInstance(),
        Tag007globe04.getInstance(),
        Tag007globe05.getInstance()
      )
    );

    positions.put(
      Control007Category.TACTILE_MATERIAL.getValue(),
      Arrays.asList(
        // Tag007tactile00.getInstance(),
        Tag007tactile01.getInstance(),
        // new ControlSubField("Undefined", 2, 3),
        Tag007tactile03.getInstance(),
        Tag007tactile05.getInstance(),
        Tag007tactile06.getInstance(),
        Tag007tactile09.getInstance()
      )
    );

    positions.put(
      Control007Category.PROJECTED_GRAPHIC.getValue(),
      Arrays.asList(
        // Tag007projected00.getInstance(),
        Tag007projected01.getInstance(),
        // new ControlSubField("Undefined", 2, 3),
        Tag007projected03.getInstance(),
        Tag007projected04.getInstance(),
        Tag007projected05.getInstance(),
        Tag007projected06.getInstance(),
        Tag007projected07.getInstance(),
        Tag007projected08.getInstance()
      )
    );

    positions.put(
      Control007Category.MICROFORM.getValue(),
      Arrays.asList(
        // Tag007microform00.getInstance(),
        Tag007microform01.getInstance(),
        // new ControlSubField("Undefined", 2, 3),
        Tag007microform03.getInstance(),
        Tag007microform04.getInstance(),
        Tag007microform05.getInstance(),
        Tag007microform06.getInstance(),
        Tag007microform09.getInstance(),
        Tag007microform10.getInstance(),
        Tag007microform11.getInstance(),
        Tag007microform12.getInstance()
      )
    );

    positions.put(
      Control007Category.NONPROJECTED_GRAPHIC.getValue(),
      Arrays.asList(
        // Tag007nonprojected00.getInstance(),
        Tag007nonprojected01.getInstance(),
        // new ControlSubField("Undefined", 2, 3),
        Tag007nonprojected03.getInstance(),
        Tag007nonprojected04.getInstance(),
        Tag007nonprojected05.getInstance()
      )
    );

    positions.put(
      Control007Category.MOTION_PICTURE.getValue(),
      Arrays.asList(
        // Tag007motionPicture00.getInstance(),
        Tag007motionPicture01.getInstance(),
        // new ControlSubField("Undefined", 2, 3),
        Tag007motionPicture03.getInstance(),
        Tag007motionPicture04.getInstance(),
        Tag007motionPicture05.getInstance(),
        Tag007motionPicture06.getInstance(),
        Tag007motionPicture07.getInstance(),
        Tag007motionPicture08.getInstance(),
        Tag007motionPicture09.getInstance(),
        Tag007motionPicture10.getInstance(),
        Tag007motionPicture11.getInstance(),
        Tag007motionPicture12.getInstance(),
        Tag007motionPicture13.getInstance(),
        Tag007motionPicture14.getInstance(),
        Tag007motionPicture15.getInstance(),
        Tag007motionPicture16.getInstance(),
        Tag007motionPicture17.getInstance()
      )
    );

    positions.put(
      Control007Category.KIT.getValue(),
      Arrays.asList(
        // Tag007kit00.getInstance(),
        Tag007kit01.getInstance()
      )
    );

    positions.put(
      Control007Category.NOTATED_MUSIC.getValue(),
      Arrays.asList(
        // Tag007music00.getInstance(),
        Tag007music01.getInstance()
      )
    );

    positions.put(
      Control007Category.REMOTE_SENSING_IMAGE.getValue(),
      Arrays.asList(
        // Tag007remoteSensing00.getInstance(),
        Tag007remoteSensing01.getInstance(),
        // new ControlSubField("Undefined", 2, 3),
        Tag007remoteSensing03.getInstance(),
        Tag007remoteSensing04.getInstance(),
        Tag007remoteSensing05.getInstance(),
        Tag007remoteSensing06.getInstance(),
        Tag007remoteSensing07.getInstance(),
        Tag007remoteSensing08.getInstance(),
        Tag007remoteSensing09.getInstance()
      )
    );

    positions.put(
      Control007Category.SOUND_RECORDING.getValue(),
      Arrays.asList(
        // Tag007soundRecording00.getInstance(),
        Tag007soundRecording01.getInstance(),
        // new ControlSubField("Undefined", 2, 3),
        Tag007soundRecording03.getInstance(),
        Tag007soundRecording04.getInstance(),
        Tag007soundRecording05.getInstance(),
        Tag007soundRecording06.getInstance(),
        Tag007soundRecording07.getInstance(),
        Tag007soundRecording08.getInstance(),
        Tag007soundRecording09.getInstance(),
        Tag007soundRecording10.getInstance(),
        Tag007soundRecording11.getInstance(),
        Tag007soundRecording12.getInstance(),
        Tag007soundRecording13.getInstance()
      )
    );

    positions.put(
      Control007Category.TEXT.getValue(),
      Arrays.asList(
        // Tag007text00.getInstance(),
        Tag007text01.getInstance()
      )
    );

    positions.put(
      Control007Category.VIDEO_RECORDING.getValue(),
      Arrays.asList(
        // Tag007video00.getInstance(),
        Tag007video01.getInstance(),
        // new ControlSubField("Undefined", 2, 3),
        Tag007video03.getInstance(),
        Tag007video04.getInstance(),
        Tag007video05.getInstance(),
        Tag007video06.getInstance(),
        Tag007video07.getInstance(),
        Tag007video08.getInstance()
      )
    );

    positions.put(
      Control007Category.UNSPECIFIED.getValue(),
      Arrays.asList(
        // Tag007unspecified00.getInstance(),
        Tag007unspecified01.getInstance()
      )
    );
  }

  public List<ControlfieldPositionDefinition> get(Control007Category category) {
    return positions.get(category.getValue());
  }
}
