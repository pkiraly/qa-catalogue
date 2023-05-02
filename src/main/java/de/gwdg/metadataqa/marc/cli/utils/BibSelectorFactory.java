package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.schema.Format;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;

public class BibSelectorFactory {
  public static BibSelector create(BibliographicRecord marcRecord) {
    BibSelector selector = null;
    if (marcRecord instanceof Marc21Record) {
      selector = new MarcSpecSelector(marcRecord);
    } else if (marcRecord instanceof PicaRecord) {
      selector = new PicaPathSelector(marcRecord);
    }
    return selector;
  }

  public static BibSelector create(String format, BibliographicRecord marcRecord) {
    BibSelector selector = null;
    if (format.equals(Format.MARC.name())) {
      selector = new MarcSpecSelector(marcRecord);
    } else if (format.equals(Format.PICA.name())) {
      selector = new PicaPathSelector(marcRecord);
    }
    return selector;
  }
}
