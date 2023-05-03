package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.model.selector.Selector;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract public class BibSelector implements Selector {
  protected final BibliographicRecord record;
  private boolean isMarc21 = false;
  private boolean isPica = false;

  public BibSelector(BibliographicRecord record) {
    if (record != null) {
      this.record = record;
      if (record instanceof Marc21Record) {
        isMarc21 = true;
      } else if (record instanceof PicaRecord) {
        isPica = true;
      }
    } else {
      throw new IllegalArgumentException("Only BibliographicRecord object as Argument");
    }
  }

  protected List<XmlFieldInstance> transformTags(List<String> tags) {
    List<XmlFieldInstance> fieldList = new ArrayList<>();
    if (tags != null) {
      for (String tag : tags) {
        fieldList.add(new XmlFieldInstance(tag));
      }
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
    return record.getId();
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
