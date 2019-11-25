package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.cli.utils.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthoritiesStatistics {
  private Map<Schema, Integer> instances = new HashMap<>();
  private Map<Schema, Integer> records = new HashMap<>();
  private Map<Schema, Map<List<String>, Integer>> subfields = new HashMap<>();

  public AuthoritiesStatistics() {
  }

  public Map<Schema, Integer> getInstances() {
    return instances;
  }

  public Map<Schema, Integer> getRecords() {
    return records;
  }

  public Map<Schema, Map<List<String>, Integer>> getSubfields() {
    return subfields;
  }
}
