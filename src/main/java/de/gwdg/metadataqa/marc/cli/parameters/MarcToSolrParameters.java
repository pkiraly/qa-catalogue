package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.model.SolrFieldType;
import org.apache.commons.cli.ParseException;
import org.apache.solr.client.solrj.SolrClient;

public class MarcToSolrParameters extends CommonParameters {

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
    return text;
  }

}
