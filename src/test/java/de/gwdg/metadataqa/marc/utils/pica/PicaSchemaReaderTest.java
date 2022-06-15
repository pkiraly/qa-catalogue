package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.*;

public class PicaSchemaReaderTest {

  @Test
  public void testFirst() {
    Map<String, PicaFieldDefinition> schema = PicaSchemaReader.create(getPath("pica/avram-k10plus.json"));
    assertEquals(434, schema.size());
    PicaFieldDefinition field = schema.get("001A");
    assertEquals("001A", field.getTag());
    assertEquals("Kennung und Datum der Ersterfassung", field.getLabel());
    assertEquals("2019-11-18T09:39:13", field.getModified());
    assertEquals("0200", field.getPica3());
    assertEquals(Cardinality.Nonrepeatable, field.getCardinality());
    assertEquals("https://format.k10plus.de/k10plushelp.pl?cmd=kat&katalog=Standard&val=0200", field.getDescriptionUrl());
    assertNotNull(field.getSubfields());
    assertEquals(1, field.getSubfields().size());
    assertEquals("0", field.getSubfields().get(0).getCode());
    assertEquals("0", field.getSubfield("0").getCode());
    assertEquals("Quelle und Datum (getrennt durch \":\")", field.getSubfield("0").getLabel());
    assertEquals(Cardinality.Nonrepeatable, field.getSubfield("0").getCardinality());
    // TODO: getModified()
    // assertEquals("Quelle und Datum (getrennt durch \":\")", field.getSubfield("0").getModified());
    // TODO: getPica3()
    // assertEquals("Quelle und Datum (getrennt durch \":\")", field.getSubfield("0").getPica3());
  }

  @Test
  public void testOneWithPercent() {
    Map<String, PicaFieldDefinition> schema = PicaSchemaReader.create(getPath("pica/avram-k10plus.json"));
    assertEquals(434, schema.size());
    PicaFieldDefinition field = schema.get("022A/00");
    assertEquals("022A/00", field.getTag());
    assertEquals("Werktitel und sonstige unterscheidende Merkmale des Werks", field.getLabel());
    assertEquals("2022-04-27T14:02:55", field.getModified());
    assertEquals("3210", field.getPica3());
    assertEquals("00", field.getOccurence());

    assertEquals(Cardinality.Nonrepeatable, field.getCardinality());
    assertEquals("https://format.k10plus.de/k10plushelp.pl?cmd=kat&katalog=Standard&val=3210", field.getDescriptionUrl());
    assertNotNull(field.getSubfields());
    assertEquals(17, field.getSubfields().size());
    assertEquals("7", field.getSubfield("7").getCode());
    assertEquals("Vorläufiger Link", field.getSubfield("7").getLabel());
    assertEquals(Cardinality.Nonrepeatable, field.getSubfield("7").getCardinality());
    // TODO: getModified()
    // assertEquals("Quelle und Datum (getrennt durch \":\")", field.getSubfield("0").getModified());
    // TODO: getPica3()
    // assertEquals("Quelle und Datum (getrennt durch \":\")", field.getSubfield("0").getPica3());
  }

  private String getPath(String fileName) {
    return Paths.get("src/test/resources/" + fileName).toAbsolutePath().toString();
  }
}