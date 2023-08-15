package de.gwdg.metadataqa.marc.utils.pica.reader;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.cli.CliTestUtils;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PicaXmlReaderTest {

  @Test
  public void xml()  {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/k10plus.json"));
    MarcReader reader = null;
    try {
      reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_XML, CliTestUtils.getTestResource("picaxml/pica.xml"), null);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    assertNotNull(reader);
    assertEquals("PicaXmlReader", reader.getClass().getSimpleName());
    assertTrue(reader.hasNext());
    Record record = reader.next();
    assertEquals("org.marc4j.marc.impl.RecordImpl", record.getClass().getCanonicalName());
    assertEquals("de.gwdg.metadataqa.marc.utils.pica.PicaDataField", record.getDataFields().get(0).getClass().getCanonicalName());
    assertEquals("029F/01 $a@CDA Vrouwenberaad", record.getVariableField("029F").toString());
    BibliographicRecord marcRecord = MarcFactory.createPicaFromMarc4j(record, schema);
    // Map<String, List<String>> map = marcRecord.getKeyValuePairs(SolrFieldType.HUMAN, true, MarcVersion.MARC21);

    // PicaNormalizedReader reader = new PicaNormalizedReader(FileUtils.getPath("pica/pica-normalized.dat").toString());
    assertEquals("318482789", record.getControlNumber());
    assertEquals(20, record.getDataFields().size());
    assertEquals("001@", record.getDataFields().get(0).getTag());
    assertEquals("192,248", record.getDataFields().get(0).getSubfield('0').getData());
  }
}