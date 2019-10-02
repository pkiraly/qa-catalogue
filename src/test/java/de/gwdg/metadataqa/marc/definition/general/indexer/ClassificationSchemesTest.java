package de.gwdg.metadataqa.marc.definition.general.indexer;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ClassificationSchemesTest {

  @Test
  public void testExisting() {
    ClassificationSchemes schemes = ClassificationSchemes.getInstance();
    assertEquals("other", schemes.resolve("Other scheme"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonExisting() {
    ClassificationSchemes schemes = ClassificationSchemes.getInstance();
    assertEquals("other", schemes.resolve("whatamicallit"));
  }
}
