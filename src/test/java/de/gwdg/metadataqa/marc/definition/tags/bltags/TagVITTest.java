package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagVITTest extends BLTagTest {

  public TagVITTest() {
    super(TagVIT.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("b", "NP000097052", "c", "PX", "d", "(1913)", "e", "1913", "f", "1",
      "g", "1", "i", "1913", "j", "01/12", "k", "01/31");
    validField("b", "NP000460014", "c", "01", "d", "(1799:Sep. 5 - 1814:Mar.31)",
      "e", "1799", "f", "9", "g", "5", "i", "1799/1814", "j", "09/03", "k", "05/31");
    validField("e", "1523");
    validField("f", "11");
    validField("g", "31");
    validField("i", "1629");
    validField("i", "1629/1703");
    validField("j", "11");
    validField("j", "11/12");
    validField("k", "11");
    validField("k", "11/12");
    validField("s", "Supplement");
    validField("s", "");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Y");
    invalidField("a", "X");
    invalidField("e", "15");
    invalidField("e", "1423");
    invalidField("f", "14");
    invalidField("g", "41");
    invalidField("i", "13");
    invalidField("j", "13");
    invalidField("j", "01/13");
    invalidField("k", "01/32");
    invalidField("s", "Supplement.");
  }
}
