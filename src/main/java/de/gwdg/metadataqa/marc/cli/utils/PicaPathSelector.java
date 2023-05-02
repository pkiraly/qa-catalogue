package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPathParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PicaPathSelector extends BibSelector {
  private static Map<String, PicaPath> picaSpecs = new HashMap<>();

  public PicaPathSelector(BibliographicRecord record) {
    super(record);
  }

  @Override
  public List<XmlFieldInstance> get(String path) {
    return transformTags(extract(path));
  }

  public List<String> extract(String path) {
    return record.select(getPicaSpec(path));
  }

  private PicaPath getPicaSpec(String path) {
    if (!picaSpecs.containsKey(path))
      picaSpecs.put(path, PicaPathParser.parse(path));
    return picaSpecs.get(path);
  }

}
