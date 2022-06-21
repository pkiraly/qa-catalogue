package de.gwdg.metadataqa.marc.utils.pica.path;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicaPathParser {

  private static final Pattern TAG = Pattern.compile("^([012\\.][0-9\\.][0-9\\.][A-Z@\\.])");
  private static final Pattern XTAG = Pattern.compile("^(2[0-9\\.][0-9\\.][A-Z@\\.]x\\d+)");
  private static final Pattern OCCURENCE = Pattern.compile("^/((\\d+)-(\\d+)|(\\d{1,3})|(\\*))");
  private static final Pattern SUBFIELDS = Pattern.compile("^[\\$.]?(([A-Za-z0-9]+)|(\\*))");
  public static PicaPath parse(String input) {
    String path = input;
    String tag = null;
    String xtag = null;
    Occurrence occurrence = null;
    Subfields subfields = null;

    String remainder = null;
    Matcher m = null;
    m = XTAG.matcher(input);
    if (m.find()) {
      xtag = m.group(1);
      if (m.end() > input.length())
        remainder = input.substring(m.end());
    } else {
      m = TAG.matcher(input);
      if (m.find()) {
        tag = m.group(1);
        if (m.end() < input.length())
          remainder = input.substring(m.end());
      } else {
        throw new IllegalArgumentException("The input does not fit to rules: " + input);
      }
    }

    if (remainder != null) {
      m = OCCURENCE.matcher(remainder);
      if (m.find()) {
        if (m.group(2) != null) {
          occurrence = new Occurrence(Occurrence.Type.RANGE, Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
        } else if (m.group(4) != null) {
          occurrence = new Occurrence(Occurrence.Type.SINGLE, Integer.parseInt(m.group(4)), null);
        } else if (m.group(5) != null) {
          occurrence = new Occurrence(Occurrence.Type.ALL, null, null);
        } else {
          throw new IllegalArgumentException("The input does not fit to rules: " + input);
        }

        remainder = m.end() < remainder.length() ? remainder.substring(m.end()) : null;
      }
    }

    if (remainder != null) {
      m = SUBFIELDS.matcher(remainder);
      if (m.find()) {
        String subfieldsRaw = m.group(1);
        Subfields.Type subfieldType = null;
        if (m.group(2) != null) {
          subfieldType = m.group(2).length() == 1 ? Subfields.Type.SINGLE : Subfields.Type.MULTI;
        } else if (m.group(3) != null) {
          subfieldType = Subfields.Type.ALL;
        }
        if (subfieldType == null)
          throw new IllegalArgumentException("The input does not fit to rules: " + input);
        subfields = new Subfields(subfieldType, subfieldsRaw);
        remainder = m.end() < remainder.length() ? remainder.substring(m.end()) : null;
      }
    }

    if (remainder != null)
      throw new IllegalArgumentException("The input does not fit to rules: " + input);

    return new PicaPath(path, tag, xtag, occurrence, subfields);
  }
}
