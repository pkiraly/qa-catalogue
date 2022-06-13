package de.gwdg.metadataqa.marc.utils.pica;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicaLine {
  private static final Logger logger = Logger.getLogger(PicaLine.class.getCanonicalName());

  private static final Pattern LINE = Pattern.compile("^(SET:|Eingabe:|Warnung:|[0-2][0-9][0-9][A-Z@])(\\/([0-9][0-9]+))? (.*)$");
  public static final String DEFAULT_SEPARATOR = "ƒ";
  private static final String SET = "SET";
  private static final String EINGABE = "Eingabe";
  private static final String WARNUNG = "Warnung";
  private int lineNumber = 0;
  private static String separator = DEFAULT_SEPARATOR;

  private String tag;
  private String occurrence;
  private String content;
  private List<PicaSubfield> subfields;

  private boolean valid = false;
  private boolean skippable = false;

  public PicaLine() {
  }

  public PicaLine(String raw) {
    parse(raw);
  }

  public PicaLine(String raw, int lineNumber) {
    this.lineNumber = lineNumber;
    parse(raw);
  }

  public boolean isSET() {
    return tag.equals(SET);
  }

  public boolean isEingabe() {
    return tag.equals(EINGABE);
  }

  public boolean isWarnung() {
    return tag.equals(WARNUNG);
  }

  public boolean isValidTag() {
    return valid && !(isSET() || isEingabe() || isWarnung());
  }

  public String getTag() {
    return tag;
  }

  public String getOccurrence() {
    return occurrence;
  }

  public List<PicaSubfield> getSubfields() {
    return subfields;
  }

  public String getQualifiedTag() {
    if (occurrence != null)
      return tag + "/" + occurrence;
    return tag;
  }

  public String formatSubfields() {
    if (subfields == null) {
      logger.severe("null subfields: " + content);
      return "";
    }
    List<String> formattedSubfields = new ArrayList<>();
    for (PicaSubfield subfield : subfields) {
      formattedSubfields.add(subfield.format());
    }
    return StringUtils.join(formattedSubfields, ", ");
  }

  public String getContent() {
    return content;
  }

  private void parse(String raw) {
    if (raw.equals("")) {
      skippable = true;
    } else {
      Matcher matcher = LINE.matcher(raw);
      if (matcher.matches()) {
        tag = matcher.group(1).replaceAll(":$", "");
        occurrence = matcher.group(3);
        content = matcher.group(4);
        parseSubfields();
        valid = true;
      } else {
        logger.warning(String.format("Unable to parse line: '%s'", raw));
      }
    }
  }

  private void parseSubfields() {
    subfields = new ArrayList<>();
    String[] parts = content.split(Pattern.quote(separator));
    for (String part : parts) {
      if (StringUtils.isNotBlank(part)) {
        subfields.add(new PicaSubfield(part.substring(0, 1), part.substring(1)));
      }
    }
  }

  @Override
  public String toString() {
    return "PicaLine{" +
      "tag='" + tag + '\'' +
      ", occurrence='" + occurrence + '\'' +
      ", subfields=" + subfields +
      '}';
  }

  public boolean isSkippable() {
    return skippable || isSET() || isEingabe() || isWarnung();
  }

  public static void setSeparator(String code) {
    separator = code;
  }
}
