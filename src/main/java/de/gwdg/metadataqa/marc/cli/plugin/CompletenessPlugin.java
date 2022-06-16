package de.gwdg.metadataqa.marc.cli.plugin;

import de.gwdg.metadataqa.marc.dao.MarcRecord;

public interface CompletenessPlugin {
  String getDocumentType(MarcRecord marcRecord);
}
