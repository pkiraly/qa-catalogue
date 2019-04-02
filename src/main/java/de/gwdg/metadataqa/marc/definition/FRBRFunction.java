package de.gwdg.metadataqa.marc.definition;

public enum FRBRFunction {
  Discovery("Discovery", null),
    DiscoverySearch("Search", Discovery),
    DiscoveryIdentify("Identify", Discovery),
    DiscoverySelect("Select", Discovery),
    DiscoveryObtain("Obtain", Discovery),
  Usage("Usage", null),
    UsageRestrict("Restrict", Usage),
    UsageManage("Manage", Usage),
    UsageOperate("Operate", Usage),
    UsageInterpret("Interpret", Usage),
  Management("Management", null),
    ManagementIdentify("Identify", Management),
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
