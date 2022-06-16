package de.gwdg.metadataqa.marc.cli.plugin;

import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.utils.TagHierarchy;

public interface CompletenessPlugin {
  String getDocumentType(MarcRecord marcRecord);
  TagHierarchy getTagHierarchy(String path);
}
