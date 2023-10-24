package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class Tag650Test {
  @Test
  public void testDefinition() {
    Tag650 field = Tag650.getInstance();
    assertEquals("650", field.getTag());
    assertEquals("Subject Added Entry - Topical Term", field.getLabel());
    assertFalse(field.getSubfields().isEmpty());
    assertNotEquals(0, field.getSubfields().size());

    SubfieldDefinition a = field.getSubfield("a");
    assertNotNull("subfield should not be null", a);
    assertEquals("a", a.getCode());
    assertEquals("Topical term or geographic name entry element", a.getLabel());
    assertEquals(Cardinality.Nonrepeatable, a.getCardinality());
  }

  // #0$aAmish.
  @Test
  public void testSubfieldA() {
    DataField field = new DataField(Tag650.getInstance(), " ", "0",
        "a", "Amish.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(3, map.size());
    assertEquals("No information provided", map.get("Level of subject").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("Amish.", map.get("Topical term or geographic name entry element").get(0));
  }

  // 0$aVocal music$zFrance$y18th century.
  @Test
  public void testSubfieldZandY() {
    DataField field = new DataField(Tag650.getInstance(), " ", "0",
        "a", "Vocal music", "z", "France", "y", "18th century.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(5, map.size());
    assertEquals("No information provided", map.get("Level of subject").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("Vocal music", map.get("Topical term or geographic name entry element").get(0));
    assertEquals("France", map.get("Geographic subdivision").get(0));
    assertEquals("18th century.", map.get("Chronological subdivision").get(0));
  }

  // #0$aDentistry$vJuvenile films.
  @Test
  public void testSubfieldV() {
    DataField field = new DataField(Tag650.getInstance(), " ", "0",
        "a", "Dentistry", "v", "Juvenile films.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(4, map.size());
    assertEquals("No information provided", map.get("Level of subject").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("Dentistry", map.get("Topical term or geographic name entry element").get(0));
    assertEquals("Juvenile films.", map.get("Form subdivision").get(0));
  }

  // #0$aCaracas.$bBolivar Statue.
  @Test
  public void testSubfieldB() {
    DataField field = new DataField(Tag650.getInstance(), " ", "0",
        "a", "Caracas.", "b", "Bolivar Statue.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(4, map.size());
    assertEquals("No information provided", map.get("Level of subject").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("Caracas.", map.get("Topical term or geographic name entry element").get(0));
    assertEquals("Bolivar Statue.", map.get("Topical term following geographic name entry element").get(0));
  }

  // #0$aSeabiscuit (Race horse),$edepicted.
  @Test
  public void testSubfieldE() {
    DataField field = new DataField(Tag650.getInstance(), " ", "0",
        "a", "Seabiscuit (Race horse),", "e", "depicted.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(4, map.size());
    assertEquals("depicted.", map.get("Relator term").get(0));
  }

  // #0$aVomiting$xTreatment$vHandbooks, manuals, etc.
  @Test
  public void testSubfieldVandX() {
    DataField field = new DataField(Tag650.getInstance(), " ", "0",
        "a", "Vomiting", "x", "Treatment", "v", "Handbooks, manuals, etc.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(5, map.size());
    assertEquals("Treatment", map.get("General subdivision").get(0));
    assertEquals("Handbooks, manuals, etc.", map.get("Form subdivision").get(0));
  }

  // #0$aMusic$y500-1400.
  @Test
  public void testSubfieldY() {
    DataField field = new DataField(Tag650.getInstance(), " ", "0",
        "a", "Music", "y", "500-1400.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(4, map.size());
    assertEquals("500-1400.", map.get("Chronological subdivision").get(0));
  }

  // #7$aEducational buildings$zWashington (D.C.)$y1890-1910.$2lctgm
  @Test
  public void testSubfield2() {
    DataField field = new DataField(Tag650.getInstance(), " ", "7",
        "a", "Educational buildings", "z", "Washington (D.C.)", "y", "1890-1910.", "2", "lctgm");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(6, map.size());
    assertEquals("Source specified in subfield $2", map.get("Thesaurus").get(0));
    assertEquals("Thesaurus for graphic materials: TGM I, Subject terms", map.get("Source of heading or term").get(0));
  }

}