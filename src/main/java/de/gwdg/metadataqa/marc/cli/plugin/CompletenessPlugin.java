package de.gwdg.metadataqa.marc.cli.plugin;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.TagHierarchy;

import java.io.Serializable;

public interface CompletenessPlugin extends Serializable {
  String getDocumentType(BibliographicRecord marcRecord);
  TagHierarchy getTagHierarchy(String path);
  String getPackageName(DataField field);
}
