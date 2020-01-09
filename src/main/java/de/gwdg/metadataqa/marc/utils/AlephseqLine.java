package de.gwdg.metadataqa.marc.utils;

import java.util.regex.Pattern;

public class AlephseqLine {
  private static final String LDR = "LDR";
  private static final Pattern numericTag = Pattern.compile("^\\d\\d\\d$");
  private static final Pattern controlField = Pattern.compile("^00\\d$");

  private String recordID;
  private String tag;
  private String ind1;
  private String ind2;
  private String content;

  public AlephseqLine() {
  }

  public AlephseqLine(String raw) {
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
    return (isLeader() || isNumericTag());
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
    if (isLeader() || isControlField())
      return content.replace("^", " ");
    else
      return content.replace("$$", "$");
  }

  private void parse(String raw) {
    recordID = raw.substring(0, 9);
    tag = raw.substring(10, 13);
    ind1 = raw.substring(14, 15);
    ind2 = raw.substring(15, 16);
    content = raw.substring(18);
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
}
