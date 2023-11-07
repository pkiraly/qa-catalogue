package de.gwdg.metadataqa.marc.utils.pica.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.cli.CliTestUtils;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.junit.Test;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PicaNormalizedReaderTest {

  @Test
  public void constructor() throws IOException, URISyntaxException {
    PicaNormalizedReader reader = new PicaNormalizedReader(FileUtils.getPath("pica/pica-normalized.dat").toString());
    Record record = null;
    if (reader.hasNext())
      record = reader.next();
    assertEquals("010000011", record.getControlNumber());
    assertEquals(37, record.getDataFields().size());
    assertEquals("001A", record.getDataFields().get(0).getTag());
    assertEquals("2000:06-11-86", record.getDataFields().get(0).getSubfield('0').getData());
  }

  @Test
  public void lineWith0xC285() throws IOException, URISyntaxException {
    Path recordsFile = FileUtils.getPath("pica/pica-line-with-0xC285.dat");
    try (BufferedReader br = new BufferedReader(new FileReader(recordsFile.toString()))) {
      String line;
      System.err.println("\u0085");
      while ((line = br.readLine()) != null) {
        System.err.println(line);
        System.err.println(line.contains("\uC285"));
        System.err.println(line.contains("\u00C2"));
        System.err.println(line.contains("\u0085"));
        System.err.println(line.replace("^.*\u0085", ""));
        /*
        for (Byte b : line.getBytes("UTF8")) {
          byte[] byteArray = new byte[] {b};
          System.err.println(b + " " + new String(new byte[]{b}));
        }
         */
          // byte[] bytes = new byte[] { b };


        // System.err.println(line.indexOf("\r"));
      }
    }
    // byte[] byteArray = new byte[] {b};
    String key = new String(new byte[]{-62, -123}, StandardCharsets.UTF_8);
    System.err.println(key);
  }

  @Test
  public void test045T_mar4j() throws IOException, URISyntaxException {
    PicaNormalizedReader reader = new PicaNormalizedReader(FileUtils.getPath("pica/045T.pica").toString());
    Record record = null;
    if (reader.hasNext())
      record = reader.next();
    assertEquals("010705201", record.getControlNumber());
    for (DataField dataField : record.getDataFields()) {
      if (dataField.getTag().equals("045T")) {
        assertEquals(2, dataField.getSubfields('k').size());
        assertEquals("Psychologie", dataField.getSubfields('k').get(0).getData());
        assertEquals("Sozialpsychologie", dataField.getSubfields('k').get(1).getData());
      }
    }
  }

  @Test
  public void test045T_picaRecord() throws IOException, URISyntaxException {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/schema/k10plus.json"));
    PicaNormalizedReader reader = new PicaNormalizedReader(FileUtils.getPath("pica/045T.pica").toString());
    Record record = null;
    if (reader.hasNext())
      record = reader.next();
    BibliographicRecord bibRecord = MarcFactory.createPicaFromMarc4j(record, schema);

    assertEquals("010705201", bibRecord.getId());
    List<de.gwdg.metadataqa.marc.dao.DataField> tags = bibRecord.getDatafield("045T");
    assertEquals(1, tags.size());
    de.gwdg.metadataqa.marc.dao.DataField tag = tags.get(0);
    assertEquals(2, tag.getSubfield("k").size());
    assertEquals("Psychologie", tag.getSubfield("k").get(0).getValue());
    assertEquals("Sozialpsychologie", tag.getSubfield("k").get(1).getValue());
  }

  @Test
  public void test045T_asJson() throws IOException, URISyntaxException {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/schema/k10plus.json"));
    PicaNormalizedReader reader = new PicaNormalizedReader(FileUtils.getPath("pica/045T.pica").toString());
    Record record = null;
    if (reader.hasNext())
      record = reader.next();
    BibliographicRecord bibRecord = MarcFactory.createPicaFromMarc4j(record, schema);

    String json = bibRecord.asJson();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> configuration = mapper.readValue(json, new TypeReference<>(){});
    assertNotNull(configuration.get("045T"));
    assertTrue(configuration.get("045T") instanceof ArrayList);
    assertEquals(1, ((List) configuration.get("045T")).size());
    assertTrue(((List)configuration.get("045T")).get(0) instanceof Map);
    Map<String, Object> tag = (Map) ((List) configuration.get("045T")).get(0);
    Map<String, Object> subfields = (Map) tag.get("subfields");
    assertTrue(subfields.get("k") instanceof List);
    assertEquals("Psychologie", ((List<?>) subfields.get("k")).get(0));
    assertEquals("Sozialpsychologie", ((List<?>) subfields.get("k")).get(1));
  }

  @Test
  public void test045R_asJson() throws IOException, URISyntaxException {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/schema/k10plus.json"));
    PicaNormalizedReader reader = new PicaNormalizedReader(FileUtils.getPath("pica/045R.pica").toString());
    Record record = null;
    if (reader.hasNext())
      record = reader.next();
    BibliographicRecord bibRecord = MarcFactory.createPicaFromMarc4j(record, schema);

    for (de.gwdg.metadataqa.marc.dao.DataField field : bibRecord.getDatafields()) {
      System.err.println(field.getTagWithOccurrence());
    }


    /*
    List<de.gwdg.metadataqa.marc.dao.DataField> tags = bibRecord.getDatafield("045R");
    assertEquals(1, tags.size());
    de.gwdg.metadataqa.marc.dao.DataField tag = tags.get(0);
    assertEquals(2, tag.getSubfield("k").size());
    assertEquals("Psychologie", tag.getSubfield("k").get(0).getValue());
    assertEquals("Sozialpsychologie", tag.getSubfield("k").get(1).getValue());

    String json = bibRecord.asJson();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> configuration = mapper.readValue(json, new TypeReference<>(){});
    assertNotNull(configuration.get("045T"));
    assertTrue(configuration.get("045T") instanceof ArrayList);
    assertEquals(1, ((List) configuration.get("045T")).size());
    assertTrue(((List)configuration.get("045T")).get(0) instanceof Map);
    Map<String, Object> tag = (Map) ((List) configuration.get("045T")).get(0);
    Map<String, Object> subfields = (Map) tag.get("subfields");
    assertTrue(subfields.get("k") instanceof List);
    assertEquals("Psychologie", ((List<?>) subfields.get("k")).get(0));
    assertEquals("Sozialpsychologie", ((List<?>) subfields.get("k")).get(1));
    */
  }
}