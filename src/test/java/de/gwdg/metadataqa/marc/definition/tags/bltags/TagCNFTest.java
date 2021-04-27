package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagCNFTest extends BLTagTest {

  public TagCNFTest() {
    super(TagCNF.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "Academy of Marketing Science (Annual Conference)", "d", "(1997 May :", "c", "Coral Gables, FL)");
    validField("a", "Database and expert systems applications (International conference)", "n", "(10th :", "d", "1999 Aug :", "c", "Florence, Italy)");
    validField("a", "Algebraic and combinatorial approaches to representation theory (Satellite conference)", "d", "(2010 Aug :", "c", "Bangalore, India)");
    validField("a", "Administrative Sciences Association of Canada.", "e", "Tourism and Hospitality Management Division (Annual conference)", "d", "(2012 May :", "c", "St John's, Canada)");
    validField("a", "Advanced materials engineering and technology (International conference)", "n", "(2nd :", "d", "2013 Nov :", "c", "Bandung, Indonesia)");
  }

  @Test
  public void testInvalidFields() {
    invalidField("3", "a", "t");
    invalidField("h", "B");
    invalidField("j", "20201236");
  }
}
