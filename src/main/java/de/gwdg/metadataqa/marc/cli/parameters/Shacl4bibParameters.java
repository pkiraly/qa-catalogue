package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.*;

public class Shacl4bibParameters extends CommonParameters {

  private boolean isOptionSet = false;
  private String shaclConfigurationFile;
  private String shaclOutputFile;

  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      options.addOption("C", "shaclConfigurationFile", true, "specify the configuration file");
      options.addOption("O", "shaclOutputFile", true, "output file");
      isOptionSet = true;
    }
  }

  public Shacl4bibParameters() {
    super();
  }

  public Shacl4bibParameters(String[] arguments) throws ParseException {
    super(arguments);

    if (cmd.hasOption("shaclConfigurationFile"))
      shaclConfigurationFile = cmd.getOptionValue("shaclConfigurationFile");

    if (cmd.hasOption("shaclOutputFile"))
      shaclOutputFile = cmd.getOptionValue("shaclOutputFile");
  }

  public String getShaclConfigurationFile() {
    return shaclConfigurationFile;
  }

  public String getShaclOutputFile() {
    return shaclOutputFile;
  }

  @Override
  public String formatParameters() {
    String text = super.formatParameters();
    text += String.format("shaclConfigurationFile: %s%n", shaclConfigurationFile);
    text += String.format("shaclOutputFile: %s%n", shaclOutputFile);
    return text;
 }
}
