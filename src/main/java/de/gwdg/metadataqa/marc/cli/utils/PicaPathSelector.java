package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.json.DataElement;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaSpec;

import java.util.List;

public class PicaPathSelector extends BibSelector {
  public PicaPathSelector(BibliographicRecord bibliographicRecord) {
    super(bibliographicRecord);
  }

  @Override
  public List<XmlFieldInstance> get(String path) {
    return transformTags(extract(path));
  }

  public List<XmlFieldInstance> get(DataElement dataElement) {
    return get(dataElement.getPath());
  }

  public List<String> extract(String path) {
    return record.select(new PicaSpec(path));
  }

}
