package de.gwdg.metadataqa.marc.analysis.thompsontraill;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class ThompsonTraillAnalysis {
  public final List<String> headers = new LinkedList<>();

  protected ThompsonTraillAnalysis() {
    for (ThompsonTraillFields field : ThompsonTraillFields.values()) {
      headers.add(field.getMachine());
    }
  }

  public abstract List<Integer> getScores(BibliographicRecord marcRecord);
  public abstract Map<ThompsonTraillFields, List<String>> getThompsonTraillTagsMap();

  public List<String> getHeader() {
    return headers;
  }

  protected boolean tagExists(BibliographicRecord marcRecord, String tag) {
    List<DataField> fields = marcRecord.getDatafield(tag);
    return (fields != null && !fields.isEmpty());
  }
}
