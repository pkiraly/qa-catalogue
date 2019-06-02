package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.Leader;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class CommonParameters implements Serializable {

  protected String[] args;
  public static final String DEFAULT_OUTPUT_DIR = ".";

  protected MarcVersion marcVersion = MarcVersion.MARC21;
  protected boolean doHelp;
  protected boolean doLog = true;
  private int limit = -1;
  private int offset = -1;
  private String id = null;
  protected Leader.Type defaultRecordType = null;
  protected boolean fixAlephseq = false;
  protected boolean marcxml = false;
  protected boolean lineSeparated = false;
  protected boolean trimId = false;
  private String outputDir = DEFAULT_OUTPUT_DIR;

  protected Options options = new Options();
  protected static final CommandLineParser parser = new DefaultParser();
  protected CommandLine cmd;
  private boolean isOptionSet = false;

  protected void setOptions() {
    if (!isOptionSet) {
      options.addOption("m", "marcVersion", true, "MARC version ('OCLC' or 'DNB')");
      options.addOption("h", "help", false, "display help");
      options.addOption("n", "nolog", false, "do not display log messages");
      options.addOption("l", "limit", true, "limit the number of records to process");
      options.addOption("o", "offset", true, "the first record to process");
      options.addOption("i", "id", true, "the MARC identifier (content of 001)");
      options.addOption("d", "defaultRecordType", true, "the default record type if the record's type is undetectable");
      options.addOption("q", "fixAlephseq", false, "fix the known issues of Alephseq format");
      options.addOption("x", "marcxml", false, "the source is in MARCXML format");
      options.addOption("y", "lineSeparated", false, "the source is in line separated MARC format");
      options.addOption("t", "outputDir", true, "output directory");
      options.addOption("r", "trimId", false, "remove spaces from the end of record IDs");
      isOptionSet = true;
    }
  }

  public CommonParameters() {
  }

  public CommonParameters(String[] arguments)  throws ParseException {
    System.err.println("CommonParameters");
    cmd = parser.parse(getOptions(), arguments);

    if (cmd.hasOption("marcVersion")) {
      marcVersion = MarcVersion.byCode(cmd.getOptionValue("marcVersion"));
      if (marcVersion == null)
        throw new ParseException(
          String.format(
            "Unrecognized marcVersion parameter value: '%s'",
            cmd.getOptionValue("marcVersion")));
    }

    doHelp = cmd.hasOption("help");

    doLog = !cmd.hasOption("nolog");

    if (cmd.hasOption("limit"))
      limit = Integer.parseInt(cmd.getOptionValue("limit"));

    if (cmd.hasOption("offset"))
      offset = Integer.parseInt(cmd.getOptionValue("offset"));

    if (offset > -1 && limit > -1)
      limit += offset;

    if (cmd.hasOption("id"))
      id = cmd.getOptionValue("id").trim();

    if (cmd.hasOption("defaultRecordType"))
      defaultRecordType = Leader.Type.valueOf(cmd.getOptionValue("defaultRecordType"));

    fixAlephseq = cmd.hasOption("fixAlephseq");

    marcxml = cmd.hasOption("marcxml");
    System.err.println("marcxml: " + marcxml);

    lineSeparated = cmd.hasOption("lineSeparated");

    if (cmd.hasOption("outputDir"))
      outputDir = cmd.getOptionValue("outputDir");

    trimId = cmd.hasOption("trimId");

    args = cmd.getArgs();
  }

  public Options getOptions() {
    if (!isOptionSet)
      setOptions();
    return options;
  }

  public MarcVersion getMarcVersion() {
    return marcVersion;
  }

  public boolean doHelp() {
    return doHelp;
  }

  public boolean doLog() {
    return doLog;
  }

  public String[] getArgs() {
    return args;
  }

  public int getLimit() {
    return limit;
  }

  public int getOffset() {
    return offset;
  }

  public boolean hasId() {
    return StringUtils.isNotBlank(id);
  }

  public String getId() {
    return id;
  }

  public Leader.Type getDefaultRecordType() {
    return defaultRecordType;
  }

  public boolean fixAlephseq() {
    return fixAlephseq;
  }

  public boolean isMarcxml() {
    return marcxml;
  }

  public boolean isLineSeparated() {
    return lineSeparated;
  }

  public String getOutputDir() {
    return outputDir;
  }

  public boolean getTrimId() {
    return trimId;
  }

  public String formatParameters() {
    String text = "";
    text += String.format("marcVersion: %s, %s%n", marcVersion.getCode(), marcVersion.getLabel());
    text += String.format("limit: %d%n", limit);
    text += String.format("offset: %s%n", offset);
    text += String.format("MARC files: %s%n", StringUtils.join(args, ", "));
    text += String.format("id: %s%n", id);
    text += String.format("defaultRecordType: %s%n", defaultRecordType);
    text += String.format("fixAlephseq: %s%n", fixAlephseq);
    text += String.format("marcxml: %s%n", marcxml);
    text += String.format("lineSeparated: %s%n", lineSeparated);
    text += String.format("outputDir: %s%n", outputDir);
    text += String.format("trimId: %s%n", trimId);

    return text;
  }
}
