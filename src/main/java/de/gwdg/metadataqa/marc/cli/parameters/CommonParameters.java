package de.gwdg.metadataqa.marc.cli.parameters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.gwdg.metadataqa.marc.cli.utils.IgnorableFields;
import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordFilter;
import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordFilterFactory;
import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordIgnorator;
import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordIgnoratorFactory;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.definition.DataSource;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.utils.alephseq.AlephseqLine;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.Serializable;

public class CommonParameters implements Serializable {

  private static final long serialVersionUID = -4760615880678251867L;

  protected String[] args;
  public static final String DEFAULT_OUTPUT_DIR = ".";
  public static final MarcVersion DEFAULT_MARC_VERSION = MarcVersion.MARC21;

  protected MarcVersion marcVersion = DEFAULT_MARC_VERSION;
  protected MarcFormat marcFormat = MarcFormat.ISO;
  protected DataSource dataSource = DataSource.FILE;
  protected boolean doHelp;
  protected boolean doLog = true;
  protected int limit = -1;
  protected int offset = -1;
  protected String id = null;
  protected MarcLeader.Type defaultRecordType = MarcLeader.Type.BOOKS;
  protected boolean fixAlephseq = false;
  protected boolean fixAlma = false;
  protected boolean fixKbr = false;
  protected boolean alephseq = false;
  protected boolean marcxml = false;
  protected boolean lineSeparated = false;
  protected boolean trimId = false;
  private String outputDir = DEFAULT_OUTPUT_DIR;
  protected String ignorableRecords;
  @JsonIgnore
  protected RecordIgnorator recordIgnorator;
  protected RecordFilter recordFilter;
  protected IgnorableFields ignorableFields = new IgnorableFields();
  protected InputStream stream = null;
  protected String defaultEncoding = null;

  @JsonIgnore
  protected Options options = new Options();
  protected static final CommandLineParser parser = new DefaultParser();
  protected CommandLine cmd;
  private boolean isOptionSet = false;
  private AlephseqLine.TYPE alephseqLineType;
  private String picaIdField = "003@$0";
  private String picaSubfieldSeparator = "$";
  private String picaSchemaFile;
  private String picaRecordTypeField = "002@$0";
  private SchemaType schemaType = SchemaType.MARC21;
  private String groupBy;
  private String groupListFile;
  private String solrForScoresUrl;

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
      options.addOption("a", "fixAlma", false, "fix the known issues of Alma format");
      options.addOption("b", "fixKbr", false, "fix the known issues of Alma format");
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
      options.addOption("1", "alephseqLineType", true, "Alephseq line type");
      options.addOption("2", "picaIdField", true, "PICA id field");
      options.addOption("u", "picaSubfieldSeparator", true, "PICA subfield separator");
      options.addOption("j", "picaSchemaFile", true, "Avram PICA schema file");
      // For now, I'll be using picaSchemaFile for both PICA and UNIMARC. The option could be renamed later or a separate option could be added
      options.addOption("w", "schemaType", true, "metadata schema type ('MARC21', 'UNIMARC', or 'PICA')");
      options.addOption("k", "picaRecordType", true, "picaRecordType");
      options.addOption("c", "allowableRecords", true, "allow records for the analysis");
      options.addOption("e", "groupBy", true, "group the results by the value of this data element (e.g. the ILN of  library)");
      options.addOption("3", "groupListFile", true, "the file which contains a list of ILN codes");
      options.addOption("4", "solrForScoresUrl", true, "the URL of the Solr server used to store scores");

      isOptionSet = true;
    }
  }

  public CommonParameters() {
  }

  public CommonParameters(String[] arguments)  throws ParseException {
    cmd = parser.parse(getOptions(), arguments);

    readSchemaType();
    readMarcVersion();
    readMarcFormat();
    readDataSource();
    doHelp = cmd.hasOption("help");
    doLog = !cmd.hasOption("nolog");
    readLimit();
    readOffset();
    if (offset > -1 && limit > -1)
      limit += offset;
    readId();
    readDefaultRecordType();
    setAlephseq(cmd.hasOption("alephseq"));
    fixAlephseq = cmd.hasOption("fixAlephseq");
    fixAlma = cmd.hasOption("fixAlma");
    fixKbr = cmd.hasOption("fixKbr");
    setMarcxml(cmd.hasOption("marcxml"));
    lineSeparated = cmd.hasOption("lineSeparated");
    readOutputDir();
    trimId = cmd.hasOption("trimId");
    readIgnorableFields();
    readIgnorableRecords();
    readAllowableRecords();
    readDefaultEncoding();
    readAlephseqLineType();
    readPicaIdField();
    readPicaSubfieldSeparator();
    readPicaSchemaFile();
    readPicaRecordType();
    readGroupBy();
    readGroupListFile();
    readSolrForScoresUrl();

    args = cmd.getArgs();
  }

  private void readPicaSchemaFile() {
    if (cmd.hasOption("picaSchemaFile"))
      picaSchemaFile = cmd.getOptionValue("picaSchemaFile");
  }

  private void readPicaRecordType() {
    if (cmd.hasOption("picaRecordType"))
      picaRecordTypeField = cmd.getOptionValue("picaRecordType");
  }

  private void readGroupBy() {
    if (cmd.hasOption("groupBy"))
      groupBy = cmd.getOptionValue("groupBy");
  }

  private void readGroupListFile() {
    if (cmd.hasOption("groupListFile"))
      groupListFile = cmd.getOptionValue("groupListFile");
  }

  private void readSolrForScoresUrl() {
    if (cmd.hasOption("solrForScoresUrl"))
      solrForScoresUrl = cmd.getOptionValue("solrForScoresUrl");
  }

  private void readPicaSubfieldSeparator() {
    if (cmd.hasOption("picaSubfieldSeparator"))
      picaSubfieldSeparator = cmd.getOptionValue("picaSubfieldSeparator");
  }

  private void readPicaIdField() {
    if (cmd.hasOption("picaIdField"))
      picaIdField = cmd.getOptionValue("picaIdField");
  }

  private void readAlephseqLineType() throws ParseException {
    if (cmd.hasOption("alephseqLineType"))
      setAlephseqLineType(cmd.getOptionValue("alephseqLineType"));
  }

  private void readDefaultEncoding() {
    if (cmd.hasOption("defaultEncoding"))
      setDefaultEncoding(cmd.getOptionValue("defaultEncoding"));
  }

  private void readIgnorableRecords() {
    ignorableRecords = cmd.hasOption("ignorableRecords") ? cmd.getOptionValue("ignorableRecords") : "";
    setRecordIgnorator(ignorableRecords);
  }

  private void readAllowableRecords() {
    String allowableRecords = cmd.hasOption("allowableRecords") ? cmd.getOptionValue("allowableRecords") : "";
    setRecordFilter(allowableRecords);
  }

  private void readIgnorableFields() {
    if (cmd.hasOption("ignorableFields"))
      setIgnorableFields(cmd.getOptionValue("ignorableFields"));
  }

  private void readOutputDir() {
    if (cmd.hasOption("outputDir"))
      outputDir = cmd.getOptionValue("outputDir");
  }

  private void readDefaultRecordType() throws ParseException {
    if (cmd.hasOption("defaultRecordType"))
      setDefaultRecordType(cmd.getOptionValue("defaultRecordType"));
  }

  private void readId() {
    if (cmd.hasOption("id"))
      id = cmd.getOptionValue("id").trim();
  }

  private void readOffset() {
    if (cmd.hasOption("offset"))
      offset = Integer.parseInt(cmd.getOptionValue("offset"));
  }

  private void readLimit() {
    if (cmd.hasOption("limit"))
      limit = Integer.parseInt(cmd.getOptionValue("limit"));
  }

  private void readDataSource() throws ParseException {
    if (cmd.hasOption("dataSource"))
      setDataSource(cmd.getOptionValue("dataSource"));
  }

  private void readSchemaType() throws ParseException {
    if (cmd.hasOption("schemaType"))
      setSchemaType(cmd.getOptionValue("schemaType"));
  }

  private void setSchemaType(String input) throws ParseException {
    try {
      schemaType = SchemaType.valueOf(input);
    } catch (IllegalArgumentException e) {
      throw new ParseException(String.format("Unrecognized schemaType parameter value: '%s'", input));
    }
  }

  private void readMarcFormat() throws ParseException {
    if (cmd.hasOption("marcFormat"))
      setMarcFormat(cmd.getOptionValue("marcFormat"));
  }

  private void readMarcVersion() throws ParseException {
    if (cmd.hasOption("marcVersion"))
      setMarcVersion(cmd.getOptionValue("marcVersion"));
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
    if (marcFormat.equals(MarcFormat.PICA_NORMALIZED) || marcFormat.equals(MarcFormat.PICA_PLAIN))
      schemaType = SchemaType.PICA;
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

  public MarcLeader.Type getDefaultRecordType() {
    return defaultRecordType;
  }

  public void setDefaultRecordType(MarcLeader.Type defaultRecordType) {
    this.defaultRecordType = defaultRecordType;
  }

  public void setDefaultRecordType(String defaultRecordType) throws ParseException {
    this.defaultRecordType = MarcLeader.Type.valueOf(defaultRecordType);
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

  public String getReplacementInControlFields() {
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

  public RecordIgnorator getRecordIgnorator() {
    return recordIgnorator;
  }

  public void setRecordIgnorator(String ignorableRecords) {
    this.recordIgnorator = RecordIgnoratorFactory.create(schemaType, ignorableRecords.trim());
  }

  public RecordFilter getRecordFilter() {
    return recordFilter;
  }

  public void setRecordFilter(String allowableRecords) {
    this.recordFilter = RecordFilterFactory.create(schemaType, allowableRecords.trim());
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

  public String getPicaSubfieldSeparator() {
    return picaSubfieldSeparator;
  }

  public void setPicaSubfieldSeparator(String picaSubfieldSeparator) {
    this.picaSubfieldSeparator = picaSubfieldSeparator;
  }

  public String getPicaSchemaFile() {
    return picaSchemaFile;
  }

  public SchemaType getSchemaType() {
    return schemaType;
  }

  public String getPicaRecordTypeField() {
    return picaRecordTypeField;
  }

  public boolean isMarc21() {
    return schemaType.equals(SchemaType.MARC21);
  }

  public boolean isPica() {
    return schemaType.equals(SchemaType.PICA);
  }

  public boolean isUnimarc() {
    return schemaType.equals(SchemaType.UNIMARC);
  }

  public String getGroupBy() {
    return groupBy;
  }

  public String getGroupListFile() {
    return groupListFile;
  }

  public String getSolrForScoresUrl() {
    return solrForScoresUrl;
  }

  public String formatParameters() {
    String text = "";
    text += String.format("schemaType: %s%n", schemaType);
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
    text += String.format("allowableRecords: %s%n", recordFilter);
    text += String.format("ignorableRecords: %s%n", recordIgnorator);
    text += String.format("defaultEncoding: %s%n", defaultEncoding);
    text += String.format("alephseqLineType: %s%n", alephseqLineType);
    if (isPica()) {
      text += String.format("picaIdField: %s%n", picaIdField);
      text += String.format("picaSubfieldSeparator: %s%n", picaSubfieldSeparator);
      text += String.format("picaRecordType: %s%n", picaRecordTypeField);
    }
    text += String.format("groupBy: %s%n", groupBy);
    text += String.format("groupListFile: %s%n", groupListFile);
    text += String.format("solrForScoresUrl: %s%n", solrForScoresUrl);

    return text;
  }
}
