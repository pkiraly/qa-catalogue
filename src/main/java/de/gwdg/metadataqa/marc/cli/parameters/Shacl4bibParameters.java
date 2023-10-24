package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.api.rule.RuleCheckingOutputType;
import org.apache.commons.cli.ParseException;

public class Shacl4bibParameters extends CommonParameters {

  private static final String DEFAULT_SHACL_OUTPUT_FILE = "shacl4bib.csv";
  private static final RuleCheckingOutputType DEFAULT_SHACL_OUTPUT_TYPE = RuleCheckingOutputType.STATUS;
  private boolean isOptionSet = false;
  private String shaclConfigurationFile;
  private String shaclOutputFile = DEFAULT_SHACL_OUTPUT_FILE;
  private RuleCheckingOutputType shaclOutputType = DEFAULT_SHACL_OUTPUT_TYPE;

  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      options.addOption("C", "shaclConfigurationFile", true, "specify the configuration file");
      options.addOption("O", "shaclOutputFile", true, "output file");
      options.addOption("P", "shaclOutputType", true, "output type (STATUS: status only, SCORE: score only, BOTH: status and score");
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

    if (cmd.hasOption("shaclOutputType"))
      shaclOutputType = RuleCheckingOutputType.valueOf(cmd.getOptionValue("shaclOutputType"));
  }

  public String getShaclConfigurationFile() {
    return shaclConfigurationFile;
  }

  public String getShaclOutputFile() {
    return shaclOutputFile;
  }

  public RuleCheckingOutputType getShaclOutputType() {
    return shaclOutputType;
  }

  @Override
  public String formatParameters() {
    String text = super.formatParameters();
    text += String.format("shaclConfigurationFile: %s%n", shaclConfigurationFile);
    text += String.format("shaclOutputFile: %s%n", shaclOutputFile);
    text += String.format("shaclOutputType: %s%n", shaclOutputType);
    return text;
 }
}
