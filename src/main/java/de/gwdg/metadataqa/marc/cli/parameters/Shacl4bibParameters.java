package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.*;

public class Shacl4bibParameters extends CommonParameters {

  private boolean isOptionSet = false;
  private boolean Configuration = false;
  private boolean Output = false;
  private String configPath;
  private String outputPath;

  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      options.addOption("C", "Configuration", true, "specify the configuration file");
      options.addOption("O", "Output", true, "output file");
      isOptionSet = true;
    }
  }

  public Shacl4bibParameters() {
    super();
  }

  public Shacl4bibParameters(String[] arguments) throws ParseException {
    super(arguments);

    if(cmd.hasOption("C")){
      Configuration = true;
      configPath = cmd.getOptionValue("C");
    }
    if(cmd.hasOption("O")){
      Output = true;
      outputPath = cmd.getOptionValue("O");
    }
  }

  public String getConfigPath() {
    return configPath;
  }

  public String getOutputPath() {
    return outputPath;
  }
}
