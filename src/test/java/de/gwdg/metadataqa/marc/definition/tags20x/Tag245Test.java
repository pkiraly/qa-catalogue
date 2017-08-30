package de.gwdg.metadataqa.marc.definition.tags20x;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.tags3xx.Tag300;
import de.gwdg.metadataqa.marc.utils.SubfieldParser;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Examples to test:
 * http://www.loc.gov/marc/bibliographic/bd245.html
 */
public class Tag245Test {
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
		Tag245 field = Tag245.getInstance();
		assertEquals("245", field.getTag());
		assertEquals("Title Statement", field.getLabel());
		assertEquals(Cardinality.Nonrepeatable, field.getCardinality());
		assertFalse(field.getSubfields().isEmpty());
		assertNotEquals(0, field.getSubfields().size());

		// System.err.println(field.getSubfields());
		SubfieldDefinition a = field.getSubfield("a");
		assertNotNull("subfield should not be null", a);
		assertEquals("a", a.getCode());
		assertEquals("Title", a.getLabel());
		assertEquals(Cardinality.Nonrepeatable, a.getType());
	}

	// 00$a[Man smoking at window].
	@Test
	public void testSubFieldAandC() {
		DataField field = new DataField(Tag245.getInstance(), "0", "0",
				"a", "[Man smoking at window].");
		assertEquals("0", field.getInd2());
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals(3, map.size());
		assertEquals("No added entry", map.get("Title added entry").get(0));
		assertEquals("No nonfiling characters", map.get("Nonfiling characters").get(0));

		assertEquals("[Man smoking at window].", map.get("Title").get(0));
	}

	// ind2
	// 04$aThe Year book of medicine.
	@Test
	public void testInd2As4() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "04$aThe Year book of medicine.");
		assertEquals("4", field.getInd2());
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals(3, map.size());
		assertEquals("4", map.get("Nonfiling characters").get(0));
	}

	// 12$aA report to the legislature for the year ...
	@Test
	public void testInd2As2() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "12$aA report to the legislature for the year ...");
		assertEquals("2", field.getInd2());
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals(3, map.size());
		assertEquals("2", map.get("Nonfiling characters").get(0));
	}

	// 16$a--the serpent--snapping eye.
	@Test
	public void testInd2As6() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "16$a--the serpent--snapping eye.");
		assertEquals("6", field.getInd2());
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals("6", map.get("Nonfiling characters").get(0));
	}

	// 03$aLe Bureau$h[filmstrip] =$bLa Oficina = Das Büro.
	@Test
	public void testSubfieldBandH() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "03$aLe Bureau$h[filmstrip] =$bLa Oficina = Das Büro.");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals("Le Bureau", map.get("Title").get(0));
		assertEquals("La Oficina = Das Büro.", map.get("Remainder of title").get(0));
		assertEquals("[filmstrip] =", map.get("Medium").get(0));
	}

	// 00$aHeritage Books archives.$pUnderwood biographical dictionary.$nVolumes 1 & 2 revised$h[electronic resource] /$cLaverne Galeener-Moore.
	// 10$aCancer research :$bofficial organ of the American Association for Cancer Research, Inc.

	// c Statement of responsibility, etc.
	// 04$aThe plays of Oscar Wilde /$cAlan Bird.
	@Test
	public void testSubfieldC() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "04$aThe plays of Oscar Wilde /$cAlan Bird.");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals("Alan Bird.", map.get("Statement of responsibility, etc.").get(0));
	}

	// $f - Inclusive dates
	// 00$aShort-Harrison-Symmes family papers,$f1760-1878.
	@Test
	public void testSubfieldF() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "00$aShort-Harrison-Symmes family papers,$f1760-1878.");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals("1760-1878.", map.get("Inclusive dates").get(0));
	}

	// $g - Bulk dates
	// 00$kRecords,$f1939-1973$g1965-1972.
	@Test
	public void testSubfieldG() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "00$kRecords,$f1939-1973$g1965-1972.");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals("1965-1972.", map.get("Bulk dates").get(0));
	}

	// $h - Medium
	// 04$aThe Green bag$h[microform] :$ba useless but entertaining magazine for lawyers.
	@Test
	public void testSubfieldH() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "04$aThe Green bag$h[microform] :$ba useless but entertaining magazine for lawyers.");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals("[microform] :", map.get("Medium").get(0));
	}

	// $k - Form
	// 10$aFour years at Yale :$kdiaries,$f1903 Sept. 16-1907 Oct. 5.
	@Test
	public void testSubfieldK() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "10$aFour years at Yale :$kdiaries,$f1903 Sept. 16-1907 Oct. 5.");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals("diaries,", map.get("Form").get(0));
	}

	// $n - Number of part/section of a work
	// 00$aMore!$n3 /$cHerbert Puchta ... [et al.].
	@Test
	public void testSubfieldN() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "00$aMore!$n3 /$cHerbert Puchta ... [et al.].");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals("3 /", map.get("Number of part/section of a work").get(0));
	}

	// $p - Name of part/section of a work
	// 10$aAdvanced calculus.$pStudent handbook.
	@Test
	public void testSubfieldP() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "10$aAdvanced calculus.$pStudent handbook.");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals("Student handbook.", map.get("Name of part/section of a work").get(0));
	}

	// $s - Version
	// 10$aDirector's report of the Association of Insurance Adjusters.$sMember release.
	@Test
	public void testSubfieldS() {
		DataField field = SubfieldParser.parseField(Tag245.getInstance(), "10$aDirector's report of the Association of Insurance Adjusters.$sMember release.");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals("Member release.", map.get("Version").get(0));
	}
}