package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.dao.MarcRecord;

public interface Classifier {
  String classify(MarcRecord marcRecord);
}
