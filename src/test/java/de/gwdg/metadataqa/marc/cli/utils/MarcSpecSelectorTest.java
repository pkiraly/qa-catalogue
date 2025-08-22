package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.UnimarcRecord;
import de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag245;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarcSpecSelectorTest {

  BibliographicRecord marcRecord;
  MarcSpecSelector selector;

  private void setUp_marc21() {
    marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(
      Tag245.getInstance(),
      "0", "0",
      "a", "Mario und der Zauberer"
    ));
    selector = new MarcSpecSelector(marcRecord);
  }

  private void setUp_unimarc() {
    marcRecord = new UnimarcRecord("u2407796");
    marcRecord.addDataField(new DataField(
      Tag245.getInstance(),
      "0", "0",
      "a", "Mario und der Zauberer"
    ));
    selector = new MarcSpecSelector(marcRecord);
  }

  @Test
  public void get_marc21() {
    setUp_marc21();
    XmlFieldInstance fieldInstance = selector.get("245$a").get(0);
    assertEquals("Mario und der Zauberer", fieldInstance.getValue());
    assertNull(fieldInstance.getLanguage());
  }

  @Test
  public void extract_marc21() {
    setUp_marc21();
    assertEquals("Mario und der Zauberer", selector.extract("245$a").get(0));
  }

  @Test
  public void get_unimarc() {
    setUp_unimarc();
    XmlFieldInstance fieldInstance = selector.get("245$a").get(0);
    assertEquals("Mario und der Zauberer", fieldInstance.getValue());
    assertNull(fieldInstance.getLanguage());
  }

  @Test
  public void extract_unimarc() {
    setUp_unimarc();
    assertEquals("Mario und der Zauberer", selector.extract("245$a").get(0));
  }
}