package de.gwdg.metadataqa.marc.definition.general.parser;

import java.util.List;

/**
 * Split content into a list
 */
public interface SubfieldContentSplitter {
  List<String> parse(String content);
}
