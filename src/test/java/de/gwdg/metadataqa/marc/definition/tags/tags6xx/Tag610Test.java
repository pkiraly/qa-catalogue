package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import org.junit.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class Tag610Test {
  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testDefinition() {
    Tag610 field = Tag610.getInstance();
    assertEquals("610", field.getTag());
    assertEquals("Subject Added Entry - Corporate Name", field.getLabel());
    assertFalse(field.getSubfields().isEmpty());
    assertNotEquals(0, field.getSubfields().size());

    SubfieldDefinition a = field.getSubfield("a");
    assertNotNull("subfield should not be null", a);
    assertEquals("a", a.getCode());
    assertEquals("Corporate name or jurisdiction name as entry element", a.getLabel());
    assertEquals(Cardinality.Nonrepeatable, a.getCardinality());
  }

  /*
  610   20$aTitanic (Steamship)
   */

  @Test
  public void testTitanic() {
    DataField field = new DataField(Tag610.getInstance(), "2", "0", "a", "Titanic (Steamship)");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(3, map.size());
    assertEquals("Name in direct order", map.get("Type of corporate name entry element").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("Titanic (Steamship)", map.get("Corporate name or jurisdiction name as entry element").get(0));
  }

  @Test
  public void testCatholicChurch() {
    DataField field = new DataField(Tag610.getInstance(), "2", "0",
        "a", "Catholic Church.",
        "b", "Province of Baltimore (Md.)");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(4, map.size());
    assertEquals("Name in direct order", map.get("Type of corporate name entry element").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("Catholic Church.", map.get("Corporate name or jurisdiction name as entry element").get(0));
    assertEquals("Province of Baltimore (Md.)", map.get("Subordinate unit").get(0));
  }

  @Test
  public void testUnitedNations() {
    DataField field = new DataField(Tag610.getInstance(), "2", "0",
        "a", "United Nations",
        "z", "Africa.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(4, map.size());
    assertEquals("Name in direct order", map.get("Type of corporate name entry element").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("United Nations", map.get("Corporate name or jurisdiction name as entry element").get(0));
    assertEquals("Africa.", map.get("Geographic subdivision").get(0));
  }

  @Test
  public void testBamberg() {
    DataField field = new DataField(Tag610.getInstance(), "1", "0",
        "a", "Bamberg (Ecclesiastical principality)");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(3, map.size());
    assertEquals("Jurisdiction name", map.get("Type of corporate name entry element").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("Bamberg (Ecclesiastical principality)", map.get("Corporate name or jurisdiction name as entry element").get(0));
  }

  // 20$aUnited Nations$xEconomic assistance$vPeriodicals.
  @Test
  public void testUnitedNationsx() {
    DataField field = new DataField(Tag610.getInstance(), "2", "0",
        "a", "United Nations", "x", "Economic assistance", "v", "Periodicals.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(5, map.size());
    assertEquals("Name in direct order", map.get("Type of corporate name entry element").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("United Nations", map.get("Corporate name or jurisdiction name as entry element").get(0));
    assertEquals("Economic assistance", map.get("General subdivision").get(0));
    assertEquals("Periodicals.", map.get("Form subdivision").get(0));
  }

  // 26$aÉglise catholique$xHistoire$y20e siècle.
  @Test
  public void testEgliseCatholique() {
    DataField field = new DataField(Tag610.getInstance(), "2", "6",
        "a", "Église catholique", "x", "Histoire", "y", "20e siècle.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(5, map.size());
    assertEquals("Name in direct order", map.get("Type of corporate name entry element").get(0));
    assertEquals("Répertoire de vedettes-matière", map.get("Thesaurus").get(0));
    assertEquals("Église catholique", map.get("Corporate name or jurisdiction name as entry element").get(0));
    assertEquals("Histoire", map.get("General subdivision").get(0));
    assertEquals("20e siècle.", map.get("Chronological subdivision").get(0));
  }

  // 10$aFrance.$tBulletin officiel du régistre du commerce et du régistre des métiers.
  @Test
  public void testFrance() {
    DataField field = new DataField(Tag610.getInstance(), "1", "0",
        "a", "France.", "t", "Bulletin officiel du régistre du commerce et du régistre des métiers.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(4, map.size());
    assertEquals("Jurisdiction name", map.get("Type of corporate name entry element").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("France.", map.get("Corporate name or jurisdiction name as entry element").get(0));
    assertEquals("Bulletin officiel du régistre du commerce et du régistre des métiers.",
        map.get("Title of a work").get(0));
  }

  // 10$aUnited States.$bSupreme Court,$edepicted.
  @Test
  public void testSupremeCourt() {
    DataField field = new DataField(Tag610.getInstance(), "1", "0",
        "a", "United States.", "b", "Supreme Court,", "e", "depicted.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(5, map.size());
    assertEquals("Jurisdiction name", map.get("Type of corporate name entry element").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("United States.", map.get("Corporate name or jurisdiction name as entry element").get(0));
    assertEquals("Supreme Court,", map.get("Subordinate unit").get(0));
    assertEquals("depicted.", map.get("Relator term").get(0));
  }

  // 10$aUnited States.$bArmy.$bCavalry, 7th.$bCompany E,$edepicted.
  @Test
  public void testArmy() {
    DataField field = new DataField(Tag610.getInstance(), "1", "0",
        "a", "United States.", "b", "Army.", "b", "Cavalry, 7th.", "b", "Company E,", "e", "depicted.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(5, map.size());
    assertEquals("Jurisdiction name", map.get("Type of corporate name entry element").get(0));
    assertEquals("Library of Congress Subject Headings", map.get("Thesaurus").get(0));
    assertEquals("United States.", map.get("Corporate name or jurisdiction name as entry element").get(0));
    assertEquals("Army.", map.get("Subordinate unit").get(0));
    assertEquals("Cavalry, 7th.", map.get("Subordinate unit").get(1));
    assertEquals("Company E,", map.get("Subordinate unit").get(2));
    assertEquals("depicted.", map.get("Relator term").get(0));
  }

}