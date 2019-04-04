package de.gwdg.metadataqa.marc.utils.marcspec.parser;

import de.gwdg.metadataqa.marc.utils.marcspec.Field;
import de.gwdg.metadataqa.marc.utils.marcspec.MARCspec;
import de.gwdg.metadataqa.marc.utils.marcspec.MARCspecParser;
import de.gwdg.metadataqa.marc.utils.marcspec.SubSpec;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * For me it is rather shortened (or implicit) notation than abbreviation
 */
public class MARCspecParser7_abbreviationTest {

  @Test
  /**
   * 007[1]/3{/0=\v}
   * Reference to third character of second field ‘007’,
   * if first character of second field ‘007’ equals ‘v’.
   */
  public void testMARCspecParserB_Example33() {
    MARCspecParser parser = new MARCspecParser();
    String[] specs = new String[]{"007[1]/3{/0=\\v}", "007[1]/3{007[1]/0=\\v}"};
    for (String specString : specs) {
      MARCspec marcSpec = parser.parse(specString);

      assertEquals("007", marcSpec.getField().getTag());
      assertEquals(0, marcSpec.getSubfields().size());
      assertEquals(3, marcSpec.getField().getCharacterPositions().getStart().value());
      assertEquals(1, marcSpec.getField().getStartIndex().value());
      assertEquals(1, marcSpec.getField().getSubSpecs().size());
      List<SubSpec> subspecs = marcSpec.getField().getSubSpecs();

      SubSpec subspec = subspecs.get(0);
      Field field = subspec.getLeftSubTerm().getMarcSpec().getField();
      assertEquals("007", field.getTag());
      assertEquals(1, field.getStartIndex().value());
      assertEquals(0, field.getCharacterPositions().getStart().value());

      assertEquals("=", subspec.getOperator());

      assertEquals("v", subspec.getRightSubTerm().getComparisonString().getRaw());
    }
  }

  @Test
  /**
   * Reference to data content of subfield ‘c’ of field 020,
   * if subfield ‘a’ of field ‘020’ exists.
   *
   * COMMENT: is it mentioned that ? is the default operator?
   */
  public void testMARCspecParserB_Example34() {
    MARCspecParser parser = new MARCspecParser();
    String[] specs = new String[]{"020$c{$a}", "020$c{020$a}"};
    for (String specString : specs) {
      MARCspec marcSpec = parser.parse(specString);

      assertEquals("020", marcSpec.getField().getTag());
      assertEquals(1, marcSpec.getSubfields().size());
      assertEquals("c", marcSpec.getSubfields().get(0).getTag());
      List<SubSpec> subspecs = marcSpec.getSubfields().get(0).getSubSpecs();

      SubSpec subspec = subspecs.get(0);
      assertEquals("020", subspec.getLeftSubTerm().getMarcSpec().getField().getTag());
      assertEquals("c", subspec.getLeftSubTerm().getMarcSpec().getSubfields().get(0).getTag());

      assertEquals("?", subspec.getOperator());

      assertEquals("020", subspec.getRightSubTerm().getMarcSpec().getField().getTag());
      assertEquals("a", subspec.getRightSubTerm().getMarcSpec().getSubfields().get(0).getTag());
    }
  }

  @Test
  /**
   * Reference of data content of subfield ‘a’ of field ‘245’,
   * if last character of the preceding spec equals the
   * comparisonString ‘/’.
   */
  public void testMARCspecParserB_Example35() {
    MARCspecParser parser = new MARCspecParser();
    String[] specs = new String[]{"245$a{/#=\\/}", "245$a{245$a/#=\\/}"};
    for (String specString : specs) {
      MARCspec marcSpec = parser.parse(specString);

      assertEquals("245", marcSpec.getField().getTag());
      assertEquals(1, marcSpec.getSubfields().size());
      assertEquals("a", marcSpec.getSubfields().get(0).getTag());
      assertEquals(1, marcSpec.getSubfields().get(0).getSubSpecs().size());
      List<SubSpec> subspecs = marcSpec.getSubfields().get(0).getSubSpecs();

      SubSpec subspec = subspecs.get(0);
      assertEquals("245", subspec.getLeftSubTerm().getMarcSpec().getField().getTag());
      assertEquals("a", subspec.getLeftSubTerm().getMarcSpec().getSubfields().get(0).getTag());
      assertEquals("#", subspec.getLeftSubTerm().getMarcSpec().getSubfields().get(0).getCharacterPositions().getStart().getPositionString());
      assertEquals("=", subspec.getOperator());
      assertEquals("/", subspec.getRightSubTerm().getComparisonString().getRaw());
    }
  }

  @Test
  /**
   * Reference to data of the first field ‘800’,
   * having ‘1’ as value for indicator 2
   * and data content of subfield ‘a’ includes the comparisonString ‘Poe’.
   *
   * COMMENT: it should be ~\1 and =\1 instead of ~1 and =1
   */
  public void testMARCspecParserB_Example36() {
    MARCspecParser parser = new MARCspecParser();
    String[] specs = new String[]{
      "800[0]{$a~\\Poe}{^2=\\1}",
      "800[0]{800[0]$a~\\Poe}{800[0]^2=\\1}"
    };
    for (String specString : specs) {
      MARCspec marcSpec = parser.parse(specString);

      assertEquals("800", marcSpec.getField().getTag());
      assertEquals(0, marcSpec.getSubfields().size());
      assertEquals(0, marcSpec.getField().getStartIndex().value());

      List<SubSpec> subspecs = marcSpec.getField().getSubSpecs();
      assertNotNull(subspecs);
      assertEquals(2, subspecs.size());

      SubSpec subspec = subspecs.get(0);
      MARCspec left = subspec.getLeftSubTerm().getMarcSpec();
      assertEquals("800", left.getField().getTag());
      assertEquals(0, left.getField().getStartIndex().value());
      assertNull(left.getField().getEndIndex());
      assertEquals(1, left.getSubfields().size());
      assertEquals("a", left.getSubfields().get(0).getTag());

      assertEquals("~", subspec.getOperator());

      assertEquals("Poe", subspec.getRightSubTerm().getComparisonString().getRaw());

      subspec = subspecs.get(1);
      left = subspec.getLeftSubTerm().getMarcSpec();
      assertEquals("800", left.getField().getTag());
      assertEquals(0, left.getField().getStartIndex().value());
      assertNull(left.getField().getEndIndex());
      assertEquals(0, left.getSubfields().size());
      assertFalse(left.getField().hasIndicator1());
      assertTrue(left.getField().hasIndicator2());

      assertEquals("=", subspec.getOperator());

      assertEquals("1", subspec.getRightSubTerm().getComparisonString().getRaw());
    }
  }

  /**
   * Implicit abbreviations
   */

  @Test
  public void testMARCspecParserB_Example37() {
    MARCspecParser parser = new MARCspecParser();
    String[] specs = new String[]{"245$a{/0-2=\\The}", "245$a{245$a/0-2=\\The}"};
    for (String specString : specs) {
      MARCspec marcSpec = parser.parse(specString);

      assertEquals("245", marcSpec.getField().getTag());
      assertEquals(1, marcSpec.getSubfields().size());
      assertEquals("a", marcSpec.getSubfields().get(0).getTag());

      SubSpec subspec = marcSpec.getSubfields().get(0).getSubSpecs().get(0);
      MARCspec left = subspec.getLeftSubTerm().getMarcSpec();
      assertEquals(0, left.getSubfields().get(0).getCharacterPositions().getStart().value());
      assertEquals(2, left.getSubfields().get(0).getCharacterPositions().getEnd().value());

      assertEquals("=", subspec.getOperator());

      assertEquals("The", subspec.getRightSubTerm().getComparisonString().getRaw());
    }
  }

  @Test
  public void testMARCspecParserB_Example38() {
    MARCspecParser parser = new MARCspecParser();
    String[] specs = new String[]{"020$q{$c}", "020[0-#]$q[0-#]{$c[0-#]}"};
    for (String specString : specs) {
      MARCspec marcSpec = parser.parse(specString);

      assertEquals("020", marcSpec.getField().getTag());
      assertEquals(1, marcSpec.getSubfields().size());
      assertEquals("q", marcSpec.getSubfields().get(0).getTag());

      SubSpec subspec = marcSpec.getSubfields().get(0).getSubSpecs().get(0);
      MARCspec left = subspec.getLeftSubTerm().getMarcSpec();
      assertEquals("020", left.getField().getTag());
      assertEquals(1, left.getSubfields().size());
      assertEquals("q", left.getSubfields().get(0).getTag());

      assertEquals("?", subspec.getOperator());

      MARCspec right = subspec.getRightSubTerm().getMarcSpec();
      assertEquals("020", right.getField().getTag());
      assertEquals(1, right.getSubfields().size());
      assertEquals("c", right.getSubfields().get(0).getTag());
    }
  }

  @Test
  public void testMARCspecParserB_Example39() {
    MARCspecParser parser = new MARCspecParser();
    String[] specs = new String[]{"020$c{$q=\\paperback}", "020[0-#]$c[0-#]{$q[0-#]=\\paperback}"};
    for (String specString : specs) {
      MARCspec marcSpec = parser.parse(specString);

      assertEquals("020", marcSpec.getField().getTag());
      assertEquals(1, marcSpec.getSubfields().size());
      assertEquals("c", marcSpec.getSubfields().get(0).getTag());

      SubSpec subspec = marcSpec.getSubfields().get(0).getSubSpecs().get(0);
      MARCspec left = subspec.getLeftSubTerm().getMarcSpec();
      assertEquals("020", left.getField().getTag());
      assertEquals(1, left.getSubfields().size());
      assertEquals("q", left.getSubfields().get(0).getTag());

      assertEquals("=", subspec.getOperator());

      assertEquals("paperback", subspec.getRightSubTerm().getComparisonString().getRaw());
    }
  }
}
