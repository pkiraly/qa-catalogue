package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Type of date/Publication status
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all06 extends ControlSubfieldDefinition {
  private static Tag008all06 uniqueInstance;

  private Tag008all06() {
    initialize();
    extractValidCodes();
  }

  public static Tag008all06 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008all06();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Type of date/Publication status";
    id = "tag008all06";
    mqTag = "typeOfDateOrPublicationStatus";
    positionStart = 6;
    positionEnd = 7;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";
    codes = Utils.generateCodes(
      "b", "No dates given; B.C. date involved",
      "c", "Continuing resource currently published",
      "d", "Continuing resource ceased publication",
      "e", "Detailed date",
      "i", "Inclusive dates of collection",
      "k", "Range of years of bulk of collection",
      "m", "Multiple dates",
      "n", "Dates unknown",
      "p", "Date of distribution/release/issue and production/recording session when different",
      "q", "Questionable date",
      "r", "Reprint/reissue date and original date",
      "s", "Single known date/probable date",
      "t", "Publication date and copyright date",
      "u", "Continuing resource status unknown",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(ManagementProcess);
  }
}