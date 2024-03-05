package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.analysis.validator.LeaderValidator;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormatter;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class LeaderTest {

  @Test
  public void test00928nama2200265c4500() {
    Marc21Leader leader = new Marc21Leader("00928nam a2200265 c 4500");
    assertNotNull(leader);
    assertEquals(MarcLeader.Type.BOOKS, leader.getType());

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
    assertEquals("Number of character positions used for indicators", leader.getIndicatorCount().resolve());

    assertEquals("2", leader.getById("leader11"));
    assertEquals("2", leader.getByLabel("Subfield code count"));
    assertEquals("2", leader.getSubfieldCodeCount().getValue());
    assertEquals("Number of character positions used for a subfield code", leader.getSubfieldCodeCount().resolve());

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
    assertEquals("Number of characters in the length-of-field portion of a Directory entry", leader.getLengthOfTheLengthOfFieldPortion().resolve());

    assertEquals("5", leader.getById("leader21"));
    assertEquals("5", leader.getByLabel("Length of the starting-character-position portion"));
    assertEquals("5", leader.getLengthOfTheStartingCharacterPositionPortion().getValue());
    assertEquals("Number of characters in the starting-character-position portion of a Directory entry", leader.getLengthOfTheStartingCharacterPositionPortion().resolve());

    assertEquals("0", leader.getById("leader22"));
    assertEquals("0", leader.getByLabel("Length of the implementation-defined portion"));
    assertEquals("0", leader.getLengthOfTheImplementationDefinedPortion().getValue());
    assertEquals("Number of characters in the implementation-defined portion of a Directory entry", leader.getLengthOfTheImplementationDefinedPortion().resolve());
  }

  @Test
  public void test03960cama22007814500() {
    Marc21Leader leader = new Marc21Leader("03960cam a2200781   4500");
    assertNotNull(leader);
    assertEquals(MarcLeader.Type.BOOKS, leader.getType());

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
    assertEquals("Number of character positions used for indicators", leader.getIndicatorCount().resolve());

    assertEquals("2", leader.getById("leader11"));
    assertEquals("2", leader.getByLabel("Subfield code count"));
    assertEquals("2", leader.getSubfieldCodeCount().getValue());
    assertEquals("Number of character positions used for a subfield code", leader.getSubfieldCodeCount().resolve());

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
    assertEquals("Number of characters in the length-of-field portion of a Directory entry", leader.getLengthOfTheLengthOfFieldPortion().resolve());

    assertEquals("5", leader.getById("leader21"));
    assertEquals("5", leader.getByLabel("Length of the starting-character-position portion"));
    assertEquals("5", leader.getLengthOfTheStartingCharacterPositionPortion().getValue());
    assertEquals("Number of characters in the starting-character-position portion of a Directory entry", leader.getLengthOfTheStartingCharacterPositionPortion().resolve());

    assertEquals("0", leader.getById("leader22"));
    assertEquals("0", leader.getByLabel("Length of the implementation-defined portion"));
    assertEquals("0", leader.getLengthOfTheImplementationDefinedPortion().getValue());
    assertEquals("Number of characters in the implementation-defined portion of a Directory entry", leader.getLengthOfTheImplementationDefinedPortion().resolve());

    assertEquals("0", leader.getById("leader23"));
    assertEquals("0", leader.getByLabel("Undefined"));
  }

  @Test
  public void test01645nam2200481ir4500() {
    Marc21Leader leader = new Marc21Leader("01645nam  2200481 ir4500");
    assertNotNull(leader);
    assertEquals(MarcLeader.Type.BOOKS, leader.getType());

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
    assertEquals("Number of character positions used for indicators", leader.getIndicatorCount().resolve());

    assertEquals("2", leader.getById("leader11"));
    assertEquals("2", leader.getByLabel("Subfield code count"));
    assertEquals("2", leader.getSubfieldCodeCount().getValue());
    assertEquals("Number of character positions used for a subfield code", leader.getSubfieldCodeCount().resolve());

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
    assertEquals("Number of characters in the length-of-field portion of a Directory entry", leader.getLengthOfTheLengthOfFieldPortion().resolve());

    assertEquals("5", leader.getById("leader21"));
    assertEquals("5", leader.getByLabel("Length of the starting-character-position portion"));
    assertEquals("5", leader.getLengthOfTheStartingCharacterPositionPortion().getValue());
    assertEquals("Number of characters in the starting-character-position portion of a Directory entry", leader.getLengthOfTheStartingCharacterPositionPortion().resolve());

    assertEquals("0", leader.getById("leader22"));
    assertEquals("0", leader.getByLabel("Length of the implementation-defined portion"));
    assertEquals("0", leader.getLengthOfTheImplementationDefinedPortion().getValue());
    assertEquals("Number of characters in the implementation-defined portion of a Directory entry", leader.getLengthOfTheImplementationDefinedPortion().resolve());
  }

  @Test
  public void testBadLeader() {
    Marc21Leader leader = new Marc21Leader("01136cnm a2200253ui 4500");
    LeaderValidator validator = new LeaderValidator();
    validator.validate(leader);

    assertNotEquals(1, validator.getValidationErrors().size());
    ValidationError error = validator.getValidationErrors().get(0);
    assertNotNull(error);
    assertEquals(ValidationErrorType.RECORD_UNDETECTABLE_TYPE, error.getType());
    assertEquals(
      "Leader/06 (typeOfRecord): 'n', Leader/07 (bibliographicLevel): 'm'",
      error.getMessage());
  }

  @Test
  public void testBadLeaderWithDefaultType() {
    Marc21Leader leader = new Marc21Leader("01136cnm a2200253ui 4500", MarcLeader.Type.BOOKS);
    assertEquals("n", leader.getTypeOfRecord().resolve());
    assertEquals("Monograph/Item", leader.getBibliographicLevel().resolve());
    LeaderValidator validator = new LeaderValidator();
    assertFalse(validator.validate(leader));
    List<ValidationError> errors = validator.getValidationErrors();
    assertFalse(errors.isEmpty());
    assertEquals(2, errors.size());
    assertEquals(
      ",Leader,1,1,undetectable type,\"Leader/06 (typeOfRecord): 'n', Leader/07 (bibliographicLevel): 'm'\",https://www.loc.gov/marc/bibliographic/bdleader.html\n",
      ValidationErrorFormatter.format(
        errors.get(0), ValidationErrorFormat.COMMA_SEPARATED
      )
    );
    assertEquals(
      ",Leader/06 (leader06),2,4,obsolete code,n,https://www.loc.gov/marc/bibliographic/bdleader.html\n",
      ValidationErrorFormatter.format(
        errors.get(1), ValidationErrorFormat.COMMA_SEPARATED
      )
    );
  }
}
