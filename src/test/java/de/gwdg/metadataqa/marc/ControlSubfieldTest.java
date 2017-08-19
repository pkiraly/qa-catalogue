package de.gwdg.metadataqa.marc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class ControlSubfieldTest {
	
	public ControlSubfieldTest() {
	}
	
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
	public void constructorTest() {
		ControlSubfield subfield = new ControlSubfield("Category of material", 0, 1);
		assertEquals(0, subfield.getPositionStart());
		assertEquals(1, subfield.getPositionEnd());
		assertEquals("Category of material", subfield.getLabel());
		assertNull(subfield.getCodes());
		/*
		map.put("Specific material designation", content.substring(1, 2));
		map.put("Undefined", content.substring(2, 3));
		map.put("Color", content.substring(3, 4));
		map.put("Videorecording format", content.substring(4, 5));
		map.put("Sound on medium or separate", content.substring(5, 6));
		map.put("Medium for sound", content.substring(6, 7));
		map.put("Dimensions", content.substring(7, 8));
		map.put("Configuration of playback channels", content.substring(8, 9));
		*/
	}

	@Test
	public void validationTest() {
		ControlSubfield subfield = new ControlSubfield("Category of material", 0, 1,
			Utils.generateCodes("a", "date 1", "b", "date 2"));
		assertTrue(subfield.validate("a"));
		assertFalse(subfield.validate("c"));
	}

	@Test
	public void repeatableContentTest() {
		ControlSubfield subfield = new ControlSubfield("Category of material", 0, 1,
			Utils.generateCodes("a", "date 1", "b", "date 2"))
			.setUnitLength(1).setRepeatableContent(true);
		assertTrue(subfield.validate("a"));
		assertFalse(subfield.validate("c"));
		assertTrue(subfield.validate("aa"));
		assertFalse(subfield.validate("ac"));
	}

	@Test
	public void testResolve() {
		ControlSubfield subfield = new ControlSubfield("Category of material", 0, 1,
			Utils.generateCodes("a", "date 1", "b", "date 2"))
			.setUnitLength(1).setRepeatableContent(true);
		assertEquals("date 1", subfield.resolve("a"));
		assertEquals("c", subfield.resolve("c"));
		assertEquals("date 1", subfield.resolve("aa"));
		assertEquals("date 1, date 2", subfield.resolve("ab"));
		assertEquals("date 2, date 1", subfield.resolve("ba"));
		assertEquals("date 1, date 2", subfield.resolve("aaaab"));
		assertEquals("date 1, c", subfield.resolve("ac"));
	}

}
