package de.gwdg.metadataqa.marc.utils.marcspec.parser;

import de.gwdg.metadataqa.marc.utils.marcspec.Field;
import de.gwdg.metadataqa.marc.utils.marcspec.MARCspec;
import de.gwdg.metadataqa.marc.utils.marcspec.MARCspecParser;
import de.gwdg.metadataqa.marc.utils.marcspec.SubSpec;
import de.gwdg.metadataqa.marc.utils.marcspec.Subfield;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Issues:
 * - it is not defined what does the left hand and right hand subSpec mean
 * - the context is the record of the field?
 *
 * Suggestion:
 * - left hand: path
 * - right hand: value
 *
 * separate existence check and comparision
 *
 * Make distinction between two cases:
 * - the context is the same (get 020 if its $a is ...)
 * - the context is different (get 020 if there is such 020$a in the
 *                             record for which ...)
 *
 * Operators:
 * ? exists
 * ! not exists
 * = equals
 * != not equals
 * ~ includes
 * !~ not includes
 */
public class MARCspecParser6_subSpecTest {

  @Test
  /**
   * Reference data content of subfield ‘c’ of field ‘020’,
   * if subfield ‘a’ of field ‘020’ exists.
   *
   * operator: existence of
   * right hand: 020$a
   * left hand: 020$s
   */
  public void testMARCspecParserB_Example27() {
    MARCspecParser parser = new MARCspecParser();
    String[] specs = new String[]{"020$s{?020$a}", "020$s{020$s?020$a}"};
    for (String spec : specs) {
      MARCspec marcSpec = parser.parse(spec);

      assertEquals("020", marcSpec.getField().getTag());
      assertEquals("s", marcSpec.getSubfields().get(0).getTag());
      assertEquals(1, marcSpec.getSubfields().get(0).getSubSpecs().size());

      SubSpec subSpec = marcSpec.getSubfields().get(0).getSubSpecs().get(0);
      assertEquals("020", subSpec.getLeftSubTerm().getMarcSpec().getField().getTag());
      assertEquals("s", subSpec.getLeftSubTerm().getMarcSpec().getSubfields().get(0).getTag());
      assertEquals("?", subSpec.getOperator());
      assertEquals("020", subSpec.getRightSubTerm().getMarcSpec().getField().getTag());
      assertEquals("a", subSpec.getRightSubTerm().getMarcSpec().getSubfields().get(0).getTag());
    }
  }

  @Test
  /**
   * Reference data content of subfield ‘z’ of field ‘020’,
   * if subfield ‘a’ of field ‘020’ does not exist.
   *
   * operator: non-existence of
   * right hand: 020$a
   * left hand: 020$z
   */
  public void testMARCspecParserB_Example28() {
    MARCspecParser parser = new MARCspecParser();
    String[] specs = new String[]{"020$z{!020$a}", "020$z{020$z!020$a}"};
    for (String spec : specs) {
      MARCspec marcSpec = parser.parse(spec);

      assertEquals("020", marcSpec.getField().getTag());
      assertEquals("z", marcSpec.getSubfields().get(0).getTag());
      assertEquals(1, marcSpec.getSubfields().get(0).getSubSpecs().size());

      SubSpec subSpec = marcSpec.getSubfields().get(0).getSubSpecs().get(0);
      MARCspec leftMarcSpec = subSpec.getLeftSubTerm().getMarcSpec();
      assertEquals("020", leftMarcSpec.getField().getTag());
      assertEquals("z", leftMarcSpec.getSubfields().get(0).getTag());

      assertEquals("!", subSpec.getOperator());

      MARCspec rightMarcSpec = subSpec.getRightSubTerm().getMarcSpec();
      assertEquals("020", rightMarcSpec.getField().getTag());
      assertEquals("a", rightMarcSpec.getSubfields().get(0).getTag());
    }
  }

  @Test
  /**
   * Reference to character with position ‘18’ of field ‘008’,
   * if character with position ‘06’ in Leader equals ‘t’ (Books).
   *
   * operator: =
   * left: LDR/6
   * rigth: t
   */
  public void testMARCspecParserB_Example29() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("008/18{LDR/6=\\t}");

    assertEquals("008", marcSpec.getField().getTag());
    assertEquals(0, marcSpec.getSubfields().size());
    assertEquals(1, marcSpec.getField().getSubSpecs().size());

    SubSpec subSpec = marcSpec.getField().getSubSpecs().get(0);
    assertEquals("LDR", subSpec.getLeftSubTerm().getMarcSpec().getField().getTag());
    assertEquals(0, subSpec.getLeftSubTerm().getMarcSpec().getSubfields().size());

    Field field = subSpec.getLeftSubTerm().getMarcSpec().getField();
    assertEquals("LDR", field.getTag());
    assertNull(field.getStartIndex());
    assertNotNull(field.getCharacterPositions());
    assertEquals(6, field.getCharacterPositions().getStart().value());
    assertFalse(field.getCharacterPositions().isRange());

    assertEquals("=", subSpec.getOperator());

    assertEquals("t", subSpec.getRightSubTerm().getComparisonString().getRaw());
  }

  @Test
  /**
   * Reference to subfield ‘b’ of field ‘245’,
   * if character with position ‘0’ of field 007 equals ‘a’ OR ‘t’.
   *
   * [007/0 = a] OR [007/0 = t]
   */
  public void testMARCspecParserB_Example30() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("245$b{007/0=\\a|007/0=\\t}");

    assertEquals("245", marcSpec.getField().getTag());
    assertEquals(1, marcSpec.getSubfields().size());
    assertEquals("b", marcSpec.getSubfields().get(0).getTag());
    assertEquals(2, marcSpec.getSubfields().get(0).getSubSpecs().size());

    List<SubSpec> subspecs = marcSpec.getSubfields().get(0).getSubSpecs();

    SubSpec subSpec0 = subspecs.get(0);
    assertEquals("007", subSpec0.getLeftSubTerm().getMarcSpec().getField().getTag());
    assertEquals(0, (int) subSpec0.getLeftSubTerm().getMarcSpec().getField().getCharacterPositions().getStart().getPositionInt());
    assertEquals("=", subSpec0.getOperator());
    assertEquals("a", subSpec0.getRightSubTerm().getComparisonString().getRaw());

    SubSpec subSpec1 = subspecs.get(1);
    assertEquals("007", subSpec1.getLeftSubTerm().getMarcSpec().getField().getTag());
    assertEquals(0, (int) subSpec1.getLeftSubTerm().getMarcSpec().getField().getCharacterPositions().getStart().getPositionInt());
    assertEquals("=", subSpec1.getOperator());
    assertEquals("t", subSpec1.getRightSubTerm().getComparisonString().getRaw());
  }

  @Test
  /**
   * Reference to character with position ‘18’ of field ‘008’,
   * if  character with position ‘06’ in Leader equals ‘a’
   * AND character with position ‘07’ in Leader equals ‘a’, ‘c’, ‘d’ OR ‘m’.
   *
   * [LDR/6 = a]
   * AND [
   *   [LDR/7 = a] OR [LDR/7 = c] OR [LDR/7 = d] OR LDR/7 = m]
   * ]
   * TODO: this logic is not implemented yet
   */
  public void testMARCspecParserB_Example31() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("008/18{LDR/6=\\a}{LDR/7=\\a|LDR/7=\\c|LDR/7=\\d|LDR/7=\\m}");

    assertEquals("008", marcSpec.getField().getTag());
    assertEquals(0, marcSpec.getSubfields().size());
    assertEquals(18, (int)marcSpec.getField().getCharacterPositions().getStart().getPositionInt());
    assertEquals(5, marcSpec.getField().getSubSpecs().size());
    List<SubSpec> subspecs = marcSpec.getField().getSubSpecs();

    SubSpec subspec = subspecs.get(0);
    assertEquals("LDR", subspec.getLeftSubTerm().getMarcSpec().getField().getTag());
    assertEquals(6, (int)subspec.getLeftSubTerm().getMarcSpec().getField().getCharacterPositions().getStart().getPositionInt());
    assertEquals("=", subspec.getOperator());
    assertEquals("a", subspec.getRightSubTerm().getComparisonString().getRaw());

    subspec = subspecs.get(1);
    assertEquals("LDR", subspec.getLeftSubTerm().getMarcSpec().getField().getTag());
    assertEquals(7, (int)subspec.getLeftSubTerm().getMarcSpec().getField().getCharacterPositions().getStart().getPositionInt());
    assertEquals("=", subspec.getOperator());
    assertEquals("a", subspec.getRightSubTerm().getComparisonString().getRaw());

    subspec = subspecs.get(2);
    assertEquals("LDR", subspec.getLeftSubTerm().getMarcSpec().getField().getTag());
    assertEquals(7, (int)subspec.getLeftSubTerm().getMarcSpec().getField().getCharacterPositions().getStart().getPositionInt());
    assertEquals("=", subspec.getOperator());
    assertEquals("c", subspec.getRightSubTerm().getComparisonString().getRaw());

    subspec = subspecs.get(3);
    assertEquals("LDR", subspec.getLeftSubTerm().getMarcSpec().getField().getTag());
    assertEquals(7, (int)subspec.getLeftSubTerm().getMarcSpec().getField().getCharacterPositions().getStart().getPositionInt());
    assertEquals("=", subspec.getOperator());
    assertEquals("d", subspec.getRightSubTerm().getComparisonString().getRaw());

    subspec = subspecs.get(4);
    assertEquals("LDR", subspec.getLeftSubTerm().getMarcSpec().getField().getTag());
    assertEquals(7, (int)subspec.getLeftSubTerm().getMarcSpec().getField().getCharacterPositions().getStart().getPositionInt());
    assertEquals("=", subspec.getOperator());
    assertEquals("m", subspec.getRightSubTerm().getComparisonString().getRaw());
  }

  @Test
  /**
   * Reference data content of subfield ‘a’ of field ‘880’,
   * if data content of subfield ‘6’ of field ‘100’
   *   includes
   *     the string ‘-01’ (characters with index range 3-5 of field ‘800’)
   *     and the string ‘880’.
   *
   * [ 100$6 ~ 880$6/3-5 ]
   * AND
   * [ 100$6 ~ 880 ]
   */
  public void testMARCspecParserB_Example32() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("880$a{100$6~$6/3-5}{100$6~\\880}");

    assertEquals("880", marcSpec.getField().getTag());
    assertEquals("a", marcSpec.getSubfields().get(0).getTag());
    assertEquals(2, marcSpec.getSubfields().get(0).getSubSpecs().size());
    List<SubSpec> subspecs = marcSpec.getSubfields().get(0).getSubSpecs();

    SubSpec subspec = subspecs.get(0);
    MARCspec leftMarcSpec = subspec.getLeftSubTerm().getMarcSpec();
    assertEquals("100", leftMarcSpec.getField().getTag());
    assertEquals("6", leftMarcSpec.getSubfields().get(0).getTag());

    assertEquals("~", subspec.getOperator());

    MARCspec rightMarcSpec = subspec.getRightSubTerm().getMarcSpec();
    assertEquals("880", rightMarcSpec.getField().getTag());
    Subfield subfield = rightMarcSpec.getSubfields().get(0);
    assertEquals("6", subfield.getTag());
    assertEquals(3, subfield.getCharacterPositions().getStart().value());
    assertEquals(5, subfield.getCharacterPositions().getEnd().value());

    subspec = subspecs.get(1);
    leftMarcSpec = subspec.getLeftSubTerm().getMarcSpec();
    assertEquals("100", leftMarcSpec.getField().getTag());
    assertEquals("6", leftMarcSpec.getSubfields().get(0).getTag());

    assertEquals("~", subspec.getOperator());

    assertNull(subspec.getRightSubTerm().getMarcSpec());
    assertEquals("880", subspec.getRightSubTerm().getComparisonString().getRaw());
  }
}
