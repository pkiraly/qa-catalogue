package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.util.FileUtils;
import org.junit.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
		// System.err.println(record.format());
		// System.err.println(record.formatAsMarc());
		// System.err.println(record.formatForIndex());
		System.err.println(record.getKeyValuePairs());
	}

	@Test
	public void marc2Test() throws IOException, URISyntaxException {
		JsonPathCache cache = new JsonPathCache(FileUtils.readFirstLine("general/marc2.json"));

		MarcRecord record = MarcFactory.create(cache);
		assertNotNull(record);
		assertNotNull("Leader should not be null", record.getLeader());

		List<DataField> admins = record.getDatafield("040");
		assertEquals(1, admins.size());
		DataField adminMeta = admins.get(0);
		List<MarcSubfield> subfields = adminMeta.getSubfields();
		for (MarcSubfield subfield : subfields) {
			if (subfield.getCode().equals("b")) {
				assertEquals("LanguageCodes", subfield.getDefinition().getCodeList().getClass().getSimpleName());
				assertEquals("English", subfield.resolve());
			}
		}

		assertEquals(Arrays.asList("English"), record.getKeyValuePairs().get("AdminMetadata_languageOfCataloging"));
	}

}
