package de.gwdg.metadataqa.marc.utils.pica;

import java.util.logging.Logger;

public class PicaTagDefinition {

  private static final Logger logger = Logger.getLogger(PicaTagDefinition.class.getCanonicalName());

  private final String pica3;
  private final String picaplus;
  private Boolean repeatable;
  private Boolean hasSheet;
  private final String description;

  public PicaTagDefinition(String pica3, String picaplus, boolean repeatable, boolean sheet, String description) {
    this.pica3 = pica3;
    this.picaplus = picaplus;
    this.repeatable = repeatable;
    this.hasSheet = sheet;
    this.description = description;
  }

  public PicaTagDefinition(String[] input) {
    this.pica3 = input[0];
    this.picaplus = input[1];
    this.description = input[4];
    parseRepeatable(input[2]);
    parseSheet(input[3]);
  }

  private void parseSheet(String input) {
    switch (input) {
      case "-": this.hasSheet = false; break;
      case "+": this.hasSheet = true; break;
      default: logger.severe("unhandled 'hasSheet' value: " + input + " (" + picaplus + ")");
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

  public String getPicaplus() {
    return picaplus;
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
}
