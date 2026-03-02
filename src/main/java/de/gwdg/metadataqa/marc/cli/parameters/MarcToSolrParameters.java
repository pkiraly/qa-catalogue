package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.SchemaSpec;
import de.gwdg.metadataqa.marc.utils.marcspec.MarcSpecParser;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaSpec;
import org.apache.commons.cli.ParseException;
import org.apache.solr.client.solrj.SolrClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MarcToSolrParameters extends CommonParameters {

  private static final Logger logger = Logger.getLogger(TranslationParameters.class.getCanonicalName());

  private int DEFAULT_COMMIT_AT = 10000;
  private boolean useEmbedded = false;
  private String solrUrl = null;
  private boolean doCommit = false;
  private SolrFieldType solrFieldType = SolrFieldType.MIXED;
  private SolrClient mainClient = null;
  private SolrClient validationClient = null;
  private boolean indexWithTokenizedField = false;

  private boolean isOptionSet = false;
  private int commitAt = DEFAULT_COMMIT_AT;
  private boolean indexFieldCounts = false;
  private boolean indexSubfieldCounts = false;
  private String fieldPrefix = null;
  private String compoundFieldsInput = null;
  private Map<String, List<SchemaSpec>> compoundFields = null;

  @Override
  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      options.addOption("S", "solrUrl", true, "the URL of Solr server including the core (e.g. http://localhost:8983/solr/loc)");
      options.addOption("A", "doCommit", false, "commits Solr index regularly");
      options.addOption("T", "solrFieldType", true,
        "type of Solr fields, could be one of 'marc-tags', 'human-readable', or 'mixed'");
      options.addOption("B", "useEmbedded", false, "use embedded Solr server (used in tests only)");
      options.addOption("C", "indexWithTokenizedField", false, "index data elements as tokenized field as well");
      options.addOption("D", "commitAt", true, "commit index after this number of records");
      options.addOption("E", "indexFieldCounts", false, "index the count of field instances");
      options.addOption("G", "indexSubfieldCounts", false, "index the count of subfield instances");
      options.addOption("F", "fieldPrefix", true, "field prefix");
      options.addOption("H", "compoundFields", true, "compound fields");
      isOptionSet = true;
    }
  }

  public MarcToSolrParameters() {
    super();
  }

  public MarcToSolrParameters(String[] arguments) throws ParseException {
    super(arguments);

    if (cmd.hasOption("solrUrl"))
      solrUrl = cmd.getOptionValue("solrUrl");

    if (cmd.hasOption("doCommit"))
      doCommit = true;

    if (cmd.hasOption("solrFieldType"))
      solrFieldType = SolrFieldType.byCode(cmd.getOptionValue("solrFieldType"));

    if (cmd.hasOption("useEmbedded"))
      useEmbedded = true;

    if (cmd.hasOption("indexWithTokenizedField"))
      indexWithTokenizedField = true;

    if (cmd.hasOption("commitAt"))
      commitAt = Integer.valueOf(cmd.getOptionValue("commitAt"));

    if (cmd.hasOption("indexFieldCounts"))
      indexFieldCounts = true;

    if (cmd.hasOption("indexSubfieldCounts"))
      indexSubfieldCounts = true;

    if (cmd.hasOption("fieldPrefix"))
      fieldPrefix = cmd.getOptionValue("fieldPrefix");

    if (cmd.hasOption("compoundFields")) {
      compoundFieldsInput = cmd.getOptionValue("compoundFields");
      logger.info("compoundFieldsInput: " + compoundFieldsInput);
      try {
        compoundFields = parseCompoundFields(compoundFieldsInput);
      } catch (Exception e) {
        e.printStackTrace();
      }
      logger.info("compoundFields: " + compoundFields);
    }
  }

  /**
   * Parse the compound field input and transforms it to a maps of solr field - bibliographic fields pairs.
   * @param compoundFieldsInput The compound field configuration. It should fit the follwoing pattern:
   *                            solrfield1=biblfield1,biblfield2|solrfield2=biblfield3,biblfield4
   * @return
   */
  private Map<String, List<SchemaSpec>> parseCompoundFields(String compoundFieldsInput) {
    logger.info("parseCompoundFields: " + compoundFieldsInput);
    Map<String, List<SchemaSpec>> compoundFieldsMap = new HashMap<>();
    String[] fields = compoundFieldsInput.split("\\|");
    for (String field : fields) {
      logger.info("field: " + field);
      String[] fieldParts = field.split("=", 2);
      String solrField = fieldParts[0];
      String[] bibFields = fieldParts[1].split(",");
      List<SchemaSpec> bibliographicFields = new ArrayList<>();
      for (String bibField : bibFields) {
        if (getSchemaType().equals(SchemaType.PICA))
          bibliographicFields.add(new PicaSpec(bibField));
        else
          bibliographicFields.add(MarcSpecParser.parse(bibField));
      }
      compoundFieldsMap.put(solrField, bibliographicFields);
    }
    return compoundFieldsMap;
  }

  public String getSolrUrl() {
    return solrUrl;
  }

  public boolean isDoCommit() {
    return doCommit;
  }

  public SolrFieldType getSolrFieldType() {
    return solrFieldType;
  }

  public SolrClient getMainClient() {
    return mainClient;
  }

  public void setMainClient(SolrClient mainClient) {
    this.mainClient = mainClient;
  }

  public SolrClient getValidationClient() {
    return validationClient;
  }

  public void setValidationClient(SolrClient validationClient) {
    this.validationClient = validationClient;
  }

  public boolean isUseEmbedded() {
    return useEmbedded;
  }

  public boolean isIndexWithTokenizedField() {
    return indexWithTokenizedField;
  }

  public int getCommitAt() {
    return commitAt;
  }

  public boolean isIndexFieldCounts() {
    return indexFieldCounts;
  }

  public boolean isIndexSubfieldCounts() {
    return indexSubfieldCounts;
  }

  public String getFieldPrefix() {
    return fieldPrefix != null ? fieldPrefix : "";
  }

  public void setFieldPrefix(String fieldPrefix) {
    this.fieldPrefix = fieldPrefix;
  }

  public Map<String, List<SchemaSpec>> getCompoundFields() {
    return compoundFields;
  }

  @Override
  public String formatParameters() {
    String text = super.formatParameters();
    text += String.format("solrUrl: %s%n", solrUrl);
    text += String.format("doCommit: %s%n", doCommit);
    text += String.format("solrFieldType: %s%n", solrFieldType);
    text += String.format("indexWithTokenizedField: %s%n", indexWithTokenizedField);
    text += String.format("commitAt: %s%n", commitAt);
    text += String.format("indexFieldCounts: %s%n", indexFieldCounts);
    text += String.format("indexSubfieldCounts: %s%n", indexSubfieldCounts);
    text += String.format("fieldPrefix: %s%n", fieldPrefix);
    text += String.format("compoundFields: %s%n", compoundFieldsInput);
    return text;
  }

}
