package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Target audience
 * https://www.loc.gov/marc/bibliographic/bd008v.html
 */
public class Tag008visual22 extends ControlfieldPositionDefinition {
  private static Tag008visual22 uniqueInstance;

  private Tag008visual22() {
    initialize();
  }


  public static Tag008visual22 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008visual22();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Target audience";
    id = "008visual22";
    mqTag = "targetAudience";
    positionStart = 22;
    positionEnd = 23;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008v.html";
    codes = Utils.generateCodes(
      " ", "Unknown or not specified",
      "a", "Preschool",
      "b", "Primary",
      "c", "Pre-adolescent",
      "d", "Adolescent",
      "e", "Adult",
      "f", "Specialized",
      "g", "General",
      "j", "Juvenile",
      "|", "No attempt to code"
    );
    historicalCodes = Utils.generateCodes(
      "f", "General [OBSOLETE]",
      "g", "Specialized [OBSOLETE]",
      "h", "Secondary (grades 10-12) [OBSOLETE] [CAN/MARC only]",
      "k", "Preschool and Kindergarten [OBSOLETE] [CAN/MARC only]",
      "m", "Primary (grades 4-6) [OBSOLETE] [CAN/MARC only]",
      "p", "Special education - general [OBSOLETE] [CAN/MARC only]",
      "q", "Physically handicapped [OBSOLETE] [CAN/MARC only]",
      "r", "Mentally retarded [OBSOLETE] [CAN/MARC only]",
      "s", "Simplified works for adults [OBSOLETE] [CAN/MARC only]",
      "t", "Gifted [OBSOLETE] [CAN/MARC only]"
    );
  }
}