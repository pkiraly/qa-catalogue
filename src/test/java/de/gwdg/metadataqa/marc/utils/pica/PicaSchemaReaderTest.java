package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PicaSchemaReaderTest {

  @Test
  public void testFirst() {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(TestUtils.getPathFromMain("pica/avram-k10plus-title.json"));
    assertEquals(240, schema.size());
    PicaFieldDefinition field = schema.lookup("001A");
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
    PicaSchemaManager schema = PicaSchemaReader.createSchema(TestUtils.getPathFromMain("pica/avram-k10plus-title.json"));
    assertEquals(240, schema.size());
    PicaFieldDefinition field = schema.lookup("022A");
    assertEquals("022A", field.getTag());
    assertEquals("Werktitel und sonstige unterscheidende Merkmale des Werks", field.getLabel());
    assertEquals("2022-06-28T15:50:08", field.getModified());
    assertEquals("3210", field.getPica3());
    // assertEquals("00", field.getCounter().getStart());

    assertEquals(Cardinality.Repeatable, field.getCardinality());
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

    field = schema.lookup("022A/01");
    assertEquals("022A", field.getTag());
    assertEquals("01", field.getOccurrence());
  }

  @Test
  public void testCloning() {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(TestUtils.getPathFromMain("pica/avram-k10plus-title.json"));
    PicaFieldDefinition tag = schema.lookup("045B");
    assertNotNull(tag);
    assertEquals("045B", tag.getId());
    assertEquals("Allgemeine Systematik für Bibliotheken (ASB)", tag.getLabel());
  }
}
