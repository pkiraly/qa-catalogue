package de.gwdg.metadataqa.marc.utils.pica;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicaTagDefinition {

  private static final Logger logger = Logger.getLogger(PicaTagDefinition.class.getCanonicalName());
  private static final Pattern rangePattern = Pattern.compile("^(\\d+)-(\\d+)$");

  private final String pica3;
  private Boolean repeatable;
  private Boolean hasSheet;
  private final String description;
  private PicaplusTag tag;

  public PicaTagDefinition(String pica3, String picaplus, boolean repeatable, boolean sheet, String description) {
    this.pica3 = pica3;
    this.repeatable = repeatable;
    this.hasSheet = sheet;
    this.description = description;
    tag = new PicaplusTag(picaplus);
  }

  public PicaTagDefinition(String[] input) {
    this.pica3 = input[0];
    tag = new PicaplusTag(input[1]);
    this.description = input[4];
    parseRepeatable(input[2]);
    parseSheet(input[3]);
  }

  public PicaplusTag getTag() {
    return tag;
  }

  private void parseSheet(String input) {
    switch (input) {
      case "":
      case "-": this.hasSheet = false; break;
      case "+": this.hasSheet = true; break;
      default: logger.severe(String.format("unhandled 'hasSheet' value: %s (%s)", input, tag.getRaw()));
    }
  }

  private void parseRepeatable(String input) {
    switch (input) {
      case "": this.repeatable = false; break;
      case "*": this.repeatable = true; break;
      default: logger.severe("unhandled 'repeatable' value: " + input);
    }
  }

  public String getPica3() {
    return pica3;
  }

  public boolean isRepeatable() {
    return repeatable;
  }

  public boolean isHasSheet() {
    return hasSheet;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "PicaTagDefinition{" +
      "pica3='" + pica3 + '\'' +
      ", picaplus='" + tag.getRaw() + '\'' +
      ", repeatable=" + repeatable +
      ", hasSheet=" + hasSheet +
      ", description='" + description + '\'' +
      '}';
  }
}
