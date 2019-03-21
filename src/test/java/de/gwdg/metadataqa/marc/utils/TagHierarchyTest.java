package de.gwdg.metadataqa.marc.utils;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class TagHierarchyTest {

  @Test
  public void testTagHierarchyExisting() {
    TagHierarchy tagHierarchy = TagHierarchy.createFromPath("245$a");
    assertNotNull(tagHierarchy);
    assertEquals("Title", tagHierarchy.getPackageLabel());
    assertEquals("Title Statement", tagHierarchy.getTagLabel());
    assertEquals("Title", tagHierarchy.getSubfieldLabel());
  }

  @Test
  public void testTagHierarchyNonExistingSubfield() {
    TagHierarchy tagHierarchy = TagHierarchy.createFromPath("245$x");
    assertNotNull(tagHierarchy);
    assertEquals("Title", tagHierarchy.getPackageLabel());
    assertEquals("Title Statement", tagHierarchy.getTagLabel());
    assertEquals("", tagHierarchy.getSubfieldLabel());
  }
}
