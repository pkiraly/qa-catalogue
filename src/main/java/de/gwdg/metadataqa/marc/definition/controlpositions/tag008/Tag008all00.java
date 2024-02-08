package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.definition.general.parser.Control008All00DateParser;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Date entered on file
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all00 extends ControlfieldPositionDefinition {
  private static Tag008all00 uniqueInstance;

  private Tag008all00() {
    initialize();
  }


  public static Tag008all00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008all00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Date entered on file";
    id = "008all00";
    mqTag = "dateEnteredOnFile";
    positionStart = 0;
    positionEnd = 6;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";

    // TODO: pattern: yymmdd
    parser = new Control008All00DateParser();
    functions = Arrays.asList(ManagementProcess);
  }
}