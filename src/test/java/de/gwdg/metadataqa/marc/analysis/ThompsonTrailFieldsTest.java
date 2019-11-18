package de.gwdg.metadataqa.marc.analysis;

import org.junit.Test;

public class ThompsonTrailFieldsTest {

  @Test
  public void fields() {
    for (ThompsonTraillFields field : ThompsonTraillFields.values()) {
      System.err.println(field);
    }
  }
}
