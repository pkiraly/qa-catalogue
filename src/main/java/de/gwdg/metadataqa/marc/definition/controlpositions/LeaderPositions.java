package de.gwdg.metadataqa.marc.definition.controlpositions;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.*;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;

import java.util.*;

public class LeaderPositions extends ControlfieldPositionList {

  private static List<ControlfieldPositionDefinition> positionList = new ArrayList<>();

  private static final Map<String, ControlfieldPositionDefinition> positionLabelMap = new HashMap<>();
  // private static final Map<String, ControlfieldPositionDefinition> positionIdMap = new HashMap<>();

  private static LeaderPositions uniqueInstance;

  private LeaderPositions() {
    initialize();
    index();
  }

  public static LeaderPositions getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new LeaderPositions();
    return uniqueInstance;
  }

  private void initialize() {

    // subfieldList.put(Control008Type.ALL_MATERIALS, Arrays.asList());

    positionList = Arrays.asList(
      Leader00.getInstance(),
      Leader05.getInstance(),
      Leader06.getInstance(),
      Leader07.getInstance(),
      Leader08.getInstance(),
      Leader09.getInstance(),
      Leader10.getInstance(),
      Leader11.getInstance(),
      Leader12.getInstance(),
      Leader17.getInstance(),
      Leader18.getInstance(),
      Leader19.getInstance(),
      Leader20.getInstance(),
      Leader21.getInstance(),
      Leader22.getInstance(),
      Leader23.getInstance()
      // new ControlSubField("undefined", 23, 24)
    );
    addAllPositions(positionList);
    positions.put(
      Control008Type.ALL_MATERIALS.getValue(),
      positionList
    );
  }

  private static void addAllPositions(List<ControlfieldPositionDefinition> positions) {
    for (ControlfieldPositionDefinition position : positions)
      positionLabelMap.put(position.getLabel(), position);
  }

  public static List<ControlfieldPositionDefinition> getPositionList() {
    return positionList;
  }

  public static ControlfieldPositionDefinition getByLabel(String key) {
    return positionLabelMap.get(key);
  }

  // public static ControlfieldPositionDefinition getById(String key) {
    //return positionIdMap.get(key);
  //}
}
