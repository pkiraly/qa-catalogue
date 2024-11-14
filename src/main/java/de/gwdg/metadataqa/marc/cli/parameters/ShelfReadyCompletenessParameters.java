package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;

public class ShelfReadyCompletenessParameters extends CommonParameters {
  public static final String DEFAULT_FILE_NAME = "shelf-ready-completeness.csv";

  private String fileName = DEFAULT_FILE_NAME;
  private boolean useStandardOutput = false;

  private boolean isOptionSet = false;

  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      options.addOption("F", "fileName", true,
        String.format("the report file name (default is %s)", DEFAULT_FILE_NAME));
      isOptionSet = true;
    }
  }

  public ShelfReadyCompletenessParameters() {
    super();
  }

  public ShelfReadyCompletenessParameters(String[] arguments) throws ParseException {
    super(arguments);

    if (cmd.hasOption("fileName"))
      fileName = cmd.getOptionValue("fileName");

    if (fileName.equals("stdout"))
      useStandardOutput = true;
  }

  public String getFileName() {
    return fileName;
  }

  public boolean useStandardOutput() {
    return useStandardOutput;
  }

  @Override
  public String formatParameters() {
    String text = super.formatParameters();
    text += String.format("fileName: %s%n", fileName);
    text += String.format("useStandardOutput: %s%n", useStandardOutput);
    text += String.format("limit: %s%n", limit);
    text += String.format("offset: %s%n", offset);
    return text;
  }
}
