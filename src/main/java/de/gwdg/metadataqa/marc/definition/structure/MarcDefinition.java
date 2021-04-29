package de.gwdg.metadataqa.marc.definition.structure;

import de.gwdg.metadataqa.marc.definition.controlpositions.LeaderPositions;
import de.gwdg.metadataqa.marc.definition.tags.control.Control001Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control003Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control005Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control006Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control007Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control008Definition;

import java.util.Arrays;
import java.util.List;

public class MarcDefinition {

  private static final List<ControlfieldPositionDefinition> leaderPositions =
    LeaderPositions.getInstance().getPositionList();

  private static final List<DataFieldDefinition> simpleControlFields = Arrays.asList(
    Control001Definition.getInstance(),
    Control003Definition.getInstance(),
    Control005Definition.getInstance()
  );

  private static final List<ControlFieldDefinition> complexControlFields = Arrays.asList(
    Control006Definition.getInstance(),
    Control007Definition.getInstance(),
    Control008Definition.getInstance()
  );

  public static List<ControlfieldPositionDefinition> getLeaderPositions() {
    return leaderPositions;
  }

  public static List<DataFieldDefinition> getSimpleControlFields() {
    return simpleControlFields;
  }

  public static List<ControlFieldDefinition> getComplexControlFields() {
    return complexControlFields;
  }
}
