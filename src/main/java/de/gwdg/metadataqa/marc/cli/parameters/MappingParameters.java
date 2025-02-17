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
  private boolean exportFrbrFunctions = false;
  private boolean exportCompilanceLevel = false;
  private boolean withLocallyDefinedFields = false;

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
      options.addOption("f", "withFrbrFunctions", false, "with FRBR functions");
      options.addOption("l", "withComplianceLevel", false, "with compilance levels (national, minimal)");
      options.addOption("d", "withLocallyDefinedFields", false, "with locally defined fields");
      isOptionSet = true;
    }
  }

  public MappingParameters() {
    super();
  }

  public MappingParameters(String[] arguments) throws ParseException {
    cmd = parser.parse(getOptions(), arguments);

    if (cmd.hasOption("withSubfieldCodelists"))
      exportSubfieldCodes = true;

    if (cmd.hasOption("withSelfDescriptiveCode"))
      exportSelfDescriptiveCodes = true;

    if (cmd.hasOption("solrFieldType"))
      solrFieldType = SolrFieldType.byCode(cmd.getOptionValue("solrFieldType"));

    if (cmd.hasOption("withFrbrFunctions"))
      exportFrbrFunctions = true;

    if (cmd.hasOption("withComplianceLevel"))
      exportCompilanceLevel = true;

    if (cmd.hasOption("withLocallyDefinedFields"))
      withLocallyDefinedFields = true;
  }

  public Options getOptions() {
    if (!isOptionSet)
      setOptions();
    return options;
  }

  public boolean doExportSubfieldCodes() {
    return exportSubfieldCodes;
  }

  public boolean doExportSelfDescriptiveCodes() {
    return exportSelfDescriptiveCodes;
  }

  public SolrFieldType getSolrFieldType() {
    return solrFieldType;
  }

  public boolean doExportFrbrFunctions() {
    return exportFrbrFunctions;
  }

  public boolean doExportCompilanceLevel() {
    return exportCompilanceLevel;
  }

  public boolean isWithLocallyDefinedFields() {
    return withLocallyDefinedFields;
  }
}
