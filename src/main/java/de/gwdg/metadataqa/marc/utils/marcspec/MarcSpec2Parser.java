package de.gwdg.metadataqa.marc.utils.marcspec;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://marcspec.github.io/MARCspec/marc-spec.html
 */
public class MarcSpec2Parser {
  private static final Logger logger = Logger.getLogger(MarcSpec2Parser.class.getCanonicalName());

  private static Pattern FIELD = Pattern.compile("^([A-Z\\d\\.]{3}|[a-z\\d\\.]{3})$");
  private static Pattern INDICATOR = Pattern.compile("^(\\d\\d\\d)\\^([12])$");

  public static MarcSpec2 parse(String s) {
    Matcher matcher;

    matcher = FIELD.matcher(s);
    if (matcher.matches()) {
      MarcSpec2 marcSpec2 = new MarcSpec2();
      marcSpec2.setTag(matcher.group(1));
      return marcSpec2;
    }

    // 9.6 Reference to indicator values
    matcher = INDICATOR.matcher(s);
    if (matcher.matches()) {
      MarcSpec2 marcSpec2 = new MarcSpec2();
      marcSpec2.setTag(matcher.group(1));
      marcSpec2.setIndicator(matcher.group(2));
      return marcSpec2;
    }

    return null;
  }

}
