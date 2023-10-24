package de.gwdg.metadataqa.marc.utils.marcspec;

import de.gwdg.metadataqa.marc.utils.marcspec.exception.InvalidMARCspecException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Specification:
 * Carsten Klee (ZDB): MARCspec - A common MARC record path language
 * https://marcspec.github.io/MARCspec/marc-spec.html
 */
public class MARCspecParserTest {

  private void build(String fieldspec) {
    // List<String> _fieldGroups = Arrays.asList("field", "tag", "index", "charpos", "indicators", "subfields");
    if (!MARCspecParser.FIELD.matcher(fieldspec).matches()) {
      throw new InvalidMARCspecException(InvalidMARCspecException.FS + InvalidMARCspecException.MISSINGFIELD, fieldspec);
    }
  }

  @Test
  public void test_build() {
    build("245");
  }

  @Test
  public void test_FIELDTAG() {
    assertTrue(MARCspecParser.FIELDTAG.matcher("245").matches());
    assertTrue(MARCspecParser.FIELDTAG.matcher("008").matches());
  }

  @Test
  public void test_POSITIONORRANGE() {
    assertTrue(MARCspecParser.POSITION_OR_RANGE.matcher("1").matches());
    assertTrue(MARCspecParser.POSITION_OR_RANGE.matcher("12").matches());
    assertTrue(MARCspecParser.POSITION_OR_RANGE.matcher("#").matches());
    assertTrue(MARCspecParser.POSITION_OR_RANGE.matcher("1-2").matches());
    assertTrue(MARCspecParser.POSITION_OR_RANGE.matcher("#-#").matches());
  }

  @Test
  public void test_CHARPOS() {
    Matcher matcher;

    matcher = MARCspecParser.CHARPOS.matcher("/1");
    assertTrue(matcher.matches());
    assertEquals("1", matcher.group("charpos"));

    matcher = MARCspecParser.CHARPOS.matcher("/12");
    assertTrue(matcher.matches());
    assertEquals("12", matcher.group("charpos"));

    matcher = MARCspecParser.CHARPOS.matcher("/#");
    assertTrue(matcher.matches());
    assertEquals("#", matcher.group("charpos"));

    matcher = MARCspecParser.CHARPOS.matcher("/1-2");
    assertTrue(matcher.matches());
    assertEquals("1-2", matcher.group("charpos"));

    matcher = MARCspecParser.CHARPOS.matcher("/#-#");
    assertTrue(matcher.matches());
    assertEquals("#-#", matcher.group("charpos"));
  }

  @Test
  public void test_INDICATORS() {
    Matcher matcher;
    String name = "indicators";

    matcher = MARCspecParser.INDICATORS.matcher("^a");
    assertFalse(matcher.matches());
    // assertEquals("a", matcher.group(name));

    matcher = MARCspecParser.INDICATORS.matcher("_ab");
    assertFalse(matcher.matches());
    // assertEquals("ab", matcher.group(name));

    matcher = MARCspecParser.INDICATORS.matcher("^1");
    assertTrue(matcher.matches());
    assertEquals("1", matcher.group(name));

    matcher = MARCspecParser.INDICATORS.matcher("^2");
    assertTrue(matcher.matches());
    assertEquals("2", matcher.group(name));
  }

  @Test
  public void test_INDEX() {
    Matcher matcher;
    Matcher positionMatcher;

    matcher = MARCspecParser.INDEX.matcher("[1]");
    assertTrue(matcher.matches());
    assertEquals("1", matcher.group("index"));
    assertEquals("[1]", matcher.group(0));
    assertEquals("1", matcher.group(1));
    positionMatcher = MARCspecParser.NAMED_POSITION_OR_RANGE.matcher(matcher.group("index"));
    assertTrue(positionMatcher.matches());
    assertNull(positionMatcher.group("start"));
    assertNull(positionMatcher.group("end"));
    assertNotNull(positionMatcher.group("single"));
    assertEquals("1", positionMatcher.group("single"));

    matcher = MARCspecParser.INDEX.matcher("[12]");
    assertTrue(matcher.matches());
    assertEquals("12", matcher.group("index"));

    matcher = MARCspecParser.INDEX.matcher("[#]");
    assertTrue(matcher.matches());
    assertEquals("#", matcher.group("index"));

    matcher = MARCspecParser.INDEX.matcher("[1-2]");
    assertTrue(matcher.matches());
    assertEquals("1-2", matcher.group("index"));
    positionMatcher = MARCspecParser.NAMED_POSITION_OR_RANGE.matcher(matcher.group("index"));
    assertTrue(positionMatcher.matches());
    assertNotNull(positionMatcher.group("start"));
    assertEquals("1", positionMatcher.group("start"));
    assertNotNull(positionMatcher.group("end"));
    assertEquals("2", positionMatcher.group("end"));
    assertNull(positionMatcher.group("single"));

    matcher = MARCspecParser.INDEX.matcher("[#-#]");
    assertTrue(matcher.matches());
    assertEquals("#-#", matcher.group("index"));
  }

  @Test
  public void test_SUBSPECS() {
    Matcher matcher;

    matcher = MARCspecParser.SUBSPECS.matcher("{$a!=$b}");
    assertTrue(matcher.matches());
    assertEquals("{$a!=$b}", matcher.group("subspecs"));

  }

  @Test
  public void test_SUBFIELDS() {
    Matcher matcher;

    matcher = MARCspecParser.SUBFIELDS.matcher("$a");
    assertTrue(matcher.matches());
    assertEquals("$a", matcher.group("subfields"));

    matcher = MARCspecParser.SUBFIELDS.matcher("$a]");
    assertTrue(matcher.matches());
    assertEquals("$a]", matcher.group("subfields"));

    matcher = MARCspecParser.SUBFIELDS.matcher("$d{$c/#=\\.}{?$a}");
    assertTrue(matcher.matches());
    assertEquals("$d{$c/#=\\.}{?$a}", matcher.group("subfields"));
  }

  @Test
  public void test_SUBFIELD() {
    Matcher matcher;

    matcher = MARCspecParser.SUBFIELD.matcher("$d{$c/#=\\.}{?$a}");
    assertTrue(matcher.matches());
    assertEquals("$d{$c/#=\\.}{?$a}", matcher.group("subfield"));
    assertEquals(null, matcher.group("subfieldtagrange"));
    assertEquals("d", matcher.group("subfieldtag"));
    assertEquals(null, matcher.group("index"));
    assertEquals(null, matcher.group("charpos"));
    assertEquals("{$c/#=\\.}{?$a}", matcher.group("subspecs"));

    matcher = MARCspecParser.SUBFIELD.matcher("$a-b{$c/#=\\.}$c-d{?$a}");
    assertTrue(matcher.groupCount() > 1);

    var i = 0;
    while (matcher.find()) {
      if (i++ == 0) {
        assertEquals("$a-b{$c/#=\\.}", matcher.group("subfield"));
        assertEquals("a-b", matcher.group("subfieldtagrange"));
        assertEquals(null, matcher.group("subfieldtag"));
        assertEquals(null, matcher.group("index"));
        assertEquals(null, matcher.group("charpos"));
        assertEquals("{$c/#=\\.}", matcher.group("subspecs"));
      } else {
        assertEquals("$c-d{?$a}", matcher.group("subfield"));
        assertEquals("c-d", matcher.group("subfieldtagrange"));
        assertEquals(null, matcher.group("subfieldtag"));
        assertEquals(null, matcher.group("index"));
        assertEquals(null, matcher.group("charpos"));
        assertEquals("{?$a}", matcher.group("subspecs"));
      }
    }

    matcher = MARCspecParser.SUBFIELD.matcher("a-b");
    assertTrue(matcher.groupCount() > 1);
    assertFalse(matcher.find());
  }

  @Test
  public void test_NAMED_SUBFIELDTAGRANGE() {
    Matcher matcher;
    matcher = MARCspecParser.NAMED_SUBFIELDTAGRANGE.matcher("a-c");
    assertTrue(matcher.matches());
    assertEquals("a", matcher.group("start"));
    assertEquals("c", matcher.group("end"));
  }

  @Test
  public void test_FIELD() {
    Matcher matcher;

    matcher = MARCspecParser.FIELD.matcher("245");
    assertTrue(matcher.matches());
    assertEquals("245", matcher.group("field"));

    matcher = MARCspecParser.FIELD.matcher("245[0]");
    assertTrue("should be match", matcher.matches());
    assertEquals("245[0]", matcher.group("field"));
    assertEquals(9, matcher.groupCount());
    assertEquals("245", matcher.group("tag"));
    assertEquals("0", matcher.group("index"));
    assertEquals(null, matcher.group("charpos"));
    assertEquals(null, matcher.group("indicators"));
    assertEquals(null, matcher.group("subfields"));
    assertEquals("", matcher.group("subspecs"));

    matcher = MARCspecParser.FIELD.matcher("245[0]$a");
    assertTrue("should be match", matcher.matches());
    assertEquals("245[0]$a", matcher.group("field"));

    assertEquals(9, matcher.groupCount());
    assertEquals("245", matcher.group("tag"));
    assertEquals("0", matcher.group("index"));
    assertEquals(null, matcher.group("charpos"));
    assertEquals(null, matcher.group("indicators"));
    assertEquals("$a", matcher.group("subfields"));
    assertEquals("", matcher.group("subspecs"));
  }

  @Test
  public void test_SUBTERMS() {
    Matcher matcher;

    matcher = MARCspecParser.SUBTERMS.matcher("$c/#=\\.");
    assertTrue("should be match", matcher.matches());
    assertEquals("$c/#", matcher.group("leftsubterm"));
    assertEquals("=", matcher.group("operator"));
    assertEquals("\\.", matcher.group("rightsubterm"));


    matcher = MARCspecParser.SUBTERMS.matcher("?$a");
    assertTrue("should be match", matcher.matches());
    assertEquals(null, matcher.group("leftsubterm"));
    assertEquals("?", matcher.group("operator"));
    assertEquals("$a", matcher.group("rightsubterm"));

    matcher = MARCspecParser.SUBTERMS.matcher("300_01$a!~\\abc");
    assertTrue("should be match", matcher.matches());
    assertEquals("300_01$a", matcher.group("leftsubterm"));
    assertEquals("!~", matcher.group("operator"));
    assertEquals("\\abc", matcher.group("rightsubterm"));
  }

  @Test
  public void testNamed() {
    Pattern FIELDTAG = Pattern.compile("^(?<tag>(?:[a-z0-9\\.]{3,3}|[A-Z0-9\\.]{3,3}|[0-9\\.]{3,3}))?"); // ?<tag>

    Matcher matcher = FIELDTAG.matcher("245");
    assertTrue(matcher.matches());
    assertEquals("245", matcher.group());
    assertEquals("245", matcher.group("tag"));
  }

  @Test
  public void testNegativeLookbehind() {
    Pattern pattern;
    Matcher mat;

    pattern = Pattern.compile("\\{.+?\\}");
    mat = pattern.matcher("{a}");
    assertTrue(mat.matches());
    assertEquals("{a}", mat.group(0));

    pattern = Pattern.compile("(?<subspecs>\\{.+?\\})");
    mat = pattern.matcher("{a}");
    assertTrue(mat.matches());
    assertEquals("{a}", mat.group(0));
    assertEquals("{a}", mat.group("subspecs"));

    pattern = Pattern.compile("(?<subspecs>(?:\\{.+?\\})*)");
    mat = pattern.matcher("{a}");
    assertTrue(mat.matches());
    assertEquals("{a}", mat.group(0));
    assertEquals("{a}", mat.group("subspecs"));

    pattern = Pattern.compile("a\\$b");
    mat = pattern.matcher("a$b");
    assertTrue(mat.matches());

    pattern = Pattern.compile("(?<subspecs>(?:\\{.+?(?<!(\\$|\\\\))\\})*)");
    mat = pattern.matcher("{a}");
    assertTrue(mat.matches());
    assertEquals("{a}", mat.group(0));
    assertEquals("{a}", mat.group("subspecs"));

    pattern = Pattern.compile("(?<subspecs>(?:\\{.+?(?<!(?<!(\\$|\\\\))(\\$|\\\\))\\})*)");
    mat = pattern.matcher("{$a!=$b}");
    assertTrue(mat.matches());
    assertEquals(3, mat.groupCount());
    assertEquals("{$a!=$b}", mat.group(0));
    assertEquals("{$a!=$b}", mat.group("subspecs"));
  }

  @Test
  public void test_OPERATOR() {
    Matcher matcher;

    matcher = MARCspecParser.OPERATOR.matcher("!=");
    assertTrue(matcher.matches());
    assertEquals("!=", matcher.group("operator"));
    assertEquals("!=", matcher.group(0));

    matcher = MARCspecParser.OPERATOR.matcher("!~");
    assertTrue(matcher.matches());
    assertEquals("!~", matcher.group("operator"));

    matcher = MARCspecParser.OPERATOR.matcher("=");
    assertTrue(matcher.matches());
    assertEquals("=", matcher.group("operator"));

    matcher = MARCspecParser.OPERATOR.matcher("~");
    assertTrue(matcher.matches());
    assertEquals("~", matcher.group("operator"));

    matcher = MARCspecParser.OPERATOR.matcher("!");
    assertTrue(matcher.matches());
    assertEquals("!", matcher.group("operator"));

    matcher = MARCspecParser.OPERATOR.matcher("?");
    assertTrue(matcher.matches());
    assertEquals("?", matcher.group("operator"));

    matcher = MARCspecParser.OPERATOR.matcher("+");
    assertFalse(matcher.matches());

    matcher = MARCspecParser.OPERATOR.matcher("<");
    assertFalse(matcher.matches());

    matcher = MARCspecParser.OPERATOR.matcher("==");
    assertFalse(matcher.matches());

    matcher = MARCspecParser.OPERATOR.matcher("!==");
    assertFalse(matcher.matches());
  }

  @Test
  public void testPatternNames() {
    Map<Pattern, List<String>> map = MARCspecParser.getPatternNames();
    assertEquals(Arrays.asList("tag"),
      map.get(MARCspecParser.FIELDTAG));
    assertEquals(Arrays.asList(),
      map.get(MARCspecParser.POSITION_OR_RANGE));
    assertEquals(Arrays.asList("start", "end", "single"),
      map.get(MARCspecParser.NAMED_POSITION_OR_RANGE));
    assertEquals(Arrays.asList("index"),
      map.get(MARCspecParser.INDEX));
    assertEquals(Arrays.asList("charpos"),
      map.get(MARCspecParser.CHARPOS));
    assertEquals(Arrays.asList("indicators"),
      map.get(MARCspecParser.INDICATORS));
    assertEquals(Arrays.asList("subspecs"),
      map.get(MARCspecParser.SUBSPECS));
    assertEquals(Arrays.asList("field", "tag", "index", "charpos", "indicators", "subspecs", "subfields"),
      map.get(MARCspecParser.FIELD));
    assertEquals(Arrays.asList("subfieldtagrange"),
      map.get(MARCspecParser.SUBFIELDTAGRANGE));
    assertEquals(Arrays.asList("subfieldtag"),
      map.get(MARCspecParser.SUBFIELDTAG));
    assertEquals(Arrays.asList("subfield", "subfieldtagrange", "subfieldtag", "index", "charpos", "subspecs"),
      map.get(MARCspecParser.SUBFIELD));
    assertEquals(Arrays.asList("leftsubterm"),
      map.get(MARCspecParser.LEFTSUBTERM));
    assertEquals(Arrays.asList("operator"),
      map.get(MARCspecParser.OPERATOR));
    assertEquals(Arrays.asList("leftsubterm", "operator", "rightsubterm"),
      map.get(MARCspecParser.SUBTERMS));
    assertEquals(Arrays.asList(),
      map.get(MARCspecParser.SUBSPEC));
  }

  @Test
  public void test_subspecs() {
    Matcher matcher;
    Pattern delimiter = Pattern.compile("(?<!\\\\)\\|");
    matcher = MARCspecParser.SUBFIELD.matcher("$a{$c|!$d}");
    assertTrue(matcher.matches());
    assertEquals("{$c|!$d}", matcher.group("subspecs"));

    matcher = MARCspecParser.SUBSPEC.matcher(matcher.group("subspecs"));

    List<List<String>> subspecs = new ArrayList<>();
    while (matcher.find()) {
      // List<String> _subspecs = Arrays.asList(matcher.group(1));
      // subspecs.add(_subspecs);
      String _subspec = matcher.group(1);
      subspecs.add(Arrays.asList(_subspec.split(delimiter.pattern())));
    }
    assertEquals(1, subspecs.size());
    assertEquals(2, subspecs.get(0).size());
    assertEquals("$c", subspecs.get(0).get(0));
    assertEquals("!$d", subspecs.get(0).get(1));

    matcher = MARCspecParser.SUBFIELD.matcher("$a{$c/#=\\.}{?$a}");
    assertTrue(matcher.matches());
    matcher = MARCspecParser.SUBSPEC.matcher(matcher.group("subspecs"));
    subspecs = new ArrayList<>();
    while (matcher.find()) {
      String _subspec = matcher.group(1);
      subspecs.add(Arrays.asList(_subspec.split(delimiter.pattern())));
    }
    assertEquals(2, subspecs.size());
    assertEquals(1, subspecs.get(0).size());
    assertEquals("$c/#=\\.", subspecs.get(0).get(0));
    assertEquals(1, subspecs.get(1).size());
    assertEquals("?$a", subspecs.get(1).get(0));


  }

  @Test
  public void test_subspecs2() {
    Matcher matcher;

    matcher = MARCspecParser.SUBFIELD.matcher("$s{?020$a}");
    assertTrue(matcher.matches());
    assertEquals("{?020$a}", matcher.group("subspecs"));
    matcher = MARCspecParser.SUBSPEC.matcher(matcher.group("subspecs"));
    assertTrue(matcher.matches());
    assertEquals(1, matcher.groupCount());
    assertEquals("?020$a", matcher.group(1));
  }

   @Test
  public void test_subspecs3() {
    Matcher matcher;
    matcher = MARCspecParser.SUBFIELD.matcher("$s{?020$a}");
    assertTrue(matcher.matches());
    matcher = MARCspecParser.SUBSPEC.matcher(matcher.group("subspecs"));
    List<List<String>> _subSpecs = new ArrayList<>();
    if (matcher.groupCount() > 0) {
      while (matcher.find()) {
        String _subSpec = matcher.group(1);
        assertEquals("?020$a", _subSpec);
        String[] allSplitted = _subSpec.split(MARCspecParser.SUBSPEC_DELIMITER.pattern());
        for (String splitted : allSplitted) {
          assertEquals("?020$a", splitted);
          Matcher subTermsMatcher = MARCspecParser.SUBTERMS.matcher(splitted);
          assertTrue(subTermsMatcher.matches());
          assertEquals(3, subTermsMatcher.groupCount());
          assertEquals("?020$a", subTermsMatcher.group(0));
          assertEquals(null, subTermsMatcher.group("leftsubterm"));
          assertEquals("?", subTermsMatcher.group("operator"));
          assertEquals("020$a", subTermsMatcher.group("rightsubterm"));
        }
      }
    }
  }

  @Test
  public void testMARCspecParser_245() {
    MARCspecParser parser = new MARCspecParser("245");
    Map<String, Object> parsedFieldSpec = parser.getParsedFieldSpec();
    assertEquals("245", parsedFieldSpec.get("field"));
    assertEquals("245", parsedFieldSpec.get("tag"));
    assertEquals(null, parsedFieldSpec.get("index"));
    assertEquals(null, parsedFieldSpec.get("subfields"));
    assertEquals(null, parsedFieldSpec.get("indicators"));
    assertEquals("", parsedFieldSpec.get("subspecs"));
  }

  @Test
  public void testMARCspecParser_245ac() {
    MARCspecParser parser = new MARCspecParser("245$a-c");
    Map<String, Object> parsedFieldSpec = parser.getParsedFieldSpec();
    assertEquals("245$a-c", parsedFieldSpec.get("field"));
    assertEquals("245", parsedFieldSpec.get("tag"));
    assertEquals(null, parsedFieldSpec.get("index"));
    assertEquals("$a-c", parsedFieldSpec.get("subfields"));
    assertEquals(null, parsedFieldSpec.get("indicators"));
    assertEquals("", parsedFieldSpec.get("subspecs"));
  }

  @Test
  public void testMARCspecParserB_24a() {
    // TODO: catch exception with annotation
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = null;
    try {
      marcSpec = parser.parse("24a$a");
    } catch (InvalidMARCspecException e) {

    }
    assertNull(marcSpec);
  }

  @Test
  public void testMARCspecParserB_LDR() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = null;
    try {
      marcSpec = parser.parse("LDR");
    } catch (InvalidMARCspecException e) {

    }
    assertNotNull(marcSpec);
    assertEquals("LDR", marcSpec.getField().getTag());
  }

  @Test
  public void testMARCspecParserB_245() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("245$a");

    assertEquals("245", marcSpec.getField().getTag());
    assertEquals(1, marcSpec.getSubfields().size());
    assertEquals("a", marcSpec.getSubfields().get(0).getTag());
  }

  @Test
  public void testMARCspecParserB_2451() {
    MARCspecParser parser = new MARCspecParser();
    MARCspec marcSpec = parser.parse("245[1]");

    assertEquals("245", marcSpec.getField().getTag());
    assertEquals(1, (int)marcSpec.getField().getStartIndex().getPositionInt());
    assertEquals(0, marcSpec.getSubfields().size());
  }

}
