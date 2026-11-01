package de.gwdg.metadataqa.marc.definition.general;

import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class LanguageCodesTest {

  @Test
  public void isValid() {
    LanguageCodes codes = LanguageCodes.getInstance();
    assertTrue(codes.isValid("aar"));
    assertFalse(codes.isValid("aarx"));
  }

  @Test
  public void isDeprecated() {
    LanguageCodes codes = LanguageCodes.getInstance();
    assertTrue(codes.isDeprecated("scc"));
    assertFalse(codes.isDeprecated("zul"));
    assertFalse(codes.isDeprecated("123"));
  }
}
