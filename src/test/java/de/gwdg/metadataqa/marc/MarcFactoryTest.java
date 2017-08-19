package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.util.FileUtils;
import org.junit.*;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNotNull;

public class MarcFactoryTest {

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
	public void mainTest() throws IOException, URISyntaxException {
		JsonPathCache cache = new JsonPathCache(FileUtils.readFirstLine("general/verbund-tit.001.0000000.formatted.json"));

		MarcRecord record = MarcFactory.create(cache);
		assertNotNull(record);
		assertNotNull("Leader should not be null", record.getLeader());

	}

}
