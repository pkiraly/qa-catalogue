package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.configuration.ConfigurationReader;
import de.gwdg.metadataqa.api.configuration.SchemaConfiguration;
import de.gwdg.metadataqa.api.configuration.schema.Rule;
import de.gwdg.metadataqa.api.json.DataElement;
import de.gwdg.metadataqa.api.rule.RuleCatalog;
import de.gwdg.metadataqa.marc.cli.Shacl4bib;
import de.gwdg.metadataqa.marc.cli.parameters.Shacl4bibParameters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ShaclUtils  {
  private static final Logger logger = Logger.getLogger(Shacl4bib.class.getCanonicalName());

  /**
   * Reads the schema from file
   * @param parameters
   * @return
   * @param <T>
   */
  public static <T extends Shacl4bibParameters> SchemaConfiguration setupSchema(T parameters) {
    SchemaConfiguration schema = null;
    String shaclConfigurationFile = parameters.getShaclConfigurationFile();
    try {
      if (shaclConfigurationFile.endsWith(".json"))
        schema = ConfigurationReader.readSchemaJson(shaclConfigurationFile);
      else
        schema = ConfigurationReader.readSchemaYaml(shaclConfigurationFile);
    } catch (IOException exception) {
      logger.severe("Error when the SHACL schema is initialized. " + exception.getLocalizedMessage());
      System.exit(0);
    }
    return schema;
  }

  /**
   * Instantiates a RuleCatalogue
   * @param schema
   * @param parameters
   * @return
   * @param <T>
   */
  public static <T extends Shacl4bibParameters> RuleCatalog setupRuleCatalog(
      SchemaConfiguration schema,
      T parameters) {
    return new RuleCatalog(schema.asSchema())
      .setOnlyIdInHeader(true)
      .setOutputType(parameters.getShaclOutputType());
  }

  public static Map<String, String> createRulePathMap(SchemaConfiguration schema) {
    Map<String, String> rulePathMap = new HashMap<>();
    for (DataElement dataElement : schema.asSchema().getPaths()) {
      for (Rule rule : dataElement.getRules()) {
        if (rule.getId() != null) {
          rulePathMap.put(rule.getId(), dataElement.getPath());
        }
      }
    }
    return rulePathMap;
  }
}
