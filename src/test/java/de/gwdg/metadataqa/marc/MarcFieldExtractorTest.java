package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.model.selector.JsonSelector;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.api.util.CompressionLevel;
import de.gwdg.metadataqa.api.util.FileUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;

import de.gwdg.metadataqa.marc.dao.Control007;
import de.gwdg.metadataqa.marc.dao.Control008;
import de.gwdg.metadataqa.marc.dao.Leader;
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

  // @Test
  public void test() throws URISyntaxException, IOException {
    MarcFieldExtractor calculator = new MarcFieldExtractor(new MarcJsonSchema());
    calculator.measure(
      new JsonSelector(
        FileUtils.readFirstLineFromResource("general/verbund-tit.001.0000000.formatted.json")
      )
    );

    Map<String, ? extends Object> resultMap = calculator.getResultMap();
    for (String key : resultMap.keySet()) {
      // System.err.printf("%s: %s\n", key, resultMap.get(key));
    }

    String expected = "\"recordId\":000000027," +
        "\"leader\":02703cas a2200481   4500," +
        "\"001\":000000027," +
        "\"007\":tu," +
        "\"008\":850101d19912003xx    p   b   0    0ger c," +
        "\"040$d\":null," +
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

    assertEquals(2, x007.getMap().size());
    assertEquals("Text", x007.getCategoryOfMaterial());
    assertEquals("u", x007.getText01().getValue());
    assertEquals("Unspecified", x007.getText01().resolve());

    Control008 x008 = calculator.getX008();
    assertEquals(19, x008.getMap().size());
    assertEquals("850101", x008.getTag008all00().resolve());
    assertEquals("d", x008.getTag008all06().getValue());
    assertEquals("Continuing resource ceased publication", x008.getTag008all06().resolve());
    assertEquals("1991", x008.getTag008all07().getValue());
    assertEquals("2003", x008.getTag008all11().getValue());
    assertEquals("xx ", x008.getTag008all15().getValue());
    assertEquals("ger", x008.getTag008all35().getValue());
    assertEquals("ger", x008.getTag008all35().resolve());
    assertEquals(" ", x008.getTag008all38().getValue());
    assertEquals("Not modified", x008.getTag008all38().resolve());
    assertEquals(Leader.Type.CONTINUING_RESOURCES, x008.getRecordType());
    assertEquals("No determinable frequency", x008.getTag008continuing18().resolve());
    assertEquals(" ", x008.getTag008continuing19().resolve());
    assertEquals("Periodical", x008.getTag008continuing21().resolve());
    assertEquals("None of the following", x008.getTag008continuing22().resolve());
    assertEquals("None of the following", x008.getTag008continuing23().resolve());
    assertEquals("Not specified", x008.getTag008continuing24().resolve());
    assertEquals("Bibliographies, Not specified", x008.getTag008continuing25().resolve());
    assertEquals("Not a government publication", x008.getTag008continuing28().resolve());
    assertEquals("Not a conference publication", x008.getTag008continuing29().resolve());
    assertEquals("No alphabet or script given/No key title", x008.getTag008continuing33().resolve());
    assertEquals("Successive entry", x008.getTag008continuing34().resolve());
  }

  @Test
  public void test008() {
    Control008 x008 = new Control008("850101d19912003xx    p   b   0    0ger c", Leader.Type.BOOKS);
    assertEquals("ger", x008.getTag008all35().getValue());
    assertEquals("German", x008.getTag008all35().resolve());
  }
}

