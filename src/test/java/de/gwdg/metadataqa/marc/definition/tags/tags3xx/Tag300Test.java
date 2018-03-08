package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import org.junit.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class Tag300Test {
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
		Tag300 field = Tag300.getInstance();
		assertEquals("300", field.getTag());
		assertEquals("Physical Description", field.getLabel());
		assertEquals(Cardinality.Repeatable, field.getCardinality());
		assertFalse(field.getSubfields().isEmpty());
		assertNotEquals(0, field.getSubfields().size());

		// System.err.println(field.getSubfieldList());
		SubfieldDefinition a = field.getSubfield("a");
		assertNotNull("subfield should not be null", a);
		assertEquals("a", a.getCode());
		assertEquals("Extent", a.getLabel());
		assertEquals(Cardinality.Repeatable, a.getCardinality());
	}

	// ##$a149 p. ;$c23 cm
	@Test
	public void testSubFieldAandC() {
		DataField field = new DataField(Tag300.getInstance(), " ", " ",
				"a", "149 p. ;", "c", "23 cm");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals(2, map.size());
		assertEquals("149 p. ;", map.get("Extent").get(0));
		assertEquals("23 cm", map.get("Dimensions").get(0));
	}

	// ##$a104 p. :$bill. ;$c20 cm
	@Test
	public void testSubFieldB() {
		DataField field = new DataField(Tag300.getInstance(), " ", " ",
				"a", "104 p. :", "b", "ill. ;", "c", "20 cm ");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals(3, map.size());
		assertEquals("ill. ;", map.get("Other physical details").get(0));
	}

	// ##$a271 p. :$bill. ;$c21 cm. +$e1 answer book
	@Test
	public void testSubFieldE() {
		DataField field = new DataField(Tag300.getInstance(), " ", " ",
				"a", "271 p. :", "b", "ill. ;", "c", "21 cm. +", "e", "1 answer book");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals(4, map.size());
		assertEquals("1 answer book", map.get("Accompanying material").get(0));
	}

	// ##$a24$ffile drawers
	@Test
	public void testSubFieldF() {
		DataField field = new DataField(Tag300.getInstance(), " ", " ",
				"a", "24", "f", "file drawers");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals(2, map.size());
		assertEquals("file drawers", map.get("Type of unit").get(0));
	}

	// ##$3records$a1$fbox$g2 x 4 x 3 1/2 ft.
	@Test
	public void testSubFieldG() {
		DataField field = new DataField(Tag300.getInstance(), " ", " ",
				"3", "records", "a", "1", "f", "box", "g", "2 x 4 x 3 1/2 ft.");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals(4, map.size());
		assertEquals("box", map.get("Type of unit").get(0));
		assertEquals("2 x 4 x 3 1/2 ft.", map.get("Size of unit").get(0));
		assertEquals("records", map.get("Materials specified").get(0));
	}

	// ##$a1 reel of 1 (37 ft.) :$bsi., b&w ;$c35 mm$3dupe neg.
	@Test
	public void testSubField3() {
		DataField field = new DataField(Tag300.getInstance(), " ", " ",
				"a", "1 reel of 1 (37 ft.) :", "b", "si., b&w ;", "c", "35 mm", "3", "dupe neg.");
		Map<String, List<String>> map = field.getHumanReadableMap();
		assertEquals(4, map.size());
		assertEquals("1 reel of 1 (37 ft.) :", map.get("Extent").get(0));
		assertEquals("si., b&w ;", map.get("Other physical details").get(0));
		assertEquals("35 mm", map.get("Dimensions").get(0));
		assertEquals("dupe neg.", map.get("Materials specified").get(0));
	}

}