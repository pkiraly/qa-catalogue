package de.gwdg.metadataqa.marc.utils.alephseq;

import de.gwdg.metadataqa.marc.DataField;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class AlephseqLine {
  private static final Logger logger = Logger.getLogger(AlephseqLine.class.getCanonicalName());

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

  public AlephseqLine() {
  }

  public AlephseqLine(String raw) {
    parse(raw);
  }

  public AlephseqLine(String raw, int lineNumber) {
    this.lineNumber = lineNumber;
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
    // logger.info(lineNumber + ") length: " + raw.length());
    if (raw.length() < 18) {
      logger.warning(String.format("%d) short line (%d): '%s'", lineNumber, raw.length(), raw));
      valid = false;
    } else {
      recordID = raw.substring(0, 9);
      tag = raw.substring(10, 13);
      ind1 = raw.substring(13, 14);
      ind2 = raw.substring(14, 15);
      content = raw.substring(18);
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
}
