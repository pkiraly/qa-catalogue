package de.gwdg.metadataqa.marc.cli.plugin;

import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;

public class CompletenessFactory {

  public static CompletenessPlugin create(CompletenessParameters parameters) {
    if (parameters.isMarc21()) {
      return new Marc21CompletenessPlugin(parameters);
    } else if (parameters.isPica()) {
      return new PicaCompletenessPlugin(parameters);
    }
    return null;
  }

}
