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

public class MarcSpec2ExtractorTest {
  @Test
  public void field_numeric() {
    MarcSpec2 spec = MarcSpec2Parser.parse("080");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    Object extracted = MarcSpec2Extractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    List<Object> fields1 = (List<Object>) extracted;
    assertEquals(1, fields1.size());
    assertEquals(DataField.class, fields1.get(0).getClass());
    List<DataField> fields = (List<DataField>) extracted;
    assertEquals(
      "DataField{080, ind1=' ', ind2=' ', subfields=[MarcSubfield{code='a', value='821.124'}]}",
      fields.get(0).toString());
  }

  @Test
  public void field_pattern() {
    MarcSpec2 spec = MarcSpec2Parser.parse("08.");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    Object extracted = MarcSpec2Extractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    List<Object> fields1 = (List<Object>) extracted;
    assertEquals(1, fields1.size());
    assertEquals(DataField.class, fields1.get(0).getClass());
    List<DataField> fields = (List<DataField>) extracted;
    assertEquals(
      "DataField{080, ind1=' ', ind2=' ', subfields=[MarcSubfield{code='a', value='821.124'}]}",
      fields.get(0).toString());
  }

  @Test
  public void indicator() {
    MarcSpec2 spec = MarcSpec2Parser.parse("080^1");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    Object extracted = MarcSpec2Extractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    List<Object> fields1 = (List<Object>) extracted;
    assertEquals(1, fields1.size());
    assertEquals(String.class, fields1.get(0).getClass());
    List<String> values = (List<String>) extracted;
    assertEquals(
      " ",
      StringUtils.join(values, ", "));
  }
}
