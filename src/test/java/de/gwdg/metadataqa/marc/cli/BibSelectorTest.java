package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.cli.utils.BibSelector;
import de.gwdg.metadataqa.marc.cli.utils.BibSelectorFactory;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;
import de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag245;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BibSelectorTest {

  BibliographicRecord marcRecord;

  @Before
  public void setUp() throws Exception {
    marcRecord = new Marc21Record("u2407796");

    marcRecord.addDataField(
      new DataField(
        Tag245.getInstance(),
        "0", "0",
        "6", "880-01",
        "a", "iPhone the Bible wan jia sheng jing."
      )
    );
  }

  @Test // (expected = IllegalArgumentException.class)
  public void testConstructorNullArgument() {
    BibSelector selector = BibSelectorFactory.create(null);
    assertNull(selector);
  }

  @Test
  public void get_pica() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/schema/k10plus.json"));
    BibliographicRecord marcRecord = new PicaRecord("u2407796");
    marcRecord.addDataField(new DataField(schema.lookup("001B"), null, null, "0", "1999:02-05-18"));
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    List<XmlFieldInstance> results = selector.get("001B$0");
    assertEquals(1, results.size());
    assertEquals("1999:02-05-18", results.get(0).getValue());
  }

  @Test
  public void get_existing() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    List<XmlFieldInstance> results = selector.get("245$a");
    assertEquals(1, results.size());
    assertEquals("iPhone the Bible wan jia sheng jing.", results.get(0).getValue());
  }

  @Test
  public void get_nonexistentField() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    List<XmlFieldInstance> results = selector.get("100");
    assertTrue(results.isEmpty());
  }

  @Test
  public void read() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    assertNull(selector.read("245$a", null));
  }

  @Test
  public void testGet1() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    List<XmlFieldInstance> results = selector.get("100", "100", "100");
    assertNull(results);
  }

  @Test
  public void testGet2() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    List<XmlFieldInstance> results = selector.get("100", "100", "100", this.getClass());
    assertNull(results);
  }

  @Test
  public void getFragment_path() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    assertNull(selector.getFragment("245$a"));
  }

  @Test
  public void getFragment_3arguments() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    assertNull(selector.getFragment("245$a", "245$a", null));
  }

  @Test
  public void getRecordId() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    assertEquals("u2407796", selector.getRecordId());
  }

  @Test
  public void setRecordId() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    selector.setRecordId("xyz");
    assertEquals("u2407796", selector.getRecordId());
  }

  @Test
  public void getCache() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    assertNull(selector.getCache());
  }

  @Test
  public void getFragmentCache() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    assertNull(selector.getFragmentCache());
  }

  @Test
  public void getContent() {
    BibSelector selector = BibSelectorFactory.create(marcRecord);
    assertNull(selector.getContent());
  }
}
