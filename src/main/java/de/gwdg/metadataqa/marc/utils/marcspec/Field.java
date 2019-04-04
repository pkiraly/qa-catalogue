package de.gwdg.metadataqa.marc.utils.marcspec;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Field {
  private String tag;
  private String indicator1;
  private String indicator2;
  private Integer charStart;
  private Integer charEnd;
  private Integer charLength;
  private Positions characterPositions;

  private Position startIndex;
  private Position endIndex;
  private Integer indexLength;
  private boolean hasWildchar = false;

  private List<SubSpec> subSpecs;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
    if (StringUtils.isNotBlank(tag) && tag.contains("."))
      hasWildchar = true;
  }

  public String getIndicator1() {
    return indicator1;
  }

  public boolean hasIndicator1() {
    return StringUtils.isNotBlank(indicator1);
  }

  public void setIndicator1(String indicator1) {
    this.indicator1 = indicator1;
  }

  public String getIndicator2() {
    return indicator2;
  }

  public boolean hasIndicator2() {
    return StringUtils.isNotBlank(indicator2);
  }

  public void setIndicator2(String indicator2) {
    this.indicator2 = indicator2;
  }

  public Integer getCharStart() {
    return charStart;
  }

  public void setCharStart(Integer charStart) {
    this.charStart = charStart;
  }

  public Integer getCharEnd() {
    return charEnd;
  }

  public void setCharEnd(Integer charEnd) {
    this.charEnd = charEnd;
  }

  public Integer getCharLength() {
    return charLength;
  }

  public void setCharLength(Integer charLength) {
    this.charLength = charLength;
  }

  public Position getStartIndex() {
    return startIndex;
  }

  public void setIndexStartEnd(Position indexStart, Position indexEnd) {
    this.startIndex = indexStart;
    this.endIndex = indexEnd;
  }

  public void setStartIndex(Position startIndex) {
    this.startIndex = startIndex;
  }

  public Position getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Position endIndex) {
    this.endIndex = endIndex;
  }

  public Integer getIndexLength() {
    return indexLength;
  }

  public void setIndexLength(Integer indexLength) {
    this.indexLength = indexLength;
  }

  public List<SubSpec> getSubSpecs() {
    return subSpecs;
  }

  public void setSubSpecs(List<SubSpec> subSpecs) {
    this.subSpecs = subSpecs;
  }

  public Positions getCharacterPositions() {
    return characterPositions;
  }

  public void setCharacterPositions(Positions characterPositions) {
    this.characterPositions = characterPositions;
  }

  public boolean hasWildchar() {
    return hasWildchar;
  }

  @Override
  public String toString() {
    return "Field{" +
      "tag='" + tag + '\'' +
      ", indicator1='" + indicator1 + '\'' +
      ", indicator2='" + indicator2 + '\'' +
      ", charStart=" + charStart +
      ", charEnd=" + charEnd +
      ", charLength=" + charLength +
      ", characterPositions=" + characterPositions +
      ", startIndex=" + startIndex +
      ", endIndex=" + endIndex +
      ", indexLength=" + indexLength +
      ", subSpecs=" + subSpecs +
      '}';
  }
}
