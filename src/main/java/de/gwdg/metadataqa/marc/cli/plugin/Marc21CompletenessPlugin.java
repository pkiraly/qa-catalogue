package de.gwdg.metadataqa.marc.cli.plugin;

import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.dao.MarcRecord;

import java.util.regex.Pattern;

public class Marc21CompletenessPlugin implements CompletenessPlugin {
  private final CompletenessParameters parameters;

  public Marc21CompletenessPlugin(CompletenessParameters parameters) {
    this.parameters = parameters;
  }

  @Override
  public String getDocumentType(MarcRecord marcRecord) {
    return marcRecord.getType().getValue();
  }
}
