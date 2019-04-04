package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.controlsubfields.tag006.*;
import de.gwdg.metadataqa.marc.definition.controltype.Control007Category;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;
import de.gwdg.metadataqa.marc.definition.controltype.ControlType;
import de.gwdg.metadataqa.marc.definition.tags.control.Control006Definition;

import java.util.*;

public class Control006Subfields extends ControlSubfieldList {

  // protected static final Map<Control008Type, List<ControlSubfieldDefinition>> subfields = new TreeMap<>();

  private static Control006Subfields uniqueInstance;

  private Control006Subfields() {
    initialize();
  }

  public static Control006Subfields getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Control006Subfields();
    return uniqueInstance;
  }

  private void initialize() {

    subfields.put(
      Control008Type.ALL_MATERIALS.getValue(),
      Arrays.asList(
        Tag006all00.getInstance()
      )
    );

    subfields.put(
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

    subfields.put(
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

    subfields.put(
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

    subfields.put(
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

    subfields.put(
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

    subfields.put(
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

    subfields.put(
      Control008Type.MIXED_MATERIALS.getValue(),
      Arrays.asList(
        // new ControlSubfield("Undefined", 1, 6),
        Tag006mixed06.getInstance()
        // new ControlSubfield("Undefined", 7, 18),
      )
    );
  }

  /*
  public static Map<Control008Type, List<ControlSubfieldDefinition>> getSubfieldList() {
    return subfields;
  }
  */

  public List<ControlSubfieldDefinition> get(Control008Type category) {
    return subfields.get(category.getValue());
  }
}
