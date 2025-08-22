package de.gwdg.metadataqa.marc.utils.marcspec;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag080;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MarcSpecExtractorTest {
  @Test
  public void field_numeric() {
    MarcSpec spec = MarcSpecParser.parse("080");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    List<DataField> extracted = (List<DataField>) MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    assertEquals(1, extracted.size());
    assertEquals(DataField.class, extracted.get(0).getClass());
    assertEquals("821.124", extracted.get(0).getSubfield("a").get(0).getValue());
  }

  @Test
  public void field_pattern() {
    MarcSpec spec = MarcSpecParser.parse("08.");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    Object extracted = MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    List<DataField> fields1 = (List<DataField>) extracted;
    assertEquals(1, fields1.size());
    assertEquals(DataField.class, fields1.get(0).getClass());
    List<DataField> fields = (List<DataField>) extracted;
    assertEquals("821.124", fields.get(0).getSubfield("a").get(0).getValue());
  }

  @Test
  public void indicator() {
    MarcSpec spec = MarcSpecParser.parse("080^1");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    Object extracted = MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    List<Object> fields1 = (List<Object>) extracted;
    assertEquals(1, fields1.size());
    assertEquals(String.class, fields1.get(0).getClass());
    List<String> values = (List<String>) extracted;
    assertEquals(" ", StringUtils.join(values, ", "));
  }
}
