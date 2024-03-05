package de.gwdg.metadataqa.marc.utils.authority;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21AuthorityRecord;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.marcreader.schema.AvramMarc21SchemaReader;
import de.gwdg.metadataqa.marc.utils.marcreader.schema.Marc21SchemaManager;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AuthorityTest {

  @Test
  public void name() throws Exception {
    String file = TestUtils.getTestResource("authority/authority.sample.xml");
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.XML, file);
    assertTrue(reader.hasNext());
    Record mar4jRecord = reader.next();
    assertEquals("21498141", mar4jRecord.getControlNumber());

    AvramMarc21SchemaReader schemaReader = new AvramMarc21SchemaReader(TestUtils.getPathFromMain("marc/authority-schema.avram.json"));
    // YaleMarc21SchemaReader schemaReader = new YaleMarc21SchemaReader(TestUtils.getPathFromMain("marc/authority-schema.yale.json"));
    Marc21SchemaManager authorityManager = new Marc21SchemaManager(schemaReader.getMap());

    BibliographicRecord authorityRecord = MarcFactory.createAuthorityFromMarc4j(mar4jRecord, authorityManager, "#");
    assertNotNull(authorityRecord);
    assertEquals("21498141", authorityRecord.getId());
  }

  @Test
  public void all() throws Exception {
    AvramMarc21SchemaReader schemaReader = new AvramMarc21SchemaReader(TestUtils.getPathFromMain("marc/authority-schema.avram.json"));
    // YaleMarc21SchemaReader schemaReader = new YaleMarc21SchemaReader(TestUtils.getPathFromMain("marc/authority-schema.yale.json"));
    Marc21SchemaManager authorityManager = new Marc21SchemaManager(schemaReader.getMap());

    String file = TestUtils.getTestResource("authority/authority.sample.xml");
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.XML, file);
    List<String> ids = new ArrayList<>();
    while (reader.hasNext()) {
      Record mar4jRecord = reader.next();
      Marc21AuthorityRecord authorityRecord = (Marc21AuthorityRecord) MarcFactory.createAuthorityFromMarc4j(mar4jRecord, authorityManager, "#");
      ids.add(authorityRecord.getId());
      System.err.println(authorityRecord.getId());
      System.err.println("LDR:");
      for (ControlValue value : authorityRecord.getLeader2().getValuesList()) {
        System.err.println(String.format("%s: %s = %s", value.getDefinition().getLabel(), value.getValue(), value.resolve()));
      }
      if (authorityRecord.getControl008() != null) {
        System.err.println("008:");
        for (ControlValue value : authorityRecord.getControl008().getValuesList()) {
          System.err.println(String.format("%s: %s = %s", value.getDefinition().getLabel(), value.getValue(), value.resolve()));
        }
      }
      System.err.println("-----");
    }
    assertEquals(ids,
      List.of("21498141", "21498142", "21521386", "21543749", "21207974", "21099399", "21636316", "21636244", "21684204", "21709883"));
  }
}
