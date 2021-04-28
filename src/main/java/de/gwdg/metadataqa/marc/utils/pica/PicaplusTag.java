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
  private String occurrence = null;
  private OccurrenceRage occurrenceRage = null;

  public PicaplusTag(String tag) {
    this.raw = tag;
    parse(tag);
  }

  public boolean hasOccurrence() {
    return occurrence != null;
  }

  public boolean hasOccurrenceRange() {
    return occurrenceRage != null;
  }

  public String getRaw() {
    return raw;
  }

  public String getTag() {
    return tag;
  }

  public String getOccurrence() {
    return occurrence;
  }

  public OccurrenceRage getOccurrenceRage() {
    return occurrenceRage;
  }

  public boolean validateOccurrence(String otherOccurrence) {
    if (!hasOccurrence()) {
      if (otherOccurrence == null)
        return true;
      return false;
    } else {
      if (!hasOccurrenceRange()) {
        return occurrence.equals(otherOccurrence);
      } else {
        return occurrenceRage.validate(otherOccurrence);
      }
    }
  }

  private void parse(String picaplus) {
    if (!picaplus.contains("/")) {
      tag = picaplus;
    } else {
      String[] parts = picaplus.split("/");
      tag = parts[0];
      occurrence = parts[1];
      if (occurrence.contains("-")) {
        parseRangePattern();
      } else if (occurrence.endsWith("X")) {
        parseXpattern();
      } else {
        if (!numericPattern.matcher(occurrence).find()) {
          logger.severe("Error in picaplus: " + picaplus + ". Does not fit to the numeric pattern.");
        }
      }
    }
  }

  private void parseRangePattern() {
    Matcher matcher = rangePattern.matcher(occurrence);
    if (matcher.find()) {
      String start = matcher.group(1);
      String end = matcher.group(2);
      if (start.length() == end.length()) {
        this.occurrenceRage = new OccurrenceRage(start.length(), Integer.parseInt(start), Integer.parseInt(end));
      } else {
        logger.severe("Error in picaplus: " + raw + ". Length of start and end are different.");
      }
    } else {
      logger.severe(String.format(
        "Error in picaplus: %s (raw: %s). Does not fit to the range pattern.",
        occurrence, raw));
    }
  }

  private void parseXpattern() {
    Matcher matcher = xPattern.matcher(occurrence);
    if (matcher.find()) {
      String base = matcher.group(1);
      this.occurrenceRage = new OccurrenceRage(2, Integer.parseInt(base + "0"), Integer.parseInt(base + "9"));
    } else {
      logger.severe("Error in picaplus: " + raw + ". Does not fit to the [0-9]X pattern.");
    }
  }
}
