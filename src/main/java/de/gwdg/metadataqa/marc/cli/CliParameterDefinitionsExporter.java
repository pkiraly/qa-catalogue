package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.cli.parameters.ClassificationParameters;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.cli.parameters.FormatterParameters;
import de.gwdg.metadataqa.marc.cli.parameters.MarcToSolrParameters;
import de.gwdg.metadataqa.marc.cli.parameters.NetworkParameters;
import de.gwdg.metadataqa.marc.cli.parameters.SerialScoreParameters;
import de.gwdg.metadataqa.marc.cli.parameters.Shacl4bibParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ShelfReadyCompletenessParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ThompsonTraillCompletenessParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import net.minidev.json.JSONValue;
import org.apache.commons.cli.Option;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Extracts the CLI parameter definitions of each component and saves them to a JSON file.
 */
public class CliParameterDefinitionsExporter {
  private static final Logger logger = Logger.getLogger(CliParameterDefinitionsExporter.class.getCanonicalName());

  private Map<String, List> options;
  private CommonParameters commonParameters;

  public CliParameterDefinitionsExporter() {
    commonParameters = new CommonParameters();
    options = new LinkedHashMap<>();
  }

  public String exportAll() {

    options.put("common", export(commonParameters));
    options.put("completeness", export(new CompletenessParameters()));
    options.put("validate", export(new ValidatorParameters()));
    options.put("index", export(new MarcToSolrParameters()));
    options.put("classifications", export(new ClassificationParameters()));
    options.put("authorities", export(new ValidatorParameters())); // TODO
    options.put("tt-completeness", export(new ThompsonTraillCompletenessParameters()));
    options.put("shelf-ready-completeness", export(new ShelfReadyCompletenessParameters()));
    options.put("bl-classification", export(new CommonParameters())); // TODO
    options.put("serial-score", export(new SerialScoreParameters()));
    options.put("formatter", export(new FormatterParameters())); // TODO at common-script
    options.put("functional-analysis", export(new CompletenessParameters())); // TODO
    options.put("network-analysis", export(new NetworkParameters()));
    options.put("record-patterns", export(new CompletenessParameters())); // TODO
    // options.put("export-schema", read(new MappingParameters()));
    options.put("shacl4bib", export(new Shacl4bibParameters()));

    return mapToJson();
  }

  private String mapToJson() {
    return JSONValue.toJSONString(options);
  }

  private <T extends CommonParameters> List<Map> export(T parameters) {
    List<Map> list = new LinkedList<>();
    for (Option option : parameters.getOptions().getOptions()) {
      if (parameters.equals(commonParameters) || !commonParameters.getOptions().getOptions().contains(option)) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("short", option.getOpt());
        map.put("long", option.getLongOpt());
        map.put("hasArg", option.hasArg());
        map.put("description", option.getDescription());
        list.add(map);
      }
    }
    return list;
  }

  public static void main(String[] args) throws IOException {
    CliParameterDefinitionsExporter cliParameterDefinitionsExporter = new CliParameterDefinitionsExporter();
    String json = cliParameterDefinitionsExporter.exportAll();
    System.out.println(json);
  }
}
