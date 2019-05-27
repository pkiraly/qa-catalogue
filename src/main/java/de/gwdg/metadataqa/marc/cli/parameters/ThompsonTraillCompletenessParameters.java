package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;

public class ThompsonTraillCompletenessParameters extends CommonParameters {
  public static final String DEFAULT_FILE_NAME = "tt-completeness.csv";

  private int limit = -1;
  private int offset = -1;
  private String fileName = DEFAULT_FILE_NAME;
  private boolean useStandardOutput = false;

  private boolean isOptionSet = false;

  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      options.addOption("l", "limit", true, "limit the number of records to process");
      options.addOption("o", "offset", true, "the first record to process");
      options.addOption("f", "fileName", true,
        String.format("the report file name (default is %s)", ValidatorParameters.DEFAULT_FILE_NAME));
      isOptionSet = true;
    }
  }

  public ThompsonTraillCompletenessParameters(String[] arguments) throws ParseException {
    super(arguments);

    if (cmd.hasOption("fileName"))
      fileName = cmd.getOptionValue("fileName");

    if (fileName.equals("stdout"))
      useStandardOutput = true;

    if (cmd.hasOption("limit"))
      limit = Integer.parseInt(cmd.getOptionValue("limit"));

    if (cmd.hasOption("offset"))
      offset = Integer.parseInt(cmd.getOptionValue("offset"));

    if (offset > -1 && limit > -1)
      limit += offset;
  }

  public String getFileName() {
    return fileName;
  }

  public int getLimit() {
    return limit;
  }

  public int getOffset() {
    return offset;
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
