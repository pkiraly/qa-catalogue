package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import org.sparkproject.jetty.server.RequestLog.Collection;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Electronic Portfolio Information (POR) from ALMA Publishing
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 */
public class TagPOR extends DataFieldDefinition {

  private static TagPOR uniqueInstance;

  private TagPOR() {
    initialize();
    postCreation();
  }

  public static TagPOR getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagPOR();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "POR";
    label = "Electronic Portfolio Information (POR)";
    mqTag = "ElectronicPortfolioInformation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality("a", "Portfolio PID", "R",
      "b", "Activation Status", "R",
      "c", "URL Type subfield", "R",
      "d", "Access URL subfield", "R",
      "e", "Static URL","R",
      "f", "Electronic Material Type subfield", "R",
      "g", "Library subfield", "R",
      "h", "Proxy Selected subfield", "R",
      "i", "Proxy Enabled subfield", "R",
      "j", "Interface Name subfield", "R",
      "k", "Authentication Note subfield", "R",
      "l", "Public Note subfield", "R",
      "m", "Portfolio/Service Internal Description subfield", "R",
      "n", "Coverage Statement subfield", "R",
      "o", "CZ Collection Identifier subfield", "R",
      "p", "Collection ID subfield", "R",
      "q", "Collection Name subfield", "R",
      "B", "Collection Internal Description subfield", "R",
      "r", "License Code subfield", "R",
      "s", "License Name subfield", "R",
      "t", "PO Line subfield", "R",
      "u", "Additional PO Line subfield", "R",
      "v", "Created by subfield", "R",
      "w", "Create date subfield", "R",
      "x", "Updated by subfield", "R",
      "y", "Update date subfield", "R",
      "z", "Activation date subfield", "R",
      "D", "Direct Link subfield", "R",
      "M", "Member code subfield", "R",
      "A", "Available for Institution subfield", "R",
      "S", "Service ID subfield", "R"

  );
  }
}
