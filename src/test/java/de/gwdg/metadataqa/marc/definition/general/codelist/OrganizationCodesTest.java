package de.gwdg.metadataqa.marc.definition.general.codelist;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrganizationCodesTest {

  @Test
  public void testGetSubfields() {
    OrganizationCodes orgCodes = OrganizationCodes.getInstance();
    assertTrue(orgCodes.isValid("DE-627"));
  }
}