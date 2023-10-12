package de.gwdg.metadataqa.marc.definition.general;

import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class LanguageCodesTest {

  @Test
  public void testDefinition() {
    LanguageCodes codes = LanguageCodes.getInstance();
    assertTrue(codes.isValid("aar"));
    assertFalse(codes.isValid("aarx"));
  }
}
