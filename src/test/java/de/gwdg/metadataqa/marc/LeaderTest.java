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
public class LeaderTest {
	
	public LeaderTest() {
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
	public void test00928nama2200265c4500() {
		Leader leader = new Leader("00928nam a2200265 c 4500");
		assertNotNull(leader);
		assertEquals(Leader.Type.BOOKS, leader.getType());

		assertEquals("00928", leader.getById("leader00"));
		assertEquals("00928", leader.getByLabel("Record length"));
		assertEquals("00928", leader.getRecordLength().getValue());

		assertEquals("n", leader.getById("leader05"));
		assertEquals("n", leader.getByLabel("Record status"));
		assertEquals("n", leader.getRecordStatus().getValue());
		assertEquals("New", leader.getRecordStatus().resolve());

		assertEquals("a", leader.getById("leader06"));
		assertEquals("a", leader.getByLabel("Type of record"));
		assertEquals("a", leader.getTypeOfRecord().getValue());
		assertEquals("Language material", leader.getTypeOfRecord().resolve());

		assertEquals("m", leader.getById("leader07"));
		assertEquals("m", leader.getByLabel("Bibliographic level"));
		assertEquals("m", leader.getBibliographicLevel().getValue());
		assertEquals("Monograph/Item", leader.getBibliographicLevel().resolve());

		assertEquals(" ", leader.getById("leader08"));
		assertEquals(" ", leader.getByLabel("Type of control"));
		assertEquals(" ", leader.getTypeOfControl().getValue());
		assertEquals("No specified type", leader.getTypeOfControl().resolve());

		assertEquals("a", leader.getById("leader09"));
		assertEquals("a", leader.getByLabel("Character coding scheme"));
		assertEquals("a", leader.getCharacterCodingScheme().getValue());
		assertEquals("UCS/Unicode", leader.getCharacterCodingScheme().resolve());

		assertEquals("2", leader.getById("leader10"));
		assertEquals("2", leader.getByLabel("Indicator count"));
		assertEquals("2", leader.getIndicatorCount().getValue());
		assertEquals("2", leader.getIndicatorCount().resolve());

		assertEquals("2", leader.getById("leader11"));
		assertEquals("2", leader.getByLabel("Subfield code count"));
		assertEquals("2", leader.getSubfieldCodeCount().getValue());
		assertEquals("2", leader.getSubfieldCodeCount().resolve());

		assertEquals("00265", leader.getById("leader12"));
		assertEquals("00265", leader.getByLabel("Base address of data"));
		assertEquals("00265", leader.getBaseAddressOfData().getValue());
		assertEquals("00265", leader.getBaseAddressOfData().resolve());

		assertEquals(" ", leader.getById("leader17"));
		assertEquals(" ", leader.getByLabel("Encoding level"));
		assertEquals(" ", leader.getEncodingLevel().getValue());
		assertEquals("Full level", leader.getEncodingLevel().resolve());

		assertEquals("c", leader.getById("leader18"));
		assertEquals("c", leader.getByLabel("Descriptive cataloging form"));
		assertEquals("c", leader.getDescriptiveCatalogingForm().getValue());
		assertEquals("ISBD punctuation omitted", leader.getDescriptiveCatalogingForm().resolve());

		assertEquals(" ", leader.getById("leader19"));
		assertEquals(" ", leader.getByLabel("Multipart resource record level"));
		assertEquals(" ", leader.getMultipartResourceRecordLevel().getValue());
		assertEquals("Not specified or not applicable", leader.getMultipartResourceRecordLevel().resolve());

		assertEquals("4", leader.getById("leader20"));
		assertEquals("4", leader.getByLabel("Length of the length-of-field portion"));
		assertEquals("4", leader.getLengthOfTheLengthOfFieldPortion().getValue());
		assertEquals("4", leader.getLengthOfTheLengthOfFieldPortion().resolve());

		assertEquals("5", leader.getById("leader21"));
		assertEquals("5", leader.getByLabel("Length of the starting-character-position portion"));
		assertEquals("5", leader.getLengthOfTheStartingCharacterPositionPortion().getValue());
		assertEquals("5", leader.getLengthOfTheStartingCharacterPositionPortion().resolve());

		assertEquals("0", leader.getById("leader22"));
		assertEquals("0", leader.getByLabel("Length of the implementation-defined portion"));
		assertEquals("0", leader.getLengthOfTheImplementationDefinedPortion().getValue());
		assertEquals("0", leader.getLengthOfTheImplementationDefinedPortion().resolve());
	}

	@Test
	public void test03960cama22007814500() {
		Leader leader = new Leader("03960cam a2200781   4500");
		assertNotNull(leader);
		assertEquals(Leader.Type.BOOKS, leader.getType());

		assertEquals("03960", leader.getById("leader00"));
		assertEquals("03960", leader.getByLabel("Record length"));
		assertEquals("03960", leader.getRecordLength().getValue());

		assertEquals("c", leader.getById("leader05"));
		assertEquals("c", leader.getByLabel("Record status"));
		assertEquals("c", leader.getRecordStatus().getValue());
		assertEquals("Corrected or revised", leader.getRecordStatus().resolve());

		assertEquals("a", leader.getById("leader06"));
		assertEquals("a", leader.getByLabel("Type of record"));
		assertEquals("a", leader.getTypeOfRecord().getValue());
		assertEquals("Language material", leader.getTypeOfRecord().resolve());

		assertEquals("m", leader.getById("leader07"));
		assertEquals("m", leader.getByLabel("Bibliographic level"));
		assertEquals("m", leader.getBibliographicLevel().getValue());
		assertEquals("Monograph/Item", leader.getBibliographicLevel().resolve());

		assertEquals(" ", leader.getById("leader08"));
		assertEquals(" ", leader.getByLabel("Type of control"));
		assertEquals(" ", leader.getTypeOfControl().getValue());
		assertEquals("No specified type", leader.getTypeOfControl().resolve());

		assertEquals("a", leader.getById("leader09"));
		assertEquals("a", leader.getByLabel("Character coding scheme"));
		assertEquals("a", leader.getCharacterCodingScheme().getValue());
		assertEquals("UCS/Unicode", leader.getCharacterCodingScheme().resolve());

		assertEquals("2", leader.getById("leader10"));
		assertEquals("2", leader.getByLabel("Indicator count"));
		assertEquals("2", leader.getIndicatorCount().getValue());
		assertEquals("2", leader.getIndicatorCount().resolve());

		assertEquals("2", leader.getById("leader11"));
		assertEquals("2", leader.getByLabel("Subfield code count"));
		assertEquals("2", leader.getSubfieldCodeCount().getValue());
		assertEquals("2", leader.getSubfieldCodeCount().resolve());

		assertEquals("00781", leader.getById("leader12"));
		assertEquals("00781", leader.getByLabel("Base address of data"));
		assertEquals("00781", leader.getBaseAddressOfData().getValue());
		assertEquals("00781", leader.getBaseAddressOfData().resolve());

		assertEquals(" ", leader.getById("leader17"));
		assertEquals(" ", leader.getByLabel("Encoding level"));
		assertEquals(" ", leader.getEncodingLevel().getValue());
		assertEquals("Full level", leader.getEncodingLevel().resolve());

		assertEquals(" ", leader.getById("leader18"));
		assertEquals(" ", leader.getByLabel("Descriptive cataloging form"));
		assertEquals(" ", leader.getDescriptiveCatalogingForm().getValue());
		assertEquals("Non-ISBD", leader.getDescriptiveCatalogingForm().resolve());

		assertEquals(" ", leader.getById("leader19"));
		assertEquals(" ", leader.getByLabel("Multipart resource record level"));
		assertEquals(" ", leader.getMultipartResourceRecordLevel().getValue());
		assertEquals("Not specified or not applicable", leader.getMultipartResourceRecordLevel().resolve());

		assertEquals("4", leader.getById("leader20"));
		assertEquals("4", leader.getByLabel("Length of the length-of-field portion"));
		assertEquals("4", leader.getLengthOfTheLengthOfFieldPortion().getValue());
		assertEquals("4", leader.getLengthOfTheLengthOfFieldPortion().resolve());

		assertEquals("5", leader.getById("leader21"));
		assertEquals("5", leader.getByLabel("Length of the starting-character-position portion"));
		assertEquals("5", leader.getLengthOfTheStartingCharacterPositionPortion().getValue());
		assertEquals("5", leader.getLengthOfTheStartingCharacterPositionPortion().resolve());

		assertEquals("0", leader.getById("leader22"));
		assertEquals("0", leader.getByLabel("Length of the implementation-defined portion"));
		assertEquals("0", leader.getLengthOfTheImplementationDefinedPortion().getValue());
		assertEquals("0", leader.getLengthOfTheImplementationDefinedPortion().resolve());
	}

	@Test
	public void test01645nam2200481ir4500() {
		Leader leader = new Leader("01645nam  2200481 ir4500");
		assertNotNull(leader);
		assertEquals(Leader.Type.BOOKS, leader.getType());

		assertEquals("01645", leader.getById("leader00"));
		assertEquals("01645", leader.getByLabel("Record length"));
		assertEquals("01645", leader.getRecordLength().getValue());

		assertEquals("n", leader.getById("leader05"));
		assertEquals("n", leader.getByLabel("Record status"));
		assertEquals("n", leader.getRecordStatus().getValue());
		assertEquals("New", leader.getRecordStatus().resolve());

		assertEquals("a", leader.getById("leader06"));
		assertEquals("a", leader.getByLabel("Type of record"));
		assertEquals("a", leader.getTypeOfRecord().getValue());
		assertEquals("Language material", leader.getTypeOfRecord().resolve());

		assertEquals("m", leader.getById("leader07"));
		assertEquals("m", leader.getByLabel("Bibliographic level"));
		assertEquals("m", leader.getBibliographicLevel().getValue());
		assertEquals("Monograph/Item", leader.getBibliographicLevel().resolve());

		assertEquals(" ", leader.getById("leader08"));
		assertEquals(" ", leader.getByLabel("Type of control"));
		assertEquals(" ", leader.getTypeOfControl().getValue());
		assertEquals("No specified type", leader.getTypeOfControl().resolve());

		assertEquals(" ", leader.getById("leader09"));
		assertEquals(" ", leader.getByLabel("Character coding scheme"));
		assertEquals(" ", leader.getCharacterCodingScheme().getValue());
		assertEquals("MARC-8", leader.getCharacterCodingScheme().resolve());

		assertEquals("2", leader.getById("leader10"));
		assertEquals("2", leader.getByLabel("Indicator count"));
		assertEquals("2", leader.getIndicatorCount().getValue());
		assertEquals("2", leader.getIndicatorCount().resolve());

		assertEquals("2", leader.getById("leader11"));
		assertEquals("2", leader.getByLabel("Subfield code count"));
		assertEquals("2", leader.getSubfieldCodeCount().getValue());
		assertEquals("2", leader.getSubfieldCodeCount().resolve());

		assertEquals("00481", leader.getById("leader12"));
		assertEquals("00481", leader.getByLabel("Base address of data"));
		assertEquals("00481", leader.getBaseAddressOfData().getValue());
		assertEquals("00481", leader.getBaseAddressOfData().resolve());

		assertEquals(" ", leader.getById("leader17"));
		assertEquals(" ", leader.getByLabel("Encoding level"));
		assertEquals(" ", leader.getEncodingLevel().getValue());
		assertEquals("Full level", leader.getEncodingLevel().resolve());

		assertEquals("i", leader.getById("leader18"));
		assertEquals("i", leader.getByLabel("Descriptive cataloging form"));
		assertEquals("i", leader.getDescriptiveCatalogingForm().getValue());
		assertEquals("ISBD punctuation included", leader.getDescriptiveCatalogingForm().resolve());

		assertEquals("r", leader.getById("leader19"));
		assertEquals("r", leader.getByLabel("Multipart resource record level"));
		assertEquals("r", leader.getMultipartResourceRecordLevel().getValue());
		assertEquals("r", leader.getMultipartResourceRecordLevel().resolve());

		assertEquals("4", leader.getById("leader20"));
		assertEquals("4", leader.getByLabel("Length of the length-of-field portion"));
		assertEquals("4", leader.getLengthOfTheLengthOfFieldPortion().getValue());
		assertEquals("4", leader.getLengthOfTheLengthOfFieldPortion().resolve());

		assertEquals("5", leader.getById("leader21"));
		assertEquals("5", leader.getByLabel("Length of the starting-character-position portion"));
		assertEquals("5", leader.getLengthOfTheStartingCharacterPositionPortion().getValue());
		assertEquals("5", leader.getLengthOfTheStartingCharacterPositionPortion().resolve());

		assertEquals("0", leader.getById("leader22"));
		assertEquals("0", leader.getByLabel("Length of the implementation-defined portion"));
		assertEquals("0", leader.getLengthOfTheImplementationDefinedPortion().getValue());
		assertEquals("0", leader.getLengthOfTheImplementationDefinedPortion().resolve());
	}
}
