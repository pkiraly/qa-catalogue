package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.io.Serializable;

public interface RecordIgnorator extends Serializable {
  boolean isEmpty();
  boolean isIgnorable(BibliographicRecord marcRecord);
}
