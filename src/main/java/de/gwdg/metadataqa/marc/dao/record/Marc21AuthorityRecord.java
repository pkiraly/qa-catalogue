package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.analysis.shelfready.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.dao.MarcPositionalControlField;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Marc21AuthorityRecord extends Marc21Record {

  MarcPositionalControlField leader;

  public Marc21AuthorityRecord() {
    super();
  }

  public Marc21AuthorityRecord(String id) {
    super(id);
  }

  @Override
  public MarcPositionalControlField getControl008() {
    return control008;
  }

  public void setLeader(MarcPositionalControlField leader) {
    this.leader = leader;
  }

  public MarcPositionalControlField getLeader2() {
    return leader;
  }

  @Override
  public Map<ShelfReadyFieldsBooks, Map<String, List<String>>> getShelfReadyMap() {
    return Collections.emptyMap();
  }
}
