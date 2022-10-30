package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.analysis.AuthorithyAnalyzer;
import de.gwdg.metadataqa.marc.analysis.AuthorityStatistics;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuthorityAnalysisTest {

  @Test
  public void pica() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/k10plus.json"));
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_PLAIN, CliTestUtils.getTestResource("pica/k10plus-sample.pica"), null);
    reader.hasNext();
    Record record = reader.next();
    BibliographicRecord marcRecord = MarcFactory.createPicaFromMarc4j(record, schema);
    assertEquals(SchemaType.PICA, marcRecord.getSchemaType());
    List<DataField> fields = marcRecord.getAuthorityFields();
    assertEquals(1, fields.size());
    assertEquals("029F", fields.get(0).getTag());
    assertEquals("Institut für Gewerbliche Wasserwirtschaft und Luftreinhaltung", fields.get(0).getSubfield("A").get(0).getValue());
    for (MarcSubfield subfield : fields.get(0).getSubfields()) {
      System.err.println(subfield);
    }
    AuthorityStatistics statistics = new AuthorityStatistics();
    var analyzer = new AuthorithyAnalyzer(marcRecord, statistics);
    int count = analyzer.process();
    System.err.println(count);
    System.err.println(statistics.getSubfields());

    // T - Feldzuordnung - field mapping
    // U - Schriftcode - writing code
    // L - Sprachencode - language code
    // 9 - PPN - PPN
    // V - undef
    // 7 - Vorläufiger Link - Tentative link
    // 3 - undef
    // A - a - Bevorzugter Name (nur in Importdaten) - preferred name
    // b - Untergeordnete Einheit - Untergeordnete Einheit
    // n - Zählung - count
  }

}