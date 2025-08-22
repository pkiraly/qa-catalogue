package de.gwdg.metadataqa.marc.utils.marcspec;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarcSpecParser {
  private static final Logger logger = Logger.getLogger(MarcSpecParser.class.getCanonicalName());

  private static Pattern FIELD = Pattern.compile("^([A-Z\\d\\.]{3}|[a-z\\d\\.]{3})");
  private static Pattern INDICATOR = Pattern.compile("^\\^([12])");
  private static Pattern POSITION = Pattern.compile("^(/(\\d+|#)(?:-(\\d+|#))?)");
  // private static Pattern SUBFIELD = Pattern.compile("^(\\$(.)(?:-(.))?)");
  private static Pattern SUBFIELD = Pattern.compile("^(\\$(.))");
  private static Pattern INDEX = Pattern.compile("^(\\[(\\d+|#)(?:-(\\d+|#))?\\])");

  public static MarcSpec parse(String input) {
    MarcSpec spec = null;
    Subfield subfield = null;
    String context = "tag";
    Matcher matcher;
    while (input.length() > 0) {
      if (spec == null) {
        spec = new MarcSpec();
        matcher = FIELD.matcher(input);
        if (matcher.find()) {
          spec.setTag(matcher.group(1));
          input = input.substring(3, input.length());
        }
      } else {
        matcher = INDICATOR.matcher(input);
        if (matcher.find()) {
          String value = matcher.group(1);
          spec.setIndicator(value);
          if (input.length() > 2) {
            input = input.substring(2, input.length());
            continue;
          } else {
            break;
          }
        }

        matcher = SUBFIELD.matcher(input);
        if (matcher.find()) {
          String value = matcher.group(1);
          subfield = new Subfield(matcher.group(2));
          spec.getSubfields().add(subfield);
          context = "subfield";

          if (input.length() > value.length()) {
            input = input.substring(value.length(), input.length());
            continue;
          } else {
            break;
          }
        }

        matcher = POSITION.matcher(input);
        if (matcher.find()) {
          String value = matcher.group(1);
          Range position = new Range(matcher.group(2));
          if (matcher.group(3) != null)
            position.setEnd(matcher.group(3));

          if (context.equals("tag"))
            spec.setPosition(position);
          else
            subfield.setPosition(position);

          if (input.length() > value.length()) {
            input = input.substring(value.length(), input.length());
            continue;
          } else {
            break;
          }
        }

        matcher = INDEX.matcher(input);
        if (matcher.find()) {
          String value = matcher.group(1);
          Range index = new Range(matcher.group(2));
          if (matcher.group(3) != null)
            index.setEnd(matcher.group(3));
          if (context.equals("tag"))
            spec.setIndex(index);
          else
            subfield.setIndex(index);

          if (input.length() > value.length()) {
            input = input.substring(value.length(), input.length());
            continue;
          } else {
            break;
          }
        }

        logger.warning("unhandled part: " + input);
        break;
      }
    }
    return spec;
  }
}
