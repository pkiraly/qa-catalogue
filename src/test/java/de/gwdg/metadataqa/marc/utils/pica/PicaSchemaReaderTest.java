package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.*;

public class PicaSchemaReaderTest {

  @Test
  public void test() {
    Map<String, PicaFieldDefinition> schema = PicaSchemaReader.create(getPath("pica/avram-k10plus.json"));
    assertEquals(434, schema.size());
    PicaFieldDefinition field = schema.get("001A");
    assertEquals("001A", field.getTag());
    assertEquals("Kennung und Datum der Ersterfassung", field.getLabel());
    assertEquals("2019-11-18T09:39:13", field.getModified());
    assertEquals("0200", field.getPica3());
    assertEquals(Cardinality.Nonrepeatable, field.getCardinality());
    assertEquals("https://format.k10plus.de/k10plushelp.pl?cmd=kat&katalog=Standard&val=0200", field.getDescriptionUrl());
  }

  private String getPath(String fileName) {
    return Paths.get("src/test/resources/" + fileName).toAbsolutePath().toString();
  }
}