package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.utils.Counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorityStatistics {
  private Map<Schema, Integer> instances = new HashMap<>();
  private Map<Schema, Integer> records = new HashMap<>();
  private Map<Schema, Map<List<String>, Integer>> subfields = new HashMap<>();
  private Counter<AuthorityCategory> instancesPerCategories = new Counter();
  private Counter<AuthorityCategory> recordsPerCategories = new Counter();
  // private Map<AuthorityCategory, Integer> instancesPerCategories = new HashMap<>();
  // private Map<AuthorityCategory, Integer> recordsPerCategories = new HashMap<>();

  public AuthorityStatistics() {
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

  public Counter<AuthorityCategory> getInstancesPerCategories() {
    return instancesPerCategories;
  }

  public Counter<AuthorityCategory> getRecordsPerCategories() {
    return recordsPerCategories;
  }
}
