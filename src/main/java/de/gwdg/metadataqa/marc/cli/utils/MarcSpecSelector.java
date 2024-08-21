package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.json.DataElement;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarcSpecSelector extends BibSelector {
  private static Map<String, MarcSpec> marcSpecs = new HashMap<>();

  public MarcSpecSelector(BibliographicRecord record) {
    super(record);
  }

  @Override
  public List<XmlFieldInstance> get(String path) {
    return transformTags(extract(path));
  }

  public List<XmlFieldInstance> get(DataElement dataElement) {
    return get(dataElement.getPath());
  }

  public List<String> extract(String path) {
    return record.select(getMarcSpec(path));
  }

  private MarcSpec getMarcSpec(String path) {
    if (!marcSpecs.containsKey(path))
      marcSpecs.put(path, new MarcSpec(path));
    return marcSpecs.get(path);
  }
}
