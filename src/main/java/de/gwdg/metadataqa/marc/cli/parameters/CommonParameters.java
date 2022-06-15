package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.cli.utils.IgnorableFields;
import de.gwdg.metadataqa.marc.cli.utils.IgnorableRecords;
import de.gwdg.metadataqa.marc.definition.DataSource;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.utils.alephseq.AlephseqLine;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;

public class CommonParameters implements Serializable {

  protected String[] args;
  public static final String DEFAULT_OUTPUT_DIR = ".";

  protected MarcVersion marcVersion = MarcVersion.MARC21;
  protected MarcFormat marcFormat = MarcFormat.ISO;
  protected DataSource dataSource = DataSource.FILE;
  protected boolean doHelp;
  protected boolean doLog = true;
  protected int limit = -1;
  protected int offset = -1;
  protected String id = null;
  protected Leader.Type defaultRecordType = null;
  protected boolean fixAlephseq = false;
  protected boolean fixAlma = false;
  protected boolean fixKbr = false;
  protected boolean alephseq = false;
  protected boolean marcxml = false;
  protected boolean lineSeparated = false;
  protected boolean trimId = false;
  private String outputDir = DEFAULT_OUTPUT_DIR;
  protected IgnorableRecords ignorableRecords = new IgnorableRecords();
  protected IgnorableFields ignorableFields = new IgnorableFields();
  protected InputStream stream = null;
  protected String defaultEncoding = null;

  protected Options options = new Options();
  protected static final CommandLineParser parser = new DefaultParser();
  protected CommandLine cmd;
  private boolean isOptionSet = false;
  private AlephseqLine.TYPE alephseqLineType;
  private String picaIdField;
  private String picaIdCode;
  private String picaSubfieldSeparator;

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
      options.addOption("X", "fixAlma", false, "fix the known issues of Alma format");
      options.addOption("R", "fixKbr", false, "fix the known issues of Alma format");
      options.addOption("p", "alephseq", false, "the source is in Alephseq format");
      options.addOption("x", "marcxml", false, "the source is in MARCXML format");
      options.addOption("y", "lineSeparated", false, "the source is in line separated MARC format");
      options.addOption("t", "outputDir", true, "output directory");
      options.addOption("r", "trimId", false, "remove spaces from the end of record IDs");
      options.addOption("z", "ignorableFields", true, "ignore fields from the analysis");
      options.addOption("v", "ignorableRecords", true, "ignore records from the analysis");
      options.addOption("f", "marcFormat", true, "MARC format (like 'ISO' or 'MARCXML')");
      options.addOption("s", "dataSource", true, "data source (file of stream)");
      options.addOption("g", "defaultEncoding", true, "default character encoding");
      options.addOption("A", "alephseqLineType", true, "Alephseq line type");
      options.addOption("B", "picaIdField", true, "PICA id field");
      options.addOption("C", "picaIdCode", true, "PICA id subfield");
      options.addOption("D", "picaSubfieldSeparator", true, "PICA subfield separator");

      isOptionSet = true;
    }
  }

  public CommonParameters() {
  }

  public CommonParameters(String[] arguments)  throws ParseException {
    cmd = parser.parse(getOptions(), arguments);

    if (cmd.hasOption("marcVersion"))
      setMarcVersion(cmd.getOptionValue("marcVersion"));

    if (cmd.hasOption("marcFormat"))
      setMarcFormat(cmd.getOptionValue("marcFormat"));

    if (cmd.hasOption("dataSource"))
      setDataSource(cmd.getOptionValue("dataSource"));

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
      setDefaultRecordType(cmd.getOptionValue("defaultRecordType"));

    setAlephseq(cmd.hasOption("alephseq"));

    fixAlephseq = cmd.hasOption("fixAlephseq");

    fixAlma = cmd.hasOption("fixAlma");
    fixKbr = cmd.hasOption("fixKbr");

    setMarcxml(cmd.hasOption("marcxml"));

    lineSeparated = cmd.hasOption("lineSeparated");

    if (cmd.hasOption("outputDir"))
      outputDir = cmd.getOptionValue("outputDir");

    trimId = cmd.hasOption("trimId");

    if (cmd.hasOption("ignorableFields"))
      setIgnorableFields(cmd.getOptionValue("ignorableFields"));

    if (cmd.hasOption("ignorableRecords"))
      setIgnorableRecords(cmd.getOptionValue("ignorableRecords"));

    if (cmd.hasOption("defaultEncoding"))
      setDefaultEncoding(cmd.getOptionValue("defaultEncoding"));

    if (cmd.hasOption("alephseqLineType"))
      setAlephseqLineType(cmd.getOptionValue("alephseqLineType"));

    if (cmd.hasOption("picaIdField"))
      picaIdField = cmd.getOptionValue("picaIdField");

    if (cmd.hasOption("picaIdCode"))
      picaIdCode = cmd.getOptionValue("picaIdCode");

    if (cmd.hasOption("picaSubfieldSeparator"))
      picaSubfieldSeparator = cmd.getOptionValue("picaSubfieldSeparator");

    args = cmd.getArgs();
  }

  private void setAlephseqLineType(String alephseqLineTypeInput) throws ParseException {
    try {
      alephseqLineType = AlephseqLine.TYPE.valueOf(cmd.getOptionValue("alephseqLineType"));
    } catch (IllegalArgumentException e) {
      throw new ParseException(String.format("Unrecognized alephseqLineType parameter value: '%s'", alephseqLineTypeInput));
    }
  }

  public Options getOptions() {
    if (!isOptionSet)
      setOptions();
    return options;
  }

  public MarcVersion getMarcVersion() {
    return marcVersion;
  }

  public void setMarcVersion(MarcVersion marcVersion) {
    this.marcVersion = marcVersion;
  }

  public void setMarcVersion(String marcVersion) throws ParseException {
    this.marcVersion = MarcVersion.byCode(marcVersion.trim());
    if (this.marcVersion == null)
      throw new ParseException(String.format("Unrecognized marcVersion parameter value: '%s'", marcVersion));
  }

  public MarcFormat getMarcFormat() {
    return marcFormat;
  }

  public void setMarcFormat(MarcFormat marcFormat) {
    this.marcFormat = marcFormat;
  }

  public void setMarcFormat(String marcFormatString) throws ParseException {
    marcFormat = MarcFormat.byCode(marcFormatString.trim());
    if (marcFormat == null)
      throw new ParseException(String.format("Unrecognized marcFormat parameter value: '%s'", marcFormatString));
    if (marcFormat.equals(MarcFormat.ALEPHSEQ))
      setAlephseq(true);
    if (marcFormat.equals(MarcFormat.XML))
      setMarcxml(true);
    if (marcFormat.equals(MarcFormat.LINE_SEPARATED))
      setLineSeparated(true);
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void setDataSource(String dataSourceString) throws ParseException {
    dataSource = DataSource.byCode(dataSourceString.trim());
    if (dataSource == null)
      throw new ParseException(String.format("Unrecognized marcFormat parameter value: '%s'", dataSourceString));
  }

  public boolean doHelp() {
    return doHelp;
  }

  public void setDoHelp(boolean doHelp) {
    this.doHelp = doHelp;
  }

  public boolean doLog() {
    return doLog;
  }

  public void setDoLog(boolean doLog) {
    this.doLog = doLog;
  }

  public String[] getArgs() {
    return args;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public boolean hasId() {
    return StringUtils.isNotBlank(id);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Leader.Type getDefaultRecordType() {
    return defaultRecordType;
  }

  public void setDefaultRecordType(Leader.Type defaultRecordType) {
    this.defaultRecordType = defaultRecordType;
  }

  public void setDefaultRecordType(String defaultRecordType) throws ParseException {
    this.defaultRecordType = Leader.Type.valueOf(defaultRecordType);
    if (this.defaultRecordType == null)
      throw new ParseException(String.format("Unrecognized defaultRecordType parameter value: '%s'", defaultRecordType));
  }

  public boolean fixAlephseq() {
    return fixAlephseq;
  }

  public void setFixAlephseq(boolean fixAlephseq) {
    this.fixAlephseq = fixAlephseq;
  }

  public boolean fixAlma() {
    return fixAlma;
  }

  public void setFixAlma(boolean fixAlma) {
    this.fixAlma = fixAlma;
  }

  public boolean fixKbr() {
    return fixKbr;
  }

  public void setFixKbr(boolean fixKbr) {
    this.fixKbr = fixKbr;
  }

  public String getReplecementInControlFields() {
    if (fixAlephseq())
      return "^";
    else if (fixAlma() || fixKbr())
      return "#";
    else
      return null;
  }

  public boolean isAlephseq() {
    return alephseq;
  }

  public void setAlephseq(boolean alephseq) {
    this.alephseq = alephseq;
    if (alephseq)
      marcFormat = MarcFormat.ALEPHSEQ;
  }

  public boolean isMarcxml() {
    return marcxml;
  }

  public void setMarcxml(boolean marcxml) {
    this.marcxml = marcxml;
    if (marcxml)
      marcFormat = MarcFormat.XML;
  }

  public boolean isLineSeparated() {
    return lineSeparated;
  }

  public void setLineSeparated(boolean lineSeparated) {
    this.lineSeparated = lineSeparated;
  }

  public String getOutputDir() {
    return outputDir;
  }

  public void setOutputDir(String outputDir) {
    this.outputDir = outputDir;
  }

  public boolean getTrimId() {
    return trimId;
  }

  public void setTrimId(boolean trimId) {
    this.trimId = trimId;
  }

  public IgnorableFields getIgnorableFields() {
    return ignorableFields;
  }

  public void setIgnorableFields(String ignorableFields) {
    this.ignorableFields.parseFields(ignorableFields.trim());
  }

  public IgnorableRecords getIgnorableRecords() {
    return ignorableRecords;
  }

  public void setIgnorableRecords(String ignorableRecords) {
    this.ignorableRecords.parseInput(ignorableRecords.trim());
  }

  public InputStream getStream() {
    return stream;
  }

  public void setStream(InputStream stream) {
    this.stream = stream;
  }

  public String getDefaultEncoding() {
    return defaultEncoding;
  }

  private void setDefaultEncoding(String defaultEncoding) {
    this.defaultEncoding = defaultEncoding;
  }

  public AlephseqLine.TYPE getAlephseqLineType() {
    return this.alephseqLineType;
  }

  public String getPicaIdField() {
    return picaIdField;
  }

  public void setPicaIdField(String picaIdField) {
    this.picaIdField = picaIdField;
  }

  public String getPicaIdCode() {
    return picaIdCode;
  }

  public void setPicaIdCode(String picaIdCode) {
    this.picaIdCode = picaIdCode;
  }

  public String getPicaSubfieldSeparator() {
    return picaSubfieldSeparator;
  }

  public void setPicaSubfieldSeparator(String picaSubfieldSeparator) {
    this.picaSubfieldSeparator = picaSubfieldSeparator;
  }

  public String formatParameters() {
    String text = "";
    text += String.format("marcVersion: %s, %s%n", marcVersion.getCode(), marcVersion.getLabel());
    text += String.format("marcFormat: %s, %s%n", marcFormat.getCode(), marcFormat.getLabel());
    text += String.format("dataSource: %s, %s%n", dataSource.getCode(), dataSource.getLabel());
    text += String.format("limit: %d%n", limit);
    text += String.format("offset: %s%n", offset);
    text += String.format("MARC files: %s%n", StringUtils.join(args, ", "));
    text += String.format("id: %s%n", id);
    text += String.format("defaultRecordType: %s%n", defaultRecordType);
    text += String.format("fixAlephseq: %s%n", fixAlephseq);
    text += String.format("fixAlma: %s%n", fixAlma);
    text += String.format("alephseq: %s%n", alephseq);
    text += String.format("marcxml: %s%n", marcxml);
    text += String.format("lineSeparated: %s%n", lineSeparated);
    text += String.format("outputDir: %s%n", outputDir);
    text += String.format("trimId: %s%n", trimId);
    text += String.format("ignorableFields: %s%n", ignorableFields);
    text += String.format("ignorableRecords: %s%n", ignorableRecords);
    text += String.format("defaultEncoding: %s%n", defaultEncoding);
    text += String.format("alephseqLineType: %s%n", alephseqLineType);
    text += String.format("picaIdField: %s%n", picaIdField);
    text += String.format("picaIdCode: %s%n", picaIdCode);
    text += String.format("picaSubfieldSeparator: %s%n", picaSubfieldSeparator);

    return text;
  }

}
