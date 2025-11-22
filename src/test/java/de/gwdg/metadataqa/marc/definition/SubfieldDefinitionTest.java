package de.gwdg.metadataqa.marc.definition;

import java.util.Arrays;

import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SubfieldDefinitionTest {

  public SubfieldDefinitionTest() {
  }

  @Test
  public void TestConstructor() {
    SubfieldDefinition subfield = new SubfieldDefinition("a", "Record control number", "NR");
    assertNotNull(subfield);
    assertEquals("a", subfield.getCode());
    assertEquals("NR", subfield.getCardinalityCode());
    assertEquals("Record control number", subfield.getLabel());
  }

  @Test
  public void testIncidator() {
    SubfieldDefinition subfield = new SubfieldDefinition("ind1", "Type of publisher number", "012345");
    assertNotNull(subfield);
    assertEquals("ind1", subfield.getCode());
    assertEquals("012345", subfield.getCardinalityCode());
    assertEquals("Type of publisher number", subfield.getLabel());
    assertEquals(
      Arrays.asList("0", "1", "2", "3", "4", "5"), 
      subfield.getAllowedCodes()
    );

    subfield = new SubfieldDefinition("ind1", "Type of publisher number", "b012345");
    assertEquals(
      Arrays.asList(" ", "0", "1", "2", "3", "4", "5"), 
      subfield.getAllowedCodes()
    );
  }

  @Test
  public void testBlankIncidator() {
    SubfieldDefinition subfield = new SubfieldDefinition("ind1", "Type of publisher number", "blank");
    assertEquals(
      Arrays.asList(" "), 
      subfield.getAllowedCodes()
    );
  }

  @Test
  public void testMixedBlankIncidator() {
    SubfieldDefinition subfield = new SubfieldDefinition("ind1", "Type of publisher number", "b7");
    assertEquals(
      Arrays.asList(" ", "7"), 
      subfield.getAllowedCodes()
    );
  }

}
