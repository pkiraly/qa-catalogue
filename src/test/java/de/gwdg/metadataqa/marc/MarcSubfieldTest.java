package de.gwdg.metadataqa.marc;

import java.util.Arrays;
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
public class MarcSubfieldTest {
	
	public MarcSubfieldTest() {
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
	public void TestConstructor() {
		MarcSubfield subfield = new MarcSubfield("a", "NR", "Record control number");
		assertNotNull(subfield);
		assertEquals("a", subfield.getCode());
		assertEquals("NR", subfield.getType());
		assertEquals("Record control number", subfield.getLabel());
	}

	@Test
	public void testIncidator() {
		MarcSubfield subfield = new MarcSubfield("ind1", "012345", "Type of publisher number");
		assertNotNull(subfield);
		assertEquals("ind1", subfield.getCode());
		assertEquals("012345", subfield.getType());
		assertEquals("Type of publisher number", subfield.getLabel());
		assertEquals(
			Arrays.asList("0", "1", "2", "3", "4", "5"), 
			subfield.getAllowedValues()
		);

		subfield = new MarcSubfield("ind1", "b012345", "Type of publisher number");
		assertEquals(
			Arrays.asList(" ", "0", "1", "2", "3", "4", "5"), 
			subfield.getAllowedValues()
		);
	}

	@Test
	public void testBlankIncidator() {
		MarcSubfield subfield = new MarcSubfield("ind1", "blank", "Type of publisher number");
		assertEquals(
			Arrays.asList(" "), 
			subfield.getAllowedValues()
		);
	}

	@Test
	public void testMixedBlankIncidator() {
		MarcSubfield subfield = new MarcSubfield("ind1", "b7", "Type of publisher number");
		assertEquals(
			Arrays.asList(" ", "7"), 
			subfield.getAllowedValues()
		);
	}

}
