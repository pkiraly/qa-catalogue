package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BibSelectorFactoryTest {

  @Test
  public void create_marc() {
    BibliographicRecord bibRecord = new Marc21Record("123");
    BibSelector selector = BibSelectorFactory.create(bibRecord);
    assertTrue(selector instanceof MarcSpecSelector);
  }

  @Test
  public void create_pica() {
    BibliographicRecord bibRecord = new PicaRecord("123");
    BibSelector selector = BibSelectorFactory.create(bibRecord);
    assertTrue(selector instanceof PicaPathSelector);
  }
}