package de.gwdg.metadataqa.marc.definition.general.parser;

import de.gwdg.metadataqa.marc.definition.general.Linkage;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkageParser implements SubfieldContentParser, Serializable {

  public static final Pattern INVALID_CHAR = Pattern.compile("[^0-9\\-\\/\\($BNSr]");

  private static final Pattern REGEX = Pattern.compile("^(\\d{3})-(\\d{2})(?:/(.*?))?(?:/(.*?))?$");

  @Override
  public Map<String, String> parse(String input) throws ParserException {
    Linkage linkage = create(input);
    if (linkage == null)
      throw new ParserException(String.format("'%s' is not parsable", input));
    return linkage.getMap();
  }

  public Linkage create(String input) throws ParserException {
    checkInvalidCharacters(input);
    Matcher matcher = REGEX.matcher(input);
    if (matcher.find()) {
      if (matcher.group(1) != null) {
        Linkage linkage = new Linkage(matcher.group(1), matcher.group(2));
        if (matcher.group(3) != null) {
          linkage.setScriptIdentificationCode(matcher.group(3));
          if (matcher.group(4) != null)
            linkage.setFieldOrientationCode(matcher.group(4));
        }
        return linkage;
      }
    }

    return null;
  }

  private void checkInvalidCharacters(String input) throws ParserException {
    Matcher matcher = INVALID_CHAR.matcher(input);
    boolean hasInvalidCharacters = false;
    List<String> invalidChars = new ArrayList<>();
    while (matcher.find()) {
      hasInvalidCharacters = true;
      String chr = matcher.group();
      String hex = String.format("\\u%04X", (int)chr.charAt(0));
      if (hex.equals("\\u200F"))
        chr = hex;
      invalidChars.add(chr);
    }
    if (hasInvalidCharacters) {
      throw new ParserException(String.format(
        "Linkage does not fit the pattern nnn-nn(/..)(/..). It contains invalid characters in '%s': '%s'", input, StringUtils.join(invalidChars, "', '")));
    }
  }

  private static LinkageParser uniqueInstance;

  private LinkageParser() {}

  public static LinkageParser getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new LinkageParser();
    return uniqueInstance;
  }

}
