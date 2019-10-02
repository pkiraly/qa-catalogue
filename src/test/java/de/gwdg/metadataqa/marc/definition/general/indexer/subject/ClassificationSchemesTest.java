package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.definition.general.indexer.subject.ClassificationSchemes;
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
