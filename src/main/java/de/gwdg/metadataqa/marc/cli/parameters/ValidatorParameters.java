package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.apache.commons.cli.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidatorParameters extends CommonParameters implements Serializable {
  public static final String DEFAULT_DETAILS_FILE_NAME = "issue-details.csv";
  public static final String DEFAULT_SUMMARY_FILE_NAME = "issue-summary.csv";

  private String detailsFileName = DEFAULT_DETAILS_FILE_NAME;
  private String summaryFileName = DEFAULT_SUMMARY_FILE_NAME;
  private boolean doDetails = true;
  private boolean doSummary = false;
  private ValidationErrorFormat format = ValidationErrorFormat.TEXT;
  private boolean useStandardOutput = false;
  private boolean isOptionSet;
  private boolean emptyLargeCollectors = false;
  private boolean collectAllErrors = false;
  private List<ValidationErrorType> ignorableIssueTypes;

  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      options.addOption("g", "summaryFileName", true, "the summary file name (provides a summary of issues, such as the number of instance and number of records having the particular issue)");
      options.addOption("s", "summary", false, "show summary instead of record level display");
      options.addOption("h", "details", false, "show record level display");
      options.addOption("f", "detailsFileName", true,
        String.format("the report file name (default is '%s')", ValidatorParameters.DEFAULT_DETAILS_FILE_NAME));
      options.addOption("r", "format", true, "specify a format");
      options.addOption("w", "emptyLargeCollectors", false, "empty large collectors");
      options.addOption("t", "collectAllErrors", false, "collect all errors (useful only for validating small number of records)");
      options.addOption("i", "ignorableIssueTypes", true, "comma separated list of issue types not to collect");
      isOptionSet = true;
    }
  }

  public ValidatorParameters() {
    super();
  }

  public ValidatorParameters(String[] arguments) throws ParseException {
    super(arguments);

    if (cmd.hasOption("detailsFileName"))
      setDetailsFileName(cmd.getOptionValue("detailsFileName"));

    if (cmd.hasOption("summaryFileName"))
      setSummaryFileName(cmd.getOptionValue("summaryFileName"));

    if (detailsFileName.equals("stdout"))
      setUseStandardOutput(true);

    if (cmd.hasOption("format"))
      setFormat(cmd.getOptionValue("format"));

    if (cmd.hasOption("summary")) {
      setDoSummary(true);
      if (!cmd.hasOption("details"))
        setDoDetails(false);
    }

    if (cmd.hasOption("emptyLargeCollectors"))
      setEmptyLargeCollectors(true);

    setCollectAllErrors(cmd.hasOption("collectAllErrors"));

    if (cmd.hasOption("ignorableIssueTypes")) {
      setIgnorableIssueTypes(cmd.getOptionValue("ignorableIssueTypes"));
    }
  }

  private void setIgnorableIssueTypes(String inputParameter) {
    ignorableIssueTypes = new ArrayList<>();
    String[] issueTypes = inputParameter.split(",");
    for (String code : issueTypes) {
      ValidationErrorType type = ValidationErrorType.byCode(code);
      if (type != null)
        ignorableIssueTypes.add(type);
    }
  }

  public String getDetailsFileName() {
    return detailsFileName;
  }

  public void setDetailsFileName(String detailsFileName) {
    this.detailsFileName = detailsFileName;
  }

  public boolean doSummary() {
    return doSummary;
  }

  public void setDoSummary(boolean doSummary) {
    this.doSummary = doSummary;
  }

  public boolean doDetails() {
    return doDetails;
  }

  public void setDoDetails(boolean doDetails) {
    this.doDetails = doDetails;
  }

  public boolean useStandardOutput() {
    return useStandardOutput;
  }

  public void setUseStandardOutput(boolean useStandardOutput) {
    this.useStandardOutput = useStandardOutput;
  }

  public ValidationErrorFormat getFormat() {
    return format;
  }

  public void setFormat(String format) throws ParseException {
    this.format = ValidationErrorFormat.byFormat(format);
    if (this.format == null)
      throw new ParseException(String.format("Unrecognized ValidationErrorFormat parameter value: '%s'", format));
  }

  public String getSummaryFileName() {
    return summaryFileName;
  }

  public void setSummaryFileName(String summaryFileName) {
    this.summaryFileName = summaryFileName;
    doSummary = true;
  }

  public boolean doEmptyLargeCollectors() {
    return emptyLargeCollectors;
  }

  public void setEmptyLargeCollectors(boolean emptyLargeCollectors) {
    this.emptyLargeCollectors = emptyLargeCollectors;
  }

  public boolean collectAllErrors() {
    return collectAllErrors;
  }

  public void setCollectAllErrors(boolean collectAllErrors) {
    this.collectAllErrors = collectAllErrors;
  }

  public List<ValidationErrorType> getIgnorableIssueTypes() {
    return ignorableIssueTypes;
  }

  @Override
  public String formatParameters() {
    String text = super.formatParameters();
    text += String.format("details: %s%n", doSummary);
    text += String.format("summary: %s%n", doSummary);
    text += String.format("detailsFileName: %s%n", detailsFileName);
    text += String.format("summaryFileName: %s%n", summaryFileName);
    text += String.format("format: %s%n", format.getLabel());
    text += String.format("emptyLargeCollectors: %s%n", emptyLargeCollectors);
    return text;
  }
}
