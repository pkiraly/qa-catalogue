package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Holding Institution
 * http://www.loc.gov/marc/bibliographic/bd850.html
 */
public class Tag850 extends DataFieldDefinition {

  private static Tag850 uniqueInstance;

  private Tag850() {
    initialize();
    postCreation();
  }

  public static Tag850 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag850();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "850";
    label = "Holding Institution";
    bibframeTag = "HeldBy";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd850.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Holding institution", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a").setCodeList(OrganizationCodes.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));

    setHistoricalSubfields(
      "b", "Holdings (MU, VM, SE) [OBSOLETE, 1990]",
      "d", "Inclusive dates (MU, VM, SE) [OBSOLETE, 1990]",
      "e", "Retention statement (CF, MU, VM, SE) [OBSOLETE, 1990]"
    );
  }
}
