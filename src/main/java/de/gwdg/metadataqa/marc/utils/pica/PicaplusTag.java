package de.gwdg.metadataqa.marc.utils.pica;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicaplusTag {

  private static final Logger logger = Logger.getLogger(PicaplusTag.class.getCanonicalName());
  private static final Pattern rangePattern = Pattern.compile("^(\\d+)-(\\d+)$");
  private static final Pattern xPattern = Pattern.compile("^(\\d)X$");
  private static final Pattern numericPattern = Pattern.compile("^(\\d+)$");

  private String raw;
  private String tag;
  private String occurence = null;
  private OccurenceRage occurenceRange = null;

  public PicaplusTag(String tag) {
    this.raw = tag;
    parse(tag);
  }

  public boolean hasOccurrence() {
    return occurence != null;
  }

  public boolean hasOccurrenceRange() {
    return occurenceRange != null;
  }

  public String getRaw() {
    return raw;
  }

  public String getTag() {
    return tag;
  }

  public String getOccurence() {
    return occurence;
  }

  public OccurenceRage getOccurenceRange() {
    return occurenceRange;
  }

  public boolean validateOccurence(String otherOccurence) {
    if (!hasOccurrence()) {
      if (otherOccurence == null)
        return true;
      return false;
    } else {
      if (!hasOccurrenceRange()) {
        return occurence.equals(otherOccurence);
      } else {
        return occurenceRange.validate(otherOccurence);
      }
    }
  }

  private void parse(String picaplus) {
    if (!picaplus.contains("/")) {
      tag = picaplus;
    } else {
      String[] parts = picaplus.split("/");
      tag = parts[0];
      occurence = parts[1];
      if (occurence.contains("-")) {
        parseRangePattern();
      } else if (occurence.endsWith("X")) {
        parseXpattern();
      } else {
        if (!numericPattern.matcher(occurence).find()) {
          logger.severe("Error in picaplus: " + picaplus + ". Does not fit to the numeric pattern.");
        }
      }
    }
  }

  private void parseRangePattern() {
    Matcher matcher = rangePattern.matcher(occurence);
    if (matcher.find()) {
      String start = matcher.group(1);
      String end = matcher.group(2);
      if (start.length() == end.length()) {
        this.occurenceRange = new OccurenceRage(start.length(), Integer.parseInt(start), Integer.parseInt(end));
      } else {
        logger.severe("Error in picaplus: " + raw + ". Length of start and end are different.");
      }
    } else {
      logger.severe(String.format("Error in picaplus: %s (raw: %s). Does not fit to the range pattern.", occurence));
    }
  }

  private void parseXpattern() {
    Matcher matcher = xPattern.matcher(occurence);
    if (matcher.find()) {
      String base = matcher.group(1);
      this.occurenceRange = new OccurenceRage(2, Integer.parseInt(base + "0"), Integer.parseInt(base + "9"));
    } else {
      logger.severe("Error in picaplus: " + raw + ". Does not fit to the [0-9]X pattern.");
    }
  }
}
