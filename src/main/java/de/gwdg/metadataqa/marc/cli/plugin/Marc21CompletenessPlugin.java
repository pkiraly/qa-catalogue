package de.gwdg.metadataqa.marc.cli.plugin;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.utils.TagHierarchy;

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

  @Override
  public TagHierarchy getTagHierarchy(String path) {
    return TagHierarchy.createFromPath(path, parameters.getMarcVersion());
  }

  @Override
  public String getPackageName(DataField field) {
    return Utils.extractPackageName(field);
  }
}
