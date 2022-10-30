package de.gwdg.metadataqa.marc.utils.pica.path;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicaPathParser {

  private static final Pattern TAG_PATTERN = Pattern.compile("^([012\\.][0-9\\.][0-9\\.][A-Z@\\.])");
  private static final Pattern XTAG_PATTERN = Pattern.compile("^(2[0-9\\.][0-9\\.][A-Z@\\.]x\\d+)");
  private static final Pattern OCCURENCE_PATTERN = Pattern.compile("^/((\\d+)-(\\d+)|(\\d{1,3})|(\\*))");
  private static final Pattern SUBFIELDS_PATTERN = Pattern.compile("^[\\$.]?(([A-Za-z0-9]+)|(\\*))");
  private static final String ERROR_MESSAGE = "The input does not fit to rules: '%s'";

  private String path;
  private String tag;
  private String xtag;
  private Occurrence occurrence;
  private Subfields subfields;
  private String remainder;

  private PicaPathParser(String input) {
    this.path = input;
  }

  public static PicaPath parse(String input) {
    PicaPathParser parser = new PicaPathParser(input);
    parser.parseTag();
    parser.parseOccurrence();
    parser.parseSubfields();

    if (parser.remainder != null)
      throw new IllegalArgumentException(String.format(ERROR_MESSAGE, input));

    return new PicaPath(parser.path, parser.tag, parser.xtag, parser.occurrence, parser.subfields);
  }

  private void parseSubfields() {
    Matcher m;
    if (remainder != null) {
      m = SUBFIELDS_PATTERN.matcher(remainder);
      if (m.find()) {
        String subfieldsRaw = m.group(1);
        Subfields.Type subfieldType = null;
        if (m.group(2) != null) {
          subfieldType = m.group(2).length() == 1 ? Subfields.Type.SINGLE : Subfields.Type.MULTI;
        } else if (m.group(3) != null) {
          subfieldType = Subfields.Type.ALL;
        }
        if (subfieldType == null)
          throw new IllegalArgumentException(String.format(ERROR_MESSAGE, path));
        subfields = new Subfields(subfieldType, subfieldsRaw);
        remainder = m.end() < remainder.length() ? remainder.substring(m.end()) : null;
      }
    }
  }

  private void parseOccurrence() {
    Matcher m;
    if (remainder != null) {
      m = OCCURENCE_PATTERN.matcher(remainder);
      if (m.find()) {
        if (m.group(2) != null) {
          occurrence = new Occurrence(Occurrence.Type.RANGE, Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
        } else if (m.group(4) != null) {
          occurrence = new Occurrence(Occurrence.Type.SINGLE, Integer.parseInt(m.group(4)), null);
        } else if (m.group(5) != null) {
          occurrence = new Occurrence(Occurrence.Type.ALL, null, null);
        } else {
          throw new IllegalArgumentException(String.format(ERROR_MESSAGE, path));
        }

        remainder = m.end() < remainder.length() ? remainder.substring(m.end()) : null;
      }
    }
  }

  private void parseTag() {
    Matcher m = null;
    m = XTAG_PATTERN.matcher(path);
    if (m.find()) {
      xtag = m.group(1);
      if (m.end() > path.length())
        remainder = path.substring(m.end());
    } else {
      m = TAG_PATTERN.matcher(path);
      if (m.find()) {
        tag = m.group(1);
        if (m.end() < path.length())
          remainder = path.substring(m.end());
      } else {
        throw new IllegalArgumentException(String.format(ERROR_MESSAGE, path));
      }
    }
  }
}
