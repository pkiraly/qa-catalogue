package de.gwdg.metadataqa.marc.definition.controlpositions;

import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader00;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader05;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader06;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader07;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader08;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader09;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader10;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader11;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader12;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader17;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader18;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader19;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader20;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader21;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader22;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader23;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
