package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.utils.SubfieldParser;
import org.junit.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Examples to test:
 * http://www.loc.gov/marc/bibliographic/bd260.html
 */
public class Tag260Test {
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
    Tag260 field = Tag260.getInstance();
    assertEquals("260", field.getTag());
    assertEquals("Publication, Distribution, etc. (Imprint)", field.getLabel());
    assertEquals(Cardinality.Repeatable, field.getCardinality());
    assertFalse(field.getSubfields().isEmpty());
    assertNotEquals(0, field.getSubfields().size());

    // System.err.println(field.getSubfieldList());
    SubfieldDefinition a = field.getSubfield("a");
    assertNotNull("subfield should not be null", a);
    assertEquals("a", a.getCode());
    assertEquals("Place of publication, distribution, etc.", a.getLabel());
    assertEquals(Cardinality.Repeatable, a.getCardinality());
  }

  // ##$aParis :$bGauthier-Villars ;$aChicago :$bUniversity of Chicago Press,$c1955.
  @Test
  public void testIndicator1AsSpace() {
    DataField field = SubfieldParser.parseField(Tag260.getInstance(), "  $aParis :$bGauthier-Villars ;$aChicago :$bUniversity of Chicago Press,$c1955.");
    assertEquals(" ", field.getInd1());
    assertEquals(" ", field.getInd2());
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(4, map.size());
    assertEquals("Not applicable/No information provided/Earliest available publisher", map.get("Sequence of publishing statements").get(0));
  }

  // 2#$31980-May 1993$aLondon :$bVogue
  @Test
  public void testIndicator1As2() {
    DataField field = SubfieldParser.parseField(Tag260.getInstance(), "2 $31980-May 1993$aLondon :$bVogue");
    assertEquals("2", field.getInd1());
    assertEquals(" ", field.getInd2());
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(4, map.size());
    assertEquals("Intervening publisher", map.get("Sequence of publishing statements").get(0));
  }

  // 3#$31998-$aWashington, D.C. :$bNational Agriculture Library :$bFor sale by the Supt. of Docs., U.S. G.P.O.
  @Test
  public void testIndicator1As3() {
    DataField field = SubfieldParser.parseField(Tag260.getInstance(), "3 $31998-$aWashington, D.C. :$bNational Agriculture Library :$bFor sale by the Supt. of Docs., U.S. G.P.O.");
    assertEquals("3", field.getInd1());
    assertEquals(" ", field.getInd2());
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals(4, map.size());
    assertEquals("Current/latest publisher", map.get("Sequence of publishing statements").get(0));
  }

  // $a - Place of publication, distribution, etc.
  // ##$aNew York, N.Y. :$bElsevier,$c1984.
  @Test
  public void testSubfieldA() {
    DataField field = SubfieldParser.parseField(Tag260.getInstance(), "  $aNew York, N.Y. :$bElsevier,$c1984.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals("New York, N.Y. :", map.get("Place of publication, distribution, etc.").get(0));
  }

  // $b - Name of publisher, distributor, etc.
  // ##$a[New York] :$bAmerican Statistical Association,$c1975.
  @Test
  public void testSubfieldB() {
    DataField field = SubfieldParser.parseField(Tag260.getInstance(), "  $a[New York] :$bAmerican Statistical Association,$c1975.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals("American Statistical Association,", map.get("Name of publisher, distributor, etc.").get(0));
  }

  // $c - Date of publication, distribution, etc.
  // ##$aNew York :$bXerox Films,$c1973.
  @Test
  public void testSubfieldC() {
    DataField field = SubfieldParser.parseField(Tag260.getInstance(), "##$aNew York :$bXerox Films,$c1973.");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals("1973.", map.get("Date of publication, distribution, etc.").get(0));
  }
///
  // $e - Place of manufacture
  // ##$aNew York :$bE.P. Dutton,$c1980$e(Moscow :$fRussky Yazyk)
  @Test
  public void testSubfieldE() {
    DataField field = SubfieldParser.parseField(Tag260.getInstance(), "  $aNew York :$bE.P. Dutton,$c1980$e(Moscow :$fRussky Yazyk)");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals("(Moscow :", map.get("Place of manufacture").get(0));
  }

  // $f - Manufacturer
  // ##$aNew York :$bPublished by W. Schaus,$cc1860$e(Boston :$fPrinted at J.H. Bufford's)
  @Test
  public void testSubfieldF() {
    DataField field = SubfieldParser.parseField(Tag260.getInstance(), "  $aNew York :$bPublished by W. Schaus,$cc1860$e(Boston :$fPrinted at J.H. Bufford's)");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals("Printed at J.H. Bufford's)", map.get("Manufacturer").get(0));
  }

  // $g - Date of manufacture
  // ##$aHarmondsworth :$bPenguin,$c1949$g(1963 printing)
  @Test
  public void testSubfieldG() {
    DataField field = SubfieldParser.parseField(Tag260.getInstance(), "  $aHarmondsworth :$bPenguin,$c1949$g(1963 printing)");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals("(1963 printing)", map.get("Date of manufacture").get(0));
  }

  // $3 - Materials specified
  // 2#$31980-May 1993$aLondon :$bVogue
  @Test
  public void testSubfield3() {
    DataField field = SubfieldParser.parseField(Tag260.getInstance(), "2 $31980-May 1993$aLondon :$bVogue");
    Map<String, List<String>> map = field.getHumanReadableMap();
    assertEquals("1980-May 1993", map.get("Materials specified").get(0));
  }
}