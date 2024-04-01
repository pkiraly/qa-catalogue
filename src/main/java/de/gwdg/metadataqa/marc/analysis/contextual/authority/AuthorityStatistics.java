package de.gwdg.metadataqa.marc.analysis.contextual.authority;

import de.gwdg.metadataqa.marc.analysis.contextual.ContextualStatistics;
import de.gwdg.metadataqa.marc.utils.Counter;

public class AuthorityStatistics extends ContextualStatistics {
  private final Counter<AuthorityCategory> instancesPerCategories = new Counter<>();
  private final Counter<AuthorityCategory> recordsPerCategories = new Counter<>();

  public Counter<AuthorityCategory> getInstancesPerCategories() {
    return instancesPerCategories;
  }

  public Counter<AuthorityCategory> getRecordsPerCategories() {
    return recordsPerCategories;
  }
}
