package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.tags6xx.Tag610;
import org.junit.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Tag040Test {

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
	public void testCStH() {
		DataField field = new DataField(Tag040.getInstance(), " ", " ", "a", "CSt-H", "c", "CSt-H", "e", "appm");
		Map<String, List<String>> map = field.getHumanReadableMap();

		assertEquals(3, map.size());
		assertEquals("Stanford University, Hoover Institution on War, Revolution, and Peace",
				map.get("Original cataloging agency").get(0));
		assertEquals("Stanford University, Hoover Institution on War, Revolution, and Peace",
				map.get("Transcribing agency").get(0));
		assertEquals("Hensen, Steven L. Archives, personal papers, and manuscripts (Washington: Library of Congress)",
				map.get("Description conventions").get(0));

	}

	@Test
	public void testMt() {
		DataField field = new DataField(Tag040.getInstance(), " ", " ", "a", "Mt", "c", "Mt");
		Map<String, List<String>> map = field.getHumanReadableMap();

		assertEquals(2, map.size());
		assertEquals("Montana State Library",
				map.get("Original cataloging agency").get(0));
		assertEquals("Montana State Library",
				map.get("Transcribing agency").get(0));
	}
}
