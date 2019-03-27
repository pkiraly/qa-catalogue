package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.model.SolrFieldType;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MappingParameters {

  private boolean exportSubfieldCodes = false;
  private boolean exportSelfDescriptiveCodes = false;
  private SolrFieldType solrFieldType = SolrFieldType.MARC;

  protected Options options = new Options();
  protected static final CommandLineParser parser = new DefaultParser();
  protected CommandLine cmd;
  private boolean isOptionSet = false;

  protected void setOptions() {
    if (!isOptionSet) {
      options.addOption("c", "withSubfieldCodelists", false, "with subfield codelists");
      options.addOption("s", "withSelfDescriptiveCode", false, "with self-descriptive codes");
      options.addOption("t", "solrFieldType", true,
        "type of Solr fields, could be one of 'marc-tags', 'human-readable', or 'mixed'");
      options.addOption("h", "help", false, "display help");
      isOptionSet = true;
    }
  }

  public MappingParameters() {
  }

  public MappingParameters(String[] arguments) throws ParseException {
    cmd = parser.parse(getOptions(), arguments);

    if (cmd.hasOption("withSubfieldCodelists"))
      exportSubfieldCodes = true;

    if (cmd.hasOption("withSelfDescriptiveCode"))
      exportSelfDescriptiveCodes = true;

    if (cmd.hasOption("solrFieldType"))
      solrFieldType = SolrFieldType.byCode(cmd.getOptionValue("solrFieldType"));
  }

  public Options getOptions() {
    if (!isOptionSet)
      setOptions();
    return options;
  }

  public boolean isExportSubfieldCodes() {
    return exportSubfieldCodes;
  }

  public boolean isExportSelfDescriptiveCodes() {
    return exportSelfDescriptiveCodes;
  }

  public SolrFieldType getSolrFieldType() {
    return solrFieldType;
  }
}