package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.model.pathcache.PathCache;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPathParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BibPathCache implements PathCache {
  private final BibliographicRecord record;
  private static Map<String, MarcSpec> marcSpecs = new HashMap<>();
  private static Map<String, PicaPath> picaSpecs = new HashMap<>();
  private boolean isMarc21 = false;
  private boolean isPica = false;

  public BibPathCache(BibliographicRecord record) {
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

  @Override
  public List<XmlFieldInstance> get(String path) {
    List<String> tags = null;
    if (isMarc21)
      tags = record.select(getMarcSpec(path));
    else if (isPica) {
      tags = record.select(getPicaSpec(path));
    }
    List<XmlFieldInstance> fieldList = new ArrayList<>();
    if (tags != null) {
      for (String tag : tags) {
        fieldList.add(new XmlFieldInstance(tag));
      }
    }
    return fieldList;
  }

  private MarcSpec getMarcSpec(String path) {
    if (!marcSpecs.containsKey(path))
      marcSpecs.put(path, new MarcSpec(path));
    return marcSpecs.get(path);
  }

  private PicaPath getPicaSpec(String path) {
    if (!picaSpecs.containsKey(path))
      picaSpecs.put(path, PicaPathParser.parse(path));
    return picaSpecs.get(path);
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
