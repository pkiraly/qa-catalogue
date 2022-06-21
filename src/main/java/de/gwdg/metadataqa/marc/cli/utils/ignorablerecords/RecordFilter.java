package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.MarcRecord;

public interface RecordFilter {
  boolean isEmpty();
  boolean isAllowable(MarcRecord marcRecord);
}
