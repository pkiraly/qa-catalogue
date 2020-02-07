package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.DataField;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicaLine {
  private static final Logger logger = Logger.getLogger(PicaLine.class.getCanonicalName());

  private static final Pattern LINE = Pattern.compile("^([0-2][0-9][0-9][A-Z@])(\\/([0-9][0-9]+))? (.*)$");
  private static final String LDR = "LDR";
  private static final Pattern numericTag = Pattern.compile("^\\d\\d\\d$");
  private static final Pattern controlField = Pattern.compile("^00\\d$");
  public static final String SEPARATOR = "ƒ";
  private int lineNumber = 0;

  private String recordID;
  private String tag;
  private String occurrence;
  private String content;
  private List<PicaSubfield> subfields;

  private boolean valid = true;

  public PicaLine() {
  }

  public PicaLine(String raw) {
    parse(raw);
  }

  public PicaLine(String raw, int lineNumber) {
    this.lineNumber = lineNumber;
    parse(raw);
  }

  public boolean isLeader() {
    return tag.equals(LDR);
  }

  public boolean isNumericTag() {
    return numericTag.matcher(tag).matches();
  }

  public boolean isControlField() {
    return controlField.matcher(tag).matches();
  }

  public boolean isValidTag() {
    return (isValid() && (isLeader() || isNumericTag()));
  }

  public String getRecordID() {
    return recordID;
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
    if (isLeader() || isControlField())
      return content.replace("^", " ");
    else
      return content.replace("$$", "$");
  }

  public String getRawContent() {
    return content;
  }

  private void parse(String raw) {
    // logger.info(lineNumber + ") length: " + raw.length());
    Matcher matcher = LINE.matcher(raw);
    if (matcher.matches()) {
      tag = matcher.group(1);
      occurrence = matcher.group(3);
      content = matcher.group(4);
      parseSubfields();
    } else {
      logger.warning("Unable to parse line: " + raw);
    }
  }

  private void parseSubfields() {
    subfields = new ArrayList<>();
    String[] parts = content.split(SEPARATOR);
    for (String part : parts) {
      if (StringUtils.isNotBlank(part)) {
        subfields.add(new PicaSubfield(part.substring(0, 1), part.substring(1)));
      }
    }
  }

  public boolean isValid() {
    return valid;
  }

  @Override
  public String toString() {
    return "AlephseqLine{" +
      "recordID='" + recordID + '\'' +
      ", tag='" + tag + '\'' +
      ", occurrence='" + occurrence + '\'' +
      ", subfields=" + subfields +
      '}';
  }

}
