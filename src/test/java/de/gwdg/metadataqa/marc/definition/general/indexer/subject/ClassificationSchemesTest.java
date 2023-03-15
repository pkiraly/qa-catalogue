package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

public class ClassificationSchemesTest {

  @Test
  public void testExisting() {
    ClassificationSchemes schemes = ClassificationSchemes.getInstance();
    assertEquals("other", schemes.resolve("Other scheme"));
  }

  @Test
  public void testNonExisting() {
    ClassificationSchemes schemes = ClassificationSchemes.getInstance();
    assertThrows(IllegalArgumentException.class, () -> schemes.resolve("whatamicallit"));
  }
}
