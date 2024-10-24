package de.gwdg.metadataqa.marc.definition.general.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Split content into list of fixed length strings
 */
public class FixedLengthSplitter implements SubfieldContentSplitter {
  private int lengh = 3;

  public FixedLengthSplitter() {}

  public FixedLengthSplitter(int lengh) {
    this.lengh = lengh;
  }

  @Override
  public List<String> parse(String content) {
    List<String> result = new ArrayList<>();
    if (StringUtils.isBlank(content))
      return result;

    if (content.length() > lengh)
      for (int i = 0; i < content.length(); i = i + lengh)
        result.add(content.substring(i, i + lengh));
    else
      result.add(content);

    return result;
  }
}
