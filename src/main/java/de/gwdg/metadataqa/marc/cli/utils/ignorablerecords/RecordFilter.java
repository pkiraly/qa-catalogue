package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.MarcRecord;

import java.io.Serializable;

public interface RecordFilter extends Serializable {
  boolean isEmpty();
  boolean isAllowable(MarcRecord marcRecord);
}
