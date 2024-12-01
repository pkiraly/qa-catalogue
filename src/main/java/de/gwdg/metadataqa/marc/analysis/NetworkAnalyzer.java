package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.HashSet;
import java.util.Set;


public class NetworkAnalyzer {

  private final BibliographicRecord marcRecord;
  private final Set<DataField> collector;

  public NetworkAnalyzer(BibliographicRecord marcRecord) {
    this.marcRecord = marcRecord;
    collector = new HashSet<>();
  }

  public Set<DataField> process() {
    for (DataField field : marcRecord.getAuthorityFields()) {
      register(field);
    }

    for (DataField field : marcRecord.getSubjects()) {
      register(field);
    }

    return collector;
  }

  private void register(DataField field) {
    collector.add(field);
  }
}
