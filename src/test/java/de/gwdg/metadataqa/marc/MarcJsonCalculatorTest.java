package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.calculator.CompletenessCalculator;
import de.gwdg.metadataqa.api.interfaces.Calculator;
import de.gwdg.metadataqa.api.model.EdmFieldInstance;
import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.util.FileUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.utils.MarcCacheWrapper;
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
public class MarcJsonCalculatorTest {
	
	public MarcJsonCalculatorTest() {
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
	public void testReader() throws URISyntaxException, IOException {
		MarcStructureDefinitionReader reader =
				new MarcStructureDefinitionReader("general/marc-structure.txt");
		List<MarcField> fields = reader.getFields();
		Map<String, SubfieldDefinition> subfields = reader.getQualifiedSubfields();
	}

	// @Test
	public void testCalculator() throws URISyntaxException, IOException {
		MarcJsonCalculatorFacade calculatorFacade = new MarcJsonCalculatorFacade();
		calculatorFacade.enableFieldExtractor(true);
		calculatorFacade.enableCompletenessMeasurement(true);
		calculatorFacade.enableFieldCardinalityMeasurement(true);
		calculatorFacade.enableFieldExistenceMeasurement(true);
		calculatorFacade.enableLanguageMeasurement(false);
		calculatorFacade.enableMultilingualSaturationMeasurement(false);
		calculatorFacade.enableProblemCatalogMeasurement(false);
		calculatorFacade.enableTfIdfMeasurement(false);
		calculatorFacade.configure();
		String expected = "ocm06783656,02249cam a2200541Ii 4500,ocm06783656 ,null,801003s1958    ja            000 0 jpn  ,null,(OCoLC)06783656," +
				"Abidatsuma shisō kenkyū :,Sasaki, Genjun,,null,null,null,Shōwa 33 [1958],null,null,Kōbundō,,null,null,ix, 8, 603, " +
				"29, xlvi pages ;,null,null,null,null,0.084356,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0," +
				"1,1,1,1,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,0,0,0,0,1,1,0,1,1,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,1,1,1,1,1,0,0,1," +
				"1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0," +
				"1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1," +
				"1,1,1,1,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0," +
				"0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,1,0,1,1,1,1,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0," +
				"0,0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0,0,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,8,0,0,0," +
				"0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,1,1,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0," +
				"1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,1," +
				"1,1,1,1,0,0,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,2,2,0,0,0,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,1,1,1,1,1,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,1,1,0,0,0,2,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,0,0,1," +
				"0,0,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,0,0,0,0,0,7,0,8,2,2,2,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," +
				"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1";
		assertEquals("test 1", expected, calculatorFacade.measure(FileUtils.readFirstLine("general/sub-test-marc.json")));

		JsonPathCache<? extends XmlFieldInstance> cache = calculatorFacade.getCache();
		MarcCacheWrapper wrapper = new MarcCacheWrapper(cache);
		assertFalse(calculatorFacade.getSchema().getCollectionPaths().isEmpty());
		for (Calculator calculator : calculatorFacade.getCalculators()) {
			if (calculator.getCalculatorName().equals(CompletenessCalculator.CALCULATOR_NAME)) {
				Map<String, Integer> cardinalityMap = ((CompletenessCalculator)calculator).getCardinalityMap();
				assertNotNull(cardinalityMap);
				assertEquals(1743, cardinalityMap.size());
				testCardinalityMap(cardinalityMap);
				// SubfieldDefinition subfield = subfields.get("588$a");
			}
		}

		Map<String, ? extends List<? extends XmlFieldInstance>> cacheMap = cache.getCache();
		assertEquals(" ", cacheMap.get("$.datafield[?(@.tag == '010')]/0/$.ind1").get(0).getValue());
		assertEquals(" ", cacheMap.get("$.datafield[?(@.tag == '010')]/0/$.ind2").get(0).getValue());
		assertNull(cacheMap.get("$.datafield[?(@.tag == '010')]/0/$.subfield[?(@.code == '8')].content"));
		assertEquals("j  61000413 ", cacheMap.get("$.datafield[?(@.tag == '010')]/0/$.subfield[?(@.code == 'a')].content").get(0).getValue());
		assertNull(cacheMap.get("$.datafield[?(@.tag == '010')]/0/$.subfield[?(@.code == 'b')].content"));
		assertNull(cacheMap.get("$.datafield[?(@.tag == '010')]/0/$.subfield[?(@.code == 'z')].content"));
		assertNull(cacheMap.get("010$ind1"));
		assertNull(cacheMap.get("010$ind2"));
		assertNull(cacheMap.get("010$a"));

		testWrapper(wrapper);
	}

	private void testWrapper(MarcCacheWrapper wrapper) {
		assertEquals("02249cam a2200541Ii 4500", wrapper.extract("leader"));
		assertEquals("ocm06783656 ", wrapper.extract("001"));
		assertEquals("801003s1958    ja            000 0 jpn  ", wrapper.extract("008"));
		assertEquals("JP-ToKJK", wrapper.extract("016", "2"));
		assertEquals("BN10063126", wrapper.extract("016", "a"));
		assertEquals("7", wrapper.extract("016", "ind1"));
		assertEquals(" ", wrapper.extract("016", "ind2"));
		assertEquals("7", wrapper.extract("016", 1, "ind1"));
		assertEquals(" ", wrapper.extract("016", 1, "ind2"));
		assertEquals("Uk", wrapper.extract("016", 1, "2"));
		assertEquals("016396039", wrapper.extract("016", 1, "a"));
		assertEquals("33658528|67606250|214975495", wrapper.extract("019", "a"));
		assertEquals(" ", wrapper.extract("019", "ind1"));
		assertEquals(" ", wrapper.extract("019", "ind2"));
		assertEquals("GEBAY", wrapper.extract("029", "a"));
		assertEquals("10740743", wrapper.extract("029", "b"));
		assertEquals("1", wrapper.extract("029", "ind1"));
		assertEquals(" ", wrapper.extract("029", "ind2"));
		assertEquals("1", wrapper.extract("029", 1, "ind1"));
		assertEquals(" ", wrapper.extract("029", 1, "ind2"));
		assertEquals("NZ1", wrapper.extract("029", 1, "a"));
		assertEquals("5803377", wrapper.extract("029", 1, "b"));
		assertEquals("(OCoLC)06783656", wrapper.extract("035", "a"));
		assertEquals(" ", wrapper.extract("035", "ind1"));
		assertEquals(" ", wrapper.extract("035", "ind2"));
		assertEquals("(OCoLC)33658528|(OCoLC)67606250|(OCoLC)214975495", wrapper.extract("035", "z"));
		assertEquals("DLC", wrapper.extract("040", "a"));
		assertEquals("eng", wrapper.extract("040", "b"));
		assertEquals("VLW", wrapper.extract("040", "c"));
		assertEquals("OCL|OCLCG|CLU|LWU|EYM|UKMGB|OCLCF|OCLCQ", wrapper.extract("040", "d"));
		assertEquals(" ", wrapper.extract("040", "ind1"));
		assertEquals(" ", wrapper.extract("040", "ind2"));
		assertEquals("jpn", wrapper.extract("041", "a"));
		assertEquals("eng", wrapper.extract("041", "b"));
		assertEquals("0", wrapper.extract("041", "ind1"));
		assertEquals(" ", wrapper.extract("041", "ind2"));
		assertEquals("L2UA", wrapper.extract("049", "a"));
		assertEquals(" ", wrapper.extract("049", "ind1"));
		assertEquals(" ", wrapper.extract("049", "ind2"));
		assertEquals("B162", wrapper.extract("050", "a"));
		assertEquals(".S3", wrapper.extract("050", "b"));
		assertEquals(" ", wrapper.extract("050", "ind1"));
		assertEquals("4", wrapper.extract("050", "ind2"));
		assertEquals("$1", wrapper.extract("066", "c"));
		assertEquals(" ", wrapper.extract("066", "ind1"));
		assertEquals(" ", wrapper.extract("066", "ind2"));
		assertEquals("880-01", wrapper.extract("100", "6"));
		assertEquals("Sasaki, Genjun,", wrapper.extract("100", "a"));
		assertEquals("1915-", wrapper.extract("100", "d"));
		assertEquals("1", wrapper.extract("100", "ind1"));
		assertEquals(" ", wrapper.extract("100", "ind2"));
		assertEquals("880-02", wrapper.extract("245", "6"));
		assertEquals("Abidatsuma shisō kenkyū :", wrapper.extract("245", "a"));
		assertEquals("Bukkyō jitsuzairon no rekishiteki hihanteki kenkyū /", wrapper.extract("245", "b"));
		assertEquals("Sasaki Genjun cho.", wrapper.extract("245", "c"));
		assertEquals("1", wrapper.extract("245", "ind1"));
		assertEquals("0", wrapper.extract("245", "ind2"));
		assertEquals("Study of Abhidharma philosophy; a historical and critical study of Buddhist realism", wrapper.extract("246", "a"));
		assertEquals("3", wrapper.extract("246", "ind1"));
		assertEquals(" ", wrapper.extract("246", "ind2"));
		assertEquals("880-03", wrapper.extract("260", "6"));
		assertEquals("Tōkyō :", wrapper.extract("260", "a"));
		assertEquals("Kōbundō,", wrapper.extract("260", "b"));
		assertEquals("Shōwa 33 [1958]", wrapper.extract("260", "c"));
		assertEquals(" ", wrapper.extract("260", "ind1"));
		assertEquals(" ", wrapper.extract("260", "ind2"));
		assertEquals("ix, 8, 603, 29, xlvi pages ;", wrapper.extract("300", "a"));
		assertEquals("22 cm", wrapper.extract("300", "c"));
		assertEquals(" ", wrapper.extract("300", "ind1"));
		assertEquals(" ", wrapper.extract("300", "ind2"));
		assertEquals("rdacontent", wrapper.extract("336", "2"));
		assertEquals("text", wrapper.extract("336", "a"));
		assertEquals("txt", wrapper.extract("336", "b"));
		assertEquals(" ", wrapper.extract("336", "ind1"));
		assertEquals(" ", wrapper.extract("336", "ind2"));
		assertEquals("rdamedia", wrapper.extract("337", "2"));
		assertEquals("unmediated", wrapper.extract("337", "a"));
		assertEquals("n", wrapper.extract("337", "b"));
		assertEquals(" ", wrapper.extract("337", "ind1"));
		assertEquals(" ", wrapper.extract("337", "ind2"));
		assertEquals("rdacarrier", wrapper.extract("338", "2"));
		assertEquals("volume", wrapper.extract("338", "a"));
		assertEquals("nc", wrapper.extract("338", "b"));
		assertEquals(" ", wrapper.extract("338", "ind1"));
		assertEquals(" ", wrapper.extract("338", "ind2"));
		assertEquals("880-04", wrapper.extract("500", "6"));
		assertEquals("Added t.p.: A study of Abhidharma Philosophy; a historical and critical study of Buddhist realism.", wrapper.extract("500", "a"));
		assertEquals(" ", wrapper.extract("500", "ind1"));
		assertEquals(" ", wrapper.extract("500", "ind2"));
		assertEquals(" ", wrapper.extract("500", 1, "ind1"));
		assertEquals(" ", wrapper.extract("500", 1, "ind2"));
		assertEquals("English summary: p. xxi-xlvi.", wrapper.extract("500", 1, "a"));
		assertEquals("Also issued online.", wrapper.extract("530", "a"));
		assertEquals(" ", wrapper.extract("530", "ind1"));
		assertEquals(" ", wrapper.extract("530", "ind2"));
		assertEquals("m9SINOL", wrapper.extract("590", "a"));
		assertEquals(" ", wrapper.extract("590", "ind1"));
		assertEquals(" ", wrapper.extract("590", "ind2"));
		assertEquals("08/08/2014", wrapper.extract("591", "a"));
		assertEquals(" ", wrapper.extract("591", "ind2"));
		assertEquals("NEW", wrapper.extract("593", "a"));
		assertEquals(" ", wrapper.extract("593", "ind1"));
		assertEquals(" ", wrapper.extract("593", "ind2"));
		assertEquals("Abhidharma.", wrapper.extract("650", "a"));
		assertEquals(" ", wrapper.extract("650", "ind1"));
		assertEquals("0", wrapper.extract("650", "ind2"));
		assertEquals(" ", wrapper.extract("650", 1, "ind1"));
		assertEquals("7", wrapper.extract("650", 1, "ind2"));
		assertEquals("(OCoLC)fst00794375", wrapper.extract("650", 1, "0"));
		assertEquals("fast", wrapper.extract("650", 1, "2"));
		assertEquals("Abhidharma.", wrapper.extract("650", 1, "a"));
		assertEquals("880-05", wrapper.extract("653", "6"));
		assertEquals("Abidatsuma.", wrapper.extract("653", "a"));
		assertEquals(" ", wrapper.extract("653", "ind1"));
		assertEquals(" ", wrapper.extract("653", "ind2"));
		assertEquals(" ", wrapper.extract("653", 1, "ind1"));
		assertEquals(" ", wrapper.extract("653", 1, "ind2"));
		assertEquals("880-06", wrapper.extract("653", 1, "6"));
		assertEquals("Bukkyō|Kyōgi.", wrapper.extract("653", 1, "a"));
		assertEquals("Sasaki, Genjun, 1915-", wrapper.extract("776", "a"));
		assertEquals("33 [1958]", wrapper.extract("776", "d"));
		assertEquals("Online version:", wrapper.extract("776", "i"));
		assertEquals("0", wrapper.extract("776", "ind1"));
		assertEquals("8", wrapper.extract("776", "ind2"));
		assertEquals("A Abidatsuma shisō kenkyū.", wrapper.extract("776", "t"));
		assertEquals("(OCoLC)541300190", wrapper.extract("776", "w"));
		assertEquals("100-01/$1", wrapper.extract("880", "6"));
		assertEquals("佐々木現順,", wrapper.extract("880", "a"));
		assertEquals("1915-", wrapper.extract("880", "d"));
		assertEquals("1", wrapper.extract("880", "ind1"));
		assertEquals(" ", wrapper.extract("880", "ind2"));
		assertEquals("1", wrapper.extract("880", 1, "ind1"));
		assertEquals("0", wrapper.extract("880", 1, "ind2"));
		assertEquals("245-02/$1", wrapper.extract("880", 1, "6"));
		assertEquals("阿毘達磨思想硏究 :", wrapper.extract("880", 1, "a"));
		assertEquals("佛敎実在論の歷史的批判的硏究 /", wrapper.extract("880", 1, "b"));
		assertEquals("佐々木現順著.", wrapper.extract("880", 1, "c"));
		assertEquals(" ", wrapper.extract("880", 2, "ind1"));
		assertEquals(" ", wrapper.extract("880", 2, "ind2"));
		assertEquals("260-03/$1", wrapper.extract("880", 2, "6"));
		assertEquals("東京 :", wrapper.extract("880", 2, "a"));
		assertEquals("弘文堂,", wrapper.extract("880", 2, "b"));
		assertEquals("昭和33 [1958]", wrapper.extract("880", 2, "c"));
		assertEquals(" ", wrapper.extract("880", 3, "ind1"));
		assertEquals(" ", wrapper.extract("880", 3, "ind2"));
		assertEquals("500-04/$1", wrapper.extract("880", 3, "6"));
		assertEquals("英語書名:A study of Abhidharma philosophy 英文併記 序:E.コンヅ, I.B.ホーナ 標題紙・背の書名(誤植):阿毘達麿思想研究.", wrapper.extract("880", 3, "a"));
		assertEquals(" ", wrapper.extract("880", 4, "ind1"));
		assertEquals(" ", wrapper.extract("880", 4, "ind2"));
		assertEquals("653-05/$1", wrapper.extract("880", 4, "6"));
		assertEquals("阿毘達磨.", wrapper.extract("880", 4, "a"));
		assertEquals(" ", wrapper.extract("880", 5, "ind1"));
		assertEquals(" ", wrapper.extract("880", 5, "ind2"));
		assertEquals("653-06/$1", wrapper.extract("880", 5, "6"));
		assertEquals("仏敎|敎義.", wrapper.extract("880", 5, "a"));
		assertEquals("1", wrapper.extract("880", 6, "ind1"));
		assertEquals(" ", wrapper.extract("880", 6, "ind2"));
		assertEquals("700-00/$1", wrapper.extract("880", 6, "6"));
		assertEquals("佐々木現順,", wrapper.extract("880", 6, "a"));
		assertEquals("1915-", wrapper.extract("880", 6, "d"));
		assertEquals("92", wrapper.extract("994", "a"));
		assertEquals("L2U", wrapper.extract("994", "b"));
		assertEquals(" ", wrapper.extract("994", "ind1"));
		assertEquals(" ", wrapper.extract("994", "ind2"));
	}

	private void testCardinalityMap(Map<String, Integer> cardinalityMap) {
		assertEquals(1, (int)cardinalityMap.get("010$ind1"));
		assertEquals(1, (int)cardinalityMap.get("010$ind2"));
		assertEquals(1, (int)cardinalityMap.get("010$a"));
		assertEquals(2, (int)cardinalityMap.get("016$ind1"));
		assertEquals(2, (int)cardinalityMap.get("016$ind2"));
		assertEquals(2, (int)cardinalityMap.get("016$2"));
		assertEquals(2, (int)cardinalityMap.get("016$a"));
		assertEquals(1, (int)cardinalityMap.get("019$ind1"));
		assertEquals(1, (int)cardinalityMap.get("019$ind2"));
		assertEquals(3, (int)cardinalityMap.get("019$a"));
		assertEquals(2, (int)cardinalityMap.get("029$ind1"));
		assertEquals(2, (int)cardinalityMap.get("029$ind2"));
		assertEquals(2, (int)cardinalityMap.get("029$a"));
		assertEquals(2, (int)cardinalityMap.get("029$b"));
		assertEquals(1, (int)cardinalityMap.get("035$ind1"));
		assertEquals(1, (int)cardinalityMap.get("035$ind2"));
		assertEquals(1, (int)cardinalityMap.get("035$a"));
		assertEquals(3, (int)cardinalityMap.get("035$z"));
		assertEquals(1, (int)cardinalityMap.get("040$ind1"));
		assertEquals(1, (int)cardinalityMap.get("040$ind2"));
		assertEquals(1, (int)cardinalityMap.get("040$a"));
		assertEquals(1, (int)cardinalityMap.get("040$b"));
		assertEquals(1, (int)cardinalityMap.get("040$c"));
		assertEquals(8, (int)cardinalityMap.get("040$d"));
		assertEquals(1, (int)cardinalityMap.get("041$ind1"));
		assertEquals(1, (int)cardinalityMap.get("041$ind2"));
		assertEquals(1, (int)cardinalityMap.get("041$a"));
		assertEquals(1, (int)cardinalityMap.get("041$b"));
		assertEquals(1, (int)cardinalityMap.get("049$ind1"));
		assertEquals(1, (int)cardinalityMap.get("049$ind2"));
		assertEquals(1, (int)cardinalityMap.get("049$a"));
		assertEquals(1, (int)cardinalityMap.get("050$ind1"));
		assertEquals(1, (int)cardinalityMap.get("050$ind2"));
		assertEquals(1, (int)cardinalityMap.get("050$a"));
		assertEquals(1, (int)cardinalityMap.get("050$b"));
		assertEquals(1, (int)cardinalityMap.get("066$ind1"));
		assertEquals(1, (int)cardinalityMap.get("066$ind2"));
		assertEquals(1, (int)cardinalityMap.get("066$c"));
		assertEquals(1, (int)cardinalityMap.get("100$ind1"));
		assertEquals(1, (int)cardinalityMap.get("100$ind2"));
		assertEquals(1, (int)cardinalityMap.get("100$6"));
		assertEquals(1, (int)cardinalityMap.get("100$a"));
		assertEquals(1, (int)cardinalityMap.get("100$d"));
		assertEquals(1, (int)cardinalityMap.get("245$ind1"));
		assertEquals(1, (int)cardinalityMap.get("245$ind2"));
		assertEquals(1, (int)cardinalityMap.get("245$6"));
		assertEquals(1, (int)cardinalityMap.get("245$a"));
		assertEquals(1, (int)cardinalityMap.get("245$b"));
		assertEquals(1, (int)cardinalityMap.get("245$c"));
		assertEquals(1, (int)cardinalityMap.get("246$ind1"));
		assertEquals(1, (int)cardinalityMap.get("246$ind2"));
		assertEquals(1, (int)cardinalityMap.get("246$a"));
		assertEquals(1, (int)cardinalityMap.get("260$ind1"));
		assertEquals(1, (int)cardinalityMap.get("260$ind2"));
		assertEquals(1, (int)cardinalityMap.get("260$6"));
		assertEquals(1, (int)cardinalityMap.get("260$a"));
		assertEquals(1, (int)cardinalityMap.get("260$b"));
		assertEquals(1, (int)cardinalityMap.get("260$c"));
		assertEquals(1, (int)cardinalityMap.get("300$ind1"));
		assertEquals(1, (int)cardinalityMap.get("300$ind2"));
		assertEquals(1, (int)cardinalityMap.get("300$a"));
		assertEquals(1, (int)cardinalityMap.get("300$c"));
		assertEquals(1, (int)cardinalityMap.get("336$ind1"));
		assertEquals(1, (int)cardinalityMap.get("336$ind2"));
		assertEquals(1, (int)cardinalityMap.get("336$2"));
		assertEquals(1, (int)cardinalityMap.get("336$a"));
		assertEquals(1, (int)cardinalityMap.get("336$b"));
		assertEquals(1, (int)cardinalityMap.get("337$ind1"));
		assertEquals(1, (int)cardinalityMap.get("337$ind2"));
		assertEquals(1, (int)cardinalityMap.get("337$2"));
		assertEquals(1, (int)cardinalityMap.get("337$a"));
		assertEquals(1, (int)cardinalityMap.get("337$b"));
		assertEquals(1, (int)cardinalityMap.get("338$ind1"));
		assertEquals(1, (int)cardinalityMap.get("338$ind2"));
		assertEquals(1, (int)cardinalityMap.get("338$2"));
		assertEquals(1, (int)cardinalityMap.get("338$a"));
		assertEquals(1, (int)cardinalityMap.get("338$b"));
		assertEquals(2, (int)cardinalityMap.get("500$ind1"));
		assertEquals(2, (int)cardinalityMap.get("500$ind2"));
		assertEquals(1, (int)cardinalityMap.get("500$6"));
		assertEquals(2, (int)cardinalityMap.get("500$a"));
		assertEquals(1, (int)cardinalityMap.get("530$ind1"));
		assertEquals(1, (int)cardinalityMap.get("530$ind2"));
		assertEquals(1, (int)cardinalityMap.get("530$a"));
		assertEquals(1, (int)cardinalityMap.get("590$ind1"));
		assertEquals(1, (int)cardinalityMap.get("590$ind2"));
		assertEquals(1, (int)cardinalityMap.get("590$a"));
		assertEquals(1, (int)cardinalityMap.get("591$ind1"));
		assertEquals(1, (int)cardinalityMap.get("591$ind2"));
		assertEquals(1, (int)cardinalityMap.get("591$a"));
		assertEquals(1, (int)cardinalityMap.get("593$ind1"));
		assertEquals(1, (int)cardinalityMap.get("593$ind2"));
		assertEquals(1, (int)cardinalityMap.get("593$a"));
		assertEquals(2, (int)cardinalityMap.get("650$ind1"));
		assertEquals(2, (int)cardinalityMap.get("650$ind2"));
		assertEquals(1, (int)cardinalityMap.get("650$0"));
		assertEquals(1, (int)cardinalityMap.get("650$2"));
		assertEquals(2, (int)cardinalityMap.get("650$a"));
		assertEquals(2, (int)cardinalityMap.get("653$ind1"));
		assertEquals(2, (int)cardinalityMap.get("653$ind2"));
		assertEquals(2, (int)cardinalityMap.get("653$6"));
		assertEquals(3, (int)cardinalityMap.get("653$a"));
		assertEquals(1, (int)cardinalityMap.get("776$ind1"));
		assertEquals(1, (int)cardinalityMap.get("776$ind2"));
		assertEquals(1, (int)cardinalityMap.get("776$a"));
		assertEquals(1, (int)cardinalityMap.get("776$d"));
		assertEquals(1, (int)cardinalityMap.get("776$i"));
		assertEquals(1, (int)cardinalityMap.get("776$t"));
		assertEquals(1, (int)cardinalityMap.get("776$w"));
		assertEquals(7, (int)cardinalityMap.get("880$ind1"));
		assertEquals(7, (int)cardinalityMap.get("880$ind2"));
		assertEquals(7, (int)cardinalityMap.get("880$6"));
		assertEquals(8, (int)cardinalityMap.get("880$a"));
		assertEquals(2, (int)cardinalityMap.get("880$b"));
		assertEquals(2, (int)cardinalityMap.get("880$c"));
		assertEquals(2, (int)cardinalityMap.get("880$d"));
		assertEquals(1, (int)cardinalityMap.get("994$ind1"));
		assertEquals(1, (int)cardinalityMap.get("994$ind2"));
		assertEquals(1, (int)cardinalityMap.get("994$a"));
		assertEquals(1, (int)cardinalityMap.get("994$b"));
	}
}
