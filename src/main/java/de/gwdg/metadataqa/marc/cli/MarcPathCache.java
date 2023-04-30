package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.model.pathcache.PathCache;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MarcPathCache implements PathCache {
  private final BibliographicRecord record;

  public MarcPathCache(BibliographicRecord record) {
    if (record != null)
      this.record = record;
    else {
      throw new IllegalArgumentException("Only BibliographicRecord object as Argument");
    }
  }

  @Override
  public List<XmlFieldInstance> get(String path) {
    List<String> tags = record.select(new MarcSpec(path));
    List<XmlFieldInstance> fieldList = new ArrayList<>();
    for (String tag : tags) {
      fieldList.add(new XmlFieldInstance(tag));
    }
    return fieldList;
  }

  @Override
  public Object read(String path, Object jsonFragment) {
    return null;
  }

  @Override
  public List get(String address, String path, Object jsonFragment) {
    return null;
  }

  @Override
  public List get(String address, String path, Object jsonFragment, Class clazz) {
    return null;
  }

  @Override
  public Object getFragment(String path) {
    return null;
  }

  @Override
  public Object getFragment(String address, String path, Object jsonFragment) {
    return null;
  }

  @Override
  public String getRecordId() {
    return null;
  }

  @Override
  public void setRecordId(String recordId) {

  }

  @Override
  public Map<String, List> getCache() {
    return null;
  }

  @Override
  public Map<String, Object> getFragmentCache() {
    return null;
  }

  @Override
  public String getContent() {
    return null;
  }
}
