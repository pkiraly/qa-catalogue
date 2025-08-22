package de.gwdg.metadataqa.marc.definition.general.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Split content into list of fixed length strings
 */
public class FixedLengthSplitter implements SubfieldContentSplitter {
  private int fixedLength = 3;

  public FixedLengthSplitter() {}

  public FixedLengthSplitter(int fixedLength) {
    this.fixedLength = fixedLength;
  }

  @Override
  public List<String> parse(String content) {
    List<String> result = new ArrayList<>();
    if (StringUtils.isBlank(content))
      return result;

    if (content.length() > fixedLength) {
      int contentLength = content.length();
      for (int i = 0; i < contentLength; i = i + fixedLength) {
        int end = i + fixedLength < contentLength ? i + fixedLength : contentLength;
        result.add(content.substring(i, end));
      }
    } else
      result.add(content);

    return result;
  }
}
