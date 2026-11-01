package de.gwdg.metadataqa.marc.analysis.contextual;

import de.gwdg.metadataqa.marc.cli.utils.Schema;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ContextualStatistics {
  private final Map<Schema, Integer> records = new LinkedHashMap<>();
  private final Map<Schema, Integer> instances = new LinkedHashMap<>();
  private final Map<Schema, Map<List<String>, Integer>> subfields = new LinkedHashMap<>();

  public Map<Schema, Integer> getInstances() {
    return instances;
  }
  public Map<Schema, Map<List<String>, Integer>> getSubfields() {
    return subfields;
  }

  public Map<Schema, Integer> getRecords() {
    return records;
  }
}
