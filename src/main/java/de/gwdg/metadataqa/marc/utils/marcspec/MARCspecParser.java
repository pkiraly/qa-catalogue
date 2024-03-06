package de.gwdg.metadataqa.marc.utils.marcspec;

import de.gwdg.metadataqa.marc.utils.marcspec.exception.InvalidMARCspecException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MARCspecParser {

  protected static final Pattern namedGroupsPattern = Pattern.compile(
    "\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>"
  );

  /**
   * Regex for field tag
   */
  protected static final Pattern FIELDTAG = Pattern.compile(
    "^(?<tag>(?:[0-9\\.]{3}|LDR|LEADER))?"
  );

  /**
   * Regex for position or range
   */
  protected static final Pattern POSITION_OR_RANGE = Pattern.compile(
    "(?:(?:(?:\\d+|#)\\-(?:\\d+|#))|(?:\\d+|#))"
  );

  /**
   * Regex for named position or range
   */
  protected static final Pattern NAMED_POSITION_OR_RANGE = Pattern.compile(
    "(?:(?:(?<start>\\d+|#)\\-(?<end>\\d+|#))|(?<single>\\d+|#))"
  );

  /**
   * Regex for index
   */
  protected static final Pattern INDEX = Pattern.compile(
    "(?:\\[(?<index>" + POSITION_OR_RANGE.pattern() + ")\\])?"
  );

  /**
   * Regex for charpos
   */
  protected static final Pattern CHARPOS = Pattern.compile(
    "\\/(?<charpos>" + POSITION_OR_RANGE.pattern() + ")"
  );

  /**
   * Regex for indicators
   */
  protected static final Pattern OLD_INDICATORS = Pattern.compile("_(?<indicators>(?:[_a-z0-9][_a-z0-9]{0,1}))");

  protected static final Pattern INDICATORS = Pattern.compile(
    "\\^(?<indicators>[12])"
  );

  /**
   * Regex for field subspecs
   */
  protected static Pattern F_SUBSPECS;

  /**
   * Regex for subfield subspecs
   */
  protected static Pattern SF_SUBSPECS;

  /**
   * Regex for subspec
   */
  protected static final Pattern SUBSPECS = Pattern.compile(
    "(?<subspecs>(?:\\{.+?(?<!(?<!(\\$|\\\\))(\\$|\\\\))\\})*)"
  );

  /**
   * Regex for subfields
   */
  protected static final Pattern SUBFIELDS = Pattern.compile(
    "(?<subfields>\\$.+)?"
  );

  /**
   * Regex for field
   */
  protected static final Pattern FIELD = Pattern.compile(
    "(?<field>(?:"
    + FIELDTAG.pattern()
    + INDEX.pattern()
    + "(?:" + CHARPOS.pattern() + "|" + INDICATORS.pattern() + ")?"
    + SUBSPECS.pattern()
    + SUBFIELDS.pattern()
    + "))"
  );

  /**
   * Regex for subfield range
   */
  protected static final Pattern SUBFIELDTAGRANGE = Pattern.compile(
    "(?<subfieldtagrange>(?:[0-9a-z]\\-[0-9a-z]))"
  );

  /**
   * Regex for subfield range
   */
  protected static final Pattern NAMED_SUBFIELDTAGRANGE = Pattern.compile(
    "(?<subfieldtagrange>(?<start>[0-9a-z])\\-(?<end>[0-9a-z]))"
  );

  /**
   * Regex for subfield tag
   */
  protected static final Pattern SUBFIELDTAG = Pattern.compile(
    "(?<subfieldtag>[\\!-\\?\\[-\\{\\}-~])"
  );

  /**
   * Regex for subfield
   */
  protected static final Pattern SUBFIELD = Pattern.compile(
    "(?<subfield>" +
      "\\$" +
      "(?:" + SUBFIELDTAGRANGE.pattern() + "|" + SUBFIELDTAG.pattern() + ")"
      + INDEX.pattern()
      + "(?:" + CHARPOS + ")?"
      + SUBSPECS
      + ")"
  );
  /**
   * Regex for leftSubTerm
   */
  protected static final Pattern LEFTSUBTERM = Pattern.compile(
    "^(?<leftsubterm>(?:\\\\(?:(?<=\\\\)[!=~\\?]|[^!=~\\?])+)|(?:(?<=\\$)[!=~\\?]|[^!=~\\?])+)?");
  /**
   * Regex for operator
   */
  protected static final Pattern OPERATOR = Pattern.compile("(?<operator>!=|!~|=|~|!|\\?)");
  /**
   * Regex for subterms
   */
  protected static final Pattern SUBTERMS = Pattern.compile(
    "(?:"
      + LEFTSUBTERM.pattern()
      + OPERATOR.pattern()
    + ")?"
    + "(?<rightsubterm>.+)$");
  /**
   * Regex for subspec
   */
  protected static final Pattern SUBSPEC = Pattern.compile("(?:\\{(.+?)\\})");

  protected static final Pattern SUBSPEC_DELIMITER = Pattern.compile("(?<!\\\\)\\|");

  protected static final Map<Pattern, List<String>> patternNames = new HashMap<>();

  protected static final List<Pattern> allPatterns = Arrays.asList(FIELDTAG,
    POSITION_OR_RANGE, NAMED_POSITION_OR_RANGE,
    INDEX, CHARPOS, INDICATORS, SUBSPECS, SUBFIELDS, FIELD, SUBFIELDTAGRANGE, SUBFIELDTAG,
    SUBFIELD, LEFTSUBTERM, OPERATOR, SUBTERMS, SUBSPEC);

  static {
    for (Pattern pattern : allPatterns)
      patternNames.put(pattern, getNamedGroupCandidates(pattern.pattern()));
  }

  /**
   * The parsed MARCspec
   */
  private Map<String, String> parsed = new HashMap<>();
  /**
   * The parsed fieldspec
   */
  private Map<String, Object> parsedFieldSpec = new HashMap<>();
  /**
   * The parsed subfieldspecs
   */
  private List<Map<String, String>> parsedSubfieldSpec = new ArrayList<>();

  public MARCspecParser() {

  }

  public MARCspec parse(String spec) {
    MARCspec marcSpec = new MARCspec();
    if (StringUtils.isBlank(spec)) {
      throw new InvalidMARCspecException("The string is empty", "");
    }
    Matcher matcher = FIELD.matcher(spec);
    if (!matcher.matches()) {
      throw new InvalidMARCspecException("input", spec);
    } else {
      Field field = null;
      // _fieldMatches
      Map<String, String> fieldMap = extractValues(matcher);
      field = new Field();
      marcSpec.setField(field);

      if (fieldMap.containsKey("tag"))
        field.setTag(fieldMap.get("tag"));
      // check what else is index

      if (fieldMap.containsKey("index") && StringUtils.isNotBlank(fieldMap.get("index"))) {
        Positions positions = extractPositions(fieldMap.get("index"));
        if (positions != null)
          field.setIndexStartEnd(positions.getStart(), positions.getEnd());
      }

      if (fieldMap.containsKey("charpos") && StringUtils.isNotBlank(fieldMap.get("charpos"))) {
        field.setCharacterPositions(extractPositions(fieldMap.get("charpos")));
      }

      if (fieldMap.containsKey("indicators") && StringUtils.isNotBlank(fieldMap.get("indicators"))) {
        String ind = fieldMap.get("indicators");
        if (ind.equals("1"))
          field.setIndicator1(ind);
        else if (ind.equals("2"))
          field.setIndicator2(ind);
      }

      if (fieldMap.containsKey("subfields") && StringUtils.isNotBlank(fieldMap.get("subfields"))) {
        processSubfields(marcSpec, fieldMap.get("subfields"));
      }

      if (fieldMap.containsKey("subspecs") && StringUtils.isNotBlank(fieldMap.get("subspecs"))) {
        field.setSubSpecs(extractSubSpecs(marcSpec, fieldMap.get("subspecs")));
      }

    }
    return marcSpec;
  }

  private void processSubfields(MARCspec marcSpec, String subfields) {
    Matcher matcher = SUBFIELD.matcher(subfields);
    while (matcher.find()) {
      Map<String, String> subfieldMap = extractValues(matcher);
      // "subspecs"

      if (subfieldMap.containsKey("subfieldtagrange") && StringUtils.isNotBlank(subfieldMap.get("subfieldtagrange"))) {
        List<String> range = extractSubfieldRange(subfieldMap.get("subfieldtagrange"));
        for (String tag : range)
          marcSpec.addSubfield(new Subfield(tag));
      } else {
        Subfield subfield = new Subfield();
        marcSpec.addSubfield(subfield);

        if (subfieldMap.containsKey("subfieldtag")
          && StringUtils.isNotBlank(subfieldMap.get("subfieldtag")))
          subfield.setTag(subfieldMap.get("subfieldtag"));

        if (subfieldMap.containsKey("index")
          && StringUtils.isNotBlank(subfieldMap.get("index")))
          subfield.setIndexPositions(extractPositions(subfieldMap.get("index")));

        if (subfieldMap.containsKey("charpos")
          && StringUtils.isNotBlank(subfieldMap.get("charpos")))
          subfield.setCharacterPositions(extractPositions(subfieldMap.get("charpos")));

        if (subfieldMap.containsKey("subspecs")
          && StringUtils.isNotBlank(subfieldMap.get("subspecs"))) {
          subfield.setSubSpecs(extractSubSpecs(marcSpec, subfieldMap.get("subspecs")));
        }
      }
    }
  }

  private List<SubSpec> extractSubSpecs(MARCspec marcSpec, String subspecsString) {
    List<List<String>> rawSubSpecsList = matchSubSpecs(subspecsString);
    List<SubSpec> subspecs = new ArrayList<>();
    for (List<String> subSpecsSequence : rawSubSpecsList) {
      for (String subSpecString : subSpecsSequence) {
        Matcher subTermsMatcher = MARCspecParser.SUBTERMS.matcher(subSpecString);
        if (subTermsMatcher.matches()) {
          SubSpec subSpec = new SubSpec();
          subSpec.setOperator(subTermsMatcher.group("operator"));
          SubTerm leftsubterm = new SubTerm();
          SubTerm rightsubterm = new SubTerm();
          if (StringUtils.isNotBlank(subTermsMatcher.group("leftsubterm"))) {
            MARCspec left = parse(subTermsMatcher.group("leftsubterm"));
            if (left.getField().getTag() == null) {
              left.getField().setTag(marcSpec.getField().getTag());
              if (left.getField().getCharacterPositions() == null
                && marcSpec.getField().getCharacterPositions() != null)
                left.getField().setCharacterPositions(
                  marcSpec.getField().getCharacterPositions()
                );
              if (left.getField().getStartIndex() == null
                && marcSpec.getField().getStartIndex() != null)
                left.getField().setStartIndex(marcSpec.getField().getStartIndex());
              if (left.getField().getEndIndex() == null
                && marcSpec.getField().getEndIndex() != null)
                left.getField().setEndIndex(marcSpec.getField().getEndIndex());

              if (left.getSubfields().isEmpty() && !marcSpec.getSubfields().isEmpty())
                left.setSubfields(marcSpec.getSubfields());

              if (left.getField().getCharacterPositions() != null && !left.getSubfields().isEmpty()) {
                for (Subfield subfield : left.getSubfields()) {
                  subfield.setCharacterPositions(left.getField().getCharacterPositions());
                }
                left.getField().setCharacterPositions(null);
              }
            }
            leftsubterm.setMarcSpec(left);
          } else {
            MARCspec copyOfThis = new MARCspec();
            copyOfThis.setField(marcSpec.getField());
            copyOfThis.setSubfields(marcSpec.getSubfields());
            leftsubterm.setMarcSpec(copyOfThis);
          }
          if (StringUtils.isNotBlank(subTermsMatcher.group("rightsubterm"))) {
            String rightsubtermString = subTermsMatcher.group("rightsubterm");
            if (rightsubtermString.startsWith("\\"))
              rightsubterm.setComparisonString(new ComparisonString(rightsubtermString.substring(1)));
            else {
              MARCspec right = parse(rightsubtermString);
              if (StringUtils.isBlank(right.getField().getTag()))
                right.getField().setTag(marcSpec.getField().getTag());
              // if (StringUtils.isBlank(right.getField().getTag()))
              //   right.getField().setTag(marcSpec.getField().getTag());
              rightsubterm.setMarcSpec(right);
            }
          }
          subSpec.setLeftSubTerm(leftsubterm);
          subSpec.setRightSubTerm(rightsubterm);
          subspecs.add(subSpec);
        }
      }
    }
    return subspecs;
  }

  private List<String> extractSubfieldRange(String subfieldTagRange) {
    List<String> range = null;
    Matcher rangeMatcher = NAMED_SUBFIELDTAGRANGE.matcher(subfieldTagRange);
    if (rangeMatcher.matches()) {
      String start = rangeMatcher.group("start");
      String end = rangeMatcher.group("end");
      Pattern lowerCase = Pattern.compile("[a-z]");
      Pattern upperCase = Pattern.compile("[A-Z]");
      Pattern numeric = Pattern.compile("\\d");
      if (lowerCase.matcher(start).matches() && !lowerCase.matcher(end).matches())
        throw new InvalidMARCspecException(InvalidMARCspecException.SF + InvalidMARCspecException.RANGE, subfieldTagRange);

      if (upperCase.matcher(start).matches() && !upperCase.matcher(end).matches())
        throw new InvalidMARCspecException(InvalidMARCspecException.SF + InvalidMARCspecException.RANGE, subfieldTagRange);

      if (numeric.matcher(start).matches() && !numeric.matcher(end).matches())
        throw new InvalidMARCspecException(InvalidMARCspecException.SF + InvalidMARCspecException.RANGE, subfieldTagRange);

      if (start.charAt(0) > end.charAt(0))
        throw new InvalidMARCspecException(InvalidMARCspecException.SF + InvalidMARCspecException.RANGE, subfieldTagRange);

      range = new ArrayList<>();
      for (int i = start.charAt(0); i <= end.charAt(0); i++) {
        range.add(Character.toString((char)i));
      }
    }
    return range;
  }

  private Positions extractPositions(String positionString) {
    Positions indexPositions = null;
    Matcher positionMatcher = NAMED_POSITION_OR_RANGE.matcher(positionString);
    if (positionMatcher.matches()) {
      indexPositions = new Positions();
      Map<String, String> indexMap = extractValues(positionMatcher);
      if (indexMap.containsKey("single")
          && StringUtils.isNotBlank(indexMap.get("single"))) {
        indexPositions.setRange(false);
        Position pos = createIndexPosition(indexMap.get("single"));
        indexPositions.setStart(pos);
        // indexPositions.setEnd(pos);
        indexPositions.setLength(1);
      } else {
        indexPositions.setRange(true);
        indexPositions.setStart(createIndexPosition(indexMap.get("start")));
        indexPositions.setEnd(createIndexPosition(indexMap.get("end")));
        if (indexPositions.getEnd().getPositionInt() != null
          && indexPositions.getStart().getPositionInt() != null) {
          indexPositions.setLength(indexPositions.getEnd().getPositionInt()+1 - indexPositions.getStart().getPositionInt());
        }
      }
    }
    return indexPositions;
  }

  private Position createIndexPosition(String positionString) {
    Position pos = null;
    if (positionString.equals("#"))
      pos = new Position(positionString);
    else
      pos = new Position(Integer.parseInt(positionString));
    return pos;
  }

  public MARCspecParser(String spec) {
    if (StringUtils.isBlank(spec)) {
      return;
    }
    fieldToArray(spec);
    if (parsed.containsKey("subfields") && StringUtils.isNotBlank(parsed.get("subfields"))) {
      parsedSubfieldSpec = matchSubfields(parsed.get("subfields"));
    }
  }

  /**
   * parses fieldspecs into array.
   * @param fieldspec The fieldspec
   * @return
   */
  public void fieldToArray(String fieldspec) {
    List<String> _fieldGroups = Arrays.asList("field", "tag", "index", "charpos", "indicators", "subfields");
    Matcher matcher = FIELD.matcher(fieldspec);
    if (matcher.matches()) {
      // _fieldMatches
      parsed = extractValues(matcher);
      for (Map.Entry<String, String> entry : parsed.entrySet()) {
        parsedFieldSpec.put(entry.getKey(), entry.getValue());
      }

      if (!parsed.containsKey("field")) { // TODO: check if 'tag' is the required key
        throw new InvalidMARCspecException(InvalidMARCspecException.FS + InvalidMARCspecException.FTAG, fieldspec);
      }
      if (parsed.get("field").length() != fieldspec.length()) {
        throw new InvalidMARCspecException(InvalidMARCspecException.FS + InvalidMARCspecException.USELESS, fieldspec);
      }
      if (parsedFieldSpec.containsKey("charpos") && parsedFieldSpec.get("charpos") != null) {
        if (parsedFieldSpec.containsKey("indicators") && parsedFieldSpec.get("indicators") != null) {
          throw new InvalidMARCspecException(InvalidMARCspecException.FS + InvalidMARCspecException.CHARORIND, fieldspec);
        }
        if (parsedFieldSpec.containsKey("subfields") && parsedFieldSpec.get("subfields") != null) {
          throw new InvalidMARCspecException(InvalidMARCspecException.FS + InvalidMARCspecException.CHARANDSF, fieldspec);
        }

        if (parsed.containsKey("subspecs") && parsed.get("subspecs") != null) {
          List<List<String>>_fieldSubSpecs = matchSubSpecs(parsed.get("subspecs"));
          parsedFieldSpec.put("subspecs", new ArrayList<Map<String, String>>());
          for (List<String> fieldSubSpec : _fieldSubSpecs) {
            if (1 < fieldSubSpec.size()) {
              List<Map<String, String>> _or = new ArrayList<>();
              for (String orSubSpec : fieldSubSpec) {
                _or.add(matchSubTerms(orSubSpec));
              }
              ((List<Map<String, String>>) parsedFieldSpec.get("subspecs")).addAll(_or);

            } else {
              ((List<Map<String, String>>) parsedFieldSpec.get("subspecs")).add(matchSubTerms(fieldSubSpec.get(0)));
            }

          }
        }
      }
    } else {
      throw new InvalidMARCspecException(InvalidMARCspecException.FS + InvalidMARCspecException.MISSINGFIELD, fieldspec);
    }
  }

  /**
   * Matches subfieldspecs.
   *
   * @param subfieldspec A string of one or more subfieldspecs
   */
  public List<Map<String, String>> matchSubfields(String subfieldspec) {
    List<Map<String, String>> _subfieldMatches = null;
    Matcher matcher = SUBFIELD.matcher(subfieldspec);
    if (matcher.groupCount() > 1) {
      StringBuffer test = new StringBuffer();
      List<Map<String, String>> subfields = new ArrayList<>();
      while (matcher.find()) {
        Map<String, String> _subfield = extractValues(matcher);
        subfields.add(_subfield);

        test.append(_subfield.get("subfield"));
        if (_subfield.containsKey("subspecs")) {
          List<Object> _ss = new ArrayList<>();
          /*
          Map<Object, List<Object>> _subfieldSubSpecs = matchSubSpecs(_subfield.get("subfield"));
          if (_subfieldSubSpecs == null) {
            // TODO: raise error;
          }
          for (Object key : _subfieldSubSpecs.keySet()) {
            List<Object> _subfieldSubSpec = _subfieldSubSpecs.get(key);
            if (1 < _subfieldSubSpec.size()) {
              List<Object> _or = new ArrayList<>();
              for (Object orSubSpec : _subfieldSubSpec) {
                _or.add(matchSubTerms(orSubSpec));
              }
              _ss.add(_or);
            } else {
              _ss.add(matchSubTerms(_subfieldSubSpec.get(0)));
            }
          }
          $_subfield['subspecs'] = $_ss;
          */
        }
      }
      if (!test.toString().equals(subfieldspec)) {
        throw new InvalidMARCspecException(InvalidMARCspecException.SF + InvalidMARCspecException.USELESS, subfieldspec);
      }

    } else {
      throw new InvalidMARCspecException(InvalidMARCspecException.SF + InvalidMARCspecException.SFCHAR, subfieldspec);
    }
        /*
        * For each subfield (array) do anonymous function
        * - first filter empty elements
        * - second look for subspecs
        * - match subspecs and match subTerms
        * - return everything in the array of subfields
        */
    return _subfieldMatches;
  }
  /**
   * calls matchSubfields but makes sure only one subfield is present.
   *
   * @param subfieldspec A subfieldspec
   *
   * @return array An Array of subfieldspec
   */
  public Map<String, String> subfieldToArray(String subfieldspec) {
    List<Map<String, String>> _sf = matchSubfields(subfieldspec);
    if (_sf == null) {
      throw new InvalidMARCspecException(InvalidMARCspecException.SF + InvalidMARCspecException.UNKNOWN, subfieldspec);
    }
    if (1 < _sf.size()) {
      throw new InvalidMARCspecException(InvalidMARCspecException.SF + InvalidMARCspecException.MULTISF, subfieldspec);
    }
    if (!_sf.get(0).get("subfield").equals(subfieldspec)) {
      throw new InvalidMARCspecException(InvalidMARCspecException.SF + InvalidMARCspecException.USELESS, subfieldspec);
    }
    return _sf.get(0);
  }
  /**
   * parses subspecs into an array.
   *
   * @param subSpecsString One or more subspecs
   *
   * @return array Array of subspecs
   */
  private List<List<String>> matchSubSpecs(String subSpecsString) {
    List<List<String>> subSpecs = new ArrayList<>();
    Matcher matcher = SUBSPEC.matcher(subSpecsString);
    if (matcher.groupCount() > 0) {
      while (matcher.find()) {
        String subSpec = matcher.group(1);
        subSpecs.add(Arrays.asList(subSpec.split(SUBSPEC_DELIMITER.pattern())));
      }
    } else {
      throw new InvalidMARCspecException(InvalidMARCspecException.SS + InvalidMARCspecException.UNKNOWN, subSpecsString);
    }
    return subSpecs;
  }

  /**
   * Parses a single SubSpec into sunTerms.
   * @param subSpec A single SubSpec
   * @return subTerms as a map
   */
  private Map<String, String> matchSubTerms(String subSpec) {
    Map<String, String> terms = null;
    Pattern matchSubTermsFilterPattern = Pattern.compile("(?<![\\\\\\$])[\\{\\}]");
    if (!matchSubTermsFilterPattern.matcher(subSpec).matches()) { // PREG_OFFSET_CAPTURE
      throw new InvalidMARCspecException(InvalidMARCspecException.SS + InvalidMARCspecException.ESCAPE, subSpec);
    }
    Matcher matcher = SUBTERMS.matcher(subSpec);
    if (matcher.groupCount() > 1) {
      while (matcher.find()) {
        terms = extractValues(matcher);
        if (terms.get("operator") == null) {
          terms.put("operator", "?");
        }
        if (terms.get("rightsubterm") == null) {
          throw new InvalidMARCspecException(InvalidMARCspecException.SS + InvalidMARCspecException.MISSINGRIGHT, subSpec);
        }
      }
    } else {
      throw new InvalidMARCspecException(InvalidMARCspecException.SS + InvalidMARCspecException.UNKNOWN, subSpec);
    }

    return terms;
  }

  public static List<String> getNamedGroupCandidates(String regex) {
    List<String> namedGroups = new ArrayList<>();
    Matcher m = namedGroupsPattern.matcher(regex);
    while (m.find()) {
      namedGroups.add(m.group(1));
    }
    return namedGroups;
  }

  public static Map<Pattern, List<String>> getPatternNames() {
    return patternNames;
  }

  public Map<String, String> extractValues(Matcher matcher) {
    Map<String, String> values = new TreeMap<>();
    for (String field : patternNames.get(matcher.pattern())) {
      values.put(field, matcher.group(field));
    }
    return values;
  }

  public Map<String, Object> getParsedFieldSpec() {
    return parsedFieldSpec;
  }
}
