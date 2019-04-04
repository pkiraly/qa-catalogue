package de.gwdg.metadataqa.marc.utils.marcspec.exception;

import org.apache.commons.lang3.StringUtils;

public class InvalidMARCspecException extends IllegalArgumentException {
  public static final String METHOD = "In method ";
  public static final String ARGUMENT = "Tried to parse ";
  public static final String UNKNOWN = "Assuming invalid spec.";
  public static final String MS = "MARCspec. ";
  public static final String FS = "Fieldspec. ";
  public static final String SF = "Subfieldspec. ";
  public static final String PR = "PositionOrRange. ";
  public static final String CS = "ComparisonString. ";
  public static final String SS = "Subspec. ";
  public static final String RANGE = "Only ranges between \"a-z\", \"A-Z\" or \"0-9\" allowed.";
  public static final String BRACKET = "Unequal count of opening and closing brackets";
  public static final String SPACE = "Whitespace detected.";
  public static final String MISSINGFIELD = "Cannot detect fieldspec.";
  public static final String MISSINGRIGHT = "Right hand subTerm is missing.";
  public static final String MINIMUM2 = "Spec must be at least two characters long.";
  public static final String MINIMUM3 = "Spec must be at least three characters long.";
  public static final String MINIMUM4 = "Spec must be at least four characters long.";
  public static final String LENGTH = "Invalid spec length.";
  public static final String LENGTH3 = "Invalid spec length. At minimum spec must be three characters long.";
  public static final String PREFIX = "Missing prefixed character \"$\".";
  public static final String ESCAPE = "Unescaped character detected";
  public static final String DETECTEDSF = "Detected Subfield. Use method MARCspec::addSubfields to add subfields.";
  public static final String DETECTEDSS = "Detected Subspec. Use method addSubSpec to add subspecs.";
  public static final String MULTISF = "Detected more than one subfieldspecs. Use method addSubfields to add more than one subfield.";
  public static final String INDEX = "Invalid index detected.";
  public static final String PRCHAR = "For character position or range minimum one digit or character # is required.";
  public static final String USELESS = "Detected useless data fragment.";
  public static final String FTAG = "For fieldtag only \".\" and digits and lowercase alphabetic or digits and upper case alphabetics characters are allowed";
  public static final String LENGTHIND = "For indicators only two characters at are allowed.";
  public static final String INDCHAR1 = "At minimum one indicator must be a digit or a lowercase alphabetic character.";
  public static final String INDCHAR2 = "For indicators only digits, lowercase alphabetic characters and \"_\" are allowed.";
  public static final String NEGATIVE = "Ending character or index position must be equal or higher than starting character or index position.";
  public static final String PR1 = "Assuming index or character position or range. Minimum one character is required. None given.";
  public static final String PR2 = "Assuming index or character position or range. Only digits, the character # and one \"-\" is allowed.";
  public static final String PR3 = "Assuming index or character range. At least two digits or the character # must be present.";
  public static final String PR4 = "Assuming index or character position or range. First character must not be \"-\".";
  public static final String PR5 = "Assuming index or character position or range. Only one \"-\" character allowed.";
  public static final String PR6 = "Assuming index or character position or range. Only digits and one \"-\" is allowed.";
  public static final String PR7 = "Assuming index or character position or range. Starting index must be positive int, 0 or \"#\".";
  public static final String PR8 = "Assuming index or character position or range. Ending index must be a higher number (or equal) than starting index.";
  public static final String MISSINGTAG = "Unexpected empty subfield tag";
  public static final String SFCHAR = "For subfields only digits, lowercase alphabetic characters or one of \"!\"#$%&\"()*+,-./0-9:;<=>?[\\]^_`a-z{}~\" are allowed.";
  public static final String SFRANGE = "Assuming subfield range. Use MARCspec::addSubfields() to add multiple subfields via a subfield range.";
  public static final String MISSINGSLASH = "Assuming subfield character position or range. Missing \"/\" delimiter";
  public static final String OPERATOR = "Operator must be one of \"=\" / \"!=\" / \"~\" / \"!~\" / \"!\" / \"?\".";
  public static final String HINTESCAPED = "Hint: Check for unescaped characters.";
  public static final String CHARORIND = "Either characterSpec or indicators are allowed.";
  public static final String CHARANDSF = "Either characterSpec for field or subfields are allowed.";

  public InvalidMARCspecException(String message, String context) {
    super(createMessage(message, context));
  }

  private static String createMessage(String message, String context) {
    context = StringUtils.isNotBlank(context)
      ? String.format("%n%s\"%s\"", ARGUMENT, context)
      : null;

    return "Detected invalid " + message + context;
  }
}
