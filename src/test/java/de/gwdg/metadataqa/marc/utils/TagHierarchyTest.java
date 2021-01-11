package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
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

  @Test
  public void testIssueWith366() {
    TagHierarchy tagHierarchy = TagHierarchy.createFromPath("366$2", MarcVersion.MARC21);
    assertNotNull(tagHierarchy);
    assertEquals("Physical Description", tagHierarchy.getPackageLabel());
    assertEquals("Trade Availability Information", tagHierarchy.getTagLabel());
    assertEquals("Source of availability status code", tagHierarchy.getSubfieldLabel());

    tagHierarchy = TagHierarchy.createFromPath("366$c");
    assertNotNull(tagHierarchy);
    assertEquals("Physical Description", tagHierarchy.getPackageLabel());
    assertEquals("Trade Availability Information", tagHierarchy.getTagLabel());
    assertEquals("Availability status code", tagHierarchy.getSubfieldLabel());
  }

  @Test
  public void createFromPath_withGent() {
    TagHierarchy tagHierarchy = TagHierarchy.createFromPath("591$a", MarcVersion.GENT);
    assertNotNull(tagHierarchy);
    assertEquals("Locally defined tags of Gent", tagHierarchy.getPackageLabel());
    assertEquals("Locally defined field in Gent", tagHierarchy.getTagLabel());
    assertEquals("Value", tagHierarchy.getSubfieldLabel());
  }
}
