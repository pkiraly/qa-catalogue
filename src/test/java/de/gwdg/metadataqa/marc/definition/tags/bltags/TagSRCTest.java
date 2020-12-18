package de.gwdg.metadataqa.marc.definition.tags.bltags;

import org.junit.Test;

public class TagSRCTest extends BLTagTest {

  public TagSRCTest() {
    super(TagSRC.getInstance());
  }

  @Test
  public void testValidFields() {
    validField("a", "HSS01", "b", "bibmp13.20030902.2.ac");
    validField("a", "NETCAT", "b", "netcatprn.20110310");
    validField("a", "Kozmenko Books - Supplier", "b", "inbl12062");
    validField("a", "Ingrams");
    validField("a", "ALDL");
    validField("a", "EThOS", "b", "ethos.mds.20120401.mrc");
    validField("a", "WW1", "b", "Europeana_Collections_1914-1918_20131010_1");
  }

  @Test
  public void testInvalidFields() {
    invalidField("1", "a", "Y");
    invalidField("c", "a");
  }
}
