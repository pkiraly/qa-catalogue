package de.gwdg.metadataqa.marc.definition.controlpositions;

import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006all00;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006book01;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006book05;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006book06;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006book07;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006book11;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006book12;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006book13;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006book14;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006book16;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006book17;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006computer05;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006computer06;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006computer09;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006computer11;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing01;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing02;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing04;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing05;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing06;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing07;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing08;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing11;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing12;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing16;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing17;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006map01;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006map05;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006map08;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006map11;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006map12;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006map14;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006map16;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006mixed06;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006music01;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006music03;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006music04;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006music05;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006music06;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006music07;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006music13;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006music16;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006visual01;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006visual05;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006visual11;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006visual12;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006visual16;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006visual17;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;

import java.util.Arrays;
import java.util.List;

public class Control006Positions extends ControlfieldPositionList {

  private static Control006Positions uniqueInstance;

  private Control006Positions() {
    initialize();
    index();
  }

  public static Control006Positions getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Control006Positions();
    return uniqueInstance;
  }

  private void initialize() {

    positions.put(
      Control008Type.ALL_MATERIALS.getValue(),
      Arrays.asList(
        Tag006all00.getInstance()
      )
    );

    positions.put(
      Control008Type.BOOKS.getValue(),
      Arrays.asList(
        Tag006book01.getInstance(),
        Tag006book05.getInstance(),
        Tag006book06.getInstance(),
        Tag006book07.getInstance(),
        Tag006book11.getInstance(),
        Tag006book12.getInstance(),
        Tag006book13.getInstance(),
        Tag006book14.getInstance(),
        // new ControlSubfield("undefined", 15, 16),
        Tag006book16.getInstance(),
        Tag006book17.getInstance()
      )
    );

    positions.put(
      Control008Type.COMPUTER_FILES.getValue(),
      Arrays.asList(
        // new ControlSubfield("undefined", 1, 5),
        Tag006computer05.getInstance(),
        Tag006computer06.getInstance(),
        // new ControlSubfield("undefined", 7, 9),
        Tag006computer09.getInstance(),
        // new ControlSubfield("undefined", 10, 11),
        Tag006computer11.getInstance()
        // new ControlSubfield("undefined", 12, 18)
      )
    );

    positions.put(
      Control008Type.MAPS.getValue(),
      Arrays.asList(
        Tag006map01.getInstance(),
        Tag006map05.getInstance(),
        // new ControlSubfield("undefined", 7, 8),
        Tag006map08.getInstance(),
        // new ControlSubfield("undefined", 9, 11),
        Tag006map11.getInstance(),
        Tag006map12.getInstance(),
        // new ControlSubfield("undefined", 13, 14),
        Tag006map14.getInstance(),
        // new ControlSubfield("undefined", 15, 16)
        Tag006map16.getInstance()
      )
    );

    positions.put(
      Control008Type.MUSIC.getValue(),
      Arrays.asList(
        Tag006music01.getInstance(),
        Tag006music03.getInstance(),
        Tag006music04.getInstance(),
        Tag006music05.getInstance(),
        Tag006music06.getInstance(),
        Tag006music07.getInstance(),
        Tag006music13.getInstance(),
        // new ControlSubfield("Undefined", 15, 16),
        Tag006music16.getInstance()
        // new ControlSubfield("Undefined", 17, 18)
      )
    );

    positions.put(
      Control008Type.CONTINUING_RESOURCES.getValue(),
      Arrays.asList(
        Tag006continuing01.getInstance(),
        Tag006continuing02.getInstance(),
        // new ControlSubfield("Undefined", 3, 4),
        Tag006continuing04.getInstance(),
        Tag006continuing05.getInstance(),
        Tag006continuing06.getInstance(),
        Tag006continuing07.getInstance(),
        Tag006continuing08.getInstance(),
        Tag006continuing11.getInstance(),
        Tag006continuing12.getInstance(),
        // new ControlSubfield("Undefined", 13, 16),
        Tag006continuing16.getInstance(),
        Tag006continuing17.getInstance()
      )
    );

    positions.put(
      Control008Type.VISUAL_MATERIALS.getValue(),
      Arrays.asList(
        Tag006visual01.getInstance(),
        // new ControlSubfield("Undefined", 4, 5),
        Tag006visual05.getInstance(),
        // new ControlSubfield("Undefined", 6, 11),
        Tag006visual11.getInstance(),
        Tag006visual12.getInstance(),
        // new ControlSubfield("Undefined", 13, 16),
        Tag006visual16.getInstance(),
        Tag006visual17.getInstance()
      )
    );

    positions.put(
      Control008Type.MIXED_MATERIALS.getValue(),
      Arrays.asList(
        // new ControlSubfield("Undefined", 1, 6),
        Tag006mixed06.getInstance()
        // new ControlSubfield("Undefined", 7, 18),
      )
    );
  }

  public List<ControlfieldPositionDefinition> get(Control008Type category) {
    return positions.get(category.getValue());
  }
}
