package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.*;

public class NetworkAnalyzer {

  private final BibliographicRecord marcRecord;
  private final Set<DataField> collector;

  public NetworkAnalyzer(BibliographicRecord marcRecord) {
    this.marcRecord = marcRecord;
    collector = new HashSet<>();
  }

  public Set<DataField> process(int recordNumber) {
    for (DataField field : marcRecord.getAuthorityFields()) {
      register(field, recordNumber);
    }

    for (DataField field : marcRecord.getSubjects()) {
      register(field, recordNumber);
    }

    return collector;
  }

  private void register(DataField field, int recordNumber) {
    if (!collector.contains(field))
      collector.add(field);
  }
}
