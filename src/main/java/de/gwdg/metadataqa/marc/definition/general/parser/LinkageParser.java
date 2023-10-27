package de.gwdg.metadataqa.marc.definition.general.parser;

import de.gwdg.metadataqa.marc.definition.general.Linkage;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkageParser implements SubfieldContentParser, Serializable {

  public static final Pattern INVALID_CHAR = Pattern.compile("[^0-9\\-\\/\\($BNSr]");

  private static final Pattern REGEX = Pattern.compile("^(\\d{3})-(\\d{2})(?:/(.{2}))?(?:/(.{1,100}))?$");
  boolean collectInvalidCharacters = false;

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
    if (matcher.find() && matcher.group(1) != null) {
      Linkage linkage = new Linkage(matcher.group(1), matcher.group(2));
      if (matcher.group(3) != null) {
        linkage.setScriptIdentificationCode(matcher.group(3));
        if (matcher.group(4) != null)
          linkage.setFieldOrientationCode(matcher.group(4));
      }
      return linkage;
    }

    return null;
  }

  private void checkInvalidCharacters(String input) throws ParserException {
    Matcher matcher = INVALID_CHAR.matcher(input);
    boolean hasInvalidCharacters = false;
    Set<String> invalidChars = new HashSet<>();
    while (matcher.find()) {
      hasInvalidCharacters = true;
      if (collectInvalidCharacters) {
        String chr = matcher.group();
        String hex = String.format("\\u%04X", (int)chr.charAt(0));
        if (hex.equals("\\u200F"))
          chr = hex;
        invalidChars.add(chr);
      }
    }

    if (hasInvalidCharacters)
      throw new ParserException("Linkage does not fit the pattern 'nnn-nn[/..][/..]'.");
  }

  public String getPattern() {
    return REGEX.toString();
  }

  private static LinkageParser uniqueInstance;

  private LinkageParser() {}

  public static LinkageParser getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new LinkageParser();
    return uniqueInstance;
  }
}
