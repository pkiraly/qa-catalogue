package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.api.util.CompressionLevel;
import de.gwdg.metadataqa.api.util.FileUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;
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
public class MarcFieldExtractorTest {

	public MarcFieldExtractorTest() {
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
		MarcFieldExtractor calculator = new MarcFieldExtractor(new MarcJsonSchema());
		calculator.measure(
			new JsonPathCache(
				FileUtils.readFirstLine("general/verbund-tit.001.0000000.formatted.json")
			)
		);

		Map<String, ? extends Object> resultMap = calculator.getResultMap();
		for (String key : resultMap.keySet()) {
			// System.err.printf("%s: %s\n", key, resultMap.get(key));
		}

		String expected = "\"recordId\":000000027,\"leader\":02703cas a2200481   4500,\"007\":tu," +
				"\"008\":850101d19912003xx    p   b   0    0ger c," +
				"\"035$a\":(OCoLC)231477039, (DE-599)ZDB1056377-5," +
				"\"245$a\":Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen :," +
				"\"100$a\":null," +
				"\"110$a\":null," +
				"\"700$a\":null," +
				"\"710$a\":Deutsche Bibliothek," +
				"\"260$c\":null," +
				"\"020$a\":null," +
				"\"028$a\":null," +
				"\"260$b\":Buchhändler-Vereinigung," +
				"\"245$n\":null," +
				"\"245$p\":A B, Monographien und Periodika des Verlagsbuchhandels und außerhalb des Verlagsbuchhandels : wöchentliches Verzeichnis ; Register," +
				"\"300$a\":null," +
				"\"254$a\":null," +
				"\"490$v\":null," +
				"\"773$g\":null," +
				"\"773$v\":null";
		assertEquals(expected, calculator.getCsv(true, CompressionLevel.ZERO));

		//
		assertEquals("p", calculator.getDuplumKeyType());
		assertEquals(Arrays.asList("Deutsche", "Nationalbibliografie", "und"), calculator.getTitleWords());
		assertEquals(Arrays.asList("Deutsche", "Bibliothek"), calculator.getAuthorWords());
		assertNull(calculator.getDateOfPublication());
		assertNull(calculator.getIsbn());
		assertNull(calculator.getPublisherOrDistributorNumber());
		assertNull(calculator.getAbbreviatedNameOfPublisher());
		assertNull(calculator.getNumberOfPart());
		assertEquals("A B, Monographien und Periodika des Verlagsbuchhandels und außerhalb des Verlagsbuchhandels : wöchentliches Verzeichnis ; Register",
				calculator.getNameOfPart());
		assertNull(calculator.getExtent());
		assertNull(calculator.getMusicalPresentationStatement());
		assertNull(calculator.getVolumeDesignation());
		assertNull(calculator.getRelatedParts());

		// check leader
		assertEquals("02703", calculator.getLeader().getRecordLength().getValue());
		assertEquals("Corrected or revised", calculator.getLeader().getRecordStatus().resolve());
		assertEquals("Language material", calculator.getLeader().getTypeOfRecord().resolve());
		assertEquals("Serial", calculator.getLeader().getBibliographicLevel().resolve());
		assertEquals("No specified type", calculator.getLeader().getTypeOfControl().resolve());
		assertEquals("UCS/Unicode", calculator.getLeader().getCharacterCodingScheme().resolve());
		assertEquals("2", calculator.getLeader().getIndicatorCount().resolve());
		assertEquals("2", calculator.getLeader().getSubfieldCodeCount().resolve());
		assertEquals("0048", calculator.getLeader().getBaseAddressOfData().resolve());
		assertEquals("Full level", calculator.getLeader().getEncodingLevel().resolve());
		assertEquals("Non-ISBD", calculator.getLeader().getDescriptiveCatalogingForm().resolve());
		assertEquals("Not specified or not applicable", calculator.getLeader().getMultipartResourceRecordLevel().resolve());
		assertEquals("4", calculator.getLeader().getLengthOfTheLengthOfFieldPortion().resolve());
		assertEquals("5", calculator.getLeader().getLengthOfTheStartingCharacterPositionPortion().resolve());
		assertEquals("0", calculator.getLeader().getLengthOfTheImplementationDefinedPortion().resolve());

		// check 007
		Control007 x007 = calculator.getX007();
		// assertEquals("0", x007.getValueMap().);


		System.err.println("[007]");
		for (ControlSubfield subfield : x007.getMap().keySet()) {
			System.err.println(String.format("%s: %s", 
				subfield.getLabel(), x007.resolve(subfield)));
		}

		Control008 x008 = calculator.getX008();
		System.err.println("[008]");
		for (ControlSubfield subfield : x008.getValueMap().keySet()) {
			System.err.println(String.format("%s: %s", 
				subfield.getLabel(), x008.resolve(subfield)));
		}
	}
}

