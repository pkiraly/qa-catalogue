package de.gwdg.metadataqa.marc.definition;

public enum FRBRFunction {
  Discovery("Resource Discovery", null),
    DiscoverySearch("Search", Discovery),
    DiscoveryIdentify("Identify", Discovery),
    DiscoverySelect("Select", Discovery),
    DiscoveryObtain("Obtain", Discovery),
  Use("Resource Use", null),
    UseRestrict("Restrict", Use),
    UseManage("Manage", Use),
    UseOperate("Operate", Use),
    UseInterpret("Interpret", Use),
    UseIdentify("Identify", Use),
  Management("Data Management", null),
    ManagementProcess("Process", Management),
    ManagementSort("Sort", Management),
    ManagementDisplay("Display", Management),
  ;

  private FRBRFunction parent = null;
  private String label = null;

  private FRBRFunction(String label, FRBRFunction parent) {
    this.parent = parent;
  }
}
