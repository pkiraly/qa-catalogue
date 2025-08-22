package de.gwdg.metadataqa.marc.cli.plugin;

import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.utils.unimarc.UnimarcSchemaManager;
import de.gwdg.metadataqa.marc.utils.unimarc.UnimarcSchemaReader;

public class CompletenessFactory {

  private CompletenessFactory() {}

  public static CompletenessPlugin create(CompletenessParameters parameters) {
    if (parameters.isMarc21()) {
      return new Marc21CompletenessPlugin(parameters);
    }

    if (parameters.isPica()) {
      return new PicaCompletenessPlugin(parameters);
    }

    if (parameters.isUnimarc()) {
      // Create the UnimarcSchemaManager
      UnimarcSchemaReader unimarcSchemaReader = new UnimarcSchemaReader();
      String schemaFilePath = parameters.getPicaSchemaFile();
      if (schemaFilePath == null) {
        schemaFilePath = "src/main/resources/unimarc/avram-unimarc.json";
      }
      UnimarcSchemaManager unimarcSchema = unimarcSchemaReader.createSchema(schemaFilePath);
      return new UnimarcCompletenessPlugin(parameters, unimarcSchema);
    }
    return null;
  }

}
