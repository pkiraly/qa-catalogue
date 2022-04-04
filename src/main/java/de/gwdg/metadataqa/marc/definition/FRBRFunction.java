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
  Management("Data Management", null),
    ManagementIdentify("Identify", Management),
    ManagementProcess("Process", Management),
    ManagementSort("Sort", Management),
    ManagementDisplay("Display", Management),
  ;

  private FRBRFunction parent = null;
  private String label = null;

  FRBRFunction(String label, FRBRFunction parent) {
    this.label = label;
    this.parent = parent;
  }

  public FRBRFunction getParent() {
    return parent;
  }

  public String getLabel() {
    return label;
  }

  public String getPath() {
    return String.format("%s/%s", parent.label, label);
  }
}
