package de.gwdg.metadataqa.marc.utils.alephseq;

import de.gwdg.metadataqa.marc.dao.DataField;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class AlephseqLine {
  private static final Logger logger = Logger.getLogger(AlephseqLine.class.getCanonicalName());

  public enum TYPE{WITH_L, WITHOUT_L}

  private static final String LDR = "LDR";
  private static final Pattern numericTag = Pattern.compile("^\\d\\d\\d$");
  private static final Pattern controlField = Pattern.compile("^00\\d$");
  public static final String SEPARATOR = "\\$\\$";
  private int lineNumber = 0;

  private String recordID;
  private String tag;
  private String ind1;
  private String ind2;
  private String content;
  private boolean valid = true;
  private TYPE type = TYPE.WITH_L;
  private Integer contentPosition;

  public AlephseqLine() {
  }

  public AlephseqLine(String raw) {
    parse(raw);
  }

  public AlephseqLine(String raw, TYPE type) {
    this.type = type;
    parse(raw);
  }

  public AlephseqLine(String raw, int lineNumber) {
    this.lineNumber = lineNumber;
    parse(raw);
  }

  public AlephseqLine(String raw, int lineNumber, TYPE type) {
    this.lineNumber = lineNumber;
    this.type = type;
    parse(raw);
  }

  public boolean isLeader() {
    if (tag == null)
      return false;
    return tag.equals(LDR);
  }

  public boolean isNumericTag() {
    if (tag == null)
      return false;

    return numericTag.matcher(tag).matches();
  }

  public boolean isControlField() {
    if (tag == null)
      return false;

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

  public String getInd1() {
    return ind1;
  }

  public String getInd2() {
    return ind2;
  }

  public String getContent() {
    if (content == null)
      return content;

    if (isLeader() || isControlField())
      return content.replace("^", " ");
    else
      return content.replace("$$", "$");
  }

  public String getRawContent() {
    return content;
  }

  private void parse(String raw) {
    if (raw.length() < 18) {
      logger.log(Level.WARNING, "{0}) short line ({1}): \"{2}\"", new Object[]{lineNumber, raw.length(), raw});
      valid = false;
    } else {
      String[] parts = raw.split(" ", 2);
      recordID = parts[0];
      tag = parts[1].substring(0, 3);
      ind1 = parts[1].substring(3, 4);
      ind2 = parts[1].substring(4, 5);
      content = parts[1].substring(getContentPosition());
    }
  }

  public List<String[]> parseSubfields() {
    List<String[]> subfields = new ArrayList<>();
    String[] segments = content.split(SEPARATOR);
    for (String segment : segments) {
      if (StringUtils.isNotBlank(segment))
        subfields.add(new String[]{segment.substring(0, 1), segment.substring(1)});
    }
    return subfields;
  }

  public boolean isValid() {
    return valid;
  }

  @Override
  public String toString() {
    return "AlephseqLine{" +
      "recordID='" + recordID + '\'' +
      ", tag='" + tag + '\'' +
      ", ind1='" + ind1 + '\'' +
      ", ind2='" + ind2 + '\'' +
      ", content='" + getContent() + '\'' +
      '}';
  }

  public List<String[]> getSubfields() {
    return DataField.parseSubfields(getContent());
  }

  private int getContentPosition() {
    if (contentPosition == null)
      contentPosition = type.equals(TYPE.WITH_L) ? 8 : 6;
    return contentPosition;
  }
}
