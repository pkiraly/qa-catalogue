package de.gwdg.metadataqa.marc.definition.general;

import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import org.junit.*;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class LanguageCodesTest {

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
		LanguageCodes codes = LanguageCodes.getInstance();
		assertTrue(codes.isValid("aar"));
		assertFalse(codes.isValid("aarx"));
	}
}
