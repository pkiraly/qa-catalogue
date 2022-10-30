package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.io.Serializable;

public interface RecordFilter extends Serializable {
  boolean isEmpty();
  boolean isAllowable(BibliographicRecord marcRecord);
}
