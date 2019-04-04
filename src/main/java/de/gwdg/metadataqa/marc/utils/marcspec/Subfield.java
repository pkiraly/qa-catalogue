package de.gwdg.metadataqa.marc.utils.marcspec;

import java.util.ArrayList;
import java.util.List;

public class Subfield {
  private String tag;

  private Integer charStart;
  private Integer charEnd;
  private Integer charLength;
  private Positions characterPositions;

  private Position indexStart;
  private Position indexEnd;
  private Integer indexLength;
  private Positions indexPositions;

  private List<SubSpec> subSpecs = new ArrayList<>();

  public Subfield() {
  }

  public Subfield(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
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

  public Position getIndexStart() {
    return indexStart;
  }

  public void setIndexStart(Position indexStart) {
    this.indexStart = indexStart;
  }

  public Position getIndexEnd() {
    return indexEnd;
  }

  public void setIndexEnd(Position indexEnd) {
    this.indexEnd = indexEnd;
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

  public Positions getIndexPositions() {
    return indexPositions;
  }

  public void setIndexPositions(Positions indexPositions) {
    this.indexPositions = indexPositions;
  }

  public Positions getCharacterPositions() {
    return characterPositions;
  }

  public void setCharacterPositions(Positions characterPositions) {
    this.characterPositions = characterPositions;
  }

  public void addSubSpec(SubSpec subSpec) {
    subSpecs.add(subSpec);
  }

  @Override
  public String toString() {
    return "Subfield{" +
      "tag='" + tag + '\'' +
      ", charStart=" + charStart +
      ", charEnd=" + charEnd +
      ", charLength=" + charLength +
      ", characterPositions=" + characterPositions +
      ", indexStart=" + indexStart +
      ", indexEnd=" + indexEnd +
      ", indexLength=" + indexLength +
      ", indexPositions=" + indexPositions +
      ", subSpecs=" + subSpecs +
      '}';
  }
}
