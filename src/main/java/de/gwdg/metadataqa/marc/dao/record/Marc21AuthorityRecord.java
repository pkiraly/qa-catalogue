package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.analysis.ThompsonTraillFields;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcPositionalControlField;

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
  public List<DataField> getAuthorityFields() {
    return null;
  }

  @Override
  public Map<DataField, AuthorityCategory> getAuthorityFieldsMap() {
    return null;
  }

  @Override
  public boolean isAuthorityTag(String tag) {
    return false;
  }

  @Override
  public boolean isSkippableAuthoritySubfield(String tag, String code) {
    return false;
  }

  @Override
  public boolean isSubjectTag(String tag) {
    return false;
  }

  @Override
  public boolean isSkippableSubjectSubfield(String tag, String code) {
    return false;
  }

  @Override
  public Map<ShelfReadyFieldsBooks, Map<String, List<String>>> getShelfReadyMap() {
    return null;
  }

  @Override
  public Map<ThompsonTraillFields, List<String>> getThompsonTraillTagsMap() {
    return null;
  }
}
