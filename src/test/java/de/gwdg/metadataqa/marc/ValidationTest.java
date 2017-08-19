package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.util.FileUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
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
public class ValidationTest {
	
	public ValidationTest() {
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
	public void hello() throws URISyntaxException, IOException {
		MarcStructureDefinitionReader reader = new MarcStructureDefinitionReader("multiline.txt");
		List<MarcField> fields = reader.getFields();
		assertEquals(2, fields.size());
	}

	@Test
	public void testFullStructure() throws URISyntaxException, IOException {
		MarcStructureDefinitionReader reader = new MarcStructureDefinitionReader("general/marc-structure.txt");
		List<MarcField> fields = reader.getFields();
		assertEquals(238, fields.size());
	}
}
