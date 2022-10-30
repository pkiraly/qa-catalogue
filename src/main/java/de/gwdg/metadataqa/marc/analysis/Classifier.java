package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

public interface Classifier {
  String classify(BibliographicRecord marcRecord);
}
